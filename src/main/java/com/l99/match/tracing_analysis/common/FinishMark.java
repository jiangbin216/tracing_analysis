package com.l99.match.tracing_analysis.common;

import com.l99.match.tracing_analysis.service.impl.BackendSummaryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FinishMark {

    private static final Logger log = LoggerFactory.getLogger(BackendSummaryServiceImpl.class);

    // FINISH_PROCESS_COUNT will add one, when process call finish();
    public static volatile Integer FINISH_PROCESS_COUNT = 0;

    public static int PROCESS_COUNT = 2;

    public static boolean hasFinished() {
        return FINISH_PROCESS_COUNT == PROCESS_COUNT;
    }

    public static boolean addFinishedProcess() {
        FINISH_PROCESS_COUNT++;
        if (FINISH_PROCESS_COUNT > PROCESS_COUNT) {
            log.warn("The finished process count is illegal ,current count is:" + FINISH_PROCESS_COUNT);
            return false;
        }
        return true;
    }
}
