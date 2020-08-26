package com.goodwill.cda.util;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * ---------------------------------------------------------------- //
 * 文件功能描述：CDA工具类 // 创建人：计彬 // 创建日期：2017/11/02 // 修改人： // 修改日期： //
 * ----------------------------------------------------------------
 */
public class CommonUtils {

	/**
	 * 类描述：将拼接的xml字符串格式化输出为xml格式
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String formatXml(String str) {
		try {
			Document document = DocumentHelper.parseText(str);
			// 格式化输出格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			StringWriter writer = new StringWriter();
			// 格式化输出流
			XMLWriter xmlWriter = new XMLWriter(writer, format);
			// 将document写入到输出流
			xmlWriter.write(document);
			xmlWriter.close();
			return writer.toString();
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * 类描述：出来时间格式 yyyyMMddHHmmss 时间格式库里面格式不统一,只取数字
	 * 
	 * @param date
	 * @return  14位的时间
	 */
	public static String DateFormats(String date) {
		if (StringUtils.isBlank(date)) {
			return "00000000000000";
		}
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(date);
		date = m.replaceAll("").trim();
		if (date.length() > 14) {
			date = date.substring(0, 14);
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(date);
			for (int i = 0; i < 14 - date.length(); i++) {
				sb = sb.append("0");
			}
			date = sb.toString();
		}
		return date;
	}

	/**
	 * 类描述：出来时间格式 yyyyMMddHHmmss 时间格式库里面格式不统一,只取数字
	 * 
	 * @param date
	 * @return  8位的时间
	 */
	public static String DateFormats8(String date) {
		if (StringUtils.isBlank(date)) {
			return "00000000";
		}
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(date);
		date = m.replaceAll("").trim();
		if (date.length() > 8) {
			date = date.substring(0, 8);
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(date);
			for (int i = 0; i < 8 - date.length(); i++) {
				sb = sb.append("0");
			}
			date = sb.toString();
		}
		return date;
	}

	/**
	 * @author 去掉小数位
	 * @param extension
	 * @return String extension
	 */
	public static String format0(String value) {
		String reValue = "0";
		if (StringUtils.isBlank(value)) {
			reValue = "0";
		} else {
			if (isNumeric(value)) {
				double dValue = (new Double(value)).doubleValue();
				reValue = String.format("%.0f", dValue);
			}
		}
		return reValue;
	}

	/**
	 * @author 保留一位小数
	 * @param extension
	 * @return String extension
	 */
	public static String format1(String value) {
		String reValue = "0.0";
		if (StringUtils.isBlank(value)) {
			reValue = "0.0";
		} else {
			if (isNumeric(value)) {
				double dValue = (new Double(value)).doubleValue();
				reValue = String.format("%.1f", dValue);
			}
		}
		return reValue;
	}

	/**
	 * @author 保留两位小数
	 * @param extension
	 * @return String extension
	 */
	public static String format2(String value) {
		String reValue = "0.00";
		if (StringUtils.isBlank(value)) {
			reValue = "0.00";
		} else {
			if (isNumeric(value)) {
				double dValue = Double.parseDouble(value);//(new Double(value)).doubleValue();
				reValue = String.format("%.2f", dValue);
			} else {
				reValue = "0.00";
			}
		}
		return reValue;
	}

	/**
	 * @author 保留一位小数
	 * @param extension
	 * @return String extension
	 */
	public static String format(String value) {
		String reValue = "0.0";
		if (StringUtils.isBlank(value)) {
			reValue = "0.0";
		} else {
			if (isNumeric(value)) {
				double dValue = (new Double(value)).doubleValue();
				reValue = String.format("%.1f", dValue);
			}
		}
		return reValue;
	}

	/**
	 * @author zhouweibin 保留四位小数
	 * @param extension
	 * @return String extension
	 */
	public static String format4(String value) {
		String reValue = "0.0000";
		if (StringUtils.isBlank(value)) {
			reValue = "0.0000";
		} else {
			if (isNumeric(value)) {
				double dValue = (new Double(value)).doubleValue();
				reValue = String.format("%.4f", dValue);
			}
		}
		return reValue;
	}

