package com.l99.match.tracing_analysis.controller;

import com.l99.match.tracing_analysis.common.CommonResult;
import com.l99.match.tracing_analysis.service.IClientFilterService;
import com.l99.match.tracing_analysis.utils.DataSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommonController {

    @Autowired
    IClientFilterService filterService;

    @GetMapping(value = "/ready")
    public String ready() {
        return "succ";
    }


    @RequestMapping("/setParameter")
    public String setParamter(@RequestParam Integer port) {
        DataSourceUtils.fillPort(port);
        if (DataSourceUtils.isClientProcess()) {
            filterService.process();
        }
        return "succ";
    }
}
