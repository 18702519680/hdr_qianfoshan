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
 * @Description 类描述：入院记录 记录患者入院时的病史情况
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：admissionRecord 版本号：1.0.0
 * @modify 修改记录：
 * 
 */
public class REPORT06 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT06.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "admissionRecord");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "admissionRecord_version");
	private static String admissionrecord_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"admissionRecord_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getDischargeDiagnosisJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncAdmissionNo", map.get("NCADMISSIONNO"));// 住院号
			json.put("ncNarratorRelationshipCode", map.get("NCNARRATORRELATIONSHIPCODE"));// 患者病史陈述者与患者关系代码
			json.put("ncNarratorRelationshipName", map.get("NCNARRATORRELATIONSHIPNAME"));// 患者病史陈述者与患者关系名称
			json.put("ncNarratorName", map.get("NCNARRATORNAME"));// 患者病史陈述者姓名
			json.put("ncReliability", map.get("NCRELIABILITY"));// 可靠程度
			json.put("ncAdmissionTime", map.get("NCADMISSIONTIME"));// 入院时间
			json.put("ncChiefComplaint", map.get("NCCHIEFCOMPLAINT"));// 主诉
			json.put("ncPresentIllnessHistory", map.get("NCPRESENTILLNESSHISTORY"));// 现病史
			json.put("ncFamilyHistory", map.get("NCFAMILYHISTORY"));// 家族史
			json.put("ncPastMedicalHistory", map.get("NCPASTMEDICALHISTORY"));// 既往疾病史
			json.put("ncTumorGeneticHistory", map.get("NCTUMORGENETICHISTORY"));// 肿瘤遗传史
			json.put("ncPersonalHistory", map.get("NCPERSONALHISTORY"));// 个人史
			json.put("ncMaritalHistory", map.get("NCMARITALHISTORY"));// 婚育史
			json.put("ncMenstrualHistory", map.get("NCMENSTRUALHISTORY"));// 月经史
			json.put("ncFertileHistory", map.get("NCFERTILEHISTORY"));// 生育史
			json.put("ncReproductiveHistory", map.get("NCREPRODUCTIVEHISTORY"));// 哺乳史
			json.put("ncAllergyHistory", map.get("NCALLERGYHISTORY"));// 过敏史
			json.put("ncExposureHistory", map.get("NCEXPOSUREHISTORY"));// 暴露史
			json.put("ncMotherFertileHistory", map.get("NCMOTHERFERTILEHISTORY"));// 母孕史
			json.put("ncFeedHistory", map.get("NCFEEDHISTORY"));// 喂养史
			json.put("ncGrowthHistory", map.get("NCGROWTHHISTORY"));// 发育史
			json.put("ncPhysicalExam", map.get("NCPHYSICALEXAM"));// 专科体格检查
			json.put("ncKpsScore", map.get("NCKPSSCORE"));// KPS评分
			json.put("ncEcogScore", map.get("NCECOGSCORE"));// ECOG评分
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(admissionrecord_tablename, start, end, filters));
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

	public static void postAdmissionRecordByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(admissionrecord_tablename, listGet, columns));
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
