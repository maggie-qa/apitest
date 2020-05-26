package com.apitest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUtils {

    // 全局map支持多列数据提取
    static Map<String, Object> param_map = new HashMap<String, Object>();

    // 将map放进全局map中
    public static void addFromMap(Map<String, Object> map) {
        if (MapUtils.isNotEmpty(map)) {
            Set<String> keySets = map.keySet();
            for (String key : keySets) {
                param_map.put(key, map.get(key));
            }
        }
    }

    public static void addFromJson(String json, String regx) {
        // regx:id1=uid;code1=code
        // 判断是否为有效json
        if (JSON.isValid(json)) {
            Map<String,Object> map = StringMapUtils.convert(regx, ";");
            // 非空判断
            if (MapUtils.isNotEmpty(map)) {
                // 从json中提取要替换的对象
                Set<String> keySets = map.keySet();
                for (String key : keySets) {
                    String value = String.valueOf(map.get(key));
                    if (StringUtils.isNotBlank(value)) ;
                    Object newValue = JSONPath.read(json, value);
                    param_map.put(key, newValue);

                }
            }


        }
    }

    public static void printMap() {
        System.out.println(param_map);
    }

    public static void main(String[] args) {
        addFromJson("{\n" +
                "\t\"msg\": \"登录成功\",\n" +
                "\t\"uid\": \"9CC972DFA2D4481F89841A46FD1B3E7B\",\n" +
                "\t\"code\": \"1\"\n" +
                "}", "id1=uid;code1=code");
        addFromJson("{\n" +
                "\t\"msg\": \"登录成功\",\n" +
                "\t\"uid\": \"9CC972DFA2D4481F89841A46FD1B3E7B\",\n" +
                "\t\"code\": \"1\"\n" +
                "}", "id3=uid");
        printMap();
    }

    static String regx = "\\$\\{(.+?)\\}";
    static Pattern pattern = Pattern.compile(regx);

    public static String replace(String str) {
        if (StringUtils.isNotEmpty(str)) {
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                str = str.replace(matcher.group(), MapUtils.getString(param_map, matcher.group(1), ""));
            }
        }
        return str;
    }

    public static void clear() {
        if(MapUtils.isNotEmpty(param_map)) {
            param_map.clear();
        }

    }

}
