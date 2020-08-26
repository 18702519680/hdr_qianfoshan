package com.goodwill.cda.hlht.section;

import com.goodwill.cda.hlht.enums.VitalSignTypeEnum;

public class VitalSignSection {

	/**
	 * @Description
	 * 方法描述:获取简单的体征entry
	 * @return 返回类型： String
	 * @param vstEnum
	 * @param valueStr
	 * @param unitStr
	 * @return
	 */
	public static String getSimpleVitalSignEntry(VitalSignTypeEnum vstEnum, String valueStr, String unitStr) {
		return VitalSignsEntry(vstEnum.getCode(), vstEnum.getLabel(), valueStr, unitStr);
	}

	/**
	 * 生命体征 ,体温 脉搏 呼吸 心率 身高 体重（血压除外）
	 * code 和 name 对应的 ENUM为 VitalSignTypeEnum
	 * @param code  例：DE04.10.186.00
	 * @param displayName 例：体温（℃）kg
	 * @param value 例：36.0
	 * @param unit 例：℃
	 * @return
	 */
	public static String VitalSignsEntry(String code, String displayName, String value, String unit) {
		StringBuffer vsXml = new StringBuffer();
		vsXml.append("<entry>");
		vsXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		vsXml.append("<code code=\"" + code
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"" + displayName
				+ "\"/>");
		vsXml.append("<value xsi:type=\"PQ\" value=\"" + value + "\" unit=\"" + unit + "\"/>");
		vsXml.append("</observation>");
		vsXml.append("</entry>");
		return vsXml.toString();
	}

	/**
	 * 生命体征，血压
	 * @param value1 收缩压
	 * @param value2 舒张压
	 * @return
	 */
	public static String getVitalSignBPEntry(String value1, String value2) {
		StringBuffer vsXml = new StringBuffer();
		vsXml.append("<entry>");
		vsXml.append("<organizer classCode=\"BATTERY\" moodCode=\"EVN\">");
		vsXml.append("<code displayName=\"血压\"/>");
		vsXml.append("<statusCode/>");
		vsXml.append("<component>");
		vsXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		vsXml.append("<code code=\"DE04.10.174.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"收缩压\"/>");
		vsXml.append("<value xsi:type=\"PQ\" value=\"" + value1 + "\" unit=\"mmHg\"/>");
		vsXml.append("</observation>");
		vsXml.append("</component>");
		vsXml.append("<component>");
		vsXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		vsXml.append("<code code=\"DE04.10.176.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"舒张压\"/>");
		vsXml.append("<value xsi:type=\"PQ\" value=\"" + value2 + "\" unit=\"mmHg\"/>");
		vsXml.append("</observation>");
		vsXml.append("</component>");
		vsXml.append("</organizer>");
		vsXml.append("</entry>");
		return vsXml.toString();
	}

}
