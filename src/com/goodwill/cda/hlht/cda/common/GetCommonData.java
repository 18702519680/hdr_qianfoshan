package com.goodwill.cda.hlht.cda.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.lib.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.globalmentor.collections.Collections;
import com.goodwill.cda.hlht.enums.BaseInfoEnum;
import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtisTools;
import com.goodwill.cda.util.FiltersUtils;
import com.goodwill.cda.util.MysqlUtil;
import com.goodwill.cda.util.Xmlutil;
import com.goodwill.core.enums.EnumType;
import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * 公共提取数据类
 * 
 * @author liuzhi
 */
public class GetCommonData {

	/*************** 裴计平 **********************************/
	/**
	 * jibin
	 * 
	 * @Description 方法描述:按照DG查询电子病历结构化内容（一次住院多次文书调用此方法）
	 * @return 返回类型： String
	 * @param pid
	 * @param vid
	 * @param mr_class
	 *            模板类型，可以为空，但是对于有些章节可能一次住院有多个模板中存在，则需要精确指定具体的模板类型MRClassEnum
	 * @param dg_code
	 *            需要查询的节点名称，可以是编码，也可以是名称MRDGCodeEnum
	 * @param fileid
	 *            文书唯一标识
	 * @return
	 */
	public static String getNurserDGValue(String pid, String vid, String mr_class, String dg_name) {
		if (StringUtils.isBlank(dg_name)) {
			return null;
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		if (StringUtils.isNotBlank(mr_class)) {
			filters.add(new PropertyFilter("MR_CLASS_CODE", "STRING", MatchType.EQ.getOperation(), mr_class));
		}
		filters.add(new PropertyFilter("DG_NAME", "STRING", MatchType.EQ.getOperation(), dg_name));
		List<Map<String, String>> list = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_EMR_CONTENT_DG_NURSE",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters, new String[] { "DG_PLAIN_CONTENT_BLOB" }));
		String str = "";
		if (list != null && list.size() > 0) {
			Map<String, String> map = list.get(0);
			if (map != null && !map.isEmpty()) {
				str = map.get("DG_PLAIN_CONTENT_BLOB");
			}
		}
		return str;
	}

	// 获取手术
	public static Map<String, String> getInOper(String pid, String vid, String orderCode) {
		List<PropertyFilter> filtersOrder = new ArrayList<PropertyFilter>();
		filtersOrder.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		//		filtersOrder.add(new PropertyFilter("OPERATION_TYPE_NAME", "STRING", MatchType.EQ.getOperation(), "手术"));
		List<Map<String, String>> list = HbaseCURDUtils.findByCondition("HDR_INP_SUMMARY_OPER",
				HbaseCURDUtils.getRowkeyPrefix(pid), FiltersUtils.addFilters(filtersOrder, ""));
		if (list.isEmpty()) {
			return new HashMap<String, String>();
		}
		Map<String, String> operMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			//String operName = map.get("OPERATION_TYPE_NAME");
			String operName = map.get("OPERATION_NAME");
			if (StringUtils.isBlank(operName)) {
				operName = map.get("OPERATION_CODE");
			}
			if (StringUtils.isBlank(operName)) {
				break;
			}
			//if (operName.equals("手术")) {
			if (operName.equals("0")) {
				operMap = list.get(i);
				break;
			}
		}
		if (operMap == null||operMap.size()==0) {
			operMap = list.get(0);
		}
		return operMap;
	}

