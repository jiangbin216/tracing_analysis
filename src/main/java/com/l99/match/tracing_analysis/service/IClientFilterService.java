package com.l99.match.tracing_analysis.service;

import com.l99.match.tracing_analysis.common.CommonResult;
import com.l99.match.tracing_analysis.pojo.Port;

public interface IClientFilterService {

    CommonResult process();

    CommonResult searchSpan(String traceId);
}
