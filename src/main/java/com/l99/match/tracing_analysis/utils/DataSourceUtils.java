package com.l99.match.tracing_analysis.utils;

import com.l99.match.tracing_analysis.constant.DataSourceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;


/**
 * 数据源工具类
 */
public class DataSourceUtils {

    private static final Logger log = LoggerFactory.getLogger(DataSourceUtils.class);

    // 远程数据端口号
    public static Integer dataSourcePort;

    /**
     * 切割字符串
     *
     * @param span
     * @return
     */
    public static String[] split(String span) {
        return span.split(DataSourceConstant.SPLIT);
    }

    // fill the data source port
    public static void fillPort(Integer port) {
        log.info("fill port: " + port);
        dataSourcePort = port;
    }

    public static boolean needFilter(String tags) {
        if (!StringUtils.isEmpty(tags)) {
            if (tags.contains(DataSourceConstant.FILTER_TAGS_2)) {
                return true;
            } else if (tags.contains("http.status_code=") && tags.indexOf(DataSourceConstant.FILTER_TAGS_1) < 0) {
                return true;
            }
        }
        return false;
    }

    public static String getCurrentPort() {
        return System.getProperty("server.port", "8080");
    }

    public static boolean isClientProcess() {
        String port = getCurrentPort();
        if (DataSourceConstant.CLIENT_PROCESS_PORT1.equals(port) ||
                DataSourceConstant.CLIENT_PROCESS_PORT2.equals(port)) {
            return true;
        }
        return false;
    }

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
