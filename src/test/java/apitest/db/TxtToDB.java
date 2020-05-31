package apitest.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TxtToDB {
    private final static String FILE_PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator + "test.txt";

    /**
     * txt文件内容插入数据库
     */
    private static void addToDb() {
        File file = new File(FILE_PATH);

        try {
            List<String> lines = FileUtils.readLines(file, "utf-8");
            ComboPooledDataSource ds = new ComboPooledDataSource();
            QueryRunner runner = new QueryRunner(ds);
            int count = 0;
            for (String line : lines) {
                JSONObject jsonObject = (JSONObject) JSON.parse(line);
                Object[] objects = new Object[]{count++, jsonObject.getString("phone"), jsonObject.getString("idcard"), jsonObject.getString("color")};
                runner.update("insert INTO fan_test_testfan(id,phone,idcard,color) values(?,?,?,?)", objects);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量插入数据
     */
    private static void addBatchToDb() {
        File file = new File(FILE_PATH);

        try {
            List<String> lines = FileUtils.readLines(file, "utf-8");
            ComboPooledDataSource ds = new ComboPooledDataSource();
            QueryRunner runner = new QueryRunner(ds);
            int count = 0;
            Object[][] params = new Object[lines.size()][4];
            for (int i = 0; i < lines.size(); i++) {
                JSONObject jsonObject = (JSONObject) JSON.parse(lines.get(i));
                params[i][0] = count++;
                params[i][1] = jsonObject.getString("phone");
                params[i][2] = jsonObject.getString("idcard");
                params[i][3] = jsonObject.getString("color");
            }
            runner.batch("insert INTO fan_test_testfan(id,phone,idcard,color) values(?,?,?,?)", params);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        addToDb();
        addBatchToDb();

    }
}
