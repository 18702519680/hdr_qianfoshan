package com.goodwill.cda.hlht.section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtisTools;
import com.goodwill.cda.util.Xmlutil;
import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * @author peijiping
 *
 */
public class SectionPJP {

	public static String propertiesFile = "hlht.properties";

	/**
	 * 简版诊断
	 * @param metadataCode DE05.01.024.00
	 * @param diagTypeName 疾病诊断编码
	 * @param diagCode P59.901
	 * @param diagName 新生儿高胆红素血症
	 * @return
	 */
	public static String getDiagEntrySimple(String metadataCode, String diagTypeName, String diagCode, String diagName) {

		// TODO 诊断字典映射
		Map<String, String> diagMatch = DictUtisTools.getIcd10Map(diagCode, diagName);
		StringBuffer diagXml = new StringBuffer();
		diagXml.append("<entry>");
		diagXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		diagXml.append("<code code=\"" + metadataCode
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"" + diagTypeName
				+ "\"/>");
		diagXml.append("<value xsi:type=\"CD\" code=\"" + diagMatch.get("code") + "\" displayName=\""
				+ diagMatch.get("name") + "\" codeSystem=\"2.16.156.10011.2.3.3.11\" codeSystemName=\"ICD-10\"/>");
		diagXml.append("</observation>");
		diagXml.append("</entry>");

		return diagXml.toString();
	}

	/**
	 * 文档主体，主诉、现病史、预防接种史、输血史、个人史、月经史、家族史（既往史除外）
	 * @param code 中文code 例：DE04.01.119.00
	 * @param name 中文名称  例：主诉
	 * @param engCode 英文code 例：10154-3
	 * @param engName 英文名称 例：CHIEF COMPLAINT
	 * @param content 章节内容 例：发现脉搏不齐3年
	 * @return
	 */
	public static String structuredBodyComponent(String code, String name, String engCode, String engName,
			String content) {
		StringBuffer ccXml = new StringBuffer();
		ccXml.append("<!--" + name + "章节-->");
		ccXml.append("<component>");
		ccXml.append("<section>");
		ccXml.append("<code code=\"" + engCode + "\" displayName=\"" + engName
				+ "\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		ccXml.append("<text/>");
		ccXml.append("<entry>");
		ccXml.append("<!-- " + name + "-->");
		ccXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		ccXml.append("<code code=\"" + code
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"" + name + "\"/>");
		ccXml.append("<value xsi:type=\"ST\">" + content + "</value>");
		ccXml.append("</observation>");
		ccXml.append("</entry>");
		ccXml.append("</section>");
		ccXml.append("</component>");

		return ccXml.toString();
	}

	/**
	 * 过敏史章节
	 * @param content  过敏描述
	 * @return
	 */
	public static String guoMinShi(String content) {
		StringBuffer ccXml = new StringBuffer();

		ccXml.append("<!--过敏史章节-->");
		ccXml.append("<component>");
		ccXml.append("<section>");
		ccXml.append("<code code=\"48765-2\" displayName=\"Allergies, adverse reactions, alerts\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		ccXml.append("<text/>");
		ccXml.append("<entry>");
		ccXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		ccXml.append("<code code=\"DE02.10.023.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"过敏史标志\"/>");
		//if (content.contains("无过敏") || content.contains("无")) {
		ccXml.append("<value xsi:type=\"BL\" value=\"false\"/>");
		//		} else {
		//			ccXml.append("<value xsi:type=\"BL\" value=\"true\"/>");
		//		}
		ccXml.append("<entryRelationship typeCode=\"COMP\">");
		ccXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		ccXml.append("<code code=\"DE05.01.022.00\" displayName=\"过敏史\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		ccXml.append("<value xsi:type=\"ST\">" + content + "</value>");
		ccXml.append("</observation>");
		ccXml.append("</entryRelationship>");
		ccXml.append("</observation></entry>");
		ccXml.append("</section>");
		ccXml.append("</component>");

		return ccXml.toString();
	}

	////////////////////////////////////////////////////////////////////////
	//--患者数据查询
	///////////////////////////////////////////////////////////////////////

	/**
	 * 读取医嘱信息
	 * @param pid
	 * @param vid
	 * @return
	 */
	public static List<Map<String, String>> getOrderInfo(String pid, String vid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		List<Map<String, String>> list = CommonUtils.formatList(HbaseCURDUtils.findByCondition("HDR_IN_ORDER",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters));
		//		List<Map<String, String>> list = CommonUtils.formatList(HbaseCURDUtils.findByCondition("HDR_IN_ORDER",
		//				"1000|1.2.156.112636|000616000100|1|75431673|1.2.156.112636.1.2.1", filters));
		//加入医嘱执行数据
		for (Map<String, String> map : list) {
			String orderNo = map.get("ORDER_NO");
			List<PropertyFilter> filters1 = new ArrayList<PropertyFilter>();
			filters1.add(new PropertyFilter("ORDER_NO", "STRING", MatchType.EQ.getOperation(), orderNo));
			List<Map<String, String>> list1 = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_ORDER_EXE",
					HbaseCURDUtils.getRowkeyPrefix(pid), filters1));
			//
			for (Map<String, String> mapPresc : list1) {
				if (mapPresc.get("PRESC_TIME") != null && mapPresc.get("PRESC_NURSE_NAME") != null) {
					map.put("PRESC_TIME", mapPresc.get("PRESC_TIME"));
					map.put("PRESC_NURSE_NAME", mapPresc.get("PRESC_NURSE_NAME"));
					map.put("PRESC_NURSE_CODE", mapPresc.get("PRESC_NURSE_CODE"));
					map.put("PRESC_DEPT_NAME", mapPresc.get("PRESC_DEPT_NAME"));
					break;
				}
			}
			//			if (list1.size() > 0) {
			//				map.put("PRESC_TIME", list1.get(0).get("PRESC_TIME"));
			//				map.put("PRESC_NURSE_NAME", list1.get(0).get("PRESC_NURSE_NAME"));
			//				map.put("PRESC_NURSE_CODE", list1.get(0).get("PRESC_NURSE_CODE"));
			//				map.put("PRESC_DEPT_NAME", list1.get(0).get("PRESC_DEPT_NAME"));
			//			}
		}
		return list;
	}

