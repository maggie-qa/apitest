package apitest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FastJsonTest {
    public static void main(String[] args) {
        String json1 = "{\"loginname\":\"abc\",\"loginpass\":\"abc\"}\n";



        //处理json格式，JSONObject为一个map
        JSONObject jsonObject = (JSONObject) JSON.parse(json1);
        // fastjson反射变化
        UserTest userTest = JSON.parseObject(json1,UserTest.class);
        System.out.println(userTest);

        System.out.println(jsonObject.get("msg"));
        // 处理json数组
        String json2 = "[{\"loginname\":\"abc0\",\"loginpass\":\"abc0\"},{\"loginname\":\"abc1\",\"loginpass\":\"abc1\"},{\"loginname\":\"abc2\",\"loginpass\":\"abc2\"}]";
        JSONArray jsonArray = (JSONArray) JSON.parse(json2);
        //将json解析成map
        Map map = (Map) JSON.parseObject(json1,Map.class);
        Set set = map.keySet();
        System.out.println("将json数组解析成map=====");
        for (Object s:set) {
            System.out.println(map.get(s));

        }

        // fastjson反射,将json数组转化为list对象
        List<UserTest> userTestList = JSON.parseArray(json2,UserTest.class);
        System.out.println("======json数组转化为list=====");
        for (UserTest user:userTestList) {
            System.out.println(user);
        }
        for(int i = 0;i < jsonArray.size();i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
            System.out.println(jsonObject1.getString("loginname"));

        }
        // json校验，提取loginname：json对象和json数组的组合
        String json3 = "{\"data\":[{\"loginname\":\"abc0\",\"loginpass\":\"abc0\"},{\"loginname\":\"abc1\",\"loginpass\":\"abc1\"},{\"loginname\":\"abc2\",\"loginpass\":\"abc2\"}]}";
        if(JSON.isValidObject(json3)) {  // 判断是否为json
            JSONObject jsonObject3 = (JSONObject) JSON.parse(json3);
            // 获取data中的json数组

            JSONArray jsonArray3 =jsonObject3.getJSONArray("data");
            for(int i = 0;i < jsonArray3.size();i++) {
                JSONObject object = (JSONObject) jsonArray3.get(i);
                System.out.println(object.getString("loginname"));

            }

        }



    }
}
