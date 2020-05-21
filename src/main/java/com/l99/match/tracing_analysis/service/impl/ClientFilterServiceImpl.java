package com.l99.match.tracing_analysis.service.impl;

import com.l99.match.tracing_analysis.common.CommonResult;
import com.l99.match.tracing_analysis.common.DataSourceHolder;
import com.l99.match.tracing_analysis.pojo.Port;
import com.l99.match.tracing_analysis.service.IDataSourceService;
import com.l99.match.tracing_analysis.service.IClientFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ClientFilterServiceImpl implements IClientFilterService {

    private static final Logger log = LoggerFactory.getLogger(ClientFilterServiceImpl.class);
    @Autowired
    IDataSourceService dataSourceService;

    @Override
    public CommonResult process() {
        log.info("start process data");
        // pull data asynchronously
        dataSourceService.process();
        return CommonResult.success();
    }

    @Override
    public CommonResult searchSpan(String traceId) {
        // add error span traceId to cache
        DataSourceHolder.addFilterTraceIdSet(traceId);
        // send error span and remove from cache
        Set<String> spanSet = DataSourceHolder.removeHolder(traceId);
        return CommonResult.success(spanSet);
    }
}
