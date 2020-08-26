package com.goodwill.cda.hlht.section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtisTools;
import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * 医嘱章节
 * @author peijiping
 *
 */
public class OrderSection {

	/**
	 * 医嘱章节头部
	 * @return
	 */
	public static String orderSectionHead() {

		StringBuffer xml = new StringBuffer();
		xml.append("<code code=\"46209-3\" codeSystem=\"2.16.840.1.113883.6.1\" displayName=\"Provider Orders\" codeSystemName=\"LOINC\"/>");

		return xml.toString();
	}

	//--------------分割线以上的代码为文本性描述医嘱公共方法，以下为格式化数据医嘱-------------------------------------------------------------

	/**
	 * 医嘱类别 
	 * 长期医嘱、临时临时医嘱
	 * @param code 编码
	 * @param name 名称
	 * @return
	 */
	public static String orderClassEntry(String code, String name) {
		StringBuffer xml = new StringBuffer();
		xml.append("<entry><observation classCode=\"OBS\" moodCode=\"EVN\"><code code=\"DE06.00.286.00\" displayName=\"医嘱类别\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>"
				+ "<value xsi:type=\"CD\" code=\""
				+ CommonUtils.formatIsblank(code, "-")
				+ "\" displayName=\""
				+ CommonUtils.formatIsblank(name, "-")
				+ "\" codeSystem=\"2.16.156.10011.2.3.2.58\" codeSystemName=\"医嘱类别代码表\"/>" + "</observation> </entry>");
		return xml.toString();
	}

	/**
	 * 医嘱项目类型
	 * 药品类，检查类，检验类
	 * @param code 编码
	 * @param name 名称
	 * @return
	 */
	public static String orderItemClassComponent(String code, String name) {
		StringBuffer xml = new StringBuffer();
		xml.append("<component>");
		xml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		xml.append("<code code=\"DE06.00.289.00\" displayName=\"医嘱项目类型\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		xml.append("<value xsi:type=\"CD\" code=\"" + CommonUtils.formatIsblank(code, "-") + "\" displayName=\""
				+ CommonUtils.formatIsblank(name, "-")
				+ "\" codeSystem=\"2.16.156.10011.2.3.1.268\" codeSystemName=\"医嘱项目类型代码表\"/>");
		xml.append("</observation>");
		xml.append("</component>");
		return xml.toString();
	}

	/**
	 * 医嘱时间和计划信息
	 * @param beginTime 计划开始时间
	 * @param endTime 计划结束时间
	 * @param content 计划信息
	 * @return
	 */
	public static String orderTimePlanInfo(String beginTime, String endTime, String content) {
		StringBuffer xml = new StringBuffer();
		if (StringUtils.isBlank(beginTime)) {
			beginTime = "00000000000000";
		}
		if (StringUtils.isBlank(endTime)) {
			endTime = "00000000000000";
		}
		if (StringUtils.isBlank(content)) {
			content = "无";
		}
		xml.append("<effectiveTime>");
		xml.append("<!--医嘱计划开始日期时间-->");
		xml.append("<low value=\"" + CommonUtils.DateFormats(beginTime) + "\"/>");
		xml.append("<!--医嘱计划结束日期时间-->");
		xml.append("<high value=\"" + CommonUtils.DateFormats(endTime) + "\"/>");
		xml.append("</effectiveTime>");
		xml.append("<!--医嘱计划信息-->");
		xml.append("<value xsi:type=\"ST\">" + CommonUtils.formatIsblank(content, "-") + "</value>");
		return xml.toString();
	}

