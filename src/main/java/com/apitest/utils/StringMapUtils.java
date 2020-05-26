package com.apitest.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringMapUtils {
    public static Map<String, Object> convert(String params, String regx) {
//        参数格式：method=loginMobile&loginname=test1&loginpass=test1&test=您好
        if (!StringUtils.isEmpty(params)) {  // 参数不为空
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            String[] paramsString = params.split(regx);  // 按照分隔符拆分
            for (String paramString : paramsString) {// 循环遍历数组,再次进行拆分
                String[] param = paramString.split("=");
                if (param.length < 2) {
                    paramsMap.put(param[0], "null");
                } else {
                    paramsMap.put(param[0], param[1]);
                }

            }
            return paramsMap;
        }
        return null;
    }

    public static void main(String[] args) {
        String s = "method=loginMobile&loginname=test1&loginpass=test1&test=您好";
        System.out.println(convert(s, "&"));
    }
}


