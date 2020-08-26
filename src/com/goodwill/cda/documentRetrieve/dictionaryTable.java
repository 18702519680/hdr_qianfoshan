package com.goodwill.cda.documentRetrieve;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * @class 术语字典存入
 * @author 计彬
 * @Date 2016年7月27日 281071
 */
public class dictionaryTable {

	public static void main(String[] args) {
		InsertDictionaryTable();
	}

	/**
	 * @author 计彬
	 * @Methods 术语字典数据存入habse中
	 * @Date 2016年7月27日
	 */
	public static void InsertDictionaryTable() {
		int countss = 0;
		String str = file("src/main/resources/OID/OIDD.txt");
		String[] tableOid = str.split(",");
		String field1 = null;
		String field2 = null;
		for (int i = 0; i < tableOid.length; i += 3) {
			// if(i%2==0){
			// 判断病区字典
			if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.7")) {
				field1 = "WARD_SN";
				field2 = "DEPT_SN";
				// 药品字典
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.12")) {
				field1 = "charge_code";
				field2 = "serial";
				// 医嘱字典
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.13")) {
				field1 = "order_code";
				field2 = "";
				// 检查项目字典
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.11")) {
				field1 = "type_code";
				field2 = "sub_code";
				// 药品库房字典
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.20")) {
				field1 = "group_no";
				field2 = "";
				// 床位字典
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.23")) {
				field1 = "ward_sn";
				field2 = "bed_no";
				// 药品附加属性表
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.46")) {
				field1 = "charge_code";
				field2 = "property";
				// 药品附加属性字典表
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.47")) {
				field1 = "property";
				field2 = "";
				// 药品名称字典
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.21")) {
				field1 = "name";
				field2 = "";
				// 手术切口等级=================
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.3.7")) {
				field1 = "code";
				field2 = "default_flag";
				// 人员字典
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.2")) {
				field1 = "code";
				field2 = "item_version";
				// 科室字典
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.1")) {
				field1 = "code";
				field2 = "item_version";
				// 药品厂商字典
			} else if (tableOid[i + 2].equals("1.2.156.112636.1.1.2.1.4.49")) {
				field1 = "manu_code";
				field2 = "manu_name";
			} else {
				field1 = "code";
				field2 = "name";
			}

			HbaseCURDUtils.batchPut(
					"HDR_DICT_ITEM",
					hbaseput(tableOid[i + 1].trim(), tableOid[i + 2], field1,
							field2, tableOid[i]));
			System.out.println(tableOid[i] + "!!!" + tableOid[i + 1]
					+ ":====================" + "插入完成");
			System.gc();
			/*
			 * try { tableCount("视图名称:====" + tableOid[i + 1] + "====长度：======="
			 * + hbaseput(tableOid[i + 1], tableOid[i + 2], field1, field2,
			 * tableOid[i]).size()); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
			// countss += hbaseput(tableOid[i + 1], tableOid[i + 2], field1,
			// field2, tableOid[i]).size();
			// System.out.println(countss);

		}
	}

	/**
	 * @author jibin
	 * @Date 2016年7月27日
	 * @Methods 读取视图数据转换后 存入list中
	 * @param tableName表名称
	 *            ,OId,field1 、field2拼接roekey字段, tableches表中文名称
	 * @return
	 */
	public static List<Map<String, String>> hbaseput(String tableName,
			String Oid, String field1, String field2, String tablechinese) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String strUrl = "jdbc:oracle:thin:@10.2.2.198:1521/hiecs";
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// List<String> rowlist=new ArrayList<String>();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(strUrl, "hdr", "hdr");
			CallableStatement proc = null; // 创建执行存储过程的对象
			proc = conn.prepareCall("{ call hiecs.SAM.DICT_PRO(?,?) }"); // 设置存储过程
			proc.registerOutParameter(1, OracleTypes.VARCHAR);
			proc.registerOutParameter(2, OracleTypes.CURSOR); // call为关键字.
			proc.setString(1, tableName); // 设置第一个输入参数
			proc.execute();// 执行
			rs = (ResultSet) proc.getObject(2);
			// /取值
			String rowkey = "";
			String str = "";
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					"c:\\log.txt"), true));
			while (rs.next()) {
				if (field2.equals(""))
					rowkey = Oid + "|" + rs.getString(field1);
				else
					rowkey = Oid + "|" + rs.getString(field1) + "|"
							+ rs.getString(field2);
				Map<String, String> map = new HashMap<String, String>();

				// 判断添加每个字典码以及字典名称
				// 病区字典
				if (Oid.equals("1.2.156.112636.1.1.2.1.4.7")) {
					map.put("DICT_ITEM_CODE", rs.getString("dept_sn"));
					map.put("DICT_ITEM_VALUE", rs.getString("ward_name"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
					// 药品字典
				} else if (Oid.equals("1.2.156.112636.1.1.2.1.4.12")) {
					map.put("DICT_ITEM_CODE", rs.getString("charge_code"));
					map.put("DICT_ITEM_VALUE", rs.getString("drugname"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
					// 医嘱字典
				} else if (Oid.equals("1.2.156.112636.1.1.2.1.4.13")) {
					map.put("DICT_ITEM_CODE", rs.getString("order_code"));
					map.put("DICT_ITEM_VALUE", rs.getString("order_name"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
					// 检查项目字典
				} else if (Oid.equals("1.2.156.112636.1.1.2.1.4.11")) {
					map.put("DICT_ITEM_CODE",
							rs.getString("type_code")
									+ rs.getString("sub_code"));
					map.put("DICT_ITEM_VALUE", rs.getString("name"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
					// 药品库房字典
				} else if (Oid.equals("1.2.156.112636.1.1.2.1.4.20")) {
					map.put("DICT_ITEM_CODE", rs.getString("group_no"));
					map.put("DICT_ITEM_VALUE", rs.getString("dept_name"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
					// 床位字典
				} else if (Oid.equals("1.2.156.112636.1.1.2.1.4.23")) {
					map.put("DICT_ITEM_CODE", rs.getString("bed_no"));
					map.put("DICT_ITEM_VALUE", rs.getString("bed_no"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
					// 药品附加属性表
				} else if (Oid.equals("1.2.156.112636.1.1.2.1.4.46")) {
					map.put("DICT_ITEM_CODE", rs.getString("charge_code"));
					map.put("DICT_ITEM_VALUE", rs.getString("p_value"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
					// 药品附加属性字典表
				} else if (Oid.equals("1.2.156.112636.1.1.2.1.4.47")) {
					map.put("DICT_ITEM_CODE", rs.getString("property"));
					map.put("DICT_ITEM_VALUE", rs.getString("p_name"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
					// 药品名称字典
				} else if (Oid.equals("1.2.156.112636.1.1.2.1.4.21")) {
					map.put("DICT_ITEM_CODE", rs.getString("code"));
					map.put("DICT_ITEM_VALUE", rs.getString("name"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
					// 药品厂商字典

				} else if (Oid.equals("1.2.156.112636.1.1.2.1.4.49")) {
					map.put("DICT_ITEM_CODE", rs.getString("manu_code"));
					map.put("DICT_ITEM_VALUE", rs.getString("manu_name"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
				} else {
					map.put("DICT_ITEM_CODE", rs.getString("code"));
					map.put("DICT_ITEM_VALUE", rs.getString("name"));
					map.put("DICT_CODE", Oid);
					map.put("DICT_NAME", tablechinese);
				}

				// /取字段名和属性
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// 根据字段名和属性动态取值
					// if
					// (rs.getMetaData().getColumnTypeName(i).endsWith("VARCHAR2"))
					// {
					str = HumpUtils.transForm((rs.getMetaData()
							.getColumnName(i)));
					map.put(str,
							rs.getString(rs.getMetaData().getColumnName(i)));
					// }
				}

				map.put("ROWKEY", rowkey);

				/*
				 * if(rowlist.contains(rowkey)){ //判断有没有重复的集合
				 * writer.write(tablechinese+"========="+rowkey);
				 * writer.newLine(); } rowlist.add(rowkey);
				 */

				list.add(map);

			}
			writer.close();

		} catch (SQLException ex2) {

			ex2.printStackTrace();

		} catch (Exception ex2) {

			ex2.printStackTrace();

		} finally {

			try {

				if (rs != null) {

					rs.close();

					if (stmt != null) {

						stmt.close();

					}
					if (conn != null) {

						conn.close();

					}

				}

			} catch (SQLException ex1) {

			}

		}

		return list;
	}

	/**
	 * @Methods 读取试图名称以及OID
	 * @param fileName
	 * @return
	 */
	public static String file(String fileName) {
		FileInputStream fos;
		BufferedReader br = null;
		StringBuffer bb = new StringBuffer();
		try {
			fos = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fos, "utf-8");
			br = new BufferedReader(isr);
			String aa = null;
			while ((aa = br.readLine()) != null) {
				bb.append(aa.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return bb.toString();
	}

	/**
	 * @Methods 记录每张表的数据量
	 * @param str
	 * @throws IOException
	 */
	public static void tableCount(String str) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				"c:\\Result.txt"), true));
		writer.write(str);
		writer.newLine();
		writer.close();
	}
}
