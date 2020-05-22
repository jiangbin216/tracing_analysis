package com.l99.match.tracing_analysis.controller;

import com.l99.match.tracing_analysis.common.CommonResult;
import com.l99.match.tracing_analysis.common.FinishMark;
import com.l99.match.tracing_analysis.pojo.Port;
import com.l99.match.tracing_analysis.service.IDataSourceService;
import com.l99.match.tracing_analysis.service.IClientFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据过滤
 */
@RestController
public class ClientFilterController {

    private static final Logger log = LoggerFactory.getLogger(ClientFilterController.class);

    @Autowired
    IClientFilterService filterService;

    @Autowired
    IDataSourceService dataSourceService;

    /**
     * 接收后台汇总的traceId，发送本地存在的缓存
     */
    @PostMapping("/filter")
    public Object getSpan(@RequestBody List<String> traceId) {
        log.info("traceId: " + traceId);
        return filterService.searchSpan(traceId);
    }
}
