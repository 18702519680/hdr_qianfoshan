package com.goodwill.cda.hlht.section;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.goodwill.cda.util.CommonUtils;
import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * @Description
 * 类描述：护理相关Section操作
 * @author xiehongwei
 * @Date 2017年11月10日
 * @modify
 * 修改记录：
 *
 */
public class NurseSection {

	/**
	 * @Description
	 * 方法描述:获取护理观察、操作的observation
	 * @return 返回类型： String
	 * @param nursItem
	 * @param nursValue
	 * @return
	 */
	public static String getNurseObserEntry(String nursItem, String nursValue) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<entry> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
		sbf.append("<code code=\"DE02.10.031.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"护理观察项目名称\"/>");
		sbf.append("<value xsi:type=\"ST\">" + nursItem + "</value>  ");
		sbf.append("<entryRelationship typeCode=\"COMP\"> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
		sbf.append("<code code=\"DE02.10.028.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"护理观察结果\"/>");
		sbf.append("<value xsi:type=\"ST\">" + nursValue + "</value>");
		sbf.append("</observation> ");
		sbf.append("</entryRelationship>");
		sbf.append("</observation>");
		sbf.append("</entry> ");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:获取每天的护理级别
	 * @return 返回类型： Map<String,String>
	 * @param rowkeyprefix
	 * @return
	 */
	public static Map<String, String> getDayNurseLevel(String rowkeyprefix) {
		List<PropertyFilter> nursFilters = new ArrayList<PropertyFilter>();
		nursFilters.add(new PropertyFilter("ORDER_CLASS_CODE", "STRING", MatchType.EQ.getOperation(), "8"));
		List<Map<String, String>> nursLevels = HbaseCURDUtils
				.findByCondition("HDR_IN_ORDER", rowkeyprefix, nursFilters);
		//处理护理等级，拆分为一天一天的格式
		Map<String, String> result = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		for (Map<String, String> nurseLevel : nursLevels) {
			String levelCodeName = nurseLevel.get("ORDER_ITEM_CODE") + "," + nurseLevel.get("ORDER_ITEM_NAME");
			String start = nurseLevel.get("ORDER_BEGIN_TIME");
			String end = nurseLevel.get("ORDER_END_TIME");
			if (StringUtils.isBlank(start)) {
				continue;
			}
			if (StringUtils.isBlank(end)) {
				result.put(nurseLevel.get("ORDER_BEGIN_TIME"), levelCodeName);
			}
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(sdf.parse(start));
				while (true) {
					result.put(sdf2.format(cal.getTime()), levelCodeName);
					cal.add(Calendar.DAY_OF_YEAR, 1);
					if (sdf2.format(cal.getTime()).compareTo(end) > 0) {
						break;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取每天的护理级别
	 * @return 返回类型： Map<String,String>
	 * @param rowkeyprefix
	 * @return
	 */
	public static Map<String, String> getDayNurseLevel(String rowkeyprefix, String targetDay) {
		List<PropertyFilter> nursFilters = new ArrayList<PropertyFilter>();
		nursFilters.add(new PropertyFilter("ORDER_CLASS_CODE", "STRING", MatchType.EQ.getOperation(), "8"));
		List<Map<String, String>> nursLevels = HbaseCURDUtils
				.findByCondition("HDR_IN_ORDER", rowkeyprefix, nursFilters);
		//处理护理等级，拆分为一天一天的格式
		Map<String, String> result = new HashMap<String, String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (Map<String, String> nurseLevel : nursLevels) {
			if (nurseLevel.get("ORDER_ITEM_NAME").indexOf("特级护理") > -1
					|| nurseLevel.get("ORDER_ITEM_NAME").indexOf("一级护理") > -1
					|| nurseLevel.get("ORDER_ITEM_NAME").indexOf("二级护理") > -1
					|| nurseLevel.get("ORDER_ITEM_NAME").indexOf("三级护理") > -1) {
				String start = nurseLevel.get("ORDER_BEGIN_TIME");
				String end = nurseLevel.get("ORDER_END_TIME");
				if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
					continue;
				}
				try {
					if (start.replace("-", "").contains(targetDay) || end.replace("-", "").contains(targetDay)) {
						result.put("code", nurseLevel.get("ORDER_ITEM_CODE"));
						result.put("name", nurseLevel.get("ORDER_ITEM_NAME"));
					} else {
						Date dateStart = df.parse(start);
						Date dateEnd = df.parse(end);
						Date dateDay = sdf.parse(targetDay);
						if (dateStart.before(dateDay) && dateEnd.after(dateDay)) {
							result.put("code", nurseLevel.get("ORDER_ITEM_CODE"));
							result.put("name", nurseLevel.get("ORDER_ITEM_NAME"));
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

	public static String getNurseOperEntry(String nurseOperName, Map<String, String> operItemValue) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<entry> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
		sbf.append("<code code=\"DE06.00.342.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"护理操作名称\"/>");
		sbf.append("<value xsi:type=\"ST\">" + CommonUtils.formatIsblank(nurseOperName, "无") + "</value> ");
		if (operItemValue.isEmpty()) {
			operItemValue.put("无", "无");
		}
		for (Map.Entry<String, String> entry : operItemValue.entrySet()) {
			sbf.append("<entryRelationship typeCode=\"COMP\"> ");
			sbf.append(" <observation classCode=\"OBS\" moodCode=\"EVN\"> ");
			sbf.append("<code code=\"DE06.00.210.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"护理操作项目类目名称\"/>  ");
			sbf.append("<value xsi:type=\"ST\">" + CommonUtils.formatIsblank(entry.getKey(), "无") + "</value>  ");
			sbf.append("<entryRelationship typeCode=\"COMP\"> ");
			sbf.append("  <observation classCode=\"OBS\" moodCode=\"EVN\"> ");
			sbf.append(" <code code=\"DE06.00.209.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"护理操作结果\"/>  ");
			sbf.append(" <value xsi:type=\"ST\">" + CommonUtils.formatIsblank(entry.getKey(), "无") + "</value> ");
			sbf.append("  </observation> ");
			sbf.append("</entryRelationship> ");
			sbf.append(" </observation> ");
			sbf.append("  </entryRelationship>");
		}
		sbf.append(" </observation> ");
		sbf.append("</entry>");
		return sbf.toString();
	}
}
