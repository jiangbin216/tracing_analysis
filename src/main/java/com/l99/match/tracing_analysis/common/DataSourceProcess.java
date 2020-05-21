package com.l99.match.tracing_analysis.common;

import com.l99.match.tracing_analysis.constant.DataSourceConstant;
import com.l99.match.tracing_analysis.utils.DataSourceUtils;
import com.l99.match.tracing_analysis.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataSourceProcess extends AbstractDataSourceProcess {

    private static final Logger log = LoggerFactory.getLogger(DataSourceProcess.class);

    @Override
    public void getData() {
        String dataUrl;
        if (DataSourceConstant.CLIENT_PROCESS_PORT1.equals(System.getProperty("server.port", "8080"))) {
            dataUrl = String.format(DataSourceConstant.TRACE_1, DataSourceUtils.dataSourcePort);
        } else {
            dataUrl = String.format(DataSourceConstant.TRACE_2, DataSourceUtils.dataSourcePort);
        }
        log.info("dataUrl: " + dataUrl);

        // 连接获取数据
        InputStream input = WebUtils.getInputStream(dataUrl);
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));
        String line;
        try {
            while ((line = bf.readLine()) != null) {
                filterData(line);
            }
        } catch (IOException e) {
            log.warn("fail to process data", e);
        }
    }
}
