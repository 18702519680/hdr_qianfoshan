package com.goodwill.cda.hlht.cda.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goodwill.cda.util.HiveUtil;

public class HiveToMysql {
	protected static Logger logger = LoggerFactory.getLogger(HiveToMysql.class);
	/**
	 *  将生成 CDA文档 统计结果插入mysql 的数据库
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 */
	private void hiveToMysql(String requestString, String responseString,String functionName,String version,String flag) {
		logger.info("===============初始化 mysql表hdr_cda_count_new1 ，将生成的CDA历史统计数据插入 mysql=====================");
		long start = System.currentTimeMillis();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into HDR_ZLSB_LOGS () values");
		Connection conns = HiveUtil.createHiveConnection();
		ResultSet res = null;
		Statement stmt = null;
		try {
			if (conns == null) {
				for (int i = 0; i < 3; i++) {
					logger.error("获取hive JDBC链接失败 。。。重新获取。。。");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						logger.error("在获取hiveJDBC链接 过程中线程进行sleep错误。。。");
						e.printStackTrace();
					}
					try {
						conns = HiveUtil.createHiveConnection();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (conns != null) {
						break;
					}
				}
			}
			if (conns == null) {
				logger.error("初始化 mysql表HDR_ZLSB_LOGS ，将上报日志数据数据插入 mysql 失败");
				return;
			}
			stmt = conns.createStatement();
			res = stmt.executeQuery(sql.toString());
		} catch (SQLException e) {
			logger.error("初始化 mysql表HDR_ZLSB_LOGS ，将上报日志数据数据插入 mysql 失败,执行SQL语句错误。。。");
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("=================将生成 上报日志数据数据插入mysql总执行时间====================="
					+ (((end - start) / 1000) / 60) + "分钟");
			logger.info("================ 将生成 上报日志数据数据插入mysql完成====================");
		}
	}

}
