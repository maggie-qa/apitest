package com.testfan.json;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class JsonPath {

	public static void main(String[] args) {
	    String jsonStr = "{\n" +
                "    \"store\": {\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        },\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"author\": \"刘慈欣\",\n" +
                "                \"price\": 8.95,\n" +
                "                \"category\": \"科幻\",\n" +
                "                \"title\": \"三体\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"author\": \"itguang\",\n" +
                "                \"price\": 12.99,\n" +
                "                \"category\": \"编程语言\",\n" +
                "                \"title\": \"go语言实战\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
	    System.out.println(jsonStr);
	    //第一本书title
        String title = (String) JSONPath.read(jsonStr, "store.book[0].title");
        System.out.println(title);
        
        List<String> titles =  (List<String>) JSONPath.read(jsonStr, "store.book.title");
        System.out.println(titles);
        
        List<Double> prices =  (List<Double>) JSONPath.read(jsonStr, "$.store.book.price");
        System.out.println(prices);
        
        //多层结构 相对路径
        List<String> prices2 =  (List<String>) JSONPath.read(jsonStr, "..price");
        System.out.println(prices2);
       

	}
}
