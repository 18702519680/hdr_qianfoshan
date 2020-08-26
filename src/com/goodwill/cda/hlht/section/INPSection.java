package com.goodwill.cda.hlht.section;

import java.util.Map;

import com.goodwill.cda.hlht.enums.VisitEffectiveTimeEnum;
import com.goodwill.cda.util.CommonUtils;
import com.goodwill.core.utils.PropertiesUtils;

/**
 * @Description 类描述：住院相关Section汇集集合
 * @author xiehongwei
 * @Date 2017年11月6日
 * @modify 修改记录：
 *
 */
public class INPSection {
	public static String propertiesFile = "hlht.properties";

	/**
	 * 患者就诊信息
	 * 
	 * @param patientId
	 * @param visitId
	 * @return modify by liuzhi 2017-11-09 增加医疗机构节点
	 */
	public static String getVisitInfo(Map<String, String> visitInfoMap, boolean admissionClass,
			VisitEffectiveTimeEnum vtEnum) {
		StringBuffer visitInfoXml = new StringBuffer();
		visitInfoXml.append("<!--文档中医疗卫生事件的就诊场景,即入院场景记录-->");
		visitInfoXml.append("<componentOf typeCode=\"COMP\">");
		visitInfoXml.append("<!--就诊-->");
		visitInfoXml.append("<encompassingEncounter classCode=\"ENC\" moodCode=\"EVN\">");
		// 入院途径判断
		if (admissionClass == false) {
			visitInfoXml.append("<code/>");
		} else {
			// TODO 入院途径匹配字典
			visitInfoXml.append("<code code=\"" + visitInfoMap.get("ADMISSION_CLASS_CODE") + "\" displayName=\""
					+ visitInfoMap.get("ADMISSION_CLASS_NAME")
					+ "\" codeSystem=\"2.16.156.10011.2.3.1.270\" codeSystemName=\"入院途径代码表\"/> ");
		}

		if (vtEnum.getCode().equals("01")) {
			visitInfoXml.append("<effectiveTime/>");
		}
		if (vtEnum.getCode().equals("02")) {
			visitInfoXml.append("<effectiveTime nullFlavor=\"NI\"/>");
		}
		if (vtEnum.getCode().equals("03")) {
			visitInfoXml.append("<effectiveTime value=\"" + CommonUtils.DateFormats(visitInfoMap.get("ADMISSION_TIME"))
					+ "\"/>");
		}
		if (vtEnum.getCode().equals("04")) {
			visitInfoXml.append("<effectiveTime xsi:type=\"IVL_TS\" value=\""
					+ CommonUtils.DateFormats(visitInfoMap.get("ADMISSION_TIME")) + "\"/>");
		}
		if (vtEnum.getCode().equals("05")) {
			visitInfoXml.append("<effectiveTime>");
			visitInfoXml.append("<!--入院日期--> ");
			visitInfoXml.append("<low value=\"" + CommonUtils.DateFormats(visitInfoMap.get("ADMISSION_TIME")) + "\"/>");
			visitInfoXml.append("<!--出院日期-->");
			visitInfoXml.append("<high value=\"" + CommonUtils.DateFormats(visitInfoMap.get("DISCHARGE_TIME"))
					+ "\"/> ");
			visitInfoXml.append("</effectiveTime>");
		}

		if (vtEnum.getCode().equals("06")) {
			visitInfoXml.append("<effectiveTime>");
			visitInfoXml.append("<!--入院日期--> ");
			visitInfoXml.append("<low value=\"" + CommonUtils.DateFormats(visitInfoMap.get("ADMISSION_TIME")) + "\"/>");
			visitInfoXml.append("<!--实际住院天数-->");
			visitInfoXml.append("<width value=\""
					+ CommonUtils.formatIsblank(visitInfoMap.get("IN_HOSPITAL_DAYS"), "0") + "\" unit = \"天\"/> ");
			visitInfoXml.append("</effectiveTime>");
		}

		visitInfoXml.append("<location typeCode=\"LOC\">");
		visitInfoXml.append("<healthCareFacility classCode=\"SDLOC\">");
		visitInfoXml.append("<!--机构角色-->");
		visitInfoXml.append("<serviceProviderOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">");
		visitInfoXml.append("<asOrganizationPartOf classCode=\"PART\">");
		visitInfoXml.append("<!--病床号：DE01.00.026.00-->");
		visitInfoXml.append("<wholeOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">");
		visitInfoXml.append("<id root=\"2.16.156.10011.1.22\" extension=\""
				+ CommonUtils.formatIsblank(visitInfoMap.get("BED_LABEL"), "-") + "\"/>");
		visitInfoXml.append("<name>" + CommonUtils.formatIsblank(visitInfoMap.get("BED_LABEL"), "-") + "</name>");
		visitInfoXml.append("<!--病房号：DE01.00.019.00-->");
		visitInfoXml.append("<asOrganizationPartOf classCode=\"PART\">");
		visitInfoXml.append("<wholeOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">");
		visitInfoXml.append("<!--N:加上OID-->");
		visitInfoXml.append("<id root=\"2.16.156.10011.1.21\" extension=\""
				+ CommonUtils.formatIsblank(visitInfoMap.get("WARD_ADMISSION_TO_CODE"), "-") + "\"/>");
		visitInfoXml.append("<name>" + CommonUtils.formatIsblank(visitInfoMap.get("WARD_ADMISSION_TO_CODE"), "-")
				+ "</name>");
		visitInfoXml.append("<!--病区名称：DE08.10.054.00-->");
		visitInfoXml.append("<asOrganizationPartOf classCode=\"PART\">");
		visitInfoXml.append("<wholeOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">");
		visitInfoXml.append("<!--N:加上OID-->");
		visitInfoXml.append("<id root=\"2.16.156.10011.1.27\" extension=\""
				+ CommonUtils.formatIsblank(visitInfoMap.get("DISTRICT_ADMISSION_TO_CODE"), "-") + "\"/>");
		visitInfoXml.append("<name>" + CommonUtils.formatIsblank(visitInfoMap.get("DISTRICT_ADMISSION_TO_NAME"), "-")
				+ "</name>");
		visitInfoXml.append("<!--科室名称：DE08.10.026.00-->");
		visitInfoXml.append("<asOrganizationPartOf classCode=\"PART\">");
		visitInfoXml.append("<wholeOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">");
		visitInfoXml.append("<!--N:加上OID-->");
		visitInfoXml.append("<id root=\"2.16.156.10011.1.26\" extension=\""
				+ CommonUtils.formatIsblank(visitInfoMap.get("DEPT_ADMISSION_TO_CODE"), "-") + "\"/>");
		visitInfoXml.append("<name>" + CommonUtils.formatIsblank(visitInfoMap.get("DEPT_ADMISSION_TO_NAME"), "-")
				+ "</name>");

		visitInfoXml.append("<asOrganizationPartOf classCode=\"PART\">");
		visitInfoXml.append("<wholeOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">");
		visitInfoXml.append("<id root=\"2.16.156.10011.1.5\" extension=\""
				+ PropertiesUtils.getPropertyValue(propertiesFile, "hospital_id") + "\"/>");
		visitInfoXml.append("<name>" + PropertiesUtils.getPropertyValue(propertiesFile, "hospital_name") + "</name>");
		visitInfoXml.append("</wholeOrganization>");
		visitInfoXml.append("</asOrganizationPartOf>");
		visitInfoXml.append("</wholeOrganization>");
		visitInfoXml.append("</asOrganizationPartOf>");
		visitInfoXml.append("</wholeOrganization>");
		visitInfoXml.append("</asOrganizationPartOf>");
		visitInfoXml.append("</wholeOrganization>");
		visitInfoXml.append("</asOrganizationPartOf>");
		visitInfoXml.append("</wholeOrganization>");
		visitInfoXml.append("</asOrganizationPartOf>");
		visitInfoXml.append("</serviceProviderOrganization>");
		visitInfoXml.append("</healthCareFacility>");
		visitInfoXml.append("</location>");
		visitInfoXml.append("</encompassingEncounter>");
		visitInfoXml.append("</componentOf>");

		return visitInfoXml.toString();
	}

}