	/**
	 * 方法描述：处理不同长度取值问题
	 * 
	 * @param value
	 *            传入值
	 * @param lengt
	 *            要截取长度
	 * @param type
	 *            截取类别code数字 name中文（因为处理编号与报告类别长度 数值类型不同，所以加一个标识）
	 * @return
	 */
	public static String formetlength(String value, int length) {
		StringBuilder sb = new StringBuilder(value);
		// 如果为空返回 默认值
		if (StringUtils.isBlank(value)) {
			return value = getUUID().substring(0, 18);
		} else {
			// 长度小于一百 从后往前取
			if (length < 100) {
				if (value.length() < length) {
					value = sb.reverse().toString();
					value = value.substring(0, value.length());
				} else {
					value = sb.reverse().toString();
					value = value.substring(0, length);
				}
			} else {
				if (value.length() < length) {
					value = value.substring(0, value.length());
				} else {

					value = value.substring(0, length);
				}
			}
		}

		return value;
	}

	// 逻辑值true或 false数据映射
	public static String formetTF(String value) {
		return StringUtils.isBlank(value) ? "false" : "true";
	}

	//截取HDR_EMR_CONTENT病历文书中的MR_CONTENT_TEXT获取相应的内容
	public static String getMRContentText(String content, String strStart, String strEnd) {
		String strContent = "";
		if (content.indexOf(strStart) > -1 && content.indexOf(strEnd) > -1) {
			strContent = content.substring(content.indexOf(strStart) + strStart.length(), content.lastIndexOf(strEnd));
		}
		return strContent;
	}

	/**
	 * 格式化时间
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, String>> formatList(List<Map<String, String>> list) {

		List<Map<String, String>> listto2 = new ArrayList<Map<String, String>>();
		String dates[] = new String[] { "ADMISSION_TIME", "DISCHARGE_TIME", "DIAGNOSIS_TIME", "OUT_CP_DATE",
				"IN_CP_DATE", "DEATH_TIME", "EXAM_APPOINT_TIME", "APPLY_TIME", "PLAN_EXAM_TIME", "RIS_CHECK_TIME",
				"REPORT_TIME", "REPORT_CONFIRM_TIME", "ABORT_DATETIME", "SAMPLE_APPOINT_TIME", "PLAN_SAMPLE_TIME",
				"SAMPLE_TIME", "SPEC_CONFIRM_TIME", "LAB_PERFORMED_TIME", "REPORT_CONFIRM_TIME",
				"REPORT_ABORT_DATETIME", "STOP_ORDER_TIME", "CANCEL_TIME", "ORDER_CONFIRM_TIME", "ORDER_DELETE_TIME",
				"PRESC_TIME", "ORDER_TIME", "ORDER_END_TIME", "ORDER_BEGIN_TIME", "CREATE_DATE_TIME",
				"LAST_MODIFY_DATE_TIME", "CAPTION_DATE_TIME", "FIRST_MR_SIGN_DATE_TIME", "QC_DATE", "ARCHIVING_DATE" };
		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> listMap = new HashMap<String, String>();
			Map<String, String> maps = iterator.next();
			for (int i = 0; i < dates.length; i++) {
				if (StringUtils.isNotBlank(maps.get(dates[i]))) {
					maps.put(dates[i], DateFormats(maps.get(dates[i])));
					listMap.putAll(maps);
				} else {
					listMap.putAll(maps);
				}
			}
			if (StringUtils.isNotBlank(maps.get("DATE_OF_BIRTH"))) {
				try {
					maps.put("DATE_OF_BIRTH", maps.get("DATE_OF_BIRTH").replace("-", "").substring(0, 8));
					listMap.putAll(maps);
				} catch (Exception e) {
					maps.put("DATE_OF_BIRTH", "00000000");
					listMap.putAll(maps);
				}
			}
			listto2.add(listMap);
		}
		return listto2;
	}

	/**
	 * 处理生日与其他时间需要长度为八位的数据(长度为0)
	 */
	public static String formatBirthday(String date) {
		if (StringUtils.isBlank(date)) {
			return "00000000";
		}
		String regex = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(date);
		if (mat.find()) {
			return "00000000";
		}
		return StringUtils.isBlank(date) ? "00000000" : date.trim().replace("-", "").replace(":", "").substring(0, 8);
	}

