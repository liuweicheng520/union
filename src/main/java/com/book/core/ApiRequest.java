package com.book.core;

import lombok.Data;

/**
 * Created by hou on 02/03/2017.
 */
@Data
public class ApiRequest {

    public final static String DATA = "data";

    private String data;// 接口请求参数

}
