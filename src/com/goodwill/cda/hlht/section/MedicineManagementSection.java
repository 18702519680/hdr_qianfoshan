package com.goodwill.cda.hlht.section;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.goodwill.cda.hlht.enums.ICodeSystemEnum;
import com.goodwill.cda.util.CommonUtils;

/***
 * 用药管理章节
 * 
 * @Description 类描述：
 * @author liuzhi
 * @Date 2017年11月5日
 * @modify 修改记录：
 *
 */
public class MedicineManagementSection {
	/**
	 * 获取value 标签为 ST类型的 entryRelationship 或者entry 用药管理中 有： 药物用法、药物使用剂量单位
	 * 
	 * @Description 方法描述: 该方法 在用药和用药管理中都可以用
	 * @return 返回类型： String
	 * @param value
	 *            value值
	 * @param medicineEnum
	 *            MedicineEnum.java中定义的枚举
	 * @param isEntryRelationship
	 *            是否是entryRelationship标签
	 * @param isQualifier
	 *            是否在code标签下有 qualifier标签
	 * 
	 * @return
	 */
	public static String getEntryST(String value, ICodeSystemEnum medicineEnum, Boolean isEntryRelationship,
			Boolean isQualifier) {
		StringBuffer sbf = new StringBuffer("<!--" + medicineEnum.getLabel() + "-->");
		sbf.append(isEntryRelationship ? "<entryRelationship typeCode=\"COMP\">" : "<entry>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"" + medicineEnum.getCode()
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
				+ medicineEnum.getLabel() + "\">");
		if (isQualifier) {
			sbf.append("<qualifier>");
			sbf.append("<name displayName=\"" + medicineEnum.getLabel() + "\"/>");
			sbf.append("</qualifier>");
		}
		sbf.append("</code>");
		sbf.append("<value xsi:type=\"ST\">" + value + "</value>");
		sbf.append("</observation>");
		sbf.append(isEntryRelationship ? "</entryRelationship>" : "</entry>");
		return sbf.toString();
	}

	/**
	 * 获取 entry 下的value 为BL 类型的entry ，entry里面可以套 entryRelationship
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： String
	 * @param value
	 *            Boolean 类型的值
	 * @param medicineEnum
	 *            枚举
	 * @param isEntryRelationship
	 *            是否是entryRelationship标签
	 * @param entryOrEntryRelationship
	 *            传递拼接好的 entry 或者entryRelationship
	 * @return
	 */
	public static String getEntryBL(Boolean value, ICodeSystemEnum medicineEnum, Boolean isEntryRelationship,
			List<String> entryOrEntryRelationship) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(isEntryRelationship ? "<entryRelationship typeCode=\"COMP\">" : "<entry>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"" + medicineEnum.getCode() + "\" displayName=\"" + medicineEnum.getLabel()
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		sbf.append("<value xsi:type=\"BL\" value=\"" + value + "\"/>");
		if (!entryOrEntryRelationship.isEmpty()) {
			for (String entry : entryOrEntryRelationship) {
				sbf.append(entry);
			}
		}
		sbf.append("</observation>");
		sbf.append(isEntryRelationship ? "</entryRelationship>" : "</entry>");
		return sbf.toString();
	}

