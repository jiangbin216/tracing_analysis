package com.l99.match.tracing_analysis.pojo;


import java.io.Serializable;

/**
 * 端口号
 */
public class Port implements Serializable {

    private static final long serialVersionUID = -6787283191648549584L;

    private String dataPort;

    public String getDataPort() {
        return dataPort;
    }

    public void setDataPort(String dataPort) {
        this.dataPort = dataPort;
    }
}
