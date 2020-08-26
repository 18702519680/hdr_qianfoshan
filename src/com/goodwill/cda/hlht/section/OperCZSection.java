package com.goodwill.cda.hlht.section;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtisTools;

/**
 * 传入首页手术数据
 * 
 * @author daijinyan 返回手术操作章节xml
 */
public class OperCZSection {

	/**
	 * @Description 方法描述:手术信息（暂时6中使用，后续有用的类似的进行修改）
	 * @return 返回类型： String
	 * @param operList
	 * @return
	 */
	public static String getOperForExamTemp(List<Map<String, String>> operList) {
		if (operList.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map<String, String> map : operList) {
			sb.append("<entry><procedure classCode=\"PROC\" moodCode=\"EVN\">");
			Map<String, String> operCodeName = DictUtisTools.getOperMap(map.get("OPERATION_CODE"),
					map.get("OPERATION_NAME"));
			// 手术操作编码 和手术名称
			sb.append("<code code=\"" + operCodeName.get("code") + "\" displayName=\"" + operCodeName.get("name")
					+ "\" codeSystem=\"2.16.156.10011.2.3.3.12\" codeSystemName=\"手术(操作)代码表(ICD-9-CM)\"/> ");
			// 手术时间赋值
			sb.append("<statusCode/><effectiveTime value=\"" + CommonUtils.formatBirthday(map.get("OPERATION_DATE"))
					+ "\"/> ");
			// 手术部位 无数据 取默认
			String site_code = map.get("SURGERY_SITE_CODE");
			String site_name = map.get("SURGERY_SITE_NAME");
			Map<String, String> operPosition = DictUtisTools.getOperPosition(site_code, site_name);
			sb.append("<targetSiteCode code=\"" + operPosition.get("code")
					+ "\" codeSystem=\"2.16.156.10011.2.3.1.266\" codeSystemName=\"操作部位代码表\" displayName=\""
					+ operPosition.get("name") + "\"/> ");
			// 手术操作名称赋值
			sb.append(BaseSection.getSimpleSTEntryRelationShip("DE06.00.094.00", "手术（操作）名称",
					CommonUtils.formatIsblank(operCodeName.get("name"), "-")));
			// 介入物名称
			sb.append(BaseSection.getSimpleSTEntryRelationShip("DE08.50.037.00", "介入物名称", "-"));
			// 操作方法描述
			String operationDesc = map.get("OPERATION_DESC");
			if (StringUtils.isBlank(operationDesc)) {
				operationDesc = "-";
			}
			sb.append(BaseSection.getSimpleSTEntryRelationShip("DE06.00.251.00", "操作方法描述", operationDesc));
			// 操作次数
			sb.append(BaseSection.getSimpleSTEntryRelationShip("DE06.00.250.00", "操作次数",
					CommonUtils.formatIsblank(map.get("OPER_NO"), "1")));
			// 麻醉信息
			sb.append(getAneMethodWithPerformer(map.get("ANESTHESIA_METHOD_CODE"), map.get("ANESTHESIA_METHOD_NAME"),
					map.get("ANESTHESIA_DOCTOR_NAME")));
			// 麻醉观察结果
			sb.append(BaseSection.getSimpleSTEntryRelationShip("DE02.10.028.00", "麻醉观察结果", "-"));
			// 麻醉中西医标志代码
			sb.append(getAneChOrWesFlag());
			sb.append("</procedure></entry>");
		}
		String result = sb.toString();
		result = result.replace("<entryRelationship typeCode=\"COMP\" moodCode=\"EVN\">",
				"<entryRelationship typeCode=\"COMP\">");
		return result;
	}

	/**
	 * @Description 方法描述:获取麻醉中西医标志（都是西医麻醉）
	 * @return 返回类型： String
	 * @return
	 */
	private static String getAneChOrWesFlag() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<entryRelationship typeCode=\"COMP\"> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"DE06.00.307.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"麻醉中西医标识代码\"/>");
		sbf.append("<value code=\"1\" codeSystem=\"2.16.156.10011.2.3.2.41\" displayName=\"西医麻醉\" codeSystemName=\"麻醉中西医标识代码表\" xsi:type=\"CD\"/> ");
		sbf.append("</observation> ");
		sbf.append("</entryRelationship>");
		return sbf.toString();
	}

	/**
	 * @Description 方法描述:获取麻醉方式（待麻醉执行医师的）
	 * @return 返回类型： String
	 * @param aneCode
	 * @param aneName
	 * @param performer
	 * @return
	 */
	private static String getAneMethodWithPerformer(String aneCode, String aneName, String performer) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<entryRelationship typeCode=\"COMP\"> ");
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\"> ");
		sbf.append("<code code=\"DE06.00.073.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"麻醉方式代码\"/>  ");
		Map<String, String> aneCodeName = DictUtisTools.getAnesMethod(aneCode, aneName);
		String code = aneCodeName.get("code");
		String name = aneCodeName.get("name");
		code = StringUtils.isBlank(code) ? "9" : code;
		name = StringUtils.isBlank(name) ? "其他麻醉方法" : name;
		sbf.append("<value code=\"" + code + "\" displayName=\"" + name
				+ "\" codeSystem=\"2.16.156.10011.2.3.1.159\" codeSystemName=\"麻醉方法代码表\" xsi:type=\"CD\"/>");
		sbf.append("<!-- 麻醉医师签名 -->");
		sbf.append("<performer> ");
		sbf.append("<assignedEntity>");
		sbf.append("<id/>  ");
		sbf.append("<assignedPerson> ");
		sbf.append("<name>" + CommonUtils.formatIsblank(performer, "-") + "</name> ");
		sbf.append("</assignedPerson> ");
		sbf.append("</assignedEntity>");
		sbf.append("</performer> ");
		sbf.append("</observation> ");
		sbf.append("</entryRelationship>");
		return sbf.toString();
	}
}
