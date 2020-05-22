package com.l99.match.tracing_analysis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.l99.match.tracing_analysis.common.*;
import com.l99.match.tracing_analysis.constant.DataSourceConstant;
import com.l99.match.tracing_analysis.service.IDataSourceService;
import com.l99.match.tracing_analysis.service.IClientFilterService;
import com.l99.match.tracing_analysis.utils.DataSourceUtils;
import com.l99.match.tracing_analysis.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class DataSourceServiceImpl implements IDataSourceService {

    private static final Logger log = LoggerFactory.getLogger(DataSourceServiceImpl.class);

    @Override
    @Async
    public void process() {
        AbstractDataSourceProcess abstractDataSourceProcess = new TestDataSourceProcess();
        abstractDataSourceProcess.process();
    }

    @Override
    @Async
    public void aggregateDataAndUpload() {
        // merge error span
        Map<String, Set<String>> errorSpanMap = ErrorSpanHolder.getAll();

        // get all span from client
        Set<String> traceIdList = errorSpanMap.keySet();
        String traceIds = JSONObject.toJSONString(traceIdList);
        getErrorSpan(traceIds);

        Map<String, String> TRACE_CHUCKSUM_MAP = new ConcurrentHashMap<>();
        errorSpanMap = ErrorSpanHolder.getAll();
        for (Map.Entry<String, Set<String>> entry : errorSpanMap.entrySet()) {
            String traceId = entry.getKey();
            Set<String> spanSet = entry.getValue();
            // order span with startTime
            String spans = spanSet.stream().sorted(
                    Comparator.comparing(DataSourceServiceImpl::getStartTime)).collect(Collectors.joining("\n"));
            spans = spans + "\n";
            // output all span to check
            log.info("traceId: " + traceId + ",value:\n" + spans);
            TRACE_CHUCKSUM_MAP.put(traceId, DataSourceUtils.MD5(spans));
        }
        String result = JSONObject.toJSONString(TRACE_CHUCKSUM_MAP);
        MultiValueMap<String, String> resultMap = new LinkedMultiValueMap<>(2);
        resultMap.add("result", result);
        log.info("ready send result: " + result);
        // send result
        WebUtils.postData(String.format(DataSourceConstant.FINISHED_ADDRESS, DataSourceUtils.dataSourcePort), resultMap, String.class);
    }


    private void getErrorSpan(String traceIds) {
        CommonResult commonResult1 = WebUtils.postJsonData(String.format("http://localhost:%s/filter", DataSourceConstant.CLIENT_PROCESS_PORT1), traceIds, CommonResult.class);
        CommonResult commonResult2 = WebUtils.postJsonData(String.format("http://localhost:%s/filter", DataSourceConstant.CLIENT_PROCESS_PORT2), traceIds, CommonResult.class);
        if (commonResult1.getData() != null) {
            putErrorSpan((Map<String, List<String>>) commonResult1.getData());
        }
        if (commonResult2.getData() != null) {
            putErrorSpan((Map<String, List<String>>) commonResult2.getData());
        }
    }

    private void putErrorSpan(Map<String, List<String>> spanMap) {
        for (Map.Entry<String, List<String>> entry : spanMap.entrySet()) {
            ErrorSpanHolder.putAll(entry.getKey(), entry.getValue());
        }
    }


    private static long getStartTime(String span) {
        if (span != null) {
            String[] cols = span.split("\\|");
            if (cols.length > 8) {
                try {
                    return Long.parseLong(cols[1]);
                } catch (Exception e) {

                }
            }
        }
        return -1;
    }


}
