package com.book.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by hou on 02/03/2017.
 */
public class ApiResponse implements Serializable {
    private int code;
    private String message;
    private Object data;

    public ApiResponse(ApiResult x) {
        this.code = x.getCode();
        this.message = x.getDetail();
        this.data = new HashMap();
    }

    public ApiResponse(ApiResult rType, Object data) {
        this.code = rType.getCode();
        this.message = rType.getDetail();
        if (data == null) {
            data = new HashMap();
        }
        this.data = data;
    }

    public static ApiResponse getDefaultResponse() {
        return new ApiResponse(ApiResult.SUCCESS);
    }

    public static ApiResponse getDefaultResponse(Object data) {
        return new ApiResponse(ApiResult.SUCCESS, data);
    }

    public static ApiResponse getResponse(ApiResult result) {
        return new ApiResponse(result);
    }

    public static ApiResponse getResponse(ApiResult rType, Object data) {
        return new ApiResponse(rType, data);
    }

    @JsonProperty(value = "code")
    public int getCode() {
        return code;
    }

    @JsonProperty(value = "message")
    public String getMessage() {
        return message;
    }

    public ApiResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse setCode(int code) {
        this.code = code;
        return this;
    }

    @JsonProperty(value = "data")
    public Object getData() {
        if (this.data == null) {
            this.data = new HashMap();
        }
        return this.data;
    }

    public ApiResponse setData(Object data) {
        if (data == null) {
            data = new HashMap();
        }
        this.data = data;
        return this;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ApiResponse [code=").append(this.code).append(", message=").append(this.message).append(", data=").append(this.data).append("]");
        return builder.toString();
    }

}
