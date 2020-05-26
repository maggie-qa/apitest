package apitest;

import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches
{
    public static void main( String args[] ){

//        System.out.println(replaceUrl("http://118.24.13.38:8080/goods/UserServlet?method=loginMobile&loginname&loginpass=${loginpass}"));
        System.out.println(replaceUrl());
    }
    public static String replaceUrl() {
        String url = "http://118.24.13.38:8080/goods/UserServlet?method=loginMobile&${loginname}=loginname&loginpass=${loginpass}";
        String pattern = "\\$\\{(.+?)\\}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(url);
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        map.put("loginname","test");
//        map.put("loginpass","abc");
        while(m.find()) {
            System.out.println(m.group());
            System.out.println(m.group(1));
            url = url.replace(m.group(), MapUtils.getString(map,m.group(1),""));  // 若没有数据，则默认为""
        }
        return url;
    }

}
