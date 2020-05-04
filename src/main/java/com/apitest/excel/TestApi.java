package com.apitest.excel;

import com.apitest.convert.IsRunConvert;
import com.github.crab2died.annotation.ExcelField;
import com.github.crab2died.converter.ReadConvertible;

public class TestApi implements Comparable<TestApi>{
    /**
     * 用例是否执行
     */
    @ExcelField(title = "是否开启",readConverter = IsRunConvert.class) // 使用注解将属性与Excel表格列明对齐
    private boolean isRun;
    /**
     * 用例执行顺序
     */
    @ExcelField(title = "顺序")
    private int order;
    /**
     * 用例名称
     */
    @ExcelField(title = "用例名称")
    private String testCase;
    /**
     * 接口类型
     */
    @ExcelField(title = "类型")
    private String type;
    /**
     * 测试环境地址
     */
    @ExcelField(title = "地址")
    private String url;
    /**
     * 入参
     */
    @ExcelField(title = "参数")
    private String params;

    public boolean getIsRun() {
        return isRun;
    }

    public void setIsRun(boolean run) {
        isRun = run;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "TestApi{" +
                "isRun=" + isRun +
                ", order=" + order +
                ", testCase='" + testCase + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", params='" + params + '\'' +
                '}';
    }

    public int compareTo(TestApi o) {
        return this.order - o.order; // 升序排序
    }
}
