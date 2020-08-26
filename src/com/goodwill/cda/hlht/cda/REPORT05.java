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
 * @Description 类描述：出院诊断信息
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：dischargeDiagnosisInfo 版本号：1.0.1
 * @modify 修改记录：
 * 
 */
public class REPORT05 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT05.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "dischargeDiagnosisInfo");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"dischargeDiagnosisInfo_version");
	private static String dischargediagnosisinfo_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"dischargeDiagnosisInfo_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getDischargeDiagnosisJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("ncMedicalRecordNo"));// 病案号
			json.put("ncDischargeTime", map.get("ncDischargeTime"));// 出院时间
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));// 住院号
			json.put("ncMainDiagnosisName", map.get("ncMainDiagnosisName"));// 出院主要诊断名称
			json.put("ncMainIcd9Code", map.get("ncMainIcd9Code"));// 出院主要诊断编码(ICD-9)
			json.put("ncMainIcd10Code", map.get("ncMainIcd10Code"));// 出院主要诊断编码(ICD-10)
			json.put("ncMainIcd11Code", map.get("ncMainIcd11Code"));// 出院主要诊断编码(ICD-11)
			json.put("ncMainConditionCode", map.get("ncMainConditionCode"));// 出院主要诊断入院病情代码
			json.put("ncMainIcdCodeL", map.get("ncMainIcdCodeL"));// 出院主要诊断编码（本院）
			json.put("ncMainDiagnosisNameL", map.get("ncMainDiagnosisNameL"));// 出院主要诊断名称（本院）
			json.put("ncMainIcdCodeVerNameL", map.get("ncMainIcdCodeVerNameL"));// 出院主要诊断编码版本（本院）
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncAdmissionNo", map.get("ncAdmissionNo"));//
			json.put("ncCancel", map.get("ncCancel"));// 取消区分
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postDischargeDiagnosisInfo(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(dischargediagnosisinfo_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < 10; i++) {
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

	public static void postDischargeDiagnosisInfoByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(dischargediagnosisinfo_tablename, listGet, columns));
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
