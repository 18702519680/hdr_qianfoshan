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
 * @Description 类描述：入出院信息
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：admissionDischargeInfo 版本号：1.0.1
 * @modify 修改记录：
 * 
 */
public class REPORT03 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT03.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "admissionDischargeInfo");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"admissionDischargeInfo_version");
	private static String admissiondischargeinfo_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"admissionDischargeInfo_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getAdmissionDischargeJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncAdmissionApproachCode", map.get("NCADMISSIONAPPROACHCODE"));// 入院途径代码
			json.put("ncAdmissionTime", map.get("NCADMISSIONTIME"));// 入院时间
			json.put("ncInpatientDepartmentCode", map.get("NCINPATIENTDEPARTMENTCODE"));// 入院科别代码
			json.put("ncInpatientDepartmentName", map.get("NCINPATIENTDEPARTMENTNAME"));// 入院科别名称
			json.put("ncInpatientDepartmentCodeL", map.get("NCINPATIENTDEPARTMENTCODEL"));// 入院科别代码（本院）
			json.put("ncInpatientDepartmentNameL", map.get("NCINPATIENTDEPARTMENTNAMEL"));// 入院科别名称（本院）
			json.put("ncInpatientRoom", map.get("NCINPATIENTROOM"));// 入院病房
			json.put("ncFirstVisitTime", map.get("NCFIRSTVISITTIME"));// 初次就诊时间
			json.put("ncDischargeTypeCode", map.get("NCDISCHARGETYPECODE"));// 离院方式代码
			json.put("ncAcceptingHospitalCode", map.get("NCACCEPTINGHOSPITALCODE"));// 拟接收医疗机构代码
			json.put("ncAcceptingHospitalName", map.get("NCACCEPTINGHOSPITALNAME"));// 拟接收医疗机构名称
			json.put("ncDischargeDepartmentCode", map.get("NCDISCHARGEDEPARTMENTCODE"));// 出院科别代码
			json.put("ncDischargeDepartmentName", map.get("NCDISCHARGEDEPARTMENTNAME"));// 出院科别名称
			json.put("ncDischargeDepartmentCodeL", map.get("NCDISCHARGEDEPARTMENTCODEL"));// 出院科别代码（本院）
			json.put("ncDischargeDepartmentNameL", map.get("NCDISCHARGEDEPARTMENTNAMEL"));// 出院科别名称（本院）
			json.put("ncDischargeRoom", map.get("NCDISCHARGEROOM"));// 出院病房
			json.put("ncHospitalizationDays", map.get("NCHOSPITALIZATIONDAYS"));// 住院患者住院天数
			json.put("ncHasReadmissionPlan", map.get("NCHASREADMISSIONPLAN"));// 是否有出院31天内再住院计划
			json.put("ncReadmissionPurpose", map.get("NCREADMISSIONPURPOSE"));// 再住院计划目的
			json.put("ncCciComaDaysBefore", map.get("NCCCICOMADAYSBEFORE"));// 颅脑损伤患者入院前昏迷天数
			json.put("ncCciComaHoursBefore", map.get("NCCCICOMAHOURSBEFORE"));// 颅脑损伤患者入院前昏迷小时数
			json.put("ncCciComaMinutesBefore", map.get("NCCCICOMAMINUTESBEFORE"));// 颅脑损伤患者入院前昏迷分钟数
			json.put("ncCciComaDaysAfter", map.get("NCCCICOMADAYSAFTER"));// 颅脑损伤患者入院后昏迷天数
			json.put("ncCciComaHoursAfter", map.get("NCCCICOMAHOURSAFTER"));// 颅脑损伤患者入院后昏迷小时数
			json.put("ncCciComaMinutesAfter", map.get("NCCCICOMAMINUTESAFTER"));// 颅脑损伤患者入院后昏迷分钟数
			json.put("ncDeathFlag", map.get("NCDEATHFLAG"));// 死亡标识
			json.put("ncDeadPatientAutopsyFlag", map.get("NCDEADPATIENTAUTOPSYFLAG"));// 死亡患者尸检标志
			json.put("ncDirector", map.get("NCDIRECTOR"));// 科主任姓名
			json.put("ncChiefPhysician", map.get("NCCHIEFPHYSICIAN"));// 主任(副主任)医师姓名
			json.put("ncAttendingPhysician", map.get("NCATTENDINGPHYSICIAN"));// 主治医生姓名
			json.put("ncResidentPhysician", map.get("NCRESIDENTPHYSICIAN"));// 住院医师姓名
			json.put("ncPrimaryPhysician", map.get("NCPRIMARYPHYSICIAN"));// 责任护士姓名
			json.put("ncTraineePhysician", map.get("NCTRAINEEPHYSICIAN"));// 进修医师姓名
			json.put("ncInternPhysician", map.get("NCINTERNPHYSICIAN"));// 实习医师姓名
			json.put("ncCoder", map.get("NCCODER"));// 编码员姓名
			json.put("ncQaPhysician", map.get("NCQAPHYSICIAN"));// 质控医师姓名
			json.put("ncQaNurse", map.get("NCQANURSE"));// 质控护士姓名
			json.put("ncMedicalRecordQualityCode", map.get("NCMEDICALRECORDQUALITYCODE"));// 病案质量代码
			json.put("ncMedicalRecordQualityName", map.get("NCMEDICALRECORDQUALITYNAME"));// 病案质量名称
			json.put("ncQaDate", map.get("NCQADATE"));// 质控日期
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postAdmissionDischargeInfo(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(admissiondischargeinfo_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getAdmissionDischargeJsonInfo(mapInfo);
					jsonall.addAll(json);
				}
				// 反馈报文
				jsonallarr.put("dataList", jsonall);
				//请求接口
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
	
	public static void postAdmissionDischargeInfoByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(admissiondischargeinfo_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getAdmissionDischargeJsonInfo(mapInfo);
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
