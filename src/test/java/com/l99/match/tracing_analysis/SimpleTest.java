package com.l99.match.tracing_analysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.l99.match.tracing_analysis.service.impl.DataSourceServiceImpl;
import com.l99.match.tracing_analysis.utils.DataSourceUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleTest {
    public static void main(String[] args) throws Exception {
        Map<String, Set<String>> map = new HashMap<>();
        File file = new File("D:\\ChromeDownload\\trace1_data.tar\\trace1_data\\c074d0a90cd607b.data");
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] col = line.split("\\|");
                Set<String> set = map.get(col[0]);
                if (set == null) {
                    set = new HashSet<>();
                    map.put(col[0], set);
                }
                set.add(line);
            }

            Set<String> set = map.get("c074d0a90cd607b");
            String spans = set.stream().sorted(
                    Comparator.comparing(SimpleTest::getStartTime)).collect(Collectors.joining("\n"));
            spans = spans + "\n";
            System.out.println(DataSourceUtils.MD5(spans));
        }
    }

    private static long getStartTime(String span) {
        if (span != null) {
            String[] cols = span.split("\\|");
            if (cols.length > 8) {
                try {
                    return Long.parseLong(cols[1]);
                } catch (Exception e) {

                }
            }
        }
        return -1;
    }
}
