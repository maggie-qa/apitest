package apitest.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbUtilsTest {
	
	public static void main(String[] args) {
//		findOne("09b2c272-88f8-4f27-b9aa-a1f16ea7f6d4");
//
//		findList();

//		findListMap();
		findMap();
	}
	
	private static void findList() {
		   ComboPooledDataSource ds = new ComboPooledDataSource();
		   QueryRunner runner = new QueryRunner(ds);
	        String sql = "select * from t_user_test";
			try {
				List list = (List) runner.query(sql, new BeanListHandler(DbUser.class));
				System.out.println(list);
				for (Object object : list) {
					System.out.println(list);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	private static void findListMap() {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner runner = new QueryRunner(ds);
		String sql = "select * from t_user_test";
		try {
			List<Map<String,Object>> list = (List) runner.query(sql, new MapListHandler());
			System.out.println(list);
			for (Object object : list) {
				System.out.println(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void findMap() {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner runner = new QueryRunner(ds);
		String sql = "select * from t_user_test where uid = ?";
		try {
			Map map = runner.query(sql, new MapHandler(),"00");
			System.out.println(map);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void findOne(String uid) {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner runner = new QueryRunner(ds);
		String sql = "select * from t_user_test where uid=?";
			try {
				DbUser dbUser = (DbUser) runner.query(sql, new BeanHandler(DbUser.class),uid);
				System.out.println(dbUser);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}

	private static void findUser(String uid) {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		QueryRunner runner = new QueryRunner(ds);
		String sql = "select * from t_user_test where uid=?";
		try {
			DbUser dbUser = (DbUser) runner.query(sql, new BeanHandler(DbUser.class),uid);
			System.out.println(dbUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
