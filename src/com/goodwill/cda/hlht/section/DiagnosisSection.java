package com.goodwill.cda.hlht.section;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtisTools;
import com.goodwill.core.utils.PropertiesUtils;

/**
 * 
 * @Description 类描述：诊断章节和条目
 * @author malongbiao
 * @Date 2017年11月4日
 * @modify 修改记录：
 *
 */
public class DiagnosisSection {

	/**
	 * 
	 * @Description 方法描述:生成西医相关诊断条目，包括西医初步诊断、修正诊断、确诊诊断
	 * @return 返回类型： String
	 * @param diagMap
	 *            入院主诊断数据Map
	 * @param diagType
	 *            诊断类型，传入DiagTypeEnum的类型的label
	 * @param isMutiObservation
	 *            诊断有两种写法，一种是直接一个observation写多个entryRelationship，
	 *            一种是多个observation
	 * @return
	 */
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();

		System.out.println(westernDiagEntry(map, "aa", false));
	}

	public static String westernDiagEntry(Map<String, String> diagMap, String diagType, boolean isMutiObservation) {
		StringBuffer sb = new StringBuffer();
		if (diagMap != null && !diagMap.isEmpty()) {
			String diagcode = diagMap.get("DIAGNOSIS_CODE");
			String diagname = diagMap.get("DIAGNOSIS_NAME");
			String diagNum = diagMap.get("DIAGNOSIS_NUM");
			Map<String, String> icd10Map = DictUtisTools.getIcd10Map(diagcode, diagname);
			sb.append("<entry>");
			// 多个observation里写诊断代码
			if (isMutiObservation) {
				sb.append("<organizer classCode=\"CLUSTER\" moodCode=\"EVN\">");
				sb.append("<statusCode/>");
				sb.append("<component> <observation classCode=\"OBS\" moodCode=\"EVN\">");
				sb.append("<code code=\"DE05.01.025.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
						+ diagType + "名称\" /> ");
				// sb.append(" <qualifier>  <name displayName=\"主要诊断名称\"/> </qualifier>  </code>");
				// sb.append("<effectiveTime value=\""+CommonUtils.DateFormats(diagMap.get("DIAGNOSIS_TIME"))
				// +"\"/>  ");
				sb.append("<value xsi:type=\"ST\">" + icd10Map.get("name") + "</value> ");
				sb.append(" </observation> </component> ");
				sb.append(" <component> ");
				sb.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
				sb.append("<code code=\"DE05.01.024.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
						+ diagType + "编码\"/>");

				sb.append("<value xsi:type=\"CD\" code=\"" + icd10Map.get("code") + "\" displayName=\""
						+ icd10Map.get("name"));
				sb.append("\" codeSystem=\"2.16.156.10011.2.3.3.11\" codeSystemName=\"ICD-10\"/>");
				sb.append("</observation>	</component></organizer>");
			} else {
				sb.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
				sb.append("<code code=\"DE05.01.025.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
						+ diagType + "名称\"/>");
				sb.append("<effectiveTime value=\"" + CommonUtils.formatBirthday(diagMap.get("DIAGNOSIS_TIME"))
						+ "\"/>");
				sb.append(" <value xsi:type=\"ST\">" + diagname + "</value>  ");
				// 西医诊断编码
				sb.append(diagEntry("E05.01.024.00", diagcode, diagname, diagType, true));
				diagNum(diagNum);
				sb.append("</observation>");
			}

			sb.append("</entry>");
		} else {
			// 如果诊断为空，则生成默认诊断
			sb.append("<entry>");
			sb.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			sb.append("<code code=\"DE05.01.025.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
					+ diagType + "\" />");
			sb.append(" <value xsi:type=\"ST\">无 </value>  ");
			// 西医诊断编码
			nullDiag("E05.01.024.00", false, true, diagType);
			sb.append("</observation>");
			sb.append("</entry>");
		}
		return sb.toString();
	}

	/**
	 * 生成中医诊断
	 * 
	 * @param diagTypeName
	 *            诊断类型，传入DiagTypeEnum的类型的label
	 * @param isMutiObservation
	 *            诊断有两种写法，一种是直接一个observation写多个entryRelationship，
	 *            一种是多个observation
	 * @return
	 */
	public static String ChineseDiagEntry(String diagTypeName, boolean isMutiObservation) {
		StringBuffer diagXml = new StringBuffer();

		diagXml.append("<!--" + diagTypeName + "-中医条目-->");
		diagXml.append("<entry>");
		// 多个observation里写诊断代码
		if (isMutiObservation) {
			diagXml.append("<organizer classCode=\"CLUSTER\" moodCode=\"EVN\">");
			diagXml.append("<statusCode/>");
			diagXml.append("<component> <observation classCode=\"OBS\" moodCode=\"EVN\">");
			diagXml.append("<code code=\"DE05.10.172.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
					+ diagTypeName + "名称\" /> ");
			// sb.append(" <qualifier>  <name displayName=\"主要诊断名称\"/> </qualifier>  </code>");
			// sb.append("<effectiveTime value=\""+CommonUtils.DateFormats(diagMap.get("DIAGNOSIS_TIME"))
			// +"\"/>  ");
			diagXml.append("<value xsi:type=\"ST\">无</value> ");
			diagXml.append(" </observation> </component> ");
			diagXml.append(" <component> ");
			diagXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			diagXml.append("<code code=\"DE05.10.130.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
					+ diagTypeName + "编码\"/>");
			diagXml.append("<value xsi:type=\"CD\" code=\"ZVV\" displayName=\"其他证类\" codeSystem=\"2.16.156.10011.2.3.3.14\" codeSystemName=\"中医病证分类与代码表( GB/T 15657)\" />");

			diagXml.append("</observation>	</component></organizer>");
		} else {
			diagXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			diagXml.append("<code code=\"DE05.10.172.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
					+ diagTypeName + "名称\"/>");
			diagXml.append("<!--" + diagTypeName + "日期-->");
			diagXml.append("<effectiveTime value=\"00000000\"/>");
			diagXml.append("<value xsi:type=\"ST\">无</value>");
			// 中医病名代码
			diagXml.append(nullDiag("DE05.10.130.00", true, true, diagTypeName + "病名"));
			// 中医证候名称
			diagXml.append("<entryRelationship typeCode=\"COMP\"> ");
			diagXml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			diagXml.append("<!--" + diagTypeName + "证候-名称-->");
			diagXml.append("<code code=\"DE05.10.172.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
					+ diagTypeName + "-证候名称\" />");
			diagXml.append("<value xsi:type=\"ST\">无</value>");
			diagXml.append("</observation>");
			diagXml.append("</entryRelationship>");

			// 中医证候编码
			diagXml.append(nullDiag("DE05.10.130.00", true, true, diagTypeName + "证候"));
			// 诊断顺位
			diagNum("1");
			diagXml.append("</observation>");
		}
		diagXml.append("</entry>");

		return diagXml.toString();
	}

	/**
	 * 
	 * @Description 方法描述:生成诊断条目头部
	 * @return 返回类型： String
	 * @param loincCode
	 * @param loincName
	 * @return
	 */

	public static String diagSectionHead(String loincCode, String loincName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<section>");
		sb.append("<code code=\"" + loincCode + "\" displayName=\"" + loincName
				+ "\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		sb.append("<text/>");
		return sb.toString();
	}


	/**
	 * 
	 * @Description 方法描述:生成疾病诊断条目（单条诊断） 如果是单独别的章节中引用诊断条目的，调用此方法
	 * @return 返回类型： String
	 * @param de
	 *            诊断数据元编码
	 * @param diagcode
	 *            诊断编码
	 * @param diagname
	 *            诊断名称
	 * @param diagType
	 *            诊断类型，传入DiagTypeEnum的类型的label
	 * @param isEntryRelationship
	 *            默认生成entry，否则生成entryRelationship 修改人：jibin 这里要去掉疾病诊断映射方法
	 * @return
	 */
	public static String diagEntry(String de, String diagcode, String diagname, String diagType,
			boolean isEntryRelationship) {
		if (StringUtils.isBlank(diagname)) {
			diagname = "无";
		}
		StringBuffer sb = new StringBuffer();
		// sb.append("<!--疾病诊断编码-->");
		if (isEntryRelationship) {
			sb.append("<entryRelationship typeCode=\"COMP\"> ");
		} else {
			sb.append("<entry>");
		}
		sb.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sb.append("<code code=\"" + de
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"" + diagType
				+ "编码\"/>");
		Map<String, String> icd10Map = DictUtisTools.getIcd10Map(diagcode, diagname);
		if (StringUtils.isNotBlank(diagcode)) {
			String code = icd10Map.get("code");
			String name = icd10Map.get("name");
			if (StringUtils.isBlank(code)) {
				code = "-";
			}
			if (StringUtils.isBlank(name)) {
				name = "-";
			}
			sb.append("<value xsi:type=\"CD\" code=\"" + code + "\" displayName=\"" + name);
			sb.append("\" codeSystem=\"2.16.156.10011.2.3.3.11\" codeSystemName=\"ICD-10\"/>");
		} else {
			sb.append("<value xsi:type=\"ST\">" + diagname + "</value>");
		}
		sb.append("</observation>");
		if (isEntryRelationship) {
			sb.append("</entryRelationship> ");
		} else {
			sb.append("</entry>");
		}

		return sb.toString();
	}

	/**
	 * 
	 * @Description 方法描述:诊断顺位
	 * @return 返回类型： String
	 * @param diagNum
	 * @return
	 */
	public static String diagNum(String diagNum) {
		StringBuffer sb = new StringBuffer();
		// 如果诊断序号不为空
		if (StringUtils.isNotBlank(diagNum)) {
			sb.append("<!--顺位-->");
			sb.append("<entryRelationship typeCode=\"COMP\">");
			sb.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			sb.append("<code code=\"DE05.01.080.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"顺位\"/>");
			sb.append("<value xsi:type=\"INT\" value=\"" + String.format("%02d", Integer.parseInt(diagNum)) + "\"/>");// 诊断序号
			sb.append("</observation>");
			sb.append("</entryRelationship>");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Description 方法描述:生成默认空诊断
	 * @return 返回类型： String
	 * @param de
	 *            数据元编码
	 * @param isChinese
	 *            是否中医诊断
	 * @param isEntryRelationship
	 *            是否EntryRelationship
	 * @param diagType
	 *            诊断类型，传入DiagTypeEnum的类型的label
	 * @return
	 */
	public static String nullDiag(String de, boolean isChinese, boolean isEntryRelationship, String diagType) {
		StringBuffer sb = new StringBuffer();
		if (isEntryRelationship) {
			sb.append("<entryRelationship typeCode=\"COMP\"> ");
		} else {
			sb.append("<entry>");
		}
		sb.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sb.append("<code code=\"" + de
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"" + diagType
				+ "\"/>");
		if (isChinese) {
			sb.append("<value xsi:type=\"CD\" code=\"ZVV\" displayName=\"其他证类\" codeSystem=\"2.16.156.10011.2.3.3.14\" codeSystemName=\"中医病证分类与代码表( GB/T 15657)\" />");
		} else {
			sb.append("<value xsi:type=\"CD\" code=\"R52.900\" displayName=\"疼痛\" codeSystem=\"2.16.156.10011.2.3.3.11\" codeSystemName=\"ICD-10\" />");
		}
		sb.append("</observation>");
		if (isEntryRelationship) {
			sb.append("</entryRelationship> ");
		} else {
			sb.append("</entry>");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Description 方法描述:中医“四诊”观察结果
	 * @return 返回类型： String
	 * @return
	 */
	public static String fourChineseDiagEntry() {
		StringBuffer sb = new StringBuffer();
		sb.append("<entry>");
		sb.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sb.append("<code code=\"DE05.01.024.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"疾病诊断编码\"/>");

		sb.append(" <value xsi:type=\"ST\">其他证类</value> ");
		sb.append("</observation>");
		sb.append("</entry>");
		return sb.toString();
	}

	public static String fourChineseDiagEntry(String codeName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<entry>");
		sb.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sb.append("<code code=\"DE05.01.024.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
				+ codeName + "\"/>");

		sb.append(" <value xsi:type=\"ST\">无</value> ");
		sb.append("</observation>");
		sb.append("</entry>");
		return sb.toString();
	}


	public static String propertiesFile = "hlht.properties";

	/**
	 * @Description
	 * 方法描述:获取带performer的诊断
	 * @return 返回类型： String
	 * @param mainDiagInfo
	 * @return
	 */
	public static String getDiagWithPerformerEntry(Map<String, String> mainDiagInfo) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<entry> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE05.01.024.00\" displayName=\"诊断代码\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		sbf.append("<!--诊断日期-->");
		sbf.append("<effectiveTime value=\"" + CommonUtils.formatBirthday(mainDiagInfo.get("DIAGNOSIS_TIME")) + "\"/>");
		Map<String, String> diagMapping = DictUtisTools.getIcd10Map(mainDiagInfo.get("DIAGNOSIS_CODE"),
				mainDiagInfo.get("DIAGNOSIS_NAME"));
		sbf.append("<value xsi:type=\"CD\" code=\"" + diagMapping.get("code") + "\" displayName=\""
				+ diagMapping.get("name") + "\" codeSystem=\"2.16.156.10011.2.3.3.11\" codeSystemName=\"ICD-10\"/>");
		sbf.append("<performer>");
		sbf.append("<assignedEntity>");
		sbf.append("<id/>");
		sbf.append("<representedOrganization>");
		sbf.append("<name>" + PropertiesUtils.getPropertyValue(propertiesFile, "hospital_name") + "</name>");
		sbf.append("</representedOrganization>");
		sbf.append("</assignedEntity>");
		sbf.append("</performer>");
		sbf.append("</observation>");
		sbf.append("</entry>");
		return sbf.toString();
	}
}
