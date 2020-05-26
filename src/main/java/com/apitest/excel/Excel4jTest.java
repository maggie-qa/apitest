package com.apitest.excel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.apitest.convert.DateConvert;
import com.apitest.utils.StringMapUtils;
import com.github.crab2died.ExcelUtils;
import com.github.crab2died.exceptions.Excel4JException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.github.crab2died.ExcelUtils.*;

public class Excel4jTest {

    public static void main(String[] args) {

        //获取当前工程的路径
        String basePath = System.getProperty("user.dir") + File.separator;
        // 获取被读取文件的路径
        String filePath = basePath + "data" + File.separator + "apitest7.xlsx";
        // 获取导出文件的路径

        String exportPath = basePath + "report" + File.separator;
        // 读取测试用例
        List<TestApi> testCases = new ArrayList<TestApi>();
        try {
            testCases = getInstance().readExcel2Objects(filePath,TestApi.class);
            // 将测试用例按照顺序排序
            Collections.sort(testCases);
            for (TestApi testCase: testCases) {
                // 将resultjson进行非空判断
                if(StringUtils.isNotEmpty(testCase.getResultJson())) {
                    String resultJson = "{\n" +
                            "\t\"msg\": \"登录成功\",\n" +
                            "\t\"uid\": \"9CC972DFA2D4481F89841A46FD1B3E7B\",\n" +
                            "\t\"code\": \"1\"\n" +
                            "}";
                    // 将resultJson转化为json对象
                    String uid = (String) JSONPath.read(resultJson,"uid");
                    String code = (String) JSONPath.read(resultJson,"code");
                    Map<String,Object> resultJsonMap = StringMapUtils.convert(testCase.getResultJson(),";");
                    // map替换
                    resultJsonMap.replace("id1",uid);
                    resultJsonMap.replace("code1",code);
                    System.out.println(resultJsonMap);
                }
                System.out.println(testCase);
            }
        } catch (Excel4JException | IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        // 写入数据,通过加时间戳的方式使写入的文件不重复
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(new Date());

        try {
            ExcelUtils.getInstance().exportObjects2Excel(testCases, TestApi.class, exportPath + str + "result.xlsx");
        } catch (Excel4JException | IOException e) {
            e.printStackTrace();
        }


    }
}
