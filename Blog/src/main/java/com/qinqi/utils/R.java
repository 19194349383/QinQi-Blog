package com.qinqi.utils;

/*
 *   通用返回结果类
 * */

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    //编码：1成功，0和其他都是失败
    private Integer code;

    //信息
    private String msg;

    //数据
    private T date;


    //响应成功
    public static <T> R<T> success(T object) {

        R<T> result = new R<>();
        result.date = object;
        result.code = 200;
        result.msg="操作成功";
        return result;

    }

    // 响应失败
    public static <T> R<T> error(String msg) {

        R<T> result = new R<>();
        result.msg = msg;
        result.code = 0;
        return result;

    }


}