	/**
	 * 为空处理
	 * 
	 * @return
	 */
	public static String formatIsblank(String value, String def) {
		return StringUtils.isBlank(value) ? def : value;
	}

	/**
	 * 处理带单位数值 截取数字
	 */
	public static String getStrValue(String val) {
		String strvalue = "0";
		if (StringUtils.isBlank(val)) {
			return strvalue;
		}
		//此判断是为了解决南通妇幼 年龄出现“2 7/12”的情况 
		if (val.contains(" ") && val.contains("/")) {
			String[] split = val.split(" ");
			return split[0];
		}
		String regex = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(val);
		// 如果包含中文 截取年龄数字
		if (mat.find()) {
			if (val.contains("岁")) {
				strvalue = val.substring(0, val.indexOf("岁"));
			} else if (val.contains("月")) {
				strvalue = val.substring(0, val.indexOf("月"));
			} else if (val.contains("天")) {
				strvalue = val.substring(0, val.indexOf("天"));
			}
		} else {
			strvalue = val;
		}
		return getNum(strvalue, "\\d+");
	}

	/**
	 *  除法运算，保留小数
	 * @param a 被除数
	 * @param b 除数
	 * @return 商
	 */
	public static String txfloat(int a, int b) {
		// 自动生成的方法存根
		DecimalFormat df = new DecimalFormat(".0");//设置保留位数
		return df.format((float) a / b);
	}

	/**
	 * 获取 数字  getNum(context, "\\d+")
	 * @Description
	 * 方法描述:
	 * @return 返回类型： String
	 * @param context
	 * @return
	 */
	public static String getNum(String context, String regx) {
		String result = "";
		if (StringUtils.isBlank(context)) {
			return result;
		}
		Pattern pat = Pattern.compile(regx);
		Matcher mat = pat.matcher(context);
		while (mat.find()) {
			result += mat.group();
		}
		return result;
	}

	/**
	 * 处理性别名称，如果不带性则自动拼接
	 */
	public static String getMatchedSexName(String sexName) {
		String strvalue = "";
		if (StringUtils.isBlank(sexName)) {
			return strvalue;
		}
		String regex = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(sexName);
		// 如果包含中文 截取年龄数字
		if (mat.find()) {
			if (!sexName.contains("性")) {
				strvalue = sexName + "性";
			}
		}
		return strvalue;
	}

	/**
	 * 处理带单位数值 截取单位
	 */
	public static String getStrUnit(String val) {
		String strunit = "岁";
		if (StringUtils.isBlank(val)) {
			return strunit;
		}
		String regex = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(val);
		// 如果包含中文 截取年龄单位值
		if (mat.find()) {
			if (val.contains("岁")) {
				strunit = "岁";
			} else if (val.contains("月")) {
				strunit = "月";
			} else if (val.contains("天")) {
				strunit = "天";
			} else if (val.contains("kg")) {
				strunit = "kg";
			}
		}
		return strunit;
	}

	// 随机生成uuid
	public static String getUUID() {
		UUID uid = UUID.randomUUID();
		if (uid.toString().length() > 20) {
			return uid.toString().substring(0, 20);
		} else {
			return uid.toString();
		}
	}