	/**
	 * 医嘱时间和计划信息
	 * @param beginTime 计划开始时间
	 * @param endTime 计划结束时间
	 * @param content 计划信息
	 * @return
	 */
	public static String orderAuthor(String orderTime, String doctorCode, String doctorName, String deptName) {
		StringBuffer xml = new StringBuffer();
		if (StringUtils.isBlank(orderTime)) {
			orderTime = "00000000";
		}
		xml.append("<!--作者：医嘱开立者-->");
		xml.append("<author>");
		xml.append("<!--医嘱开立日期时间：DE06.00.220.00-->");
		xml.append("<time value=\"" + CommonUtils.DateFormats(orderTime) + "\"/>");
		xml.append("<assignedAuthor>");
		xml.append("<id root=\"2.16.156.10011.1.4\" extension=\"" + CommonUtils.formatIsblank(doctorCode, "-") + "\"/>");
		xml.append("<code displayName=\"医嘱开立者\"/>");
		xml.append("<!--医嘱开立者签名：DE02.01.039.00-->");
		xml.append("<assignedPerson>");
		xml.append("<name>" + CommonUtils.formatIsblank(doctorName, "-") + "</name>");
		xml.append("</assignedPerson>");
		xml.append("<!--医嘱开立科室：DE08.10.026.00-->");
		xml.append("<representedOrganization>");
		xml.append("<name>" + CommonUtils.formatIsblank(deptName, "-") + "</name>");
		xml.append("</representedOrganization>");
		xml.append("</assignedAuthor>");
		xml.append("</author>");
		return xml.toString();
	}

	/**
	 * 医嘱时间和计划信息
	 * @param beginTime 计划开始时间
	 * @param endTime 计划结束时间
	 * @param content 计划信息
	 * @return
	 */
	public static String orderPerformer(String prescTime, String nurseCode, String nurseName, String deptName) {
		StringBuffer xml = new StringBuffer();
		xml.append("<!--执行者-->");
		xml.append("<performer>");
		xml.append("<!--医嘱执行日期时间：DE06.00.222.00-->");
		if (StringUtils.isBlank(prescTime)) {
			prescTime = "00000000";
		}
		xml.append("<time value=\"" + CommonUtils.DateFormats(prescTime) + "\"/>");
		xml.append("<assignedEntity>");
		xml.append("<id root=\"2.16.156.10011.1.4\" extension=\"" + CommonUtils.formatIsblank(nurseCode, "-") + "\"/>");
		xml.append("<code displayName=\"医嘱执行者\"/>");
		xml.append("<!--医嘱执行者签名：DE02.01.039.00-->");
		xml.append("<assignedPerson>");
		xml.append("<name>" + CommonUtils.formatIsblank(nurseName, "-") + "</name>");
		xml.append("</assignedPerson>");
		xml.append("<!--医嘱执行科室：DE08.10.026.00-->");
		xml.append("<representedOrganization>");
		xml.append("<name>" + CommonUtils.formatIsblank(deptName, "-") + "</name>");
		xml.append("</representedOrganization>");
		xml.append("</assignedEntity>");
		xml.append("</performer>");
		return xml.toString();
	}

	/**
	 * 医嘱 审核、核对、取消、停止
	 * @param time
	 * @param code
	 * @param name
	 * @param deptName
	 */
	public static String orderParticipant(String time, String code, String name, String displayName) {
		StringBuffer xml = new StringBuffer();
		if (StringUtils.isBlank(time)) {
			time = "00000000";
		}
		xml.append("<participant typeCode=\"ATND\">");
		xml.append("<!--日期时间：DE09.00.088.00-->");
		xml.append("<time value=\"" + CommonUtils.DateFormats(time) + "\"/>");
		xml.append("<participantRole classCode=\"ASSIGNED\">");
		xml.append("<id root=\"2.16.156.10011.1.4\" extension=\"" + CommonUtils.formatIsblank(code, "-") + "\"/>");
		xml.append("<!--角色-->");
		xml.append("<code displayName=\"" + CommonUtils.formatIsblank(displayName, "-") + "\"/>");
		xml.append("<!--签名：DE02.01.039.00-->");
		xml.append("<playingEntity classCode=\"PSN\" determinerCode=\"INSTANCE\">");
		xml.append("<name>" + CommonUtils.formatIsblank(name, "-") + "</name>");
		xml.append("</playingEntity>");
		xml.append("</participantRole>");
		xml.append("</participant>");

		return xml.toString();
	}

