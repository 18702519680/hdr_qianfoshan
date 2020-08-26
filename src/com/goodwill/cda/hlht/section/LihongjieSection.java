package com.goodwill.cda.hlht.section;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtisTools;
import com.goodwill.core.utils.PropertiesUtils;

public class LihongjieSection {

	private static final String propertiesFile = "hlht.properties";

	public static void main(String[] args) {

	}

	/**
	 * 西药处方用药费用
	 * @param costs 在对应的patient_id下的西药处方的用药费用集 
	 * @return
	 */
	public static String drugsCost(List<Map<String, String>> costs) {

		StringBuffer sb = new StringBuffer();
		sb.append("<component>");
		sb.append("<section>");
		sb.append("<code code=\"48768-6\" displayName=\"PAYMENT SOURCES\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		sb.append("<text/>");

		for (Map<String, String> map : costs) {
			StringBuffer sbtmp = new StringBuffer();
			sbtmp.append("<entry>");
			sbtmp.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			sbtmp.append("<code code=\"DE07.00.004.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"处方药品金额\"/>");
			String cost = map.get("CHARGE_FEE_VALUE");
			try {
				cost = CommonUtils.format2(cost);
			} catch (Exception e) {
				e.printStackTrace();
			}
			sbtmp.append("<value xsi:type=\"MO\" value=\"" + cost + "\" currency=\"元\"/>");
			sbtmp.append("</observation>");
			sbtmp.append("</entry>");
			sb.append(sbtmp);
		}
		sb.append("</section>");
		sb.append("</component>");
		return sb.toString();
	}

	/**
	 * 处方用药费用 针对南通妇幼特有
	 * @param costs 在对应的patient_id下的西药处方的用药费用集 
	 * @return
	 */
	public static String drugsCost_NTFY(List<Map<String, String>> costs) {

		StringBuffer sb = new StringBuffer();
		sb.append("<component>");
		sb.append("<section>");
		sb.append("<code code=\"48768-6\" displayName=\"PAYMENT SOURCES\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		sb.append("<text/>");

		for (Map<String, String> map : costs) {
			StringBuffer sbtmp = new StringBuffer();
			sbtmp.append("<entry>");
			sbtmp.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			sbtmp.append("<code code=\"DE07.00.004.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"处方药品金额\"/>");
			String cost = map.get("CLINICAMOUNT");
			cost = CommonUtils.format2(cost);
			sbtmp.append("<value xsi:type=\"MO\" value=\"" + cost + "\" currency=\"元\"/>");
			sbtmp.append("</observation>");
			sbtmp.append("</entry>");
			sb.append(sbtmp);
		}
		sb.append("</section>");
		sb.append("</component>");
		return sb.toString();
	}

	/**
	 * 获取患者基本信息
	 * @param patInfos
	 * @return
	 */
	public static String getBaseInfo(Map<String, String> baseInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("<recordTarget typeCode=\"RCT\" contextControlCode=\"OP\">");
		sb.append("<patientRole classCode=\"PAT\">");
		//门（急）诊号标识
		if (baseInfo.containsKey("OUTP_NO")) {
			sb.append("<id root=\"2.16.156.10011.1.11\" extension=\""
					+ (baseInfo.containsKey("OUTP_NO") ? baseInfo.get("OUTP_NO") : "000000") + "\"/>");
		}
		//住院号标识
		if (baseInfo.containsKey("INP_NO")) {
			sb.append("<id root=\"2.16.156.10011.1.12\" extension=\""
					+ (baseInfo.containsKey("INP_NO") ? baseInfo.get("INP_NO") : "000000") + "\"/>");
		}
		//检查报告单号标识 
		if (baseInfo.containsKey("REPORT_NO")) {
			sb.append("<id root=\"2.16.156.10011.1.32\" extension=\"" + baseInfo.get("REPORT_NO") + "\"/>");
		}
		//电子申请单编号
		if (baseInfo.containsKey("APPLY_NO")) {
			sb.append("<id root=\"2.16.156.10011.1.24\" extension=\"" + baseInfo.get("APPLY_NO") + "\"/>");
		}
		//标本编号标识
		if (baseInfo.containsKey("SAMPLE_NO")) {
			sb.append("<id root=\"2.16.156.10011.1.14\" extension=\"" + baseInfo.get("REPORT_NO") + "\"/>");
		}
		//处方编号     父医嘱唯一标识， 门诊对应于处方号，一个处方上可以有多个药品医嘱
		if (baseInfo.containsKey("PARENT_ORDER_NO")) {
			sb.append("<id root=\"2.16.156.10011.1.20\" extension=\""
					+ (StringUtils.isBlank(baseInfo.get("PARENT_ORDER_NO")) ? "000000" : baseInfo
							.get("PARENT_ORDER_NO")) + "\"/>");
		}
		sb.append("<patient classCode=\"PSN\" determinerCode=\"INSTANCE\">");
		//下面的是身份证号 如果没有 可以不添加
		if (baseInfo.containsKey("ID_CARD_NO")) {
			String id_card = "-";
			if (StringUtils.isNotBlank(baseInfo.get("ID_CARD_NO"))) {
				id_card=baseInfo.get("ID_CARD_NO").trim();
			}
			sb.append("<id root=\"2.16.156.10011.1.3\" extension=\"" + id_card + "\"/>");
		} else {
			sb.append("<id root=\"2.16.156.10011.1.3\" extension=\"-\"/>");
		}
		//患者姓名必须有
		sb.append("<name>"
				+ (baseInfo.containsKey("PERSON_NAME") ? baseInfo.get("PERSON_NAME").toString().trim() : "不详")
				+ "</name>");
		//administrativeGenderCode 节点是必须的 
		Map<String, String> sexMap = DictUtisTools.getSexMap(baseInfo.get("SEX_CODE"), baseInfo.get("SEX_NAME"));
		sb.append("<administrativeGenderCode code=\"" + sexMap.get("code") + "\" displayName=\"" + sexMap.get("name")
				+ "\" codeSystem=\"2.16.156.10011.2.3.3.4\" codeSystemName=\"生理性别代码表(GB/T 2261.1)\"/>");
		// 年龄值
		String ageValue = baseInfo.get("AGE_VALUE");
		if (StringUtils.isBlank(ageValue)) {
			ageValue = baseInfo.get("AGE");
		}
		//年龄单位
		String ageUnit = baseInfo.get("AGE_UNIT");
		if (StringUtils.isBlank(ageUnit)) {
			ageUnit = baseInfo.get("AGE");
		}
		sb.append("<age unit=\"" + CommonUtils.getStrUnit(ageUnit) + "\"  value=\"" + CommonUtils.getStrValue(ageValue)
				+ "\"/>");
		//下面 的节点 是必须的 
		sb.append("</patient>");
		sb.append("<providerOrganization>");
		sb.append("<id root=\"2.16.156.10011.1.26\"/>");
		sb.append("<name>" + baseInfo.get("ORDER_DEPT_NAME") + "</name>");
		sb.append("<asOrganizationPartOf>");
		sb.append("<wholeOrganization>");
		sb.append("<id root=\"2.16.156.10011.1.5\" extension=\""
				+ PropertiesUtils.getPropertyValue(propertiesFile, "hospital_id") + "\"/>");
		sb.append("<name>" + PropertiesUtils.getPropertyValue(propertiesFile, "hospital_name") + "</name>");
		sb.append("</wholeOrganization>");
		sb.append("</asOrganizationPartOf>");
		sb.append("</providerOrganization>");
		sb.append("</patientRole>");
		sb.append("</recordTarget>");
		return sb.toString();
	}

}
