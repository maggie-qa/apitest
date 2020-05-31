package apitest.id;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardTest {

    private static final String FILEPATH = System.getProperty("user.dir") + File.separator +
            "data" + File.separator + "test.txt";

    /**
     * 读取文件
     */
    public static File readFile() {
        return new File(FILEPATH);
    }



    // 通过json对象提取
    public static void countByJsonObject() {
        int count = 0;
        try {
            List<String> lines = FileUtils.readLines(readFile(), "utf-8");  // 读取文件
            for (String line : lines) {
                JSONObject jsonObject = (JSONObject) JSON.parse(line);
                String idCard = jsonObject.getString("idcard");
                if (StringUtils.isNotBlank(idCard)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("countByJsonObjectidcard非空统计：" + count);
    }

    // 通过jsonPath提取
    public static void countByJsonPath() {
        int count = 0;
        try {
            List<String> lines = FileUtils.readLines(readFile(),"utf-8");
            for(String line:lines) {
                String str = String.valueOf(JSONPath.read(line,"idcard"));
                if(StringUtils.isNoneBlank(str)) {
                    count ++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("countByJsonPathidcard非空统计：" + count);


    }

    public static void countByRegex() {
        int count = 0;
        File file = new File(FILEPATH);
        String regex = "\"idcard\":\"(.+?)\",";
        // 正则表达类
        Pattern pattern = Pattern.compile(regex);
        try {
            String string = FileUtils.readFileToString(readFile(),"utf-8");
//            List<String> lines = FileUtils.readLines(file,"UTF-8");

                Matcher matcher = pattern.matcher(string);
                while(matcher.find()) {
                    if(StringUtils.isNotBlank(matcher.group(1))) {
                        count ++;
                    }
//                    System.out.println(matcher.group());
//                    System.out.println(matcher.group(1));
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("countByRegidcard非空统计:" + count);
    }

    // 通过数据库查找
    public static void countByDB() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        QueryRunner runner = new QueryRunner(ds);
        String sql = "select * from fan_test where idcard != ''";
        try {
            List<Map<String,Object>> list = (List) runner.query(sql, new MapListHandler());
            System.out.println("countByDBdcard非空统计:" + list.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        countByJsonObject();
        countByJsonPath();
        countByRegex();
        countByDB();
    }


}
