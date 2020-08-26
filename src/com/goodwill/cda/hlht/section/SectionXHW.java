package com.goodwill.cda.hlht.section;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtils;
import com.goodwill.core.utils.PropertiesUtils;

public class SectionXHW {
	public static String propertiesFile = "hlht.properties";

	/**
	 * @Description
	 * 方法描述:拼接科室编码名称以及机构信息
	 * @return 返回类型： String
	 * @param orgCode
	 * @param orgName
	 * @return
	 */
	public static String scopingOrg(String deptCode, String deptName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<participant typeCode=\"PRF\">");
		sb.append("<time/>");
		sb.append("<associatedEntity classCode=\"ASSIGNED\">");
		sb.append("<scopingOrganization> ");
		sb.append("<id root=\"2.16.156.10011.1.26\" extension=\"" + CommonUtils.formatIsblank(deptCode, "-") + "\"/>");
		if (StringUtils.isBlank(deptName)) {
			deptName = CommonUtils.formatIsblank(DictUtils.getDeptName(deptCode), "-");
		}
		sb.append("<name>" + deptName + "</name>  ");
		sb.append("<asOrganizationPartOf>");
		sb.append("<wholeOrganization>");
		sb.append("<id root=\"2.16.156.10011.1.5\" extension=\""
				+ PropertiesUtils.getPropertyValue(propertiesFile, "hospital_id") + "\"/>");
		sb.append("<name>" + PropertiesUtils.getPropertyValue(propertiesFile, "hospital_name") + "</name>");
		sb.append("</wholeOrganization>");
		sb.append("</asOrganizationPartOf>");
		sb.append("</scopingOrganization>");
		sb.append("</associatedEntity>");
		sb.append("</participant>");
		return sb.toString();
	}

	/**
	 * @Description
	 * 方法描述:检查报告显示拼接
	 * @return 返回类型： String
	 * @param exam
	 * @return
	 */
	public static String examRecordStr(Map<String, String> exam) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<component>");
		sbf.append("<section>");
		sbf.append("<code displayName=\"检查报告\"/>");
		sbf.append("<text/>");
		sbf.append("<entry>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
		sbf.append("<code code=\"DE04.50.131.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"检查报告结果-客观所见\"/>  ");
		sbf.append("<value xsi:type=\"ST\">" + exam.get("EXAM_FEATURE") + "</value> ");
		sbf.append("</observation> ");
		sbf.append("</entry>  ");
		sbf.append("<entry> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
		sbf.append("<code code=\"DE04.50.132.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"检查报告结果-主观提示\"/>  ");
		sbf.append("<value xsi:type=\"ST\">" + exam.get("EXAM_DIAG") + "</value> ");
		sbf.append("</observation> ");
		sbf.append("</entry>  ");
		sbf.append("<entry> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
		sbf.append("<code code=\"DE08.10.026.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"检查报告科室\"/>  ");
		sbf.append("<value xsi:type=\"ST\">" + exam.get("APPLY_DEPT_NAME") + "</value> ");
		sbf.append("</observation> ");
		sbf.append("</entry>  ");
		sbf.append("<entry> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
		sbf.append("<code code=\"DE08.10.013.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"检查报告机构名称\"/>  ");
		sbf.append("<value xsi:type=\"ST\">" + PropertiesUtils.getPropertyValue(propertiesFile, "hospital_name")
				+ "</value> ");
		sbf.append("</observation> ");
		sbf.append("</entry>  ");
		sbf.append("<entry> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
		sbf.append("<code code=\"DE06.00.179.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"检查报告备注\"/>  ");
		sbf.append("<value xsi:type=\"ST\">" + exam.get("EXAM_FEATURE") + " </value> ");
		sbf.append("</observation> ");
		sbf.append("</entry> ");
		sbf.append("</section> ");
		sbf.append("</component>");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:治疗计划
	 * @return 返回类型： String
	 * @return
	 */
	public static String curePlanStr(String cureNote, String cureRule) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<!--");
		sbf.append("***********************************************");
		sbf.append("治疗计划章节");
		sbf.append("***********************************************");
		sbf.append("-->");
		sbf.append("<component>");
		sbf.append("<section>");
		sbf.append("<code code=\"18776-5\" displayName=\"TREATMENT PLAN\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		sbf.append("<text/>");
		sbf.append("<!--处方备注信息-->");
		sbf.append("<entry>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE06.00.179.00\" displayName=\"处方备注信息\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		sbf.append("<value xsi:type=\"ST\">" + cureNote + "</value>");
		sbf.append("</observation>");
		sbf.append("</entry>");
		sbf.append("<!--治则治法-->");
		sbf.append("<entry>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE06.00.300.00\" displayName=\"治则治法\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		sbf.append("<value xsi:type=\"ST\">" + cureRule + "</value>");
		sbf.append("</observation>");
		sbf.append("</entry>");
		sbf.append("</section>");
		sbf.append("</component>");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:护理标志信息 下面的应用字段，是支持的信息
	 *    VOMIT_FLAG：呕吐标志
	 *    URINATE_HARD_FLAG：排尿困难标志
	 * @return 返回类型： String
	 * @param nurseFlags
	 * @return
	 */
	public static String nurseFlag(Map<String, String> nurseFlags) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<!--护理标志章节-->  ");
		sbf.append("<component> ");
		sbf.append("  <section> ");
		sbf.append(" <code displayName=\"护理标志\"/>  ");
		sbf.append(" <text/>  ");
		if (nurseFlags.containsKey("VOMIT_FLAG")) {
			sbf.append(" <entry> ");
			sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
			sbf.append("  <code code=\"DE04.01.048.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"呕吐标志\"/>  ");
			sbf.append("  <value xsi:type=\"BL\" value=\"" + checkBLString(nurseFlags.get("VOMIT_FLAG")) + "\"/> ");
			sbf.append("</observation> ");
			sbf.append(" </entry>  ");
		}
		if (nurseFlags.containsKey("URINATE_HARD_FLAG")) {
			sbf.append(" <entry> ");
			sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
			sbf.append("  <code code=\"DE04.01.051.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"排尿困难标志\"/>  ");
			sbf.append("  <value xsi:type=\"BL\" value=\"" + checkBLString(nurseFlags.get("URINATE_HARD_FLAG"))
					+ "\"/> ");
			sbf.append("</observation> ");
			sbf.append(" </entry> ");
		}
		sbf.append("  </section> ");
		sbf.append("</component> ");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:判断传入的字符串与布尔的对照，true的返回true，其他返回false
	 * true和1的都返回 true
	 * @return 返回类型： String
	 * @param valueStr
	 * @return
	 */
	private static String checkBLString(String valueStr) {
		if ("true".equalsIgnoreCase(valueStr) || "1".equals(valueStr)) {
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * @Description
	 * 方法描述:护理记录
	 * @return 返回类型： String
	 * @param nurseLevelCode
	 * @param nurseLevelName
	 * @param nurseTypeCode
	 * @param nurseTypeName
	 * @return
	 */
	public static String nurseRecord(String nurseLevelCode, String nurseLevelName, String nurseTypeCode,
			String nurseTypeName) {
		//1 特级护理
		//1 基础护理
		StringBuffer sbf = new StringBuffer();
		sbf.append("<!--护理记录章节-->");
		sbf.append("<component>");
		sbf.append("<section>");
		sbf.append("<code displayName=\"护理记录\"/>");
		sbf.append("<text/>");
		sbf.append("<entry>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE06.00.211.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"护理等级代码\"/>");
		sbf.append("<value xsi:type=\"CD\" code=\"" + nurseLevelCode + "\" displayName=\"" + nurseLevelName
				+ "\" codeSystem=\"2.16.156.10011.2.3.1.259\" codeSystemName=\"护理等级代码表\"/>");
		sbf.append("</observation>");
		sbf.append("</entry>");
		sbf.append("<entry>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE06.00.212.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"护理类型代码\"/>");
		sbf.append("<value xsi:type=\"CD\" code=\"" + nurseTypeCode + "\" displayName=\"" + nurseTypeName
				+ "\" codeSystem=\"2.16.156.10011.2.3.1.260\" codeSystemName=\"护理类型代码表\"/>");
		sbf.append("</observation>");
		sbf.append("</entry>");
		sbf.append("</section>");
		sbf.append("</component>");
		return sbf.toString();
	}

	public static void checkAndPut(Map<String, String> resultMap, String fieldName, String putValue, String defStr) {
		if (!resultMap.containsKey(fieldName)) {
			if (StringUtils.isBlank(putValue)) {
				resultMap.put(fieldName, defStr);
			} else {
				resultMap.put(fieldName, putValue);
			}
		} else {
			if (defStr.equals(resultMap.get(fieldName)) && StringUtils.isNotBlank(putValue)) {
				resultMap.put(fieldName, putValue);
			}
		}
	}
}
