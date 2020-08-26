package com.goodwill.cda.hlht.cda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.goodwill.cda.hlht.cda.common.HttpClientPost;
import com.goodwill.cda.hlht.cda.common.ResponseToMysql;
import com.goodwill.cda.util.HbaseCURDUtilsToCDA;
import com.goodwill.cda.util.Xmlutil;
import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.core.utils.PropertiesUtils;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

import net.sf.json.JSONArray;

/**
 * @Description 类描述：治疗信息（手术记录） 上传每次归档后，当次住院的手术信息。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：operationRecord 版本号：1.1.0
 * @modify 修改记录：
 * 
 */
public class REPORT07 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT07.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "operationRecord");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "operationRecord_version");
	private static String operationrecord_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"operationRecord_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getDischargeDiagnosisJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncOperationApplyNo", map.get("NCOPERATIONAPPLYNO"));// 手术申请单号
			json.put("ncOperationNo", map.get("NCOPERATIONNO"));// 手术序列号
			json.put("ncOperationDate", map.get("NCOPERATIONDATE"));// 手术日期
			json.put("ncOperationCategoryCode", map.get("NCOPERATIONCATEGORYCODE"));// 手术分类代码
			json.put("ncOperationCategoryName", map.get("NCOPERATIONCATEGORYNAME"));// 手术分类名称
			json.put("ncOperationCategoryCodeL", map.get("NCOPERATIONCATEGORYCODEL"));// 手术分类代码（本院）
			json.put("ncOperationCategoryNameL", map.get("NCOPERATIONCATEGORYNAMEL"));// 手术分类名称（本院）
			json.put("ncPreoperativeDiagnosisCode", map.get("NCPREOPERATIVEDIAGNOSISCODE"));// 术前诊断代码
			json.put("ncPreoperativeDiagnosisName", map.get("NCPREOPERATIVEDIAGNOSISNAME"));// 术前诊断名称
			json.put("ncPreDiagnosisCodeL", map.get("NCPREDIAGNOSISCODEL"));// 术前诊断代码（本院）
			json.put("ncPreDiagnosisNameL", map.get("NCPREDIAGNOSISNAMEL"));// 术前诊断名称（本院）
			json.put("ncPreDiagnosisVerNameL", map.get("NCPREDIAGNOSISVERNAMEL"));// 术前诊断代码版本（本院
			json.put("ncHasPreoperativeInfection", map.get("NCHASPREOPERATIVEINFECTION"));// 术前是否发生院内感染
			json.put("ncPostoperativeDiagnosisCode", map.get("NCPOSTOPERATIVEDIAGNOSISCODE"));// 术后诊断代码
			json.put("ncPostoperativeDiagnosisName", map.get("NCPOSTOPERATIVEDIAGNOSISNAME"));// 术后诊断名称
			json.put("ncPostDiagnosisCodeL", map.get("NCPOSTDIAGNOSISCODEL"));// 术后诊断代码（本院）
			json.put("ncPostDiagnosisNameL", map.get("NCPOSTDIAGNOSISNAMEL"));// 术后诊断名称（本院）
			json.put("ncPostDiagnosisVerNameL", map.get("NCPOSTDIAGNOSISVERNAMEL"));// 术后诊断代码版本（本院）
			json.put("ncIncisionHealGradeCode", map.get("ncIncisionHealGradeCode"));// 手术切口愈合等级代码
			json.put("ncWhetherReentry", map.get("NCWHETHERREENTRY"));// 是否重返手术
			json.put("ncHasAntibacterialDrug", map.get("NCHASANTIBACTERIALDRUG"));// 是否预防使用抗菌药物
			json.put("ncAntibacterialDrugDays", map.get("NCANTIBACTERIALDRUGDAYS"));// 预防使用抗菌药物天数
			json.put("ncOperationCode", map.get("NCOPERATIONCODE"));// 手术/操作代码
			json.put("ncOperationName", map.get("NCOPERATIONNAME"));// 手术/操作名称
			json.put("ncOperationCodeL", map.get("NCOPERATIONCODEL"));// 手术/操作代码（本院）
			json.put("ncOperationNameL", map.get("NCOPERATIONNAMEL"));// 手术/操作名称（本院）
			json.put("ncOperationVerNameL", map.get("NCOPERATIONVERNAMEL"));// 手术/操作代码版本名称（本院）
			json.put("ncOperationGradeCode", map.get("NCOPERATIONGRADECODE"));// 手术级别代码
			json.put("ncOperationGradeName", map.get("NCOPERATIONGRADENAME"));// 手术级别名称
			json.put("ncOperationStartTime", map.get("NCOPERATIONSTARTTIME"));// 手术开始时间
			json.put("ncOperationEndTime", map.get("NCOPERATIONENDTIME"));// 手术结束时间
			json.put("ncIsSterileOperation", map.get("NCISSTERILEOPERATION"));// 是否无菌手术
			json.put("ncIsInfection", map.get("NCISINFECTION"));// 无菌手术是否感染
			json.put("ncOperationDepartmentCode", map.get("NCOPERATIONDEPARTMENTCODE"));// 手术执行科室代码
			json.put("ncOperationDepartmentName", map.get("NCOPERATIONDEPARTMENTNAME"));// 手术执行科室名称
			json.put("ncOperationDepartmentCodeL", map.get("NCOPERATIONDEPARTMENTCODEL"));// 手术执行科室代码（本院）
			json.put("ncOperationDepartmentNameL", map.get("NCOPERATIONDEPARTMENTNAMEL"));// 手术执行科室名称（本院）
			json.put("ncPathologyExam", map.get("NCPATHOLOGYEXAM"));// 病理检查
			json.put("ncOtherMedicalTreatment", map.get("NCOTHERMEDICALTREATMENT"));// 其他医学处置
			json.put("ncIsOutOfRange", map.get("NCISOUTOFRANGE"));// 是否超出标准手术时间
			json.put("ncOperationSurgeon", map.get("NCOPERATIONSURGEON"));// 手术医师姓名
			json.put("ncFirstAssistant", map.get("NCFIRSTASSISTANT"));// 助手Ⅰ姓名
			json.put("ncSecondAssistant", map.get("NCSECONDASSISTANT"));// 助手II姓名
			json.put("ncWhetherSelective", map.get("NCWHETHERSELECTIVE"));// 是否择期
			json.put("ncWhetherCancel", map.get("NCWHETHERCANCEL"));// 是否选择取消手术
			json.put("ncOperationSiteCode", map.get("NCOPERATIONSITECODE"));// 手术操作部位代码
			json.put("ncOperationSiteName", map.get("NCOPERATIONSITENAME"));// 手术操作部位
			json.put("ncOperationSiteCodeL", map.get("NCOPERATIONSITECODEL"));// 手术操作部位代码（本院）
			json.put("ncOperationSiteNameL", map.get("NCOPERATIONSITENAMEL"));// 手术操作部位名称（本院）
			json.put("ncOperationDuration", map.get("NCOPERATIONDURATION"));// 手术持续时间
			json.put("ncOperationPatientTypeCode", map.get("NCOPERATIONPATIENTTYPECODE"));// 手术患者类型代码
			json.put("ncIsMainOperation", map.get("NCISMAINOPERATION"));// 是否主要手术
			json.put("ncPostoperativeSituation", map.get("NCPOSTOPERATIVESITUATION"));// 手术后情况
			json.put("ncHasComorbidity", map.get("NCHASCOMORBIDITY"));// 是否手术合并症
			json.put("ncHasComplications", map.get("NCHASCOMPLICATIONS"));// 是否手术并发症
			json.put("ncPostoperativeComplication", map.get("NCPOSTOPERATIVECOMPLICATION"));// 术后并发症名称
			json.put("ncOperationRiskAssessment", map.get("NCOPERATIONRISKASSESSMENT"));// 手术风险评估
			json.put("ncBleedingVolume", map.get("NCBLEEDINGVOLUME"));// 出血量
			json.put("ncBleedingUnit", map.get("NCBLEEDINGUNIT"));// 出血计量单位
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分

		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postAdmissionRecord(String startDate, String endDate) {
		// 将从配置文件中得到的日期反转，跟rowkey一样
		String start = new StringBuffer(startDate.replaceAll("-", "")).reverse().toString();
		String end = new StringBuffer(endDate.replaceAll("-", "")).reverse().toString();

		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("RYSJ", "STRING", MatchType.GE.getOperation(), startDate + " 00:00:00"));
		filters.add(new PropertyFilter("RYSJ", "STRING", MatchType.LE.getOperation(), endDate + " 23:59:59"));
		/**
		 * 在HDR_PATIENT_ZLSB表中 rowkey 是按照入院时间 来开头的所以 查询 过滤 是按照入院时间查询的
		 */
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(operationrecord_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getDischargeDiagnosisJsonInfo(mapInfo);
					jsonall.addAll(json);
				}
				// 反馈报文
				jsonallarr.put("dataList", jsonall);
				// 请求接口
				String responseString = HttpClientPost.post(function_name, version, jsonallarr.toString());
				// 将反馈报文及请求报文记录起来
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				ResponseToMysql.ResponseToMysql(jsonallarr.toString(), responseString, uuid);
				ResponseToMysql.ErrorDetailsToMysql(jsonallarr.toString(), responseString, uuid, function_name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void postAdmissionRecordByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(operationrecord_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getDischargeDiagnosisJsonInfo(mapInfo);
					jsonall.addAll(json);
				}
				jsonallarr.put("dataList", jsonall);
				// 反馈报文
				String responseString = HttpClientPost.post(function_name, version, jsonallarr.toString());
				// 将反馈报文及请求报文记录起来
				ResponseToMysql.RePostResponseToMysql(jsonallarr.toString(), responseString, rowkey);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
