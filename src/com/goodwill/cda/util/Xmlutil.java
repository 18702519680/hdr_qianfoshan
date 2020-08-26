/**---------------------------------------------------------------- 
 * // Copyright (C) 2016 北京嘉和美康信息技术有限公司版权所有。
 *  // 文件名： Xmlutil.java
 *  // 文件功能描述：xml工具类
 *  // 创建人：计彬
 *  // 创建日期：2016/03/15
 *  // 修改人：
 *  // 修改日期： 
 *  // ----------------------------------------------------------------*/
package com.goodwill.cda.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.goodwill.core.utils.PropertiesUtils;

public class Xmlutil {
	private static String CONFIG_FILE_NAME = "hlht.properties";

	/**
	 * setTexts 给xml添加text值
	 * 
	 * @parameter document，xpath，text
	 * @author 计彬
	 * @since 2016/03/15
	 */
	public static void setTexts(Document document, String xpath, String text, String defaultStr) throws Exception {
		if (StringUtils.isBlank(text)) {
			text = defaultStr;
		}
		@SuppressWarnings("unchecked")
		List<Element> list = document.selectNodes(xpath);
		for (Element aa : list) {
			aa.setText(text);
		}
	}

	public static String MFormats(String time) {
		if (StringUtils.isBlank(time)) {
			return "0";
		}
		if (time.endsWith("°")) {
			time = time.substring(0, time.indexOf("°"));
		}
		return time;
	}

	public static void setTexts(Document document, String xpath, String text) throws Exception {
		if (StringUtils.isBlank(text)) {
			text = "-";
		}
		@SuppressWarnings("unchecked")
		List<Element> list = document.selectNodes(xpath);
		for (Element aa : list) {
			aa.setText(text);
		}
	}

	/**
	 * addAttributes 给xml添加属性值
	 * 
	 * @parameter document，xpath，name,value
	 * @author 计彬
	 * @since 2016/03/15
	 */
	public static void addAttributes(Document document, String xpath, String name, String value) throws Exception {
		if (StringUtils.isBlank(value)) {
			value = "-";
		}
		@SuppressWarnings("unchecked")
		List<Element> list = document.selectNodes(xpath);
		for (Element aa : list) {
			aa.addAttribute(name, value);
		}

	}

	public static void removeXPath(Document document, String xpath) throws Exception {
		/*List<Element> list = document.selectNodes(xpath);
		if (list.size() > 0) {
			list.get(0).getParent().remove(list.get(0));
		}*/
	}

	/**
	 * @Description
	 * 方法描述:xhw 2017年10月28日	如果没有值，传入一个默认值
	 * @return 返回类型： void
	 * @param document
	 * @param xpath
	 * @param name
	 * @param value
	 * @param defaultStr
	 * @throws Exception
	 */
	public static void addAttributes(Document document, String xpath, String name, String value, String defaultStr)
			throws Exception {
		if (StringUtils.isBlank(value)) {
			value = defaultStr;
		}
		@SuppressWarnings("unchecked")
		List<Element> list = document.selectNodes(xpath);
		for (Element aa : list) {
			aa.addAttribute(name, value);
		}

	}

	/**
	 * @author jibin 判断年龄是否为空
	 * @param age
	 * @return String age
	 * @since 2016/8/23
	 */
	public static String AgeIsBlank(String age) {
		return StringUtils.isBlank(age) ? "0岁" : age;
	}

	/**
	 * @author jibin 判断除code以外，所以的编码是否为空
	 * @param extension
	 * @return String extension
	 */
	public static String ExtensionIsBlank(String extension) {
		return StringUtils.isBlank(extension) ? "-" : extension;
	}

	/**
	 * addAttributes 重载
	 * 
	 * @parameter document，xpath，name,value,names,values
	 * @author 计彬
	 * @since 2016/06/30
	 */
	public static void addAttributes(Document document, String xpath, String name, String value, String names,
			String values) throws Exception {
		if (value == null) {
			value = "-";
		} else if (values == null) {
			values = "-";
		}
		@SuppressWarnings("unchecked")
		List<Element> list = document.selectNodes(xpath);
		for (Element aa : list) {
			aa.addAttribute(name, value);
			aa.addAttribute(names, values);
		}

	}

