package com.l99.match.tracing_analysis.constant;

public class DataSourceConstant {

    public static final String CLIENT_PROCESS_PORT1 = "8000";

    public static final String CLIENT_PROCESS_PORT2 = "8001";

    public static final String BACKEND_PROCESS_PORT1 = "8002";

    public static final String SPLIT = "\\|";

    public static final String TRACE_1 = "http://localhost:%s/trace1.data";

    public static final String TRACE_2 = "http://localhost:%s/trace2.data";

    public static final String FINISHED_ADDRESS = "http://localhost:%s/api/finished";

    public static final String SUMMARY_ADDRESS = "http://localhost:" + BACKEND_PROCESS_PORT1 + "/data?sourcePort=%s";

    public static final String FILTER_TAGS_1 = "http.status_code=200";

    public static final String FILTER_TAGS_2 = "error=1";

    public static int PROCESS_COUNT = 2;
}
