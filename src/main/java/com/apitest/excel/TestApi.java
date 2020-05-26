package com.apitest.excel;

import com.apitest.convert.IsRunConvert;
import com.apitest.convert.ReadConvert;
import com.github.crab2died.annotation.ExcelField;
import com.github.crab2died.converter.ReadConvertible;

import java.util.Objects;

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
    @ExcelField(title = "参数",readConverter = ReadConvert.class)  // 通过反射获取数据
    private String params;
    /**
     * header
     */
    @ExcelField(title = "头部")
    private String header;

    @ExcelField(title = "返回结果从json提取")
    private String resultJson;

    @ExcelField(title = "返回结果校验")
    private String checkJson;

    @ExcelField(title = "返回结果校验")
    private String checkResult;

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckJson() {
        return checkJson;
    }

    public void setCheckJson(String checkJson) {
        this.checkJson = checkJson;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestApi)) return false;
        TestApi testApi = (TestApi) o;
        return isRun == testApi.isRun &&
                getOrder() == testApi.getOrder() &&
                Objects.equals(getTestCase(), testApi.getTestCase()) &&
                Objects.equals(getType(), testApi.getType()) &&
                Objects.equals(getUrl(), testApi.getUrl()) &&
                Objects.equals(getParams(), testApi.getParams()) &&
                Objects.equals(getHeader(), testApi.getHeader()) &&
                Objects.equals(getResultJson(), testApi.getResultJson()) &&
                Objects.equals(getCheckJson(), testApi.getCheckJson()) &&
                Objects.equals(getCheckResult(), testApi.getCheckResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRun, getOrder(), getTestCase(), getType(), getUrl(), getParams(), getHeader(), getResultJson(), getCheckJson(), getCheckResult());
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
                ", header='" + header + '\'' +
                ", resultJson='" + resultJson + '\'' +
                ", checkJson='" + checkJson + '\'' +
                ", checkResult='" + checkResult + '\'' +
                '}';
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

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

    public int compareTo(TestApi o) {
        return this.order - o.order; // 升序排序
    }
}