	/**
	 * @author jibin 获取元素标签值 name
	 * @param extension
	 * @return String extension
	 */
	public static void getNames(Document document, String xpath, String text) throws Exception {
		if (StringUtils.isBlank(text)) {
			text = "-";
		}
		@SuppressWarnings("unchecked")
		List<Element> list = document.selectNodes(xpath);
		for (Element aa : list) {
			aa.getName().toString();
		}
	}

	/**
	 * 返回不同的数字长度
	 * @param index
	 * @return
	 */
	public static String Numeral(String index) {
		String str = "0";
		if ("0".equals(index))
			str = "0";
		if ("1".equals(index))
			str = "0.0";
		if ("2".equals(index))
			str = "0.00";
		if ("3".equals(index))
			str = "0.000";
		if ("4".equals(index))
			str = "0.0000";
		return str;
	}

	/**
	 * @author jibin 获取元素标签值value
	 * @param extension
	 * @return String extension
	 */
	public static void getVlaues(Document document, String xpath, String text) throws Exception {
		if (StringUtils.isBlank(text)) {
			text = "-";
		}
		@SuppressWarnings("unchecked")
		List<Element> list = document.selectNodes(xpath);
		for (Element aa : list) {
			aa.getStringValue().toString();
		}
	}

	//TODO 医疗机构组织机构代码
	public static String GetHospitaCode() {
		return PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hospital_id");
	}

	//TODO 医疗机构组织机构名称
	public static String GetHospitaName() {
		return PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hospital_name");
	}

