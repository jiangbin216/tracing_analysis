package com.l99.match.tracing_analysis.utils;

import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class WebUtils {

    static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        restTemplate = new RestTemplate(factory);
    }

    public static <T> T get(String url, Class<T> returnClass) {
        T result = restTemplate.getForObject(url, returnClass);
        return result;
    }

    public static <T> T postJsonData(String url, @Nullable String jsonData, Class<T> returnClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
        T result = restTemplate.postForObject(url, request, returnClass);
        return result;
    }

    public static <T> T postData(String url, MultiValueMap<String, String> data, Class<T> returnClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(data, headers);
        T result = restTemplate.postForObject(url, requestEntity, returnClass);
        return result;
    }

    public static InputStream getInputStream(String url) {
        ResponseEntity<Resource> entity = restTemplate.getForEntity(url, Resource.class);
        try {
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                return entity.getBody().getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
