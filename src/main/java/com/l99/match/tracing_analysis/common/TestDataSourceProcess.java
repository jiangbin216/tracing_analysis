package com.l99.match.tracing_analysis.common;

import com.l99.match.tracing_analysis.constant.DataSourceConstant;
import com.l99.match.tracing_analysis.utils.DataSourceUtils;
import com.l99.match.tracing_analysis.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

public class TestDataSourceProcess extends AbstractDataSourceProcess {

    private static final Logger log = LoggerFactory.getLogger(TestDataSourceProcess.class);

    @Override
    public void getData() {
        String filePath;
        if (DataSourceConstant.CLIENT_PROCESS_PORT1.equals(System.getProperty("server.port", "8080"))) {
            filePath = "D:\\ChromeDownload\\trace1_data.tar\\trace1_data\\trace1.data";
        } else {
            filePath = "D:\\ChromeDownload\\trace2_data.tar\\trace2_data\\trace2.data";
        }

        // 测试处理从文件获取数据
        File file = new File(filePath);
        log.info("filePath: " + filePath);
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bf.readLine()) != null) {
                filterData(line);
            }
            log.info("call finish");
            WebUtils.get("http://localhost:8002/finish", CommonResult.class);
        } catch (IOException e) {

        }
    }
}
