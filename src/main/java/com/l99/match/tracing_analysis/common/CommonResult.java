package com.l99.match.tracing_analysis.common;

import java.io.Serializable;

public class CommonResult implements Serializable {

    private static final long serialVersionUID = -6310505316175743561L;

    /**
     * 错误信息 正确返回空字符串
     */
    private String message = "";

    /**
     * 返回值对象
     */
    private Object data;

    /**
     * 空构造函数
     */
    public CommonResult() {
    }

    /**
     * 正确响应构造函数
     *
     * @param data
     */
    public CommonResult(Object data) {
        this.data = data;
    }

    /**
     * @param errorMessage
     * @param data
     */
    public CommonResult(String errorMessage, Object data) {
        this.message = errorMessage;
        this.data = data;
    }


    /**
     * 空响应
     *
     * @return
     */
    public static CommonResult success() {
        return new CommonResult();
    }

    /**
     * 带响应的成功请求
     *
     * @param data
     * @return
     */
    public static CommonResult success(Object data) {
        return new CommonResult(data);
    }

    /**
     * 错误响应
     *
     * @param errorMessage
     * @return
     */
    public static CommonResult failure(String errorMessage) {
        return new CommonResult(errorMessage, null);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
