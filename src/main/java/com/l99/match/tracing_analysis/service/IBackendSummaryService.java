package com.l99.match.tracing_analysis.service;

import com.l99.match.tracing_analysis.common.CommonResult;

import java.util.List;

public interface IBackendSummaryService {

    CommonResult collect(List<String> datas);

    CommonResult finished();
}
