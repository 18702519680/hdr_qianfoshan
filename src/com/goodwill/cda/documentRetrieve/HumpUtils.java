package com.goodwill.cda.documentRetrieve;

/**
 * @Description
 * 类描述：工具类
 * @author jibin
 * @Date 2016年1月17日
 * @modify
 * 修改记录：
 */
public class HumpUtils {
	/**
	 * transform 转换驼峰命名
	 * @return String
	 * @author jibin
	 * @throws  
	 * @since 2016/07/28
	 */
	public static String transForm(String str) {
		String[] t = str.split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < t.length; i++) {
			if (i == 0)
				sb.append(t[i].toLowerCase());
			else
				sb.append(t[i].substring(0, 1).toUpperCase() + t[i].substring(1).toLowerCase());
		}
		return sb.toString();
	}

	/**
	 * ToHump 首字母小写其余全大写
	 * @return String
	 * @author jibin
	 * @throws  
	 * @since 2016/07/22
	 */
	public static String ToHump(String str) {
		if (str.indexOf("_") == -1)
			str = str.toLowerCase();
		else
			str = (str.substring(0, str.indexOf("_"))).toLowerCase() + ""
					+ str.substring(str.indexOf("_"), str.length()).replace("_", "");
		return str;
	}
}