	/**
	 * 医嘱备注
	 * @param content
	 * @return
	 */
	public static String orderRemark(String content) {
		StringBuffer xml = new StringBuffer();
		xml.append("<!--医嘱备注信息-->");
		xml.append("<entryRelationship typeCode=\"COMP\">");
		xml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		xml.append("<code code=\"DE06.00.179.00\" displayName=\"医嘱备注信息\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		xml.append("<value xsi:type=\"ST\">" + CommonUtils.formatIsblank(content, "无") + "</value>");
		xml.append("</observation>");
		xml.append("</entryRelationship>");

		return xml.toString();
	}

	/**
	 * 医嘱执行
	 * @param status 状态
	 * @param performerXml 是否有执行人xml节点，如果有传xml(调用orderPerformer方法生成)
	 * @return
	 */
	public static String orderExec(String status, String performerXml) {
		StringBuffer xml = new StringBuffer();
		xml.append("<!--医嘱执行状态-->");
		xml.append("<entryRelationship typeCode=\"COMP\">");
		xml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		xml.append("<code code=\"DE06.00.290.00\" displayName=\"医嘱执行状态\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		xml.append("<value xsi:type=\"ST\">" + CommonUtils.formatIsblank(status, "无") + "</value>");
		//医嘱执行者-------------
		if (StringUtils.isNotBlank(performerXml)) {
			xml.append(performerXml);
		}
		//---------
		xml.append("</observation>");
		xml.append("</entryRelationship>");

		return xml.toString();
	}

	/**
	 * 医嘱电子申请单编号
	 * @param orderNo
	 * @return
	 */
	public static String orderApplyNo(String orderNo) {
		StringBuffer xml = new StringBuffer();
		xml.append("<!--电子申请单编号：例如检查检验申请单编号？？？-->");
		xml.append("<entryRelationship typeCode=\"COMP\">");
		xml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		xml.append("<code code=\"DE01.00.008.00\" displayName=\"电子申请单编号\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		xml.append("<value xsi:type=\"ST\">" + CommonUtils.formatIsblank(orderNo, "-") + "</value>");
		xml.append("</observation>");
		xml.append("</entryRelationship>");

		return xml.toString();
	}

	/**
	 * 组药品号
	 * @param groupNo
	 * @return
	 */
	public static String orderDrugGroupNo(String groupNo) {
		StringBuffer xml = new StringBuffer();
		xml.append("<!--处方药品组号：例如如果是用药医嘱的话指向处方单号？？？-->");
		xml.append("<entryRelationship typeCode=\"COMP\">");
		xml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		xml.append("<code code=\"DE08.50.056.00\" displayName=\"处方药品组号\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		xml.append("<value xsi:type=\"ST\">" + CommonUtils.formatIsblank(groupNo, "0") + "</value>");
		xml.append("</observation>");
		xml.append("</entryRelationship>");
		return xml.toString();
	}

