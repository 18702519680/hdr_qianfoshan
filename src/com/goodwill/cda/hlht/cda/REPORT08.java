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
 * @Description 类描述：麻醉事件 上传每次归档后，手术麻醉中发生的各个事件。包括麻醉用药，输血，以及其他事件
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：anesthesiaEvent 版本号：1.1.0
 * @modify 修改记录：
 * 
 */
public class REPORT08 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT08.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "anesthesiaEvent");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "anesthesiaEvent_version");
	private static String anesthesiaevent_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"anesthesiaEvent_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getAnesthesiaEventJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncOperationApplyNo", map.get("NCOPERATIONAPPLYNO"));// 手术申请单号
			json.put("ncOperationNo", map.get("NCOPERATIONNO"));// 手术序列号
			json.put("ncOperationDate", map.get("NCOPERATIONDATE"));// 手术日期
			json.put("ncEventNo", map.get("NCEVENTNO"));// 事件番号
			json.put("ncEventCategory", map.get("NCEVENTCATEGORY"));// 事件分类
			json.put("ncEventName", map.get("NCEVENTNAME"));// 事件名称
			json.put("ncDrugApproachCode", map.get("NCDRUGAPPROACHCODE"));// 用药途径代码
			json.put("ncDrugApproachName", map.get("NCDRUGAPPROACHNAME"));// 用药途径名称
			json.put("ncDrugApproachCodeL", map.get("NCDRUGAPPROACHCODEL"));// 用药途径代码（本院）
			json.put("ncDrugApproachNameL", map.get("NCDRUGAPPROACHNAMEL"));// 用药途径名称（本院）
			json.put("ncAnesthesiaDrugCode", map.get("NCANESTHESIADRUGCODE"));// 麻醉药物代码
			json.put("ncAnesthesiaDrugName", map.get("NCANESTHESIADRUGNAME"));// 麻醉药物名称
			json.put("ncAnesthesiaDrugCodeL", map.get("NCANESTHESIADRUGCODEL"));// 麻醉药物代码（本院）
			json.put("ncAnesthesiaDrugNameL", map.get("NCANESTHESIADRUGNAMEL"));// 麻醉药物名称（本院）
			json.put("ncConcentration", map.get("NCCONCENTRATION"));// 浓度
			json.put("ncConcentrationUnit", map.get("NCCONCENTRATIONUNIT"));// 浓度单位
			json.put("ncSpeed", map.get("NCSPEED"));// 速度
			json.put("ncSpeedUnit", map.get("NCSPEEDUNIT"));// 速度单位
			json.put("ncDosage", map.get("NCDOSAGE"));// 剂量
			json.put("ncDosageUnit", map.get("NCDOSAGEUNIT"));// 剂量单位
			json.put("ncEventStartTime", map.get("NCEVENTSTARTTIME"));// 开始时间
			json.put("ncEventEndTime", map.get("NCEVENTENDTIME"));// 结束时间
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分

		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postAnesthesiaEvent(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(anesthesiaevent_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 10; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getAnesthesiaEventJsonInfo(mapInfo);
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

	public static void postAnesthesiaEventByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil
				.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(anesthesiaevent_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getAnesthesiaEventJsonInfo(mapInfo);
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
