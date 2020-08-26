/**---------------------------------------------------------------- 
 * // Copyright (C) 2016 北京嘉和美康信息技术有限公司版权所有。
 *  // 文件名：HiveUtil.java
 *  // 文件功能描述：hive连接
 *  // 创建人：周伟彬
 *  // 创建日期：2016/11/30 
 *  // 修改人：
 *  // 修改日期： 
 *  // ----------------------------------------------------------------*/
package com.goodwill.cda.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.goodwill.core.utils.ApplicationException;
import com.goodwill.core.utils.PropertiesUtils;

public class HiveUtil {
	private static String CONFIG_FILE_NAME = "sqoop.properties";

	/**
	 * 执行hive外表连接
	 * 
	 * @return
	 */
	public static Connection createHiveConnection() {
		String driverName = "org.apache.hive.jdbc.HiveDriver";
		// /三院hive
		//String hive2_str = "jdbc:hive2://172.21.3.74:10000/default";
		// /公司hive
		//		String hive2_str = "jdbc:hive2://192.168.7.108:10000/default";
		//		String hive_user = "hive";
		//		String hive_pass = "123456";
		String hive_url = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hive2.conn");
		String hive_user = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hive2.user");
		String hive_pass = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hive2.password");

		Connection conn = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(hive_url, hive_user, hive_pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ApplicationException("hive驱动加载失败！");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("hive连接创建失败！");
		}
		return conn;

	}
}