	/**
	 * 住院医嘱的医嘱章节拼接方法
	 * @param orderMap
	 * @return
	 */
	public static String inpOrderSction(Map<String, String> orderMap) {

		//TODO 格式化医嘱类别、医嘱项目类型

		StringBuffer xml = new StringBuffer();
		//医嘱章节
		xml.append("<section>");
		xml.append(orderSectionHead());
		xml.append("<text/>");
		//医嘱类别----------
		Map<String, String> yzlb = DictUtisTools.getYZLB(orderMap.get("ORDER_PROPERTIES_CODE"),
				orderMap.get("ORDER_PROPERTIES_NAME"));
		xml.append(orderClassEntry(yzlb.get("code"), yzlb.get("name")));
		//医嘱entry开始
		xml.append("<entry>");
		xml.append("<organizer classCode=\"CLUSTER\" moodCode=\"EVN\">");
		xml.append("<statusCode/>");
		//医嘱项目类型----------
		Map<String, String> orderClassMap = DictUtisTools.getOrderClassMap(orderMap.get("ORDER_CLASS_CODE"),
				orderMap.get("ORDER_CLASS_NAME"));
		xml.append(orderItemClassComponent(orderClassMap.get("code"), orderClassMap.get("name")));

		//医嘱的另一个compont开始
		xml.append("<component>");
		xml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		xml.append("<code code=\"DE06.00.288.00\" displayName=\"医嘱项目内容\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");

		//医嘱时间和计划信息----------
		xml.append(orderTimePlanInfo(orderMap.get("ORDER_BEGIN_TIME"), orderMap.get("ORDER_END_TIME"),
				orderMap.get("ORDER_ITEM_NAME")));

		//医嘱开立者----------
		xml.append(orderAuthor(orderMap.get("ORDER_TIME"), orderMap.get("ORDER_DOCTOR_CODE"),
				orderMap.get("ORDER_DOCTOR_NAME"), orderMap.get("ORDER_DEPT_NAME")));

		//医嘱审核------
		xml.append("<!--医嘱审核-->");
		xml.append(orderParticipant(orderMap.get("ORDER_CONFIRM_TIME"), orderMap.get("ORDER_CONFIRMER_CODE"),
				orderMap.get("ORDER_CONFIRMER_NAME"), "医嘱审核人"));

		//医嘱核对-----
		xml.append("<!--医嘱核对-->");
		xml.append(orderParticipant(orderMap.get("ORDER_CONFIRM_TIME"), orderMap.get("ORDER_CONFIRMER_CODE"),
				orderMap.get("ORDER_CONFIRMER_NAME"), "医嘱核对人"));

		//医嘱停止----
		if (orderMap.get("STOP_ORDER_DOCTOR_CODE") != null) {
			xml.append("<!--医嘱停止-->");
			xml.append(orderParticipant(orderMap.get("ORDER_END_TIME"), orderMap.get("STOP_ORDER_DOCTOR_CODE"),
					orderMap.get("STOP_ORDER_DOCTOR_NAME"), "医嘱停止人"));
		}

		//医嘱取消，可能数据为空要特别处理
		if (orderMap.get("CANCEL_DOCTOR_CODE") != null) {
			xml.append("<!--医嘱取消-->");
			xml.append(orderParticipant(orderMap.get("CANCEL_TIME"), orderMap.get("CANCEL_DOCTOR_CODE"),
					orderMap.get("CANCEL_DOCTOR_NAME"), "医嘱取消人"));
		}
		//备注信息，默认为无-----
		xml.append(orderRemark(orderMap.get("ORDER_NOTE")));

		//医嘱执行-----
		String performer = orderPerformer(orderMap.get("PRESC_TIME"), orderMap.get("PRESC_NURSE_CODE"),
				orderMap.get("PRESC_NURSE_NAME"), orderMap.get("PRESC_DEPT_NAME"));
		xml.append("<!--医嘱执行状态-->");
		xml.append(orderExec(orderMap.get("ORDER_STATUS_NAME"), performer));

		//申请单编号
		xml.append(orderApplyNo(orderMap.get("ORDER_NO")));

		//药品组医嘱号可能为空，特殊处理一下
		xml.append(orderDrugGroupNo(orderMap.get("PARENT_ORDER_NO")));

		xml.append("</observation>");
		xml.append("</component>");

		xml.append("</organizer>");
		xml.append("</entry>");

		xml.append("</section>");
		return xml.toString();
	}

