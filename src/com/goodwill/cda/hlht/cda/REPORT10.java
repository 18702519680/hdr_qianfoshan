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
 * @Description 类描述：治疗信息（化疗用药记录） 上传每次归档后，当次住院的化疗时使用的药物信息。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：chemotherapyDrugRecord 版本号：1.0.1
 * @modify 修改记录：
 * 
 */
public class REPORT10 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT10.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "chemotherapyDrugRecord");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"chemotherapyDrugRecord_version");
	private static String chemotherapydrugrecord_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"chemotherapyDrugRecord_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getChemotherapyDrugRecordJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncChemotherapyRecordNo", map.get("NCCHEMOTHERAPYRECORDNO"));// 化疗记录编号
			json.put("ncChemotherapyTime", map.get("NCCHEMOTHERAPYTIME"));// 化疗时间
			json.put("ncWhichChemotherapyCourse", map.get("NCWHICHCHEMOTHERAPYCOURSE"));// 化疗第几疗程
			json.put("ncWhichChemotherapyCycle", map.get("NCWHICHCHEMOTHERAPYCYCLE"));// 化疗第几周期
			json.put("ncWhichChemotherapyDay", map.get("NCWHICHCHEMOTHERAPYDAY"));// 化疗第几天
			json.put("ncChemotherapyDrug", map.get("NCCHEMOTHERAPYDRUG"));// 化疗药物
			json.put("ncDosage", map.get("NCDOSAGE"));// 剂量
			json.put("ncUnit", map.get("NCUNIT"));// 单位
			json.put("ncCount", map.get("NCCOUNT"));// 次数
			json.put("ncDrugApproachCode", map.get("NCDRUGAPPROACHCODE"));// 用药途径代码
			json.put("ncDrugApproachName", map.get("NCDRUGAPPROACHNAME"));// 用药途径名称
			json.put("ncDrugApproachCodeL", map.get("NCDRUGAPPROACHCODEL"));// 用药途径代码（本院）
			json.put("ncDrugApproachNameL", map.get("NCDRUGAPPROACHNAMEL"));// 用药途径名称（本院）
			json.put("ncAdministrationStartTime", map.get("NCADMINISTRATIONSTARTTIME"));// 给药开始时间
			json.put("ncAdministrationEndTime", map.get("NCADMINISTRATIONENDTIME"));// 给药结束时间
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postChemotherapyDrugRecord(String startDate, String endDate) {
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
		listSumm = Xmlutil
				.formatList(HbaseCURDUtils.findByRowkey(chemotherapydrugrecord_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 10; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getChemotherapyDrugRecordJsonInfo(mapInfo);
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

	public static void postAllPatientBasicInfoByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil
				.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(chemotherapydrugrecord_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getChemotherapyDrugRecordJsonInfo(mapInfo);
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
