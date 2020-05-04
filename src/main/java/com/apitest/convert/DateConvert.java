package com.apitest.convert;

import com.github.crab2died.converter.WriteConvertible;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvert implements WriteConvertible {
    @Override
    public Object execWrite(Object object) {
        if(object instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(object);
            return str;
        }
        return object;

    }
}
