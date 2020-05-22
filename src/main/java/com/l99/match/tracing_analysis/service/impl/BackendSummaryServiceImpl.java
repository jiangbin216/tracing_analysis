package com.l99.match.tracing_analysis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.l99.match.tracing_analysis.common.CommonResult;
import com.l99.match.tracing_analysis.common.ErrorSpanHolder;
import com.l99.match.tracing_analysis.common.FinishMark;
import com.l99.match.tracing_analysis.constant.DataSourceConstant;
import com.l99.match.tracing_analysis.service.IBackendSummaryService;
import com.l99.match.tracing_analysis.service.IDataSourceService;
import com.l99.match.tracing_analysis.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackendSummaryServiceImpl implements IBackendSummaryService {

    private static final Logger log = LoggerFactory.getLogger(BackendSummaryServiceImpl.class);

    @Autowired
    IDataSourceService dataSourceService;

    @Override
    public CommonResult finished() {
        boolean result = FinishMark.addFinishedProcess();
        if (!result) {
            return CommonResult.failure("The method was called more than the predetermined value: 2");
        }
        if (FinishMark.hasFinished()) {
            log.info("The process has been completed filtering, began to summarize and upload");
            dataSourceService.aggregateDataAndUpload();
        }
        return CommonResult.success();
    }

    @Override
    public CommonResult collect(List<String> datas) {
        if (datas == null || datas.isEmpty()) {
            log.warn("no error span");
            return CommonResult.success();
        }

        String traceId = datas.get(0).split(DataSourceConstant.SPLIT)[0];
        ErrorSpanHolder.putAll(traceId, datas);
//        // 获取另一台机器的数据
//        String anotherClientPort;
//        if (DataSourceConstant.CLIENT_PROCESS_PORT1.equals(sourcePort)) {
//            anotherClientPort = DataSourceConstant.CLIENT_PROCESS_PORT2;
//        } else {
//            anotherClientPort = DataSourceConstant.CLIENT_PROCESS_PORT1;
//        }
//        log.info("receive error traceId: " + traceId + "from port: " + sourcePort + ",now require all span from another port: " + anotherClientPort);
//        CommonResult commonResult = WebUtils.get(String.format("http://localhost:%s/filter/%s", anotherClientPort, traceId), CommonResult.class);
//        if (commonResult.getData() != null) {
//            log.info("receive span from anotherPort: " + anotherClientPort + ",the span list:\n" + JSONObject.toJSONString(commonResult.getData()));
//            ErrorSpanHolder.putAll(traceId, (List<String>) commonResult.getData());
//        }
        return CommonResult.success();
    }
}
