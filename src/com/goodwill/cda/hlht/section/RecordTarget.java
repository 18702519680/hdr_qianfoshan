package com.goodwill.cda.hlht.section;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictCode;
import com.goodwill.cda.util.DictUtils;
import com.goodwill.cda.util.DictUtisTools;

/**
 * @Description
 * 类描述：XML头部文档记录对象（患者）相关信息生成,个性化的在此处拼接
 * @author xiehongwei
 * @Date 2017年11月9日
 * @modify
 * 修改记录：
 *
 */
public class RecordTarget {

	public static String genRecordTargetForAdmRec(Map<String, String> inpSummary) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<recordTarget typeCode=\"RCT\" contextControlCode=\"OP\">");
		sbf.append("<patientRole classCode=\"PAT\"> ");
		sbf.append(genIdInfos("2.16.156.10011.1.19", "-"));
		//住院号
		sbf.append(genIdInfos("2.16.156.10011.1.12", CommonUtils.formatIsblank(inpSummary.get("INP_NO"), "-")));
		//现住址
		sbf.append(genAddrInfo(inpSummary));
		sbf.append(" <patient classCode=\"PSN\" determinerCode=\"INSTANCE\"> ");
		//身份证号
		sbf.append(genIdInfos("2.16.156.10011.1.3", CommonUtils.formatIsblank(inpSummary.get("ID_CARD_NO"), "-")));
		//
		sbf.append("<name>" + CommonUtils.formatIsblank(inpSummary.get("PERSON_NAME"), "-") + "</name>");
		// 患者性别必须有，并且要对照字典
		Map<String, String> sexMap = DictUtisTools.getSexMap(inpSummary.get("SEX_CODE"), inpSummary.get("SEX_NAME"));
		sbf.append("<administrativeGenderCode code=\"" + sexMap.get("code") + "\" displayName=\"" + sexMap.get("name")
				+ "\" codeSystem=\"2.16.156.10011.2.3.3.4\" codeSystemName=\"生理性别代码表(GB/T 2261.1)\"/>");
		//婚姻
		Map<String, String> maritalMap = DictUtisTools.getMaritalMap(inpSummary.get("MARITAL_STATUS_CODE"),
				inpSummary.get("MARITAL_STATUS_NAME"));
		sbf.append(genMaritalInfo(maritalMap.get("code"), maritalMap.get("name")));
		//民族
		Map<String, String> nationalMap = DictUtisTools.getNationalMap(inpSummary.get("NATIONALITY_CODE"),
				inpSummary.get("NATIONALITY_NAME"));
		sbf.append(ethnicInfo(nationalMap.get("code"), nationalMap.get("name")));
		// 年龄值
		String ageValue = inpSummary.get("AGE_VALUE");
		if (StringUtils.isBlank(ageValue)) {
			ageValue = inpSummary.get("AGE");
		}
		//年龄单位
		String ageUnit = inpSummary.get("AGE_UNIT");
		if (StringUtils.isBlank(ageUnit)) {
			ageUnit = inpSummary.get("AGE");
		}
		sbf.append("<age unit=\"" + CommonUtils.getStrUnit(ageUnit) + "\"  value=\""
				+ CommonUtils.getStrValue(ageValue) + "\"/>");
		//职业
		sbf.append(occupInfo(inpSummary.get("OCCUPATION_CODE"), inpSummary.get("OCCUPATION_NAME")));
		sbf.append("</patient></patientRole></recordTarget>");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:拼接患者ID类别的节点，root extension
	 * @return 返回类型： String
	 * @param root
	 * @param extension
	 * @return
	 */
	public static String genIdInfos(String root, String extension) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<id root=\"").append(root).append("\" extension=\"").append(extension).append("\"/>");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:获取患者现住址信息字符串
	 * @return 返回类型： String
	 * @param mapWithAddr
	 * @return
	 */
	public static String genAddrInfo(Map<String, String> mapWithAddr) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<!-- 现住址 -->");
		sbf.append("<addr use=\"H\">");
		String mailing_address = mapWithAddr.get("MAILING_ADDRESS");
		if (StringUtils.isBlank(mailing_address)) {
			mailing_address = mapWithAddr.get("MAILING_ADDRESS_NAME");
		}
		sbf.append("<houseNumber>" + mailing_address + "</houseNumber>");
		sbf.append("<streetName>" + CommonUtils.formatIsblank(mapWithAddr.get("MAILING_ADDR_STREET_NAME"), "-")
				+ "</streetName>");
		sbf.append("<township>" + "-" + "</township>");
		sbf.append("<county>" + CommonUtils.formatIsblank(mapWithAddr.get("MAILING_ADDR_COUNTY_NAME"), "-")
				+ "</county>");
		sbf.append("<city>" + CommonUtils.formatIsblank(mapWithAddr.get("MAILING_ADDR_CITY_NAME"), "-") + "</city>");
		sbf.append("<state>" + CommonUtils.formatIsblank(mapWithAddr.get("MAILING_ADDR_PROVINCE_NAME"), "-")
				+ "</state>");
		sbf.append("</addr>");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:拼接婚姻状况处理信息
	 * @return 返回类型： String
	 * @param maritCode
	 * @param maritName
	 * @return
	 */
	public static String genMaritalInfo(String maritCode, String maritName) {
		return genPatCDDealInfo("maritalStatusCode", maritCode, maritName, DictCode.marital, "2.16.156.10011.2.3.3.5",
				"婚姻状况代码表(GB/T 2261.2)");
	}

	/**
	 * @Description
	 * 方法描述:获取民族的处理信息
	 * @return 返回类型： String
	 * @param ethnicCode
	 * @param ethnicName
	 * @return
	 */
	public static String ethnicInfo(String ethnicCode, String ethnicName) {
		return genPatCDDealInfo("ethnicGroupCode", ethnicCode, ethnicName, DictCode.national, "2.16.156.10011.2.3.3.3",
				"民族类别代码表(GB 3304)");
	}

	/**
	 * @Description
	 * 方法描述:获取职业的处理信息
	 * @return 返回类型： String
	 * @param occupCode
	 * @param occupName
	 * @return
	 */
	public static String occupInfo(String occupCode, String occupName) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<occupation>");
		sbf.append(genPatCDDealInfo("occupationCode", occupCode, occupName, DictCode.GBT2261_4_2003,
				"2.16.156.10011.2.3.3.13", "从业状况(个人身体)代码表(GB/T 2261.4)"));
		sbf.append("</occupation>");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:获取患者信息通用方法
	 * @return 返回类型： String
	 * @param oriCode
	 * @param oriName
	 * @param dictStr
	 * @param codeSystem
	 * @param codeSystemName
	 * @return
	 */
	public static String genPatCDDealInfo(String eleName, String oriCode, String oriName, String dictStr,
			String codeSystem, String codeSystemName) {
		StringBuffer sbf = new StringBuffer();
		Map<String, String> mapDict = DictUtils.getFinalDict(dictStr, oriCode, oriName);
		sbf.append("<" + eleName + " code=\"" + mapDict.get("code") + "\" displayName=\"" + mapDict.get("name")
				+ "\" codeSystem=\"" + codeSystem + "\" codeSystemName=\"" + codeSystemName + "\"/>");
		return sbf.toString();
	}

}
