package com.l99.match.tracing_analysis.controller;

import com.alibaba.fastjson.JSONObject;
import com.l99.match.tracing_analysis.common.CommonResult;
import com.l99.match.tracing_analysis.service.IBackendSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据汇总
 */
@RestController
public class BackendSummaryController {

    private static final Logger log = LoggerFactory.getLogger(BackendSummaryController.class);

    @Autowired
    IBackendSummaryService summaryService;

    @PostMapping("/data")
    public Object collectTraceId(@RequestBody List<String> datas, @RequestParam String sourcePort) {
        log.info(String.format("collectTraceId had called,traceIdList: %s", JSONObject.toJSONString(datas)));
        return summaryService.collect(datas,sourcePort);
    }

    @GetMapping("/finish")
    public Object finished() {
        log.info("finished has called by process");
        return summaryService.finished();
    }
}