	//TODO 医院OID
	public static String GetHospitalOid() {
		return PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "org_oid");
	}

	//TODO 医疗机构组织机构地址
	public static String GetHospitaAddr() {
		return PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hospital_addr");
	}

	//TODO 医疗机构组织机构地址
	public static String GetHospitalTel() {
		return PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hospital_tel");
	}

	//数字返回 (邮编电话数字类 使用)
	public static String GetNumber(String Number) {
		return StringUtils.isBlank(Number) ? "000000" : Number;

	}

	//处理检验报告单 长度20错误
	public static String formetlength(String value) {
		if (StringUtils.isNotBlank(value) && value.length() > 19) {

			return StringUtils.isBlank(value) ? "00000000000" : value.substring(0, 19);
		}
		return StringUtils.isBlank(value) ? "00000000000" : value.substring(0, value.length());
	}

	//处理超长问题 取最大长度为len
	public static String formetMaxlength(String value, int len) {
		if (StringUtils.isNotBlank(value) && value.length() >= 100) {

			return StringUtils.isBlank(value) ? "未见异常" : value.substring(0, len);
		}
		return StringUtils.isBlank(value) ? "未见异常" : value.substring(0, value.length());
	}

	//逻辑值true或 false数据映射
	public static String formetTF(String value) {
		return StringUtils.isBlank(value) ? "false" : "true";
	}

	//ABO血型 
	public static String BloodAboCODE(String code) {
		return StringUtils.isBlank(code) ? "5" : code;
	}

	public static String BloodAboNAME(String name) {
		return StringUtils.isBlank(name) ? "不详" : name;
	}

	//RH血型
	public static String BloodRHCODE(String code) {
		//hbase中血型代码为0 name也为0
		return "0".equals(code) ? "2" : code;
	}

	public static String BloodRHNAME(String name) {
		//hbase中血型代码为0 name也为0
		return "0".equals(name) ? "2" : "阳性";
	}

	/**
	 * 格式化时间
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
					maps.put(dates[i], CommonUtils.DateFormats(maps.get(dates[i])));
					//listMap.put(dates[i], DateFormats(maps.get(dates[i])));
					listMap.putAll(maps);
				} else {
					listMap.putAll(maps);
				}
			}
			/*if (StringUtils.isNotBlank(maps.get("AGE_VALUE"))) {
				if (maps.get("AGE_VALUE").contains("岁")) {
					maps.put("AGE_VALUE", maps.get("AGE_VALUE").substring(0, maps.get("AGE_VALUE").lastIndexOf("岁")));
					maps.put(
							"AGE_UNIT",
							maps.get("AGE_VALUE").substring(maps.get("AGE_VALUE").lastIndexOf("岁"),
									maps.get("AGE_VALUE").length()));
					listMap.putAll(maps);
				} else if (maps.get("AGE_VALUE").contains("月")) {
					maps.put("AGE_VALUE", maps.get("AGE_VALUE").substring(0, maps.get("AGE_VALUE").lastIndexOf("月")));
					maps.put(
							"AGE_UNIT",
							maps.get("AGE_VALUE").substring(maps.get("AGE_VALUE").lastIndexOf("月"),
									maps.get("AGE_VALUE").lastIndexOf("月") + 1));
					listMap.putAll(maps);
				} else if (maps.get("AGE_VALUE").contains("天")) {
					maps.put(
							"AGE_VALUE",
							maps.get("AGE_VALUE").substring(maps.get("AGE_VALUE").lastIndexOf("天") - 1,
									maps.get("AGE_VALUE").lastIndexOf("天")));
					maps.put(
							"AGE_UNIT",
							maps.get("AGE_VALUE").substring(maps.get("AGE_VALUE").lastIndexOf("天"),
									maps.get("AGE_VALUE").lastIndexOf("天") + 1));
					listMap.putAll(maps);
				}
			}*/
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

		/*if (listto2.size() == 0) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("", "");
			listto2.add(m);
		}*/
		/*for (Map<String, String> map : list) {
			for (int i = 0; i < dates.length; i++) {
				//System.out.println(map.get(dates[i]));
				System.out.println();
				if (StringUtils.isNotBlank(map.get(dates[i]))) {
					map.put(dates[i], DateFormats(map.get(dates[i])));
					list.add(map);
				}
			}
			if (StringUtils.isNotBlank(map.get("AGE_VALUE"))) {
		
			}
		}
		listto2.addAll(list);*/
		return listto2;
	}

	/**
	 * 计算时间差
	 * @param stratTime
	 * @param endTime
	 * @return
	 */
	public static String computationsTime(String stratTime, String endTime) {
		SimpleDateFormat fd = new SimpleDateFormat("yyyyMMddHHmmss");
		String day = "1";
		Date inDate;
		Date outDate;
		try {
			if (StringUtils.isBlank(endTime)) {
				outDate = fd.parse(fd.format(new Date()));
			} else {
				outDate = fd.parse(endTime);
			}
			inDate = fd.parse(stratTime);
			long diff = outDate.getTime() - inDate.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			day = String.valueOf(days);
		} catch (ParseException e) {
			return day;
		}
		return day;

	}

	/**
	 * 截取邮政编码
	 * @Description  不足6位前面补0，长度大于6位截取前6位
	 * 方法描述:
	 * @return 返回类型： String
	 * @param Number
	 * @return
	 */
	public static String GetPostCode(String Number) {
		if (Number == null) {
			Number = "000000";
		} else {
			Number = Number.trim();
		}
		if (Number.length() > 6) {
			Number = Number.substring(0, 6);
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 6 - Number.length(); i++) {
				sb = sb.append("0");
			}
			Number = sb.append(Number).toString();
		}
		return StringUtils.isBlank(Number) ? "000000" : Number;

	}

	/**
	 * 计算时间差    
	 * @param stratTime
	 * @param endTime
	 * @return
	 */
	public static String computationsTime(String stratTime, String endTime, SimpleDateFormat fds, SimpleDateFormat fd) {
		String day = "0";
		Date inDate;
		Date outDate;
		try {
			if (StringUtils.isBlank(endTime)) {
				outDate = fd.parse(fd.format(new Date()));
			} else {
				outDate = fd.parse(fd.format(fds.parse(endTime)));
			}
			inDate = fd.parse(fd.format(fds.parse(stratTime)));
			long diff = outDate.getTime() - inDate.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			day = String.valueOf(days);
		} catch (ParseException e) {
			return day;
		}
		return day;

	}

}