	// 获取当前系统时间
	public static String getSystemTiem() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}

	//获取当前时间
	public static String SystemTiem() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	/** 
	 * 获得字符串中以start开始 end结束 之间的子字符串
	 * @return 
	 */
	public static String getMsg(String content_txt, String start, String end) {
		if (StringUtils.isBlank(content_txt)) {
			return null;
		}
		String rgex = start + "(.*?)" + end;
		ArrayList<String> list = getSubUtil(content_txt, rgex);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/** 
	 * 正则表达式匹配两个指定字符串中间的内容 
	 * @param soap 
	 * @return 
	 */
	public static ArrayList<String> getSubUtil(String soap, String rgex) {
		ArrayList<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile(rgex);// 匹配的模式  
		Matcher m = pattern.matcher(soap);
		while (m.find()) {
			int i = 1;
			list.add(m.group(i));
			i++;
		}
		return list;
	}

	/**
	 * 处理数字   去掉中文 、英文、-、+、±、; 等特殊符号
	 * @Description
	 * 方法描述:
	 * @return 返回类型： String
	 * @param num
	 * @return
	 */
	public static String disposeNum(String num) {
		if (null == num) {
			return "0";
		}
		String regChinese = "[\u4e00-\u9fa5]";
		String noChinese = num.replaceAll(regChinese, "");
		noChinese = noChinese.replaceAll("-", "");
		noChinese = noChinese.replaceAll("\\+", "");
		noChinese = noChinese.replaceAll("±", "");
		noChinese = noChinese.replaceAll("&lt;", "");
		noChinese = noChinese.replaceAll("[a-zA-z&;:]", "");
		noChinese = noChinese.replaceAll(" ", "");
		if (StringUtils.isBlank(noChinese.trim())) {
			return "0";
		}
		return noChinese.trim();
	}

	/**
	 * 去掉文本中的特殊字符串 比如 ：&amp; &lt; 等
	 * @Description
	 * 方法描述:
	 * @return 返回类型： String
	 * @param text
	 * @return
	 */
	public static String takeOutSpecialString(String text) {
		if (StringUtils.isBlank(text)) {
			return text;
		}
		text = text.replaceAll("[↑↓]", "");
		text = text.replaceAll("&lt;", "＜");
		text = text.replaceAll("&gt;", "＞");
		text = text.replaceAll("&nbsp;", "");
		text = text.replaceAll("&amp;", "");
		text = text.replaceAll(">", "＞");
		text = text.replaceAll("<", "＜");
		text = text.replaceAll("\\script1", "");
		text = text.replaceAll("\\script0", "");
		return text;
	}

	/** 
	 * 判断是否为数字
	 * @param soap 
	 * @return 返回true 则是数字或小数
	 * 返回false 则是其他类型
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[0-9]+([.]{1}[0-9]{1,20})?$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/** 
	 * 判断是否为数字
	 * @param soap 
	 * @return 返回true 则是数字或小数
	 * 返回false 则是其他类型
	 */
	public static boolean isNumeric00(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			System.out.println("异常：\"" + str + "\"不是数字/整数...");
			return false;
		}
	}

	/**
	 * 判断字符串是否包含数字
	 * @Description
	 * 方法描述:
	 * @return 返回类型： boolean
	 * @param company
	 * @return
	 */
	public static boolean isContainNumber(String company) {

		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(company);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	* 按指定大小，分隔集合，将集合按规定个数分为n个部分
	* 
	* @param list
	* @param len
	* @return
	*/
	public static <T> List<List<T>> splitList(List<T> list, int len) {
		if (list == null || list.size() == 0 || len < 1) {
			return null;
		}

		List<List<T>> result = new ArrayList<List<T>>();

		int size = list.size();
		int count = (size + len - 1) / len;

		for (int i = 0; i < count; i++) {
			List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}
		return result;
	}

	/**
	 * 获取内容中的数字  
	 * @Description
	 * 方法描述:
	 * @return 返回类型： String
	 * @param context
	 * @param unit
	 * @return
	 */
	public static String getNumber(String context, String unit, String regx1) {
		if (StringUtils.isBlank(context)) {
			context = "";
		} else {
			Pattern p = Pattern.compile(regx1);
			Matcher m = p.matcher(context);
			if (m.find()) {
				String group = m.group(0);
				String[] groups = group.split(unit);
				context = groups[0];
			} else {
				regx1 = "\\d+";
				p = Pattern.compile(regx1);
				m = p.matcher(context);
				if (m.find()) {
					context = m.group(0);
				} else {
					context = "0";
				}
			}
		}
		return context;
	}

	/**
	 * 以天为单位，计算日期
	 * @param date 时间
	 * @param day 移动的天数
	 * @return
	 */
	public static Date moveTime(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);//把日期往后增加一天.整数往后推,负数往前移动
		return date = calendar.getTime(); //这个时间就是日期往后推一天的结果
	}
}
