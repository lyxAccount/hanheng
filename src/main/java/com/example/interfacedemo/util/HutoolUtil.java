package com.example.interfacedemo.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Date: 2020/11/6
 */
public class HutoolUtil {
    /**
     * post请求 传入map参数，可上传文件
     *
     * @param url
     * @param paramMap
     * @param timeout
     * @date: 2020/11/6
     * @return: java.lang.String
     */
    public static String postByMap(String url, Map<String, Object> paramMap, Map<String, List<String>> headerMap, int timeout) {
        String result = "";
        try {
            result = HttpRequest.post(url)
                    .header(headerMap)
                    .form(paramMap)//表单内容
                    .timeout(timeout)//超时，毫秒
                    .execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            Console.log(result);
        }
        return result;
    }

    /**
     * post请求 传入map参数，可上传文件
     *
     * @param url
     * @param paramMap
     * @param timeout
     * @date: 2020/11/6
     * @return: java.lang.String
     */
    public static String getByMap(String url, Map<String, Object> paramMap, Map<String, List<String>> headerMap, int timeout) {
        String result = "";
        try {
            result = HttpRequest.get(url)
                    .header(headerMap)//头信息，多个头信息多次调用此方法即可
                    .form(paramMap)//表单内容
                    .timeout(timeout)//超时，毫秒
                    .execute().body();
            Console.log(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * post请求 传入body参数json
     *
     * @param url
     * @param json
     * @param timeout
     * @date: 2020/11/6
     * @return: java.lang.String
     */
    public static String postByJson(String url, String json, int timeout) {
        String result = "";
        try {
            result = HttpRequest.post(url)
                    .body(json)
                    .timeout(timeout)
                    .execute().body();
            Console.log(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 【DateUtil】格式化日期输出：yyyy-MM-dd
     *
     * @param date
     * @date: 2020/11/16
     * @return: java.lang.String
     */
    public static String formatDate(Date date) {
        return DateUtil.formatDate(date);
    }

    /**
     * 【DateUtil】格式化时间输出：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @date: 2020/11/16
     * @return: java.lang.String
     */
    public static String formatDateTime(Date date) {
        return DateUtil.formatDateTime(date);
    }

    /**
     * 【DateUtil】 字符串转日期
     *
     * @param date
     * @param format
     * @date: 2021/1/12
     * @return: cn.hutool.core.date.DateTime
     */
    public static DateTime formatDateStr(String date, String format) {
        DateTime parse = DateUtil.parse(date, format);
        return parse;
    }



    /**
     * 【DateUtil】当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @param
     * @date: 2020/11/18
     * @return: java.lang.String
     */
    public static String getDateNow() {
        String now = DateUtil.now();
        return now;
    }

    /**
     * 【NumberUtil】是否为整数
     *
     * @param str
     * @date: 2020/11/19
     * @return: boolean
     */
    public static boolean isInteger(String str) {
        return NumberUtil.isInteger(str);
    }

}