	/**
	 * 获取药品医嘱
	 * @param pid
	 * @param vid
	 * @return
	 */
	public static List<Map<String, String>> getDrugOrder(String pid, String vid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		filters.add(new PropertyFilter("ORDER_STATUS_NAME", "STRING", MatchType.NOTIN.getOperation(), "撤销"));
		filters.add(new PropertyFilter("ORDER_CLASS_CODE", "STRING", MatchType.IN.getOperation(), "A,a"));
		List<Map<String, String>> list = CommonUtils.formatList(HbaseCURDUtils.findByCondition("HDR_IN_ORDER",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters));

		return list;
	}
	
	/**千佛山
	 * 获取药品医嘱
	 * @param pid
	 * @param vid
	 * @return
	 */
	public static List<Map<String, String>> getDrugOrderQFS(String pid, String vid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		filters.add(new PropertyFilter("ORDER_STATUS_NAME", "STRING", MatchType.EQ.getOperation(), "执行"));
		filters.add(new PropertyFilter("ORDER_CLASS_NAME", "STRING", MatchType.EQ.getOperation(), "西药"));
		List<Map<String, String>> list = CommonUtils.formatList(HbaseCURDUtils.findByCondition("HDR_IN_ORDER",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters));

		return list;
	}

	/**
	 * 获取药品医嘱  广东医附院
	 * @param pid
	 * @param vid
	 * @return
	 */
	public static List<Map<String, String>> getDrugOrder_GDYFY(String pid, String vid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		filters.add(new PropertyFilter("ORDER_STATUS_NAME", "STRING", MatchType.NOTIN.getOperation(), "撤销"));
		filters.add(new PropertyFilter("ORDER_CLASS_CODE", "STRING", MatchType.IN.getOperation(), "C,c"));
		List<Map<String, String>> list = CommonUtils.formatList(HbaseCURDUtils.findByCondition("HDR_IN_ORDER",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters));

		return list;
	}

	/**
	 * 获取针剂医嘱  广东医附院
	 * @param pid
	 * @param vid
	 * @return
	 */
	public static List<Map<String, String>> getDrugOrder_ZJ_GDYFY(String pid, String vid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		filters.add(new PropertyFilter("ORDER_STATUS_NAME", "STRING", MatchType.NOTIN.getOperation(), "撤销"));
		filters.add(new PropertyFilter("ORDER_CLASS_CODE", "STRING", MatchType.IN.getOperation(), "F,f"));
		List<Map<String, String>> list = CommonUtils.formatList(HbaseCURDUtils.findByCondition("HDR_IN_ORDER",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters));

		return list;
	}

	/**
	 * 提取患者生命体征数据，每一项数据都按时间提取第一条数据
	 * 体重，体温，心率、呼吸、脉搏、血压
	 * @param pid
	 * @param vid
	 * @return
	 */
	public static Map<String, Map<String, String>> getPatVitalSign(String pid, String vid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		List<Map<String, String>> patvsList = CommonUtils.formatList(HbaseCURDUtils.findByCondition(
				"HDR_VITAL_MEASURE", HbaseCURDUtils.getRowkeyPrefix(pid), filters));

		//去重，取每个小项最后的报告时间的数据
		Map<String, Map<String, String>> deduplicationMap = new HashMap<String, Map<String, String>>();
		for (Map<String, String> map : patvsList) {
			//String mTime = map.get("MEASURING_TIME");
			String keyString = map.get("VITAL_TYPE_NAME");
			//过滤掉正常值，保留异常值
			if (deduplicationMap.containsKey(keyString)) {
				//取最大报告时间数据
				String currTimeString = deduplicationMap.get(keyString).get("MEASURING_TIME");
				String newTimeString = map.get("MEASURING_TIME");
				//新时间比原有时间小
				if (newTimeString.compareTo(currTimeString) < 0) {
					deduplicationMap.put(keyString, map);
				}
			} else {
				//添加进临时map,同时
				deduplicationMap.put(keyString, map);
			}
		}

		return deduplicationMap;
	}

	///////////////////////////////////////////////////////////

	/**
	 * 获取医嘱执行数据
	 * @param pid
	 * @param vid
	 * @param orderNo
	 * @return
	 */
	public static List<Map<String, String>> getOrderExeList(String pid, String vid, String orderNo) {

		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		if (StringUtils.isNotBlank(orderNo)) {
			filters.add(new PropertyFilter("ORDER_NO", "STRING", MatchType.EQ.getOperation(), orderNo));
		}
		List<Map<String, String>> list = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_ORDER_EXE",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters));
		return list;
	}

	/**
	 * 获取医嘱执行数据
	 * @param pid
	 * @param vid
	 * @param orderNo
	 * @return
	 */
	public static Map<String, String> getOrderExeMap(String pid, String vid, String orderNo) {
		Map<String, String> map = new HashMap<String, String>();

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = getOrderExeList(pid, vid, orderNo);
		if (!list.isEmpty()) {
			map.putAll(list.get(0));
		}
		return map;
	}

}
