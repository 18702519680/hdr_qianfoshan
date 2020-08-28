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
 * @Description 类描述：病理记录（淋巴结受累） 上传每次归档后，当次住院的病理信息的淋巴结受累情况。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：lymphNodePathology 版本号：1.0.0
 * @modify 修改记录：
 * 
 */
public class REPORT25 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT25.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "lymphNodePathology");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "lymphNodePathology_version");
	private static String lymphnodepathology_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"lymphNodePathology_tablename");

	public static void main(String[] args) throws Exception {
		postLymphNodePathology("2018-10-10", "2018-10-11");
	}

	public static JSONArray getLymphNodePathologyJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//		filters.add(new PropertyFilter("NCMEDICALRECORDNO", "STRING", MatchType.EQ.getOperation(), map.get("ncMedicalRecordNo")));
//		List<Map<String, String>> listDiag = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByCondition(
//				"HDR_MEDICALRECORDFIRSTPAGE_ZLSB", HbaseCURDUtilsToCDA.getRowkeyPrefix(map.get("ncMedicalRecordNo")), filters));
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("ncMedicalRecordNo"));// 病案号
			json.put("ncDischargeTime", map.get("ncDischargeTime"));// 出院时间
			json.put("ncPathologyNo", map.get("ncPathologyNo"));// 病理号
			json.put("ncPathologyChineseName", map.get("ncPathologyChineseName"));// 病理项目中文名称
			json.put("ncExamTime", map.get("ncExamTime"));// 病理检查时间
			json.put("ncSendToTestTime", map.get("ncSendToTestDate"));// 送检时间
			json.put("ncReportTime", map.get("ncReportTime"));// 报告时间
			json.put("ncRegionalLymphNodeCount", "");// 区域淋巴结个数
			json.put("ncLymphNodeInvolvementCount", "");// 淋巴结受累个数
			json.put("ncSendToTestCount", "");// 送检个数
			json.put("ncCancel", "1");// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postLymphNodePathology(String startDate, String endDate) {
		// 将从配置文件中得到的日期反转，跟rowkey一样
		String start = new StringBuffer(startDate.replaceAll("-", "")).reverse().toString();
		String end = new StringBuffer(endDate.replaceAll("-", "")).reverse().toString();

		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("ncReportTime", "STRING", MatchType.GE.getOperation(), (startDate + "000000").replace("-", "")));
		filters.add(new PropertyFilter("ncReportTime", "STRING", MatchType.LE.getOperation(), (endDate + "235959").replace("-", "")));
		/**
		 * 在HDR_PATIENT_ZLSB表中 rowkey 是按照入院时间 来开头的所以 查询 过滤 是按照入院时间查询的
		 */
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(lymphnodepathology_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 2; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getLymphNodePathologyJsonInfo(mapInfo);
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

	public static void postLymphNodePathologyByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil
				.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(lymphnodepathology_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getLymphNodePathologyJsonInfo(mapInfo);
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
