package com.l99.match.tracing_analysis.service;

import com.l99.match.tracing_analysis.common.CommonResult;

import java.util.List;

public interface IClientFilterService {

    CommonResult process();

    CommonResult searchSpan(List<String> traceId);
}
