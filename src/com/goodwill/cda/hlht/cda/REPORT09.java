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
 * @Description 类描述：治疗信息（化疗记录） 上传每次归档后，当次住院的化疗信息。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：chemotherapyRecord 版本号：1.0.2
 * @modify 修改记录：
 * 
 */
public class REPORT09 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT09.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "chemotherapyRecord");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "chemotherapyRecord_version");
	private static String chemotherapyrecord_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"chemotherapyRecord_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getChemotherapyRecordJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncChemotherapyNo", map.get("NCCHEMOTHERAPYNO"));// 化疗记录编号
			json.put("ncNowWeight", map.get("NCNOWWEIGHT"));// 现体重
			json.put("ncNowArea", map.get("NCNOWAREA"));// 现体表面积
			json.put("ncChemotherapyStartTime", map.get("NCCHEMOTHERAPYSTARTTIME"));// 化疗开始时间
			json.put("ncChemotherapyEndTime", map.get("NCCHEMOTHERAPYENDTIME"));// 化疗结束时间
			json.put("ncPreOrPostOperation", map.get("NCPREORPOSTOPERATION"));// 与手术关系
			json.put("ncChemotherapyPlan", map.get("NCCHEMOTHERAPYPLAN"));// 化疗方案名称
			json.put("ncChemotherapyContent", map.get("NCCHEMOTHERAPYCONTENT"));// 化疗方案内容
			json.put("ncCourseCount", map.get("NCCOURSECOUNT"));// 疗程数
			json.put("ncCycleCount", map.get("NCCYCLECOUNT"));// 化疗周期数
			json.put("ncDaysPerCycle", map.get("NCDAYSPERCYCLE"));// 化疗一周期长度
			json.put("ncEffect", map.get("NCEFFECT"));// 疗效评价
			json.put("ncComplicationName", map.get("NCCOMPLICATIONNAME"));// 并发症名称
			json.put("ncComplicationHandle", map.get("NCCOMPLICATIONHANDLE"));// 并发症处理
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分

		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postChemotherapyRecord(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(chemotherapyrecord_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getChemotherapyRecordJsonInfo(mapInfo);
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

	public static void postChemotherapyRecordByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil
				.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(chemotherapyrecord_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getChemotherapyRecordJsonInfo(mapInfo);
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
