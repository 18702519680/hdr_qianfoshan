package com.goodwill.cda.hlht.section;

import org.apache.commons.lang.StringUtils;

import com.goodwill.cda.hlht.cda.common.GetCommonData;

/**
 * 实验室章节
 * @author jibin
 *
 */
public class ExperimentSction {
	/**
	 * 实验章节有两种
	 * 第一种<component> 
		<observation classCode="OBS" moodCode="EVN">
		<code code="DE04.30.009.00" codeSystem="2.16.156.10011.2.2.1" codeSystemName="卫生信息数据元目录" displayName="辅助检查结果"/>
		<value xsi:type="ST">肠镜;多发结肠息肉 ;胃镜 食管炎、胃溃疡、浅表性胃炎、十二指肠球炎</value>
		</observation>
		</component>
	 * @param DEcode =DE04.30.009.00
	 * @param displayName = 辅助检查结果
	 * @param Vtext =肠镜;多发结肠息肉 ;胃镜 食管炎、胃溃疡、浅表性胃炎、十二指肠球炎
	 * 第二种 <component>
		<!-- ABO血型 -->
		<observation classCode="OBS" moodCode="EVN">
		<code code="DE04.50.001.00" codeSystem="2.16.156.10011.2.2.1" codeSystemName="卫生信息数据元目录"/>
			<value xsi:type="CD" code="1" displayName="A型" codeSystem="2.16.156.10011.2.3.1.85" codeSystemName="ABO血型代码表"/>
			</observation>
			</component>

	 * @param Vcode=1
	 * @param VdisplayName=A型
	 * @param flag =true false
	 * @return
	 */
	public static String getExperimentSction(String Vtext, String Vcode, String VdisplayName, String VcodeSystemName,
			boolean flag) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<component>");
		sbf.append("<section>");
		sbf.append("<code code=\"30954-2\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"STUDIES SUMMARY\"/>");
		sbf.append("<text/>");
		sbf.append("<entry>");
		sbf.append("<organizer classCode=\"CLUSTER\" moodCode=\"EVN\">");
		sbf.append("<statusCode/>");
		if (flag) {
			sbf.append("<component>");
			sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			sbf.append("<code code=\"DE04.30.010.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"辅助检查项目\"/>");
			sbf.append("<value xsi:type=\"ST\">无</value>");
			sbf.append("</observation>");
			sbf.append("</component>");
			//
			sbf.append("<component>");
			sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			sbf.append("<code code=\"DE04.30.009.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"辅助检查结果\"/>");
			sbf.append("<value xsi:type=\"ST\">" + Vtext + "</value>");
			sbf.append("</observation>");
			sbf.append("</component>");
		} else {
			sbf.append("<component>");
			sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			sbf.append("<code code=\"DE04.50.010.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			sbf.append("<value xsi:type=\"CD\" code=\"" + Vcode + "\" displayName=\"" + VdisplayName
					+ "\" codeSystem=\"2.16.156.10011.2.3.1.250\" codeSystemName=\"" + VcodeSystemName + "\"/>");
			sbf.append("</observation>");
			sbf.append("</component>");
		}
		sbf.append("</organizer>");
		sbf.append("</entry>");
		sbf.append("</section>");
		sbf.append("</component>");
		return sbf.toString();
	}

	public static String getExperimentSctionToGDYFY(String pid, String vid) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<component>");
		sbf.append("<section>");
		sbf.append("<code code=\"30954-2\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"STUDIES SUMMARY\"/>");
		sbf.append("<text/>");
		sbf.append("<entry>");
		sbf.append("<organizer classCode=\"CLUSTER\" moodCode=\"EVN\">");
		sbf.append("<statusCode/>");
		sbf.append("<component>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE04.30.010.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"辅助检查项目\"/>");
		String content = GetCommonData.getEmrDGValue(pid, pid, "", "检查");
		if (StringUtils.isBlank(content)) {
			content = "-";
		}
		sbf.append("<value xsi:type=\"ST\">" + content + "</value>");
		sbf.append("</observation>");
		sbf.append("</component>");
		sbf.append("<component>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE04.30.009.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"辅助检查结果\"/>");
		content = GetCommonData.getEmrDGValue(pid, pid, "", "检查结果");
		if (StringUtils.isBlank(content)) {
			content = "-";
		}
		sbf.append("<value xsi:type=\"ST\">" + content + "</value>");
		sbf.append("</observation>");
		sbf.append("</component>");
		sbf.append("</organizer>");
		sbf.append("</entry>");
		sbf.append("</section>");
		sbf.append("</component>");
		return sbf.toString();
	}
}