	/**
	 * 门诊医嘱的医嘱章节拼接方法
	 * @param orderMap
	 * @return
	 */
	public static String outOrderSction(Map<String, String> orderMap) {
		//TODO 格式化医嘱类别、医嘱项目类型
		StringBuffer xml = new StringBuffer();
		//医嘱章节
		xml.append("<section>");
		xml.append(orderSectionHead());
		xml.append("<text/>");
		//医嘱entry开始
		xml.append("<entry>");
		xml.append("<organizer classCode=\"CLUSTER\" moodCode=\"EVN\">");
		xml.append("<statusCode/>");
		//医嘱项目类型----------
		Map<String, String> orderClassMap = DictUtisTools.getOrderClassMap(orderMap.get("ORDER_ITEM_CODE"),
				orderMap.get("ORDER_ITEM_NAME"));
		xml.append(orderItemClassComponent(orderClassMap.get("code"), orderClassMap.get("name")));

		//医嘱的另一个compont开始
		xml.append("<component>");
		xml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		xml.append("<code code=\"DE06.00.288.00\" displayName=\"医嘱项目内容\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");

		//医嘱时间和计划信息----------
		xml.append(orderTimePlanInfo(orderMap.get("ORDER_TIME"), orderMap.get("ORDER_TIME"), orderMap.get("ORDER_TEXT")));
		//执行者
		xml.append(orderPerformer(orderMap.get("PRESC_TIME"), orderMap.get("PRESC_NURSE_CODE"),
				orderMap.get("PRESC_NURSE_NAME"), orderMap.get("PRESC_DEPT_NAME")));
		//医嘱开立者----------
		xml.append(orderAuthor(orderMap.get("ORDER_TIME"), orderMap.get("ORDER_DOCTOR_CODE"),
				orderMap.get("ORDER_DOCTOR_NAME"), orderMap.get("ORDER_DEPT_NAME")));

		//医嘱审核------
		xml.append("<!--医嘱审核-->");
		xml.append(orderParticipant(orderMap.get("ORDER_TIME"), orderMap.get("ORDER_DOCTOR_CODE"),
				orderMap.get("ORDER_DOCTOR_NAME"), "医嘱审核人"));

		//医嘱核对-----
		xml.append("<!--医嘱核对-->");
		xml.append(orderParticipant(orderMap.get("ORDER_TIME"), orderMap.get("ORDER_DOCTOR_CODE"),
				orderMap.get("ORDER_DOCTOR_NAME"), "医嘱核对人"));

		//医嘱取消，可能数据为空要特别处理
		xml.append("<!--医嘱取消-->");
		xml.append(orderParticipant(orderMap.get("CANCEL_TIME"), orderMap.get("CANCEL_DOCTOR_CODE"),
				orderMap.get("CANCEL_DOCTOR_NAME"), "医嘱取消人"));
		//备注信息，默认为无-----
		xml.append(orderRemark(orderMap.get("ORDER_NOTE")));
		//医嘱执行-----
		xml.append("<!--医嘱执行状态-->");
		xml.append(orderExec(orderMap.get("PRES_STATUS_NAME"), null));
		xml.append("</observation>");
		xml.append("</component>");
		xml.append("</organizer>");
		xml.append("</entry>");
		xml.append("</section>");
		return xml.toString();
	}

