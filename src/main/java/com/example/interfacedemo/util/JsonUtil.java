package com.example.interfacedemo.util;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {


    /***
     * 字符串转对象
     * @param benstr
     * @param class1
     * @return
     */
    public static Object StrToBean(String benstr, Class<?> class1) {
        JSONObject jsonobject = JSONObject.fromObject(benstr);
        Object object = JSONObject.toBean(jsonobject, class1);
        return object;
    }

    /***
     * 阿里巴巴字符串转对象
     * @param benstr
     * @param class1
     * @return
     */
    public static Object AlbbStrToBean(String benstr, Class<?> class1) {
        Object object = com.alibaba.fastjson.JSONObject.parseObject(benstr,class1);
        return object;
    }

    /***
     * 阿里巴巴根据Key字符串转对象
     * @param benstr
     * @param class1
     * @return
     */
    public static <TResult> TResult AlbbStrToBeanByKey(String benstr, String key, Class<TResult> class1) {
        String result = com.alibaba.fastjson.JSONObject.parseObject(benstr).getString(key);

        return (TResult)AlbbStrToBean(result, class1);
    }

    /**
     *Albb 对象转字符串
     *
     * @param o
     * @return
     */
    public static String AlbbBeantoString(Object o) {
        String string = com.alibaba.fastjson.JSONObject.toJSONString(o);
        return string;
    }


    /**
     * 对象转字符串
     *
     * @param o
     * @return
     */
    public static String BeantoString(Object o) {
        JSONObject fromObject = JSONObject.fromObject(o);
        String string = fromObject.toString();
        return string;
    }


    /**
     * array字符串转对象
     *
     * @param str
     * @param class1
     * @return
     */
    public static <T> List<T> StringtoList(String str, Class<T> class1) {
        List<T> list = JSON.parseArray(str, class1);
        return list;
    }


    /**
     * array字符串转对象
     *
     * @param str
     * @param class1
     * @param o
     * @return
     */
    public static List<?> StringtoList(String str, Class<?> class1, List<?> o) {
        JSONArray jsonarray = JSONArray.fromObject(str);
        List<?> list = JSONArray.toList(jsonarray, class1);
        return list;
    }



    /**
     * list转字符串
     *
     * @param o
     * @return
     */
    public static String ListtoString(List<?> o) {
        JSONArray json = JSONArray.fromObject(o);
        String string = json.toString();
        return string;
    }


    /**
     * 字符串 转数组
     *
     * @param arrstr
     * @return
     */
    public static String[] StringtoArray(Object arrstr) {
        JSONArray objs = JSONArray.fromObject(arrstr);
        String[] newarray = new String[objs.size()];
        if (objs != null && objs.size() > 0) {
            for (int i = 0; i < objs.size(); i++) {
                newarray[i] = objs.getString(i);
            }
        }
        return newarray;

    }

    /**
     * 数组转字符串
     *
     * @param arr
     * @return
     */
    public static String ArraytoString(Object arr) {
        String jsonArray = ArrayUtils.toString(arr, ",");
        return jsonArray;

    }



    public static Map StringToMap(String str){
        HashMap hashMap = JSON.parseObject(str, HashMap.class);
        return hashMap;
    }
}