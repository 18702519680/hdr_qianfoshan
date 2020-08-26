package com.goodwill.cda.hlht.section;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.goodwill.cda.hlht.enums.PatIdEleEnum;
import com.goodwill.cda.hlht.enums.ValueTypeEnum;
import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtisTools;
import com.goodwill.core.utils.PropertiesUtils;

public class OtherSection {

	public static String propertiesFile = "hlht.properties";

	/**
	 * 
	 * @Description 方法描述:保管机构
	 * @return 返回类型： String
	 * @return
	 */
	public static String custodian() {
		StringBuffer sb = new StringBuffer();
		sb.append("<custodian typeCode=\"CST\">\n");
		sb.append("<assignedCustodian classCode=\"ASSIGNED\">\n");
		sb.append("<representedCustodianOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">\n");
		sb.append("<id root=\"2.16.156.10011.1.5\" extension=\""
				+ PropertiesUtils.getPropertyValue(propertiesFile, "hospital_id") + "\"/>\n");
		sb.append("<name>" + PropertiesUtils.getPropertyValue(propertiesFile, "hospital_name") + "</name>\n");
		sb.append("</representedCustodianOrganization></assignedCustodian></custodian>\n");
		return sb.toString();
	}

	/**
	 * 
	 * @Description 方法描述:文档作者
	 * @return 返回类型： String
	 * @param time
	 *            文档生成时间
	 * @param code
	 *            文档作者工号
	 * @param name
	 *            文档作者姓名
	 * @return
	 */
	public static String author(String time, String code, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("<author typeCode=\"AUT\" contextControlCode=\"OP\">\n");
		sb.append("<time value=\"" + CommonUtils.DateFormats(time) + "\"/>\n");
		sb.append("<assignedAuthor classCode=\"ASSIGNED\">\n");
		sb.append("<id root=\"2.16.156.10011.1.7\" extension=\"" + CommonUtils.formatIsblank(code, "-") + "\"/>\n");
		sb.append("<assignedPerson> ");
		sb.append(" <name>" + CommonUtils.formatIsblank(name, "-") + "</name>\n");
		sb.append(" </assignedPerson>  </assignedAuthor> </author>\n");
		return sb.toString();
	}

	public static String author1(String time, String code, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("<author typeCode=\"AUT\" contextControlCode=\"OP\">\n");
		sb.append("<time value=\"" + CommonUtils.DateFormats8(time) + "\"/>\n");
		sb.append("<assignedAuthor classCode=\"ASSIGNED\">\n");
		sb.append("<id root=\"2.16.156.10011.1.7\" extension=\"" + CommonUtils.formatIsblank(code, "-") + "\"/>\n");
		sb.append("<assignedPerson> ");
		sb.append(" <name>" + CommonUtils.formatIsblank(name, "-") + "</name>\n");
		sb.append(" </assignedPerson>  </assignedAuthor> </author>\n");
		return sb.toString();
	}

	/**
	 * 
	 * @Description 方法描述:文档作者
	 * @return 返回类型： String
	 * @param time
	 *            文档生成时间
	 * @param code
	 *            文档作者工号
	 * @param name
	 *            文档作者姓名
	 * @return
	 */
	public static String author(String time, String signType, String code, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("<author typeCode=\"AUT\" contextControlCode=\"OP\"> ");
		sb.append("<time value=\"" + CommonUtils.DateFormats(time) + "\"/> ");
		sb.append("<assignedAuthor classCode=\"ASSIGNED\"> ");
		sb.append("<id root=\"2.16.156.10011.1.7\" extension=\"" + CommonUtils.formatIsblank(code, "-") + "\"/> ");
		sb.append("<code displayName=\"" + signType + "\"/>");
		sb.append("<assignedPerson> ");
		sb.append(" <name>" + CommonUtils.formatIsblank(name, "-") + "</name>");
		sb.append(" </assignedPerson>  </assignedAuthor> </author>  ");
		return sb.toString();
	}

	/**
	 * 
	 * @Description 方法描述:签名作者
	 * @return 返回类型： String
	 * @param signName
	 *            签名名称，比如authenticator，legalAuthenticator
	 * @param signType
	 *            签名类型，比如：审核医师签名、检验技师
	 * @param time
	 *            文档生成时间
	 * @param code
	 *            文档作者工号
	 * @param name
	 *            文档作者姓名
	 * @return
	 */
	public static String authorSign(String signName, String signType, String time, String code, String name) {

		StringBuffer sb = new StringBuffer();
		sb.append("<" + signName + ">");
		sb.append("<time value=\"" + CommonUtils.DateFormats(time) + "\"/> ");
		sb.append(" <signatureCode/> ");
		sb.append("<assignedEntity> ");
		sb.append("<id root=\"2.16.156.10011.1.4\" extension=\"" + CommonUtils.formatIsblank(code, "-") + "\"/> ");
		sb.append("<code displayName=\"" + signType + "\"/>  ");
		sb.append("<assignedPerson classCode=\"PSN\" determinerCode=\"INSTANCE\" > ");
		sb.append(" <name>" + CommonUtils.formatIsblank(name, "-") + "</name>");
		sb.append(" </assignedPerson>  </assignedEntity>  </" + signName + ">  ");
		return sb.toString();

	}

	/**
	 * 
	 * @Description 方法描述:签名作者
	 * @return 返回类型： String
	 * @param signName
	 *            签名名称，比如authenticator，legalAuthenticator
	 * @param signType
	 *            签名类型，比如：审核医师签名、检验技师
	 * @param time
	 *            文档生成时间
	 * @param code
	 *            文档作者工号
	 * @param name
	 *            文档作者姓名
	 * @return
	 */
	public static String authorSignAndTitle(String signName, String signType, String time, String code, String name,
			String titleID, String titleName) {

		StringBuffer sb = new StringBuffer();
		sb.append("<" + signName + ">");
		sb.append("<time value=\"" + CommonUtils.DateFormats(time) + "\"/> ");
		sb.append(" <signatureCode/> ");
		sb.append("<assignedEntity> ");
		sb.append("<id root=\"2.16.156.10011.1.4\" extension=\"" + CommonUtils.formatIsblank(code, "-") + "\"/> ");
		sb.append("<code displayName=\"" + signType + "\"/>  ");
		sb.append("<assignedPerson classCode=\"PSN\" determinerCode=\"INSTANCE\" > ");
		sb.append(" <name>" + CommonUtils.formatIsblank(name, "-") + "</name>");
		sb.append("		<professionalTechnicalPosition> ");
		sb.append("			<professionaltechnicalpositionCode code=\"" + titleID
				+ "\" codeSystem=\"2.16.156.10011.2.3.1.209\" codeSystemName=\"专业技术职务类别代码表\" displayName=\""
				+ titleName + "\"/> ");
		sb.append("			</professionalTechnicalPosition> ");
		sb.append(" </assignedPerson>  </assignedEntity>  </" + signName + ">  ");
		return sb.toString();

	}

	public static String authorSign(String signName, String signType, String time, String code, String name,
			boolean timeflag) {
		StringBuffer sb = new StringBuffer();
		sb.append("<!--" + signType + "-->");
		sb.append("<" + signName + ">");
		if (timeflag) {
			sb.append("<time value=\"" + CommonUtils.DateFormats(time) + "\"/> ");
		} else {
			sb.append("<time/>");
		}
		sb.append(" <signatureCode/> ");
		sb.append("<assignedEntity> ");
		sb.append("<id root=\"2.16.156.10011.1.4\" extension=\"" + CommonUtils.formatIsblank(code, "-") + "\"/> ");
		sb.append("<code displayName=\"" + signType + "\"/>  ");
		sb.append("<assignedPerson classCode=\"PSN\" determinerCode=\"INSTANCE\" > ");
		sb.append(" <name>" + CommonUtils.formatIsblank(name, "-") + "</name>");
		sb.append(" </assignedPerson>  </assignedEntity>  </" + signName + ">  ");
		return sb.toString();
	}

	/**
	 * @Description 方法描述:relatedDocumentString
	 * @return 返回类型： String
	 * @return
	 */
	public static String relatedDocumentString() {
		return "<relatedDocument typeCode=\"RPLC\"><parentDocument><id/><setId/><versionNumber/></parentDocument></relatedDocument>";
	}

	public static String componementHead(String codeCode, String displayName) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<component> ");
		sbf.append("<section>");
		sbf.append("<code code=\"" + codeCode + "\" displayName=\"" + displayName
				+ "\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		sbf.append("<text/>  ");
		return sbf.toString();
	}

	public static String endSectionComp() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("</section>");
		sbf.append("</component> ");
		return sbf.toString();
	}

	/**
	 * 获取每一个xml中文档头
	 * 
	 * @param titleName
	 *            文档名称
	 * @param code
	 *            文档对应的code下的code值
	 * @param templateId
	 *            文档模板编号OID 每一个xml都不一样
	 * @return
	 * 修改人：jibin
	 * 文档唯一标识UUID需要外部传入,文档标识要与索引表里面唯一标识保持一致
	 */
	public static String getDocumentHead(String titleName, String code, String templateId, String uuid) {

		if (StringUtils.isEmpty(titleName)) {
			return null;
		}
		if (StringUtils.isEmpty(code)) {
			code = "";
		}

		//String uuid = CommonUtils.getUUID();
		String curtime = CommonUtils.getSystemTiem();
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<ClinicalDocument xmlns:mif=\"urn:hl7-org:v3/mif\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns =\"urn:hl7-org:v3\">\n");
		sb.append("<realmCode code=\"CN\"/>\n");
		sb.append("<typeId root=\"2.16.840.1.113883.1.3\" extension=\"POCD_MT000040\"/>\n");
		sb.append("<templateId root=\"" + templateId + "\"/>\n");
		sb.append("<!-- 文档流水号 -->\n");
		sb.append("<id root=\"2.16.156.10011.1.1\" extension=\"" + uuid + "\"/>\n");
		sb.append("<code code=\"" + code + "\" codeSystem=\"2.16.156.10011.2.4\" codeSystemName=\"卫生信息共享文档规范编码体系\"/>\n");
		sb.append("<title>" + titleName + "</title>\n");
		sb.append("<!-- 文档机器生成时间 -->\n");
		sb.append("<effectiveTime value=\"" + curtime + "\"/>\n");
		sb.append("<!--N-->\n");
		sb.append("<confidentialityCode code=\"N\" codeSystem=\"2.16.840.1.113883.5.25\" codeSystemName=\"Confidentiality\" displayName=\"正常访问保密级别\"/>\n");
		sb.append("<!--N:全部改为zh-CN,ＯＫ-->\n");
		sb.append("<languageCode code=\"zh-CN\"/>\n");
		sb.append("<setId/>\n");
		sb.append("<versionNumber/>\n");

		return sb.toString();
	}

	/**
	 * @Description 方法描述:获取指定id个数的患者基本信息
	 * @return 返回类型： String
	 * @param baseInfo
	 * @param elements
	 * @return
	 */
	public static String createPatientBaseInfo(Map<String, String> baseInfo, PatIdEleEnum[] elements) {
		return createPatientBaseInfo(baseInfo, elements, false);
	}

	/**
	 * @Description 方法描述:获取指定id个数的患者基本信息
	 * @return 返回类型： String
	 * @param baseInfo
	 * @param elements
	 * @return
	 */
	public static String createPatientBaseInfoMZ(Map<String, String> baseInfo, PatIdEleEnum[] elements) {
		return createPatientBaseInfo(baseInfo, elements, true);
	}

	/**
	 * @Description 方法描述:获取指定id个数的患者基本信息
	 * @return 返回类型： String
	 * @param baseInfo
	 * @param elements
	 * @param mzFlag
	 * @return
	 */
	public static String createPatientBaseInfo(Map<String, String> baseInfo, PatIdEleEnum[] elements, boolean mzFlag) {
		StringBuffer bf = new StringBuffer("<recordTarget typeCode=\"RCT\" contextControlCode=\"OP\">");
		bf.append("<patientRole classCode=\"PAT\">");
		PatIdEleEnum idCardEnum = null;
		// 循环需要的编号，进行生成和赋值
		for (PatIdEleEnum ele : elements) {
			if (PatIdEleEnum.ID_CARD_NO.equals(ele)) {// 身份证号存储位置不是这里，特殊处理
				idCardEnum = ele;
				continue;
			}
			String num = baseInfo.get(ele.getCode());
			bf.append("<id root=\"" + ele.getLabel() + "\" extension=\"" + CommonUtils.formatIsblank(num, "-") + "\"/>");
		}
		bf.append("<patient classCode=\"PSN\" determinerCode=\"INSTANCE\">");
		// 身份证号判断需要的话，加入进去
		if (idCardEnum != null) {
			bf.append("<id root=\"2.16.156.10011.1.3\" extension=\""
					+ CommonUtils.formatIsblank(baseInfo.get("ID_CARD_NO"), "-") + "\"/>");
		}
		// 患者姓名必须有
		bf.append("<name>" + CommonUtils.formatIsblank(baseInfo.get("PERSON_NAME"), "-") + "</name>");
		// 患者性别必须有，并且要对照字典
		Map<String, String> sexMap = DictUtisTools.getSexMap(baseInfo.get("SEX_CODE"), baseInfo.get("SEX_CODE"));
		bf.append("<administrativeGenderCode code=\"" + sexMap.get("code") + "\" displayName=\"" + sexMap.get("name")
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
		bf.append("<age unit=\"" + CommonUtils.getStrUnit(ageUnit) + "\"  value=\"" + CommonUtils.getStrValue(ageValue)
				+ "\"/>");

		bf.append("</patient>");

		if (mzFlag) {
			bf.append("<providerOrganization>");
			bf.append("<id root=\"2.16.156.10011.1.26\"/>");
			bf.append("<name>" + CommonUtils.formatIsblank(baseInfo.get("VISIT_DEPT_NAME"), "-") + "</name>");
			bf.append("<asOrganizationPartOf>");
			bf.append("<wholeOrganization>");
			bf.append("<id root=\"2.16.156.10011.1.5\" extension=\""
					+ PropertiesUtils.getPropertyValue(propertiesFile, "hospital_id") + "\" />");
			bf.append("<name>" + PropertiesUtils.getPropertyValue(propertiesFile, "hospital_name") + "</name>");
			bf.append("</wholeOrganization>");
			bf.append("</asOrganizationPartOf>");
			bf.append("</providerOrganization>");
		} else {
			// 机构信息必须有
			bf.append("<providerOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">");
			bf.append("<id root=\"2.16.156.10011.1.5\" extension=\""
					+ PropertiesUtils.getPropertyValue(propertiesFile, "hospital_id") + "\"/>");
			bf.append("<name>" + PropertiesUtils.getPropertyValue(propertiesFile, "hospital_name") + "</name>");
			bf.append("</providerOrganization>");
		}
		bf.append("</patientRole>");
		bf.append("</recordTarget>");
		return bf.toString();
	}

	/**
	 * @Description
	 * 方法描述:返回实验室检查血型 ABO RH的entry字符串
	 * @return 返回类型： String
	 * @return
	 */
	public static String getLabBloodCheckEntry(String aboCode, String aboName, String rhCode, String rhName) {
		StringBuffer sbf = new StringBuffer();
		//TODO 两个血型需要对照字典
		//		血型的单写
		sbf.append("<entry typeCode=\"COMP\">");
		sbf.append("<!-- 血型-->  ");
		sbf.append("<organizer classCode=\"BATTERY\" moodCode=\"EVN\">");
		sbf.append("<statusCode/>  ");
		sbf.append("<component typeCode=\"COMP\"> ");
		sbf.append("<!-- ABO血型 -->  ");
		sbf.append(BaseSection.getTargetObservation("DE04.50.001.00", "ABO血型代码", null, ValueTypeEnum.CD.getCode(),
				aboCode, aboName, "2.16.156.10011.2.3.1.85", "ABO血型代码表"));
		sbf.append("</component>  ");
		sbf.append("<component typeCode=\"COMP\">");
		sbf.append("<!-- Rh血型 -->  ");
		sbf.append(BaseSection.getTargetObservation("DE04.50.010.00", "Rh（D）血型代码", null, ValueTypeEnum.CD.getCode(),
				rhCode, rhName, "2.16.156.10011.2.3.1.250", "Rh(D)血型代码表"));
		sbf.append("</component>  ");
		sbf.append("</organizer>  ");
		sbf.append("</entry>   ");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:主要健康问题章节：一般检查名称与结果；特殊检查名称与结果；
	 * @return 返回类型： String
	 * @param routeExamName
	 * @param routeExamResult
	 * @param specExamName
	 * @param specExamResult
	 * @return
	 */
	public static String getProblemList(String routeExamName, String routeExamResult, String specExamName,
			String specExamResult) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(OtherSection.componementHead("11450-4", "PROBLEM LIST"));
		List<String> ers1 = new ArrayList<String>();
		ers1.add(BaseSection.getSimpleSTEntryRelationShip("DE06.00.281.00", "常规监测项目结果", routeExamResult));
		sbf.append(BaseSection.getEntryRelationShips("DE06.00.216.00", "常规监测项目名称", null, ValueTypeEnum.ST.getCode(),
				routeExamName, null, null, null, ers1, true));
		List<String> ers2 = new ArrayList<String>();
		ers2.add(BaseSection.getSimpleSTEntryRelationShip("DE06.00.216.00", "特殊监测项目结果", specExamResult));
		sbf.append(BaseSection.getEntryRelationShips("DE06.00.216.00", "特殊监测项目名称", null, ValueTypeEnum.ST.getCode(),
				specExamName, null, null, null, ers2, true));
		sbf.append(OtherSection.endSectionComp());
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:获取辅助检查项目、结果
	 * @return 返回类型： String
	 * @return
	 */
	public static String getStudiesSummary(String summaryItem, String summaryResult) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<component>");
		sbf.append("<section>");
		sbf.append("<code code=\"30954-2\" displayName=\"STUDIES SUMMARY\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		sbf.append("<text/>");
		sbf.append("<entry>");
		sbf.append("<organizer classCode=\"CLUSTER\" moodCode=\"EVN\">");
		sbf.append("<statusCode/>");
		sbf.append("<component>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE04.30.010.00\" displayName=\"辅助检查项目\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		sbf.append("<value xsi:type=\"ST\">" + summaryItem + "</value>");
		sbf.append("</observation>");
		sbf.append("</component>");
		sbf.append("<component>");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE04.30.009.00\" displayName=\"辅助检查结果\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		sbf.append("<value xsi:type=\"ST\">" + summaryResult + "</value>");
		sbf.append("</observation>");
		sbf.append("</component>");
		sbf.append("</organizer>");
		sbf.append("</entry>");
		sbf.append("</section>");
		sbf.append("</component>");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:获取联系人参与者的姓名和电话信息
	 * @return 返回类型： String
	 * @param assocName
	 * @param assocTele
	 * @return
	 */
	public static String getPartAssocInfoStr(String assocName, String assocTele) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<participant typeCode=\"NOT\"> ");
		sbf.append("<!--联系人@classCode：CON，固定值，表示角色是联系人 -->");
		sbf.append("<associatedEntity classCode=\"ECON\"> ");
		sbf.append("<!--HDSD00.16.023 DE02.01.010.00 联系人电话号码-->");
		sbf.append("<telecom value=\"" + CommonUtils.formatIsblank(assocTele, "-") + "\"/>");
		sbf.append("<!--联系人-->");
		sbf.append("<associatedPerson>");
		sbf.append("<!--HDSD00.16.024 DE02.01.039.00 联系人姓名-->");
		sbf.append("<name>" + CommonUtils.formatIsblank(assocName, "-") + "</name> ");
		sbf.append("</associatedPerson> ");
		sbf.append("</associatedEntity> ");
		sbf.append("</participant>");
		return sbf.toString();
	}
}
