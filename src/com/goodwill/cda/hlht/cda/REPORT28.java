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
 * @Description 类描述：死亡记录 上传每次归档后，患者的死亡记录
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：deathRecord 版本号：1.0.1
 * @modify 修改记录：
 * 
 */
public class REPORT28 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT28.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "deathRecord");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "deathRecord_version");
	private static String deathrecord_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"deathRecord_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getDeathRecordJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncAdmissionTime", map.get("NCADMISSIONTIME"));// 入院时间
			json.put("ncChiefComplaint", map.get("NCCHIEFCOMPLAINT"));// 主诉
			json.put("ncAdmissionCondition", map.get("NCADMISSIONCONDITION"));// 入院情况
			json.put("ncDiagnosisProcess", map.get("NCDIAGNOSISPROCESS"));// 诊疗过程
			json.put("ncDeathTime", map.get("NCDEATHTIME"));// 死亡时间
			json.put("ncDeathDepartmentCode", map.get("NCDEATHDEPARTMENTCODE"));// 死亡科室代码
			json.put("ncDeathDepartmentName", map.get("NCDEATHDEPARTMENTNAME"));// 死亡科室名称
			json.put("ncDeathDepartmentCodeL", map.get("NCDEATHDEPARTMENTCODEL"));// 死亡科室代码（本院）
			json.put("ncDeathDepartmentNameL", map.get("NCDEATHDEPARTMENTNAMEL"));// 死亡科室名称（本院）
			json.put("ncDeathReason", map.get("NCDEATHREASON"));// 死亡原因
			json.put("ncDeathDetail", map.get("NCDEATHDETAIL"));// 死亡详细记录
			json.put("ncDeathDiagnosisList", map.get("NCDEATHDIAGNOSISLIST"));// 死亡诊断（结构化）

			json.put("ncDeathReasonIcd9Code", map.get("NCDEATHREASONICD9CODE"));// 死因编码ICD-9
			json.put("ncDeathReasonIcd10Code", map.get("NCDEATHREASONICD10CODE"));// 死因编码ICD-10
			json.put("ncDeathReasonIcd11Code", map.get("NCDEATHREASONICD11CODE"));// 死因编码ICD-11
			json.put("ncDeathReasonDiagnosisName", map.get("NCDEATHREASONDIAGNOSISNAME"));// 死因诊断名称
			json.put("ncDeathReasonIcdCodeL", map.get("NCDEATHREASONICDCODEL"));// 死因编码ICD（本院）
			json.put("ncDeathReasonDiagnosisNameL", map.get("NCDEATHREASONDIAGNOSISNAMEL"));// 死因诊断名称（本院）
			json.put("ncDeathReasonDiagnosisVerNameL", map.get("NCDEATHREASONDIAGNOSISVERNAMEL"));// 死因诊断编码版本（本院）

			json.put("ncFile", map.get("NCFILE"));// 二进制文件
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postDeathRecord(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(deathrecord_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 10; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getDeathRecordJsonInfo(mapInfo);
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

	public static void postDeathRecordByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(deathrecord_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getDeathRecordJsonInfo(mapInfo);
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
