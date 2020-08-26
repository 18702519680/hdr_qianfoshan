package com.goodwill.cda.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 字典工具，包括字典对照获取
 * @Description
 * 类描述：
 * @author malongbiao
 * @Date 2017年11月2日
 * @modify
 * 修改记录：jibin 2017-11-02
 * 完善查询字典映射编码
 *
 */
public class DictUtils {
	public static void main(String[] args) {
	}

	/**
	 * 
	 * @Description
	 * 方法描述: 查询对照字典结果
	 * @return 返回类型： Map<String,String>
	 * @param dictType 字典类型（通过 DictCode.java 获取相对应的字典类型）
	 * @param code  查询的字典编码，就是院内字典编码
	 * @param name  查询的字典名称，就是院内字典名称
	 * @return
	 */
	public static Map<String, String> getFinalDict(String dictType, String code, String name) {
		Map<String, String> map = new HashMap<String, String>();
		//按照字典类型和编码查询

		ResultSet rs = null;
		Statement statement = null;
		Connection conn = null;
		try {
			conn = MysqlUtil.GetConnection();
			statement = conn.createStatement();
			if (StringUtils.isNotBlank(code)) {
				String codeSql = "select * from hdr_md_outer_dict_item a where a.dict_code='" + dictType
						+ "' and (match_codes like '%," + name + ",%' or match_codes like '%," + code + ",%')";
				rs = statement.executeQuery(codeSql);
				while (rs.next()) {
					map.put("code", rs.getString("item_value").replaceAll(" ", "").trim());
					map.put("name", rs.getString("item_meaning").replaceAll(" ", "").trim());
				}
			}
			if (map.isEmpty() || map.size() <= 0) {
				if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(code)) {
					//ICD单独处理
					if (dictType.equals(DictCode.diagnosis)) {
						//code匹配不上用name完全匹配
						String sql = "select * from hdr_md_outer_dict_item a where a.dict_code='" + dictType
								+ "' and match_codes like '%," + name + ",%'";
						if (rs != null) {
							rs.close();
						}
						rs = statement.executeQuery(sql);
						while (rs.next()) {
							map.put("code", rs.getString("item_value").replaceAll(" ", "").trim());
							map.put("name", rs.getString("item_meaning").replaceAll(" ", "").trim());
						}
					}
				}
			}
			if (map.isEmpty()) {
				String nameSql = "select * from hdr_md_outer_dict_item a where a.dict_code='" + dictType
						+ "' and (match_codes like '%," + name + ",%' or match_codes like '%," + code + ",%')";
				if (rs != null) {
					rs.close();
				}
				rs = statement.executeQuery(nameSql);
				while (rs.next()) {
					map.put("code", rs.getString("item_value").replaceAll(" ", "").trim());
					map.put("name", rs.getString("item_meaning").replaceAll(" ", "").trim());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(statement);
			closeConnection(conn);
		}
		return map;
	}

	/**
	 * @Description
	 * 方法描述:传入科室编码查询科室名称
	 * @return 返回类型： String
	 * @param deptCode
	 * @return
	 */
	public static String getDeptName(String deptCode) {
		String result = null;
		if (StringUtils.isBlank(deptCode)) {
			return result;
		}
		ResultSet rs = null;
		Statement statement = null;
		try {
			statement = MysqlUtil.GetConnection().createStatement();
			String sql = "select deptname from security_dept where deptcode = '" + deptCode + "' limit 1";
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(statement);
		}
		return result;
	}

	public static void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
