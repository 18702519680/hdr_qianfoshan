package com.goodwill.cda.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.goodwill.core.utils.PropertiesUtils;

public class MysqlUtil {
	private static String CONFIG_FILE_NAME = "jdbc.properties";

	public static Connection GetConnection() {
		Connection con = null;
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名mydata
		String url = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "jdbc.url");
		//		String url = "jdbc:mysql://10.2.2.68:3306/hdr";
		// MySQL配置时的用户名
		String user = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "jdbc.username");
		//String user ="root";
		// MySQL配置时的密码
		String password = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "jdbc.password");
		//		String password = "123456";
		// 遍历查询结果集
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 1.getConnection()方法，连接MySQL数据库！！
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// 数据库驱动类异常处理
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			// 数据库连接失败异常处理
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	//连接集成平台
	public static Connection GetJhipConnection() {
		Connection con = null;
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名mydata
		String url = "jdbc:mysql://172.16.80.183:3306/hdrs?useUnicode=true&characterEncoding=utf8";
		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "hduap001";
		// 遍历查询结果集
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 1.getConnection()方法，连接MySQL数据库！！
			con = DriverManager.getConnection(url, user, password);
			/*
			 * if(!con.isClosed())
			 * System.out.println("Succeeded connecting to the Database!");
			 */
			// return con;
		} catch (ClassNotFoundException e) {
			// 数据库驱动类异常处理
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			// 数据库连接失败异常处理
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
