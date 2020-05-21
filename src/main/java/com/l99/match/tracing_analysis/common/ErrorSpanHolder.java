package com.l99.match.tracing_analysis.common;

import java.util.*;

/**
 * 后台汇总程序使用
 */
public class ErrorSpanHolder {

    private static Map<String, Set<String>> SPAN_MAP = new HashMap<>(1000);

    public static void put(String key, String value) {
        Set<String> spanSet = SPAN_MAP.get(key);
        if (spanSet == null) {
            spanSet = new HashSet<>();
            SPAN_MAP.put(key, spanSet);
        }
        spanSet.add(value);
    }

    public static void putAll(String key, List<String> list) {
        Set<String> spanSet = SPAN_MAP.get(key);
        if (spanSet == null) {
            spanSet = new HashSet<>();
            SPAN_MAP.put(key, spanSet);
        }
        spanSet.addAll(list);
    }

    public static Map<String, Set<String>> getAll() {
        return SPAN_MAP;
    }

}