	/**
	 * 获取 value标签为 TS类型的 entryRelationship 或者entry
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： String
	 * @param value
	 *            值
	 * @param medicineEnum
	 *            ICodeSystemEnum 类型的枚举
	 * @param isEntryRelationship
	 *            是否是 entryRelationship 标签
	 * @return
	 */
	public static String getEntryTS(String value, ICodeSystemEnum medicineEnum, Boolean isEntryRelationship) {

		StringBuffer sbf = new StringBuffer("<!--" + medicineEnum.getLabel() + "-->");
		sbf.append(isEntryRelationship ? "<entryRelationship typeCode=\"COMP\">" : "<entry>");

		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"" + medicineEnum.getCode()
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
				+ medicineEnum.getLabel() + "\" />");
		sbf.append("<value xsi:type=\"TS\" value=\"" + value + "\"/>");
		sbf.append("</observation>");
		sbf.append(isEntryRelationship ? "</entryRelationship>" : "</entry>");
		return sbf.toString();
	}

	/**
	 * 获取value 标签为 CD类型的 entryRelationship 或者entry 用药管理中有：
	 * 药物剂型代码、药物使用频率、中药使用类别代码
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： String
	 * @param code
	 *            对照字典后的值 必须传递 如果没有 对照字典 查询默认值 传递 如：中药使用类别 代码中没有使用中药的情况下 传递默认值
	 *            传递code 为 1 displayName 为 未使用
	 * @param displayName
	 *            对照字典后的值 必须传递 如果没有 对照字典 查询默认值 传递
	 * @param medicineEnum
	 *            MedicineEnum.java中定义的枚举
	 * @param isEntryRelationship
	 *            是否是entryRelationship标签
	 * @return
	 */
	public static String getEntryCD(String code, String displayName, ICodeSystemEnum medicineEnum,
			Boolean isEntryRelationship) {
		StringBuffer sbf = new StringBuffer("<!--" + medicineEnum.getLabel() + "-->");
		sbf.append(isEntryRelationship ? "<entryRelationship typeCode=\"COMP\">" : "<entry>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"" + medicineEnum.getCode()
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
				+ medicineEnum.getLabel() + "\"/>");
		sbf.append("<value xsi:type=\"CD\" code=\"" + code + "\" displayName=\"" + displayName + "\" codeSystem=\""
				+ medicineEnum.getCodeSystem() + "\" codeSystemName=\"" + medicineEnum.getCodeSystemName() + "\"/>");
		sbf.append("</observation>");
		sbf.append(isEntryRelationship ? "</entryRelationship>" : "</entry>");
		return sbf.toString();
	}

	/**
	 * 获取value 标签为 PQ类型的 entryRelationship 或者entry
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： String
	 * @param value
	 *            数值类型
	 * @param unit
	 *            单位
	 * @param medicineEnum
	 *            MedicineEnum.java 中定义的枚举
	 * @param isEntryRelationship
	 *            是否是entryRelationship标签
	 * @return
	 */
	public static String getEntryPQ(String value, String unit, ICodeSystemEnum medicineEnum,
			Boolean isEntryRelationship, Boolean needFormatNum, Boolean format2) {
		StringBuffer sbf = new StringBuffer("<!--" + medicineEnum.getLabel() + "-->");
		sbf.append(isEntryRelationship ? "<entryRelationship typeCode=\"COMP\">" : "<entry>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"" + medicineEnum.getCode()
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
				+ medicineEnum.getLabel() + "\"/>");
		try {
			sbf.append("<value xsi:type=\"PQ\" value=\""
					+ (needFormatNum ? (format2 ? CommonUtils.format2(value) : CommonUtils.format4(value)) : value)
					+ "\" unit=\"" + unit + "\"/>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		sbf.append("</observation>");
		sbf.append(isEntryRelationship ? "</entryRelationship>" : "</entry>");
		return sbf.toString();
	}

	/**
	 * 获取药物使用剂量和药物使用途径
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： String
	 * @param code
	 *            药物使用途径代码
	 * @param displayName
	 *            药物使用途径名称
	 * @param value
	 *            药物使用剂量
	 * @param unit
	 *            药物使用剂量单位
	 * @return
	 */
	public static String getRouteCodeAndDoseQuantity(String code, String displayName, String value, String unit) {
		StringBuffer sbf = new StringBuffer("<!--药物使用途径代码-->");
		sbf.append("<routeCode code=\"" + code + "\" displayName=\"" + displayName
				+ "\" codeSystem=\"2.16.156.10011.2.3.1.158\" codeSystemName=\"用药途径代码表\"/>");
		sbf.append("<!--药物使用次剂量-->");
		try {
			if (StringUtils.isBlank(value)) {
				value = "0";
			}
			double dValue = (new Double(value)).doubleValue();
			if (dValue >= 100) {
				dValue = 99;
			}
			sbf.append("<doseQuantity value=\"" + CommonUtils.format2(dValue + "") + "\" unit=\"" + unit + "\"/>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sbf.toString();
	}

	/**
	 * 获取药品代码 及名称
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： String
	 * @param name
	 *            药品名称
	 * @return
	 */
	public static String getConsumable(String name) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<consumable>");
		sbf.append("<manufacturedProduct>");
		sbf.append("<manufacturedLabeledDrug>");
		sbf.append("<!--药品代码及名称(通用名) -->");
		// TODO code 填值是什么？
		sbf.append("<code/>");
		sbf.append("<name>" + name + "</name>");
		sbf.append("</manufacturedLabeledDrug>");
		sbf.append("</manufacturedProduct>");
		sbf.append("</consumable>");
		return sbf.toString();
	}

	/**
	 * 获取用药管理章节的头部
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： String
	 * @return
	 */
	public static String getMedicineManagementHead() {
		StringBuffer sbf = new StringBuffer("<!--用药管理-->");
		sbf.append("<component>");

		sbf.append("<section>");
		sbf.append("<code code=\"18610-6\" codeSystem=\"2.16.840.1.113883.6.1\" displayName=\"MEDICATION ADMINISTERED\" codeSystemName=\"LOINC\"/>");
		sbf.append("<text/>");
		sbf.append("<entry>");
		sbf.append("<substanceAdministration classCode=\"SBADM\" moodCode=\"EVN\">");
		sbf.append("<text/>");
		return sbf.toString();
	}

	/**
	 * 获取 entry 下面是一个procedure标签的方法
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： String
	 * @param value
	 *            值
	 * @param medicineEnum
	 *            ICodeSystemEnum 枚举
	 * @param isEntryRelationship
	 *            是否是entryRelationship标签
	 * @param isProcedure
	 *            是否是procedure标签
	 * @return
	 */
	public static String getEntryProcedureST(String value, ICodeSystemEnum medicineEnum, Boolean isEntryRelationship,
			Boolean isProcedure) {
		StringBuffer sbf = new StringBuffer("<!--" + medicineEnum.getLabel() + "-->");
		sbf.append(isEntryRelationship ? "<entryRelationship typeCode=\"COMP\">" : "<entry>");

		sbf.append(isProcedure ? "<procedure classCode=\"ACT\" moodCode=\"EVN \">"
				: "<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"" + medicineEnum.getCode()
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\""
				+ medicineEnum.getLabel() + "\">");
		sbf.append("</code>");
		sbf.append(isProcedure ? "<text xsi:type=\"ST\">" + value + "</text>" : "<value xsi:type=\"ST\">" + value
				+ "</value>");
		sbf.append(isProcedure ? "</procedure>" : "</observation>");
		sbf.append(isEntryRelationship ? "</entryRelationship>" : "</entry>");
		return sbf.toString();
	}

}