	/**
	 * 住院医嘱的医嘱章节拼接方法
	 * @param 重载
	 * @return
	 */
	public static String inpOrderSction(Map<String, String> orderMap, boolean flag) {

		//TODO 格式化医嘱类别、医嘱项目类型

		StringBuffer xml = new StringBuffer();
		//医嘱章节
		xml.append("<section>");
		xml.append(orderSectionHead());
		xml.append("<text/>");
		xml.append("<entry>");
		//医嘱类别----------
		//医嘱entry开始
		xml.append("<organizer classCode=\"CLUSTER\" moodCode=\"EVN\">");
		xml.append("<statusCode/>");
		//医嘱项目类型----------
		//没有调用医嘱项目类型代码
		Map<String, String> orderClassMap = DictUtisTools.getOrderClassMap(orderMap.get("ORDER_CLASS_CODE"),
				orderMap.get("ORDER_CLASS_NAME"));
		xml.append(orderItemClassComponent(orderClassMap.get("code"), orderClassMap.get("name")));

		//医嘱的另一个compont开始
		xml.append("<component>");
		xml.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		xml.append("<code code=\"DE06.00.288.00\" displayName=\"医嘱项目内容\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");

		//医嘱时间和计划信息----------
		xml.append(orderTimePlanInfo(orderMap.get("ORDER_BEGIN_TIME"), orderMap.get("ORDER_END_TIME"),
				orderMap.get("ORDER_ITEM_NAME")));

		//医嘱开立者----------
		xml.append(orderAuthor(orderMap.get("ORDER_TIME"), orderMap.get("ORDER_DOCTOR_CODE"),
				orderMap.get("ORDER_DOCTOR_NAME"), orderMap.get("ORDER_DEPT_NAME")));

		//医嘱审核------
		xml.append("<!--医嘱审核-->");
		xml.append(orderParticipant(orderMap.get("ORDER_CONFIRM_TIME"), orderMap.get("ORDER_CONFIRMER_CODE"),
				orderMap.get("ORDER_CONFIRMER_NAME"), "医嘱审核人"));

		//医嘱核对-----
		xml.append("<!--医嘱取消-->");
		xml.append(orderParticipant(orderMap.get("CANCEL_TIME"), orderMap.get("CANCEL_DOCTOR_CODE"),
				orderMap.get("CANCEL_DOCTOR_NAME"), "医嘱取消人"));

		//医嘱停止----
		if (orderMap.get("STOP_ORDER_DOCTOR_CODE") != null) {
			xml.append("<!--医嘱停止-->");
			xml.append(orderParticipant(orderMap.get("ORDER_END_TIME"), orderMap.get("STOP_ORDER_DOCTOR_CODE"),
					orderMap.get("STOP_ORDER_DOCTOR_NAME"), "医嘱停止人"));
		}

		//医嘱取消，可能数据为空要特别处理
		if (orderMap.get("CANCEL_DOCTOR_CODE") != null) {
			xml.append("<!--医嘱取消-->");
			xml.append(orderParticipant(orderMap.get("CANCEL_TIME"), orderMap.get("CANCEL_DOCTOR_CODE"),
					orderMap.get("CANCEL_DOCTOR_NAME"), "医嘱取消人"));
		}
		//备注信息，默认为无-----
		xml.append(orderRemark(orderMap.get("ORDER_NOTE")));

		//医嘱执行-----
		String performer = orderPerformer(orderMap.get("PRESC_TIME"), orderMap.get("PRESC_NURSE_CODE"),
				orderMap.get("PRESC_NURSE_NAME"), orderMap.get("PRESC_DEPT_NAME"));
		xml.append("<!--医嘱执行状态-->");
		xml.append(orderExec(orderMap.get("ORDER_STATUS_NAME"), performer));
		if (flag) {
			//申请单编号
			xml.append(orderApplyNo(orderMap.get("ORDER_NO")));
			//药品组医嘱号可能为空，特殊处理一下
			xml.append(orderDrugGroupNo(orderMap.get("PARENT_ORDER_NO")));
		}
		xml.append("</observation>");
		xml.append("</component>");
		xml.append("</organizer>");
		xml.append("</entry>");
		xml.append("</section>");
		return xml.toString();
	}

	/**
	 * @Description
	 * 方法描述:获取每天的输液类的医嘱执行记录：每天取一条
	 * @return 返回类型： Map<String,Map<String,String>>
	 * @param rowkeyprefix
	 * @return
	 */
	public static Map<String, Map<String, String>> getDaySYOrderExe(String rowkeyprefix) {
		List<PropertyFilter> orderExeFilter = new ArrayList<PropertyFilter>();
		orderExeFilter.add(new PropertyFilter("PRESC_WAY_CODE", "STRING", MatchType.EQ.getOperation(), "004"));
		List<Map<String, String>> orderExes = HbaseCURDUtils.findByCondition("HDR_ORDER_EXE", rowkeyprefix,
				orderExeFilter);
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		if (orderExes.isEmpty()) {
			return result;
		}
		for (Map<String, String> orderExe : orderExes) {
			String prescTime = orderExe.get("PRESC_TIME");
			if (StringUtils.isBlank(prescTime)) {
				continue;
			}
			String pt = prescTime.substring(0, 10);
			if (!result.containsKey(pt)) {
				result.put(pt, orderExe);
			}
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取指定医嘱号的医嘱记录
	 * @return 返回类型： Map<String,String>
	 * @param rowkeyprefix
	 * @param orderNo
	 * @return
	 */
	public static Map<String, String> getInOrderByOrderNo(String rowkeyprefix, String orderNo) {
		Map<String, String> result = new HashMap<String, String>();
		if (StringUtils.isBlank(orderNo)) {
			return result;
		}
		List<PropertyFilter> orderFilter = new ArrayList<PropertyFilter>();
		orderFilter.add(new PropertyFilter("ORDER_NO", "STRING", MatchType.EQ.getOperation(), orderNo));
		List<Map<String, String>> orders = HbaseCURDUtils.findByCondition("HDR_IN_ORDER", rowkeyprefix, orderFilter);
		if (!orders.isEmpty()) {
			result = orders.get(0);
		}
		return result;
	}
}
