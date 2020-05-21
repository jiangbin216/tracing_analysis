package com.l99.match.tracing_analysis.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerPortConfig {

    @Value("${server.port}")
    public String port;
}
