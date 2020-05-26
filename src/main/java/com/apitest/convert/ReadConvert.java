package com.apitest.convert;

import com.github.crab2died.ExcelUtils;
import com.github.crab2died.converter.ReadConvertible;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ReadConvert implements ReadConvertible {
    @Override
    public Object execRead(String object) {
        // 判断获取的参数以txt，csv结尾，则获取当前文件路径，读取数据
        if(StringUtils.endsWith(object,"csv") |StringUtils.endsWith(object,"txt")) {
            String basePath = System.getProperty("user.dir") + File.separator;
            String filePath = basePath + "data" + File.separator + object;
            // 使用FileUtils读取csv/txt格式的文件
            try {
                // 通过readFileToString可直接将获取的数据转为string类型
                return FileUtils.readFileToString(new File (filePath),"utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // 若非csv、txt文件时，直接返回即可
        return object;

    }
}
