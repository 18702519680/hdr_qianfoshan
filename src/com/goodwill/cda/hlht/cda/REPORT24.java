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
 * @Description 类描述：病理记录 上传每次归档后，当次住院的日常病程详细记录。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：pathologyInfo 版本号：1.0.0
 * @modify 修改记录：
 * 
 */
public class REPORT24 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT24.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "pathologyInfo");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "pathologyInfo_version");
	private static String pathologyinfo_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"pathologyInfo_tablename");

	public static void main(String[] args) throws Exception {
		postPathologyInfo("2014-10-10", "2014-12-15");
	}

	public static JSONArray getPathologyInfoJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("ncMedicalRecordNo"));// 病案号
			//json.put("ncDischargeTime", map.get("ncDischargeTime"));// 出院时间
			json.put("ncDischargeTime", "20200806093625");// 出院时间
			json.put("ncPathologyNo", map.get("ncPathologyNo"));// 病理号
			json.put("ncPathologyChineseName", map.get("ncPathologyChineseName"));// 病理项目中文名称
			json.put("ncExamTime", map.get("ncExamTime"));// 病理检查时间
			json.put("ncDetectionMethod", map.get("ncDetectionMethod"));// 检测方法
			json.put("ncDetectionEquipment", map.get("ncDetectionEquipment"));// 检测设备
			json.put("ncSpecimen", map.get("ncSpecimen"));// 标本信息
			json.put("ncSpecimenNo", map.get("ncSpecimenNo"));// 标本号
			json.put("ncSpecimenType", map.get("ncSpecimenType"));// 标本类型
			json.put("ncSpecimenSite", map.get("ncSpecimenSite"));// 标本部位
			json.put("ncSpecimenWeight", map.get("ncSpecimenWeight"));// 标本重量
			json.put("ncQuantity", map.get("ncQuantity"));// 数量
			json.put("ncCollectMethod", map.get("ncCollectMethod"));// 采集方法
			json.put("ncSpecimenState", map.get("ncSpecimenState"));// 标本状态
			json.put("ncSamplingTime", map.get("ncSamplingTime"));// 采样时间
			json.put("ncSendToTestDate", map.get("ncSendToTestDate"));// 送检时间
			json.put("ncPrimaryFociSize", map.get("ncPrimaryFociSize"));// 原发灶大小
			json.put("ncReportTime", map.get("ncReportTime"));// 报告时间
			json.put("ncStainingMethod", map.get("ncStainingMethod"));// 染色方法
			json.put("ncClinicalDiagnosis", map.get("ncClinicalDiagnosis"));// 临床诊断
			json.put("ncGrossFindings", map.get("ncGrossFindings"));// 大体所见
			json.put("ncPathologyDiagnosis", map.get("ncPathologyDiagnosis"));// 病理诊断
			json.put("ncDifferentiation", map.get("ncDifferentiation"));// 分化程度
			json.put("ncInvasionRange", map.get("ncInvasionRange"));// 浸润范围
			json.put("ncVesselInvasion", map.get("ncVesselInvasion"));// 脉管浸润
			json.put("ncNervusInvasion", map.get("ncNervusInvasion"));// 神经周围浸润
			json.put("ncApsularInvasion", map.get("ncApsularInvasion"));// 包膜浸润
			json.put("ncSurgicalMargin", map.get("ncSurgicalMargin"));// 手术切缘
			json.put("ncIhcResult", map.get("ncIhcResult"));// 免疫组化结果
			json.put("ncFile", map.get("ncFile"));// 二进制文件
			json.put("ncCancel", "0");// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postPathologyInfo(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(pathologyinfo_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 2; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getPathologyInfoJsonInfo(mapInfo);
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

	public static void postPathologyInfoByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(pathologyinfo_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getPathologyInfoJsonInfo(mapInfo);
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
