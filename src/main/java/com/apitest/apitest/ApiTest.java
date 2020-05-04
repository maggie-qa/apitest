package com.apitest.apitest;

import com.apitest.excel.TestApi;
import com.apitest.utils.HttpClientUtils;
import com.github.crab2died.exceptions.Excel4JException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.crab2died.ExcelUtils.getInstance;

public class ApiTest {
    public static void main(String[] args) {
        //获取当前工程的路径
        String basePath = System.getProperty("user.dir") + File.separator;
        // 获取被读取文件的路径
        String filePath = basePath + "data" + File.separator + "apitest3.xlsx";
        // 获取导出文件的路径

        String exportPath = basePath + "report" + File.separator;
        // 读取测试用例
        List<TestApi> testCases = new ArrayList<TestApi>();
        try {
            testCases = getInstance().readExcel2Objects(filePath,TestApi.class);
            // 将测试用例按照顺序排序
            Collections.sort(testCases);
            for (TestApi testCase: testCases) {
                if(testCase.getIsRun()) { // 判断用例是否需要测试
                    if(testCase.getType().equalsIgnoreCase("get")) {
                        System.out.println(HttpClientUtils.doGet(testCase.getUrl()));
                    } else if(testCase.getType().equalsIgnoreCase("post")) {
                        System.out.println(HttpClientUtils.doPost(testCase.getUrl(),testCase.getParams()));
                    } else if (testCase.getType().equalsIgnoreCase("postjson")) {
                        System.out.println(HttpClientUtils.doPostJson(testCase.getUrl(),testCase.getParams()));
                    }
                }
            }
        } catch (Excel4JException | IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