	// 获取手术
	public static Map<String, String> getInOperGDYFY(String pid, String vid, String operationDate) {
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String operationTime = "";
		try {
			operationTime = sdf.format(sdfs.parse(operationDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<PropertyFilter> filtersOrder = new ArrayList<PropertyFilter>();
		filtersOrder.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		if (StringUtils.isNotBlank(operationTime)) {
			filtersOrder.add(new PropertyFilter("OPERATION_DATE", "STRING", MatchType.LIKE.getOperation(),
					operationTime));
		}
		List<Map<String, String>> list = HbaseCURDUtils.findByCondition("HDR_INP_SUMMARY_OPER",
				HbaseCURDUtils.getRowkeyPrefix(pid), FiltersUtils.addFilters(filtersOrder, "CDA_MAIN_OPER"));
		if (list.isEmpty()) {
			return new HashMap<String, String>();
		}
		Map<String, String> operMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			//String operName = map.get("OPERATION_TYPE_NAME");
			String operName = map.get("OPERATION_TYPE");
			if (StringUtils.isBlank(operName)) {
				operName = map.get("OPERATION_TYPE_CODE");
			}
			if (StringUtils.isBlank(operName)) {
				break;
			}
			//if (operName.equals("手术")) {
			if (operName.equals("0")) {
				operMap = list.get(i);
				break;
			}
		}
		if (operMap == null) {
			operMap = list.get(0);
		}
		return operMap;
	}

	/**
	 * @Description 方法描述:获取患者体征测量信息，并按天进行分组
	 * @return 返回类型： Map<String,List<Map<String,String>>>
	 * @param pid
	 * @param vid
	 * @return
	 */
	public static Map<String, List<Map<String, String>>> getDayVitalMeasures(String pid, String vid, boolean DaySubStr) {
		//先获取所有的出入量记录
		List<Map<String, String>> inOutList = HbaseCURDUtils.findByRowkeyPrefix("HDR_VITAL_MEASURE",
				HbaseCURDUtils.getRowkeyPrefix(pid) + "|" + vid);
		//判断记录条数，如果没有发，返回一个空结果集
		Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
		if (inOutList.isEmpty()) {
			return result;
		}
		// 进行group by操作，按测量时间到天,没有测量时间的，忽略掉？
		for (Map<String, String> inOut : inOutList) {
			String measuring_time = inOut.get("RECORD_TIME");
			if (StringUtils.isBlank(measuring_time)) {
				continue;
			}
			if (DaySubStr) {
				measuring_time = measuring_time.substring(0, 10);
			}
			// 截取日当做key，存过的话继续放入，没有存过的，生成一个list放入
			if (result.containsKey(measuring_time)) {
				List<Map<String, String>> dayInOuts = result.get(measuring_time);
				dayInOuts.add(inOut);
			} else {
				List<Map<String, String>> dayInOuts = new ArrayList<Map<String, String>>();
				dayInOuts.add(inOut);
				result.put(measuring_time, dayInOuts);
			}
		}
		return result;
	}

	/*************** 刘智 ********************************/
	/**
	 * 根据患者号 和 就诊次获取 患者基本信息
	 * 
	 * @param patient_id
	 *            患者号
	 * @param visit_id
	 *            就诊次
	 * @param baseInfo
	 *            要查询的就诊表@BaseInfoEnum.java
	 * @return
	 */
	public static List<Map<String, String>> getBaseInfoData(String patient_id, String visit_id, BaseInfoEnum baseInfo) {
		List<Map<String, String>> baseInfos = HbaseCURDUtils.findByRowkeyPrefix(baseInfo.getCode(),
				HbaseCURDUtils.getRowkeyPrefix(patient_id) + "|" + visit_id);
		return baseInfos;
	}

	/**
	 * 诊断类型 枚举 
	 * @Description
	 * 类描述：
	 * @author liuzhi
	 * @Date 2017年11月22日
	 * @modify
	 * 修改记录：
	 *
	 */
	public enum DiagEnum implements EnumType {
		/**
		 * 门诊诊断
		 */
		MZZD {

			@Override
			public String getCode() {
				return "1";
			}

			@Override
			public String getLabel() {
				return "门诊诊断";
			}

		},
		/**
		 * 入院诊断
		 */
		RYZD {

			@Override
			public String getCode() {
				return "2";
			}

			@Override
			public String getLabel() {
				return "入院诊断";
			}

		},

		/**
		 * 出院诊断
		 */
		CYZD {

			@Override
			public String getCode() {
				return "3";
			}

			@Override
			public String getLabel() {
				return "出院诊断";
			}

		},
		/**
		 * 手术并发症
		 */
		SSBFZ {

			@Override
			public String getCode() {
				return "4";
			}

			@Override
			public String getLabel() {
				return "手术并发症";
			}

		},
		/**
		 * 非手术并发症
		 */
		FSSBFZ {

			@Override
			public String getCode() {
				return "5";
			}

			@Override
			public String getLabel() {
				return "非手术并发症";
			}
		},
		/**
		 * 院内感染
		 */
		YNGR {

			@Override
			public String getCode() {
				return "6";
			}

			@Override
			public String getLabel() {
				return "院内感染";
			}

		},
		/**
		 * 损伤、中毒等外部原因
		 */
		SSZDWBYY {

			@Override
			public String getCode() {
				return "7";
			}

			@Override
			public String getLabel() {
				return "损伤、中毒等外部原因";
			}

		},
		/**
		 * 病理诊断
		 */
		BLZD {

			@Override
			public String getCode() {
				return "8";
			}

			@Override
			public String getLabel() {
				return "病理诊断";
			}

		},
		/**
		 * 病情转归代码
		 * 病情转归 包含  TREAT_RESULT_CODE  1是治愈   2是好转  3是未愈
		 */
		BQZG {

			@Override
			public String getCode() {
				return "0";
			}

			@Override
			public String getLabel() {
				return "病情转归";
			}
		},
		/**
		 * 死亡原因
		 */
		SWYY {

			@Override
			public String getCode() {
				return "4";
			}

			@Override
			public String getLabel() {
				return "死亡";
			}
		}

	}

	/**
	 * 获取 入院诊断、出院诊断、门诊诊断 等
	 *   DIAGNOSIS_TYPE_CODE  1门诊诊断，2入院初诊，3出院诊断
	 * @return 返回类型： Map<String,String>
	 * @param diagList
	 *            传入的诊断列表
	 * @param diagType   GetCommonData.DiagEnum
	 *            
	 * @param checkStr
	 *            判断返回的条件，住院诊断（入院诊断、出院诊断）时传DIAGNOSIS_TYPE_CODE对应的值，门诊诊断时传MAIN_FLAG对应的值
	 * @return
	 */
	public static Map<String, String> getDiagMap(List<Map<String, String>> diagList, EnumType diagType, String checkStr) {
		Map<String, String> targetDiagMap = new HashMap<String, String>();
		if (diagList.isEmpty() || null == diagList) {
			return targetDiagMap;
		}
		if (diagType == null) {
			return diagList.get(0);
		}
		if (diagType.getCode().equals(DiagEnum.CYZD.getCode()) || diagType.getCode().equals(DiagEnum.RYZD.getCode())) {
			//查询出院诊断
			targetDiagMap = getCYDiag(diagList, diagType);
			if (diagType.getCode().equals(DiagEnum.RYZD.getCode()) && targetDiagMap.isEmpty()) {
				targetDiagMap = getCYDiag(diagList, DiagEnum.RYZD);
			}
		} else if (diagType.getCode().equals(DiagEnum.MZZD.getCode())) {// 门诊的判断主诊断标识为目标的
			for (Map<String, String> diag : diagList) {
				String mainFlag = diag.get("DIAGNOSIS_NUM");
				if (StringUtils.isNotBlank(mainFlag) && mainFlag.equals(checkStr)) {
					targetDiagMap = diag;
					break;
				}
			}
			if (targetDiagMap.isEmpty()) {
				targetDiagMap = getCYDiag(diagList, DiagEnum.RYZD);
			}
		}
		if (diagType.getCode().equals(DiagEnum.BLZD.getCode())
				|| diagType.getCode().equals(DiagEnum.SSZDWBYY.getCode())) {
			targetDiagMap = getCYDiag(diagList, diagType);
		} else {
			if (targetDiagMap.isEmpty()) {
				targetDiagMap = diagList.get(0);
			}
		}
		if (diagType.getCode().equals(DiagEnum.BQZG.getCode())) {
			targetDiagMap = getDiag(diagList, diagType);
		}
		if (diagType.getCode().equals(DiagEnum.SWYY.getCode())) {
			targetDiagMap = getDiagSW(diagList, diagType);
		}
		return targetDiagMap;
	}

	/**
	 * 获取治愈或者死亡
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Map<String,String>
	 * diagType=4 时只查询死亡原因   diagType=0时查询病情的好转 
	 * 1代表治愈    2代表好转  3代表未愈  4代表死亡
	 * 
	 * @return
	 */
	private static Map<String, String> getDiag(List<Map<String, String>> diagList, EnumType diagType) {
		Map<String, String> targetDiagMap = new HashMap<String, String>();
		for (Map<String, String> diag : diagList) {
			String diagnoseType = diag.get("TREAT_RESULT_CODE");
			if (StringUtils.isNotBlank(diagnoseType) && diagnoseType.equals(diagType.getCode())) {
				targetDiagMap = diag;
				break;
			} else if (StringUtils.isNotBlank(diagnoseType)
					&& (diagnoseType.equals("1") || diagnoseType.equals("2") || diagnoseType.equals("3") || diagnoseType
							.equals("4"))) {
				targetDiagMap = diag;
				break;
			}
		}
		return targetDiagMap;
	}

	/**
	 * 获取出院诊断
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @return
	 */
	private static Map<String, String> getCYDiag(List<Map<String, String>> diagList, EnumType diagType) {
		Map<String, String> targetDiagMap = new HashMap<String, String>();
		for (Map<String, String> diag : diagList) {
			if (diag.containsKey("DIAGNOSIS_NUM") && "1".equals(diag.get("DIAGNOSIS_NUM"))) {
				String diagnoseType = diag.get("DIAGNOSIS_TYPE_CODE");
				if (StringUtils.isNotBlank(diagnoseType) && diagnoseType.equals(diagType.getCode())) {
					targetDiagMap = diag;
					break;
				}
			}
		}
		return targetDiagMap;
	}

	/*************** 刘智 ********************************/

	/*************** 裴计平 **********************************/
	/**
	 * 读取医嘱信息
	 * 
	 * @param pid
	 * @param vid
	 * @return
	 */
	public static List<Map<String, String>> getOrderInfo(String pid, String vid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		List<Map<String, String>> list = CommonUtils.formatList(HbaseCURDUtils.findByCondition("HDR_IN_ORDER",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters));
		// 加入医嘱执行数据
		for (Map<String, String> map : list) {
			String orderNo = map.get("ORDER_NO");
			List<PropertyFilter> filters1 = new ArrayList<PropertyFilter>();
			filters1.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
			filters1.add(new PropertyFilter("ORDER_NO", "STRING", MatchType.EQ.getOperation(), orderNo));
			List<Map<String, String>> list1 = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_ORDER_EXE",
					HbaseCURDUtils.getRowkeyPrefix(pid), filters1));
			if (list1.size() > 0) {
				map.put("PRESC_TIME", list1.get(0).get("PRESC_NURSE_CODE"));
				map.put("PRESC_NURSE_NAME", list1.get(0).get("PRESC_NURSE_NAME"));
				map.put("PRESC_NURSE_CODE", list1.get(0).get("PRESC_NURSE_CODE"));
				map.put("PRESC_DEPT_NAME", list1.get(0).get("PRESC_DEPT_NAME"));
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description 方法描述:按照DG查询电子病历结构化内容（一次住院一次文书调用此方法）
	 * @return 返回类型： String
	 * @param pid
	 * @param vid
	 * @param mr_class
	 *            模板类型，可以为空，但是对于有些章节可能一次住院有多个模板中存在，则需要精确指定具体的模板类型MRClassEnum
	 * @param dg_code
	 *            需要查询的节点名称，可以是编码，也可以是名称MRDGCodeEnum
	 * @return
	 */
	public static String getEmrDGValue(String pid, String vid, String mr_class, String dg_name) {
		if (StringUtils.isBlank(dg_name)) {
			return null;
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		if (StringUtils.isNotBlank(mr_class)) {
			String regex = "[\u4e00-\u9fa5]";
			Pattern pat = Pattern.compile(regex);
			Matcher mat = pat.matcher(mr_class);
			if (mat.find()) {
				filters.add(new PropertyFilter("MR_CLASS_NAME", "STRING", MatchType.LIKE.getOperation(), mr_class));
			} else {
				filters.add(new PropertyFilter("MR_CLASS_CODE", "STRING", MatchType.EQ.getOperation(), mr_class));
			}
		}
		filters.add(new PropertyFilter("DG_NAME", "STRING", MatchType.EQ.getOperation(), dg_name));
		List<Map<String, String>> list = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_EMR_CONTENT_DG",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters, new String[] { "DG_PLAIN_CONTENT_BLOB" }));
		String str = "";
		if (list != null && list.size() > 0) {
			Map<String, String> map = list.get(0);
			if (map != null && !map.isEmpty()) {
				str = map.get("DG_PLAIN_CONTENT_BLOB");
			}
		}
		return str;
	}

	/**
	 * jibin
	 * 
	 * @Description 方法描述:按照DG查询电子病历结构化内容（一次住院多次文书调用此方法）
	 * @return 返回类型： String
	 * @param pid
	 * @param vid
	 * @param mr_class
	 *            模板类型，可以为空，但是对于有些章节可能一次住院有多个模板中存在，则需要精确指定具体的模板类型MRClassEnum
	 * @param dg_code
	 *            需要查询的节点名称，可以是编码，也可以是名称MRDGCodeEnum
	 * @param fileid
	 *            文书唯一标识
	 * @return
	 */
	public static String getEmrDGValue(String pid, String vid, String mr_class, String dg_name, String fileid) {
		if (StringUtils.isBlank(dg_name)) {
			return null;
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		if (StringUtils.isNotBlank(mr_class)) {
			String regex = "[\u4e00-\u9fa5]";
			Pattern pat = Pattern.compile(regex);
			Matcher mat = pat.matcher(mr_class);
			if (mat.find()) {
				filters.add(new PropertyFilter("MR_CLASS_NAME", "STRING", MatchType.LIKE.getOperation(), mr_class));
			} else {
				filters.add(new PropertyFilter("MR_CLASS_CODE", "STRING", MatchType.EQ.getOperation(), mr_class));
			}
		}
		if (StringUtils.isNotBlank(fileid)) {
			filters.add(new PropertyFilter("FILE_UNIQUE_ID", "STRING", MatchType.EQ.getOperation(), fileid));
		}
		filters.add(new PropertyFilter("DG_NAME", "STRING", MatchType.EQ.getOperation(), dg_name));
		List<Map<String, String>> list = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_EMR_CONTENT_DG",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters, new String[] { "DG_PLAIN_CONTENT_BLOB" }));
		String str = "";
		if (list != null && list.size() > 0) {
			Map<String, String> map = list.get(0);
			if (map != null && !map.isEmpty()) {
				str = map.get("DG_PLAIN_CONTENT_BLOB");
			}
		}
		return str;
	}

	/**
	 * 
	 * @Description
	 * 方法描述: 获取电子病历中DG和DE表中的数据
	 * @return 返回类型： String
	 * @param pid 患者号
	 * @param vid 就诊次数
	 * @param name 关键字
	 * @param fileid 文书唯一标识
	 * @return
	 */
	public static String getEmrDGAndDEValue(String pid, String vid, String name, Map<String, String> mapBlws) {
		String content = "", fileid = "";
		if (mapBlws != null) {
			if (!mapBlws.isEmpty()) {
				fileid = mapBlws.get("FILE_UNIQUE_ID");
			}
		}
		if (StringUtils.isBlank(fileid)) {
			fileid = "";
		}
		content = GetCommonData.getEmrDGValue(pid, vid, "", name, fileid);
		if (StringUtils.isBlank(content)) {
			content = GetCommonData.getEmrDGValue(pid, vid, "", name, "");
		}
		if (StringUtils.isBlank(content)) {
			content = GetCommonData.getEmrDEValue(pid, vid, "", name, fileid);
		}
		if (StringUtils.isBlank(content)) {
			content = GetCommonData.getEmrDEValue(pid, vid, "", name, "");
		}
		return StringUtils.isBlank(content) ? "无" : content;
	}

	/**
	 * hanfei
	 * 
	 * @Description 方法描述:按照DG查询电子病历结构化内容（一次住院多次文书调用此方法）
	 * @return 返回类型： String
	 * @param pid
	 * @param vid
	 * @param mr_class
	 *            模板类型，可以为空，但是对于有些章节可能一次住院有多个模板中存在，则需要精确指定具体的模板类型MRClassEnum
	 * @param dg_code
	 *            需要查询的节点名称，可以是编码，也可以是名称MRDGCodeEnum
	 * @param fileid
	 *            文书唯一标识
	 * @return
	 */
	public static String getEmrDEValue(String pid, String vid, String mr_class, String de_name, String fileid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		if (StringUtils.isNotBlank(vid)) {
			filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		}
		if (StringUtils.isNotBlank(mr_class)) {
			filters.add(new PropertyFilter("MR_CLASS_CODE", "STRING", MatchType.EQ.getOperation(), mr_class));
		}
		if (StringUtils.isNotBlank(fileid)) {
			filters.add(new PropertyFilter("FILE_UNIQUE_ID", "STRING", MatchType.EQ.getOperation(), fileid));
		}
		filters.add(new PropertyFilter("DE_NAME", "STRING", MatchType.EQ.getOperation(), de_name));
		List<Map<String, String>> list = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_EMR_CONTENT_DE",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters, new String[] { "S_VALUE" }));
		String str = "";
		if (list != null && list.size() > 0) {
			Map<String, String> map = list.get(0);
			if (map != null && !map.isEmpty()) {
				str = map.get("S_VALUE");
			}
		}
		return str;
	}

	/**
	 * 
	 * @Description 方法描述:按照DE查询护理文书结构化内容
	 * @return 返回类型： String
	 * @param pid
	 * @param vid
	 * @param topic
	 *            文书名称，可以为空，但是对于有些章节可能一次住院有多个模板中存在，则需要精确指定具体的模板类型，如“入院评估”等
	 * @param d_code
	 *            需要查询的节点名称，可以是编码，也可以是名称
	 * @return 修改人：jibin 增加名称查询方法（尽量通过code与name一种方式查询）
	 */
	public static String getNurseDEValue(String pid, String vid, String topic, String de_code, String de_name) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		if (StringUtils.isNotBlank(topic)) {
			filters.add(new PropertyFilter("TOPIC", "STRING", MatchType.LIKE.getOperation(), topic));
		}
		if (StringUtils.isNotBlank(de_code)) {
			filters.add(new PropertyFilter("DE_CODE", "STRING", MatchType.EQ.getOperation(), de_code));
		}
		if (StringUtils.isNotBlank(de_name)) {
			filters.add(new PropertyFilter("DE_NAME", "STRING", MatchType.LIKE.getOperation(), de_name));
		}
		List<Map<String, String>> list = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_NURSE_CONTENT_DE",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters, new String[] { "DE_VALUE" }));
		String str = "无";
		if (list != null && list.size() > 0) {
			Map<String, String> map = list.get(0);
			if (map != null && !map.isEmpty()) {
				str = map.get("DE_VALUE");
			}
		}
		return str;
	}

	/**
	 * 提取患者生命体征数据，每一项数据都按时间提取第一条数据 体重，体温，心率、呼吸、脉搏、血压
	 * 
	 * @param pid
	 * @param vid
	 * @return
	 */
	public static Map<String, Map<String, String>> getPatVitalSign(String pid, String vid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		List<Map<String, String>> patvsList = CommonUtils.formatList(HbaseCURDUtils.findByCondition(
				"HDR_VITAL_MEASURE", HbaseCURDUtils.getRowkeyPrefix(pid), filters));

		// 去重，取每个小项最后的报告时间的数据
		Map<String, Map<String, String>> deduplicationMap = new HashMap<String, Map<String, String>>();
		for (Map<String, String> map : patvsList) {
			//String mTime = map.get("MEASURING_TIME");
			String keyString = map.get("VITAL_TYPE_NAME");
			// 过滤掉正常值，保留异常值
			if (deduplicationMap.containsKey(keyString)) {
				// 取最大报告时间数据
				String currTimeString = deduplicationMap.get(keyString).get("MEASURING_TIME");
				String newTimeString = map.get("MEASURING_TIME");
				// 新时间比原有时间小
				if (newTimeString.compareTo(currTimeString) < 0) {
					deduplicationMap.put(keyString, map);
				}
			} else {
				// 添加进临时map,同时
				deduplicationMap.put(keyString, map);
			}
		}
		Map<String, String> tmpMap = new HashMap<String, String>();
		tmpMap.put("VITAL_SIGN_VALUE", "");
		tmpMap.put("VITAL_SIGN2_VALUE", "");
		tmpMap.put("VITAL_SIGN3_VALUE", "");
		List<String> tmpList = new ArrayList<String>();
		tmpList.add("体温");
		tmpList.add("脉搏");
		tmpList.add("大便");
		tmpList.add("年龄");
		tmpList.add("体重");
		tmpList.add("血压");
		tmpList.add("身高");
		tmpList.add("心率");
		tmpList.add("呼吸");
		tmpList.add("尿量");
		tmpList.add("入量");
		for (String str : tmpList) {
			if (!deduplicationMap.containsKey(str)) {
				deduplicationMap.put(str, tmpMap);
			}
		}

		return deduplicationMap;
	}

	/**
	 * 获取患者主诊断
	 * 
	 * @param pid
	 * @param vid
	 * @param diagType
	 *            1门诊诊断，2入院初诊，3出院诊断
	 * @return
	 */
	public static List<Map<String, String>> getPatDiagInfo(String pid, String vid, String diagType) {
		List<PropertyFilter> filtersDIAG = new ArrayList<PropertyFilter>();
		filtersDIAG.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		// 查询住院数据
		filtersDIAG.add(new PropertyFilter("VISIT_TYPE_CODE", "STRING", MatchType.EQ.getOperation(), "02"));
		// 诊断序号为1的为主诊断
		filtersDIAG.add(new PropertyFilter("DIAGNOSIS_NUM", "STRING", MatchType.EQ.getOperation(), "1"));

		// 如果00则查询全部诊断
		if (!"00".equals(diagType)) {
			filtersDIAG.add(new PropertyFilter("DIAGNOSIS_TYPE_CODE", "STRING", MatchType.EQ.getOperation(), diagType));
		}

		List<Map<String, String>> diagList = CommonUtils.formatList(HbaseCURDUtils.findByCondition(
				"HDR_INP_SUMMARY_DIAG", HbaseCURDUtils.getRowkeyPrefix(pid), filtersDIAG));
		return diagList;
	}

	/*************** 裴计平 **********************************/

	/**
	 * 获取 入院诊断、出院诊断、门诊诊断 等
	 *   DIAGNOSIS_TYPE_CODE  1门诊诊断，2入院初诊，3出院诊断
	 * @return 返回类型： Map<String,String>
	 * @param diagList
	 *            传入的诊断列表
	 * @param diagType   GetCommonData.DiagEnum
	 *            
	 * @param checkStr
	 *            判断返回的条件，住院诊断（入院诊断、出院诊断）时传DIAGNOSIS_TYPE_CODE对应的值，门诊诊断时传MAIN_FLAG对应的值
	 * @param isMain  标志 是否是查询主诊断
	 * @return
	 */
	public static List<Map<String, String>> getDiagMapList(List<Map<String, String>> diagList, EnumType diagType,
			String checkStr, Boolean isMain) {
		List<Map<String, String>> targetDiagList = new ArrayList<Map<String, String>>();
		if (diagList.isEmpty() || null == diagList) {
			return targetDiagList;
		}
		if (diagType == null) {
			return diagList;
		}
		if (diagType.getCode().equals(DiagEnum.CYZD.getCode()) || diagType.getCode().equals(DiagEnum.RYZD.getCode())) {
			//查询出院诊断
			targetDiagList = getCYDiagList(diagList, diagType, isMain);
			if (diagType.getCode().equals(DiagEnum.RYZD.getCode()) && targetDiagList.isEmpty()) {
				targetDiagList = getCYDiagList(diagList, DiagEnum.RYZD, isMain);
			}
		} else if (diagType.getCode().equals(DiagEnum.MZZD.getCode())) {// 门诊的判断主诊断标识为目标的
			for (Map<String, String> diag : diagList) {
				//String mainFlag = diag.get("MAIN_FLAG");
				//修改了门诊DIAGNOSIS_TYPE_CODE=1 时是门诊诊断  不区分主诊断或其他诊断
				String mainFlag = diag.get("DIAGNOSIS_TYPE_CODE");
				if (StringUtils.isNotBlank(mainFlag) && mainFlag.equals(checkStr)) {
					targetDiagList.add(diag);
				}
			}
			if (targetDiagList.isEmpty()) {
				targetDiagList = getCYDiagList(diagList, DiagEnum.RYZD, isMain);
			}
		}
		if (diagType.getCode().equals(DiagEnum.BLZD.getCode())
				|| diagType.getCode().equals(DiagEnum.SSZDWBYY.getCode())) {
			targetDiagList = getCYDiagList(diagList, diagType, isMain);
		} else {
			if (targetDiagList.isEmpty()) {
				targetDiagList = diagList;
			}
		}
		if (diagType.getCode().equals(DiagEnum.BQZG.getCode())) {
			targetDiagList = getCYDiagList(diagList, diagType, isMain);
		}
		if (diagType.getCode().equals(DiagEnum.SWYY.getCode())) {
			targetDiagList.add(getDiagSW(diagList, diagType));
		}
		return targetDiagList;
	}

	/**
	 * 获取出院诊断
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @return
	 */
	private static List<Map<String, String>> getCYDiagList(List<Map<String, String>> diagList, EnumType diagType,
			Boolean isMain) {
		List<Map<String, String>> targetDiagMapList = new ArrayList<Map<String, String>>();
		for (Map<String, String> diag : diagList) {
			String diagnoseType = diag.get("DIAGNOSIS_TYPE_CODE");
			if (isMain) {
				if (diag.containsKey("DIAGNOSIS_NUM") && "1".equals(diag.get("DIAGNOSIS_NUM"))) {
					if (StringUtils.isNotBlank(diagnoseType) && diagnoseType.equals(diagType.getCode())) {
						targetDiagMapList.add(diag);
					}
				}
			} else {
				if (StringUtils.isNotBlank(diagnoseType) && diagnoseType.equals(diagType.getCode())) {
					targetDiagMapList.add(diag);
				}
			}

		}
		return targetDiagMapList;
	}

	/**
	 * 获取治愈或者死亡
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Map<String,String>
	 * diagType=4 时只查询死亡原因   diagType=0时查询病情的好转 
	 * 1代表治愈    2代表好转  3代表未愈  4代表死亡
	 * 
	 * @return
	 */
	private static Map<String, String> getDiagSW(List<Map<String, String>> diagList, EnumType diagType) {
		Map<String, String> targetDiagMap = new HashMap<String, String>();
		for (Map<String, String> diag : diagList) {
			String diagnoseType = diag.get("TREAT_RESULT_CODE");
			if (StringUtils.isNotBlank(diagnoseType) && diagnoseType.equals(diagType.getCode())) {
				targetDiagMap = diag;
				break;
			}
		}
		return targetDiagMap;
	}

	/**
	 * 根据患者号和住院次 获取护理等级 ，查询住院医嘱表，取第一次护理
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param piv
	 * @param vid
	 * @return
	 */
	public static Map<String, String> getNurseGrade(String piv, String vid) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		filters.add(new PropertyFilter("ORDER_ITEM_NAME", "STRING", MatchType.LIKE.getOperation(), "级护理"));
		List<Map<String, String>> orderInfo = CommonUtils.formatList(HbaseCURDUtils.findByCondition("HDR_IN_ORDER",
				HbaseCURDUtils.getRowkeyPrefix(piv), filters));
		Map<String, Map<String, String>> nurseMap = new TreeMap<String, Map<String, String>>();
		for (int i = 0; i < orderInfo.size(); i++) {
			Map<String, String> map = orderInfo.get(i);
			String orderName = map.get("ORDER_ITEM_NAME");
			if (orderName.contains("级护理")) {
				nurseMap.put(map.get("ORDER_BEGIN_TIME"), map);
			}
		}
		Set<String> keySet = nurseMap.keySet();
		if (keySet.isEmpty()) {
			return new HashMap<String, String>();
		}
		List<String> list = Collections.toList(keySet);
		Map<String, String> nurseGradeMap = nurseMap.get(list.get(0));
		Map<String, String> hldj = DictUtisTools.getHLDJ(nurseGradeMap.get("ORDER_ITEM_NAME"),
				nurseGradeMap.get("ORDER_ITEM_NAME"));
		return hldj;
	}
	
	
	public static String GetCodeFromMysql(String name,String code,String codetype) {
		StringBuilder sql = new StringBuilder();
		if(StringUtil.isEmpty(name)) {
		sql.append("select * from hdr_dmb_zlsb where ");
		sql.append("dl='"+codetype+"'");
		sql.append("and dmmc='"+code+"'");
		}
		else {
			sql.append("select * from hdr_dmb_zlsb where ");
			sql.append("dl='"+codetype+"'");
			sql.append("and dmmc='"+name+"'");
		}
		Statement statement = null;
		Connection conn = null;
		ResultSet res= null;
		String returncode= null;
		try {
			conn = MysqlUtil.GetConnection();
			if (conn == null) {
				for (int i = 0; i < 3; i++) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						conn = MysqlUtil.GetConnection();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (conn != null) {
						break;
					}
				}
			}
			if (conn == null) {
				return null;
			}
			statement = conn.createStatement();
			res= statement.executeQuery(sql.toString());
				while (res.next()) {
					returncode = res.getString("dm");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return returncode;
	}
}
