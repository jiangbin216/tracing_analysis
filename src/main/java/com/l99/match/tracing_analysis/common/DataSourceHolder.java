package com.l99.match.tracing_analysis.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourceHolder {

    // key 为 traceId，value 为对应的 span
    private static Map<String, Set<String>> holder = new ConcurrentHashMap<>();


    // 存储待过滤的 traceId
    private static Set<String> filterTraceIdSet = new HashSet<>();


    public static Set<String> getHolder(String key) {
        return holder.get(key);
    }

    public static void putHolder(String key, String value) {
        Set<String> set = holder.get(key);
        if (set == null) {
            set = new HashSet<>();
            holder.put(key, set);
        }
        set.add(value);
    }

    /**
     * 删除并返回删除的集合
     *
     * @param key
     * @return
     */
    public static Set<String> removeHolder(String key) {
        Set<String> spanSet = holder.remove(key);
        return spanSet;
    }

    public static void addFilterTraceIdSet(String value) {
        filterTraceIdSet.add(value);
    }

    public static boolean containedByFilterTraceIdSet(String key) {
        return filterTraceIdSet.contains(key);
    }
}
