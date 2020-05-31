package apitest.db;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * dbutils 增删修改例子
 * @author pc
 *
 */
public class DbUtilsCRUDTest {

	public static void main(String[] args) throws SQLException {
		//修改，删除，添加测试
		//update();
		add();
		//addbatch();
	}

	
	/**
	 * 修改测试
	 * @throws SQLException
	 */
	private static void update() throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		// dbutis使用数据源
		QueryRunner runner = new QueryRunner(ds);
		// 可变变量  无限 也可以没有 也可以数组
		Object[] objects= new Object[] {"123_test","333_test", "14ba4bd0-a0da-4a2c-b136-de036b54e98a"};
		//runner.update("update t_user_test set loginname=?,loginpass=? where uid=?", "123_dbutils","123_dbutils","14ba4bd0-a0da-4a2c-b136-de036b54e98a");
		runner.update("update t_user_test set loginname=?,loginpass=? where uid=?", objects);
	}
	
	/**
	 * 删除和添加测试
	 * @throws SQLException
	 */
	private static void add() throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner runner = new QueryRunner(ds);
		//删除
		runner.update("delete from t_user_test");
		//添加
		for (int i = 0; i < 10; i++) {
			Object[] objects= new Object[] {UUID.randomUUID().toString(),"test"+i, "pass"+i};
			runner.update("insert INTO t_user_test(uid,loginname,loginpass) values(?,?,?)", objects);
		}
		
	}
	
	//批量操作
	private static void addbatch() throws SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner runner = new QueryRunner(ds);
		//删除
		runner.update("delete from t_user_test");
		Object[][] params= new Object[10][3];
		for (int i = 0; i < 10; i++) {
			params[i][0]=UUID.randomUUID().toString();
			params[i][1]="test"+i;
			params[i][2]="pass"+i;
		}
		//批量操作
		runner.batch("insert INTO t_user_test(uid,loginname,loginpass) values(?,?,?)", params);
	}
	


}
