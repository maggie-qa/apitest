package com.apitest.apitest;

import com.apitest.excel.TestApi;
import com.apitest.utils.*;
import com.github.checkpoint.CheckPointUtils;
import com.github.crab2died.ExcelUtils;
import com.github.crab2died.exceptions.Excel4JException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase;
import sun.net.www.http.HttpClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.crab2died.ExcelUtils.getInstance;

class RunTask extends Thread {
    private Map<String, Object> map;

    public RunTask(Map<String, Object> map) {
        super();
        this.map = map;
    }

    @Override
    public void run() {
        // 读取测试用例
        ParamUtils.addFromMap(map);
        try {
            List<TestApi> testCases = ExcelUtils.getInstance().readExcel2Objects(ApiTestThread.filePath, TestApi.class);
            // 测试用例排序
            Collections.sort(testCases);
            for (TestApi testCase : testCases) {
                if (testCase.getIsRun()) { // 判断用例是否需要测试
                    // 替换url
                    ApiTestThread.replace(testCase);
                    String result = null;
                    if (testCase.getType().equalsIgnoreCase("get")) {
                        result = HttpClientUtils.doGet(testCase.getUrl());
                    } else if (testCase.getType().equalsIgnoreCase("post")) {
                        result = HttpClientUtils.doPost(testCase.getUrl(),
                                StringMapUtils.convert(testCase.getParams(), "&"));
                    } else if (testCase.getType().equalsIgnoreCase("postjson")) {
                        result = HttpClientUtils.doPostJson(testCase.getUrl(), testCase.getParams(),
                                StringMapUtils.convert(testCase.getHeader(), ";"));

                    }
                    System.out.println(result);
                    // 数据提取
                    ParamUtils.addFromJson(result, testCase.getResultJson());
                    if (StringUtils.isNoneEmpty(testCase.getCheckJson())) {
                        System.out.println("检查点不为空============");
                        System.out.println(CheckPointUtils.check(result, testCase.getCheckJson()).getMsg());

                    }
                    testCase.setCheckResult(CheckPointUtils.check(result, testCase.getCheckJson()).getMsg());
                    //校验数据库
                    String dbCheckResult = DbCheckUtils.getDbCheck(testCase);
                    testCase.setDbCheckResult(dbCheckResult);
                    ApiTestThread.allResult.add(testCase);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ParamUtils.clear();
    }
}

public class ApiTestThread {
    //获取当前工程的路径
    static String basePath = System.getProperty("user.dir") + File.separator;
    // 获取被读取文件的路径
    static String filePath = basePath + "data" + File.separator + "apitest6-2.xlsx";
    // 测试结果
    static List<TestApi> allResult = new ArrayList<>();

    public static void main(String[] args) {
        // 获取导出文件的路径
        String exportPath = basePath + "report" + File.separator;
        try {
            // 读取第二个sheet，准备要覆盖测试的参数
            List<Map<String, Object>> listMaps = ExcelToMapUtils.importExcel(filePath, 1);

            // 默认不开启代理设置，手动开启，可以调用HttpClientUtils中的openProxy属性开启代理设置
            HttpClientUtils.openProxy = true;

            // 若由多个参数，则再次循环一层
            for (Map<String, Object> map : listMaps) {
                System.out.println("map:" + map);
                RunTask runTask = new RunTask(map);
                runTask.start();
            }
            // 主程序休眠10秒
            Thread.sleep(10000L);

            // 写入数据,通过加时间戳的方式使写入的文件不重复
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(new Date());

            ExcelUtils.getInstance().exportObjects2Excel(allResult, TestApi.class, exportPath + "result " + str + ".xlsx");
            System.out.println("文件写入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    static String regx = "\\$\\{(.+?)\\}";
//    static Pattern pattern = Pattern.compile(regx);
//    public static String pattern(String str,Map<String,Object> map) {
//        if(StringUtils.isNotEmpty(str)) {
//            Matcher matcher = pattern.matcher(str);
//            while(matcher.find()) {
//                str = str.replace(matcher.group(),MapUtils.getString(map,matcher.group(1),""));
//            }
//        }
//        return str;
//    }

    // 参数化支持
    public static void replace(TestApi testCase) {
        // 替换url
        testCase.setUrl(ParamUtils.replace(testCase.getUrl()));
        // 替换params
        testCase.setParams(ParamUtils.replace(testCase.getParams()));
        // 替换头部
        testCase.setHeader(ParamUtils.replace(testCase.getHeader()));
        // 替换checkpoint
        testCase.setCheckJson(ParamUtils.replace(testCase.getCheckJson()));

    }
}
