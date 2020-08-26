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
 * @Description 类描述：首次病程记录 上传每次归档后，当次住院的首次病程记录详细信息。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：fistDiseaseCourse 版本号：1.0.0
 * @modify 修改记录：
 * 
 */
public class REPORT22 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT22.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "fistDiseaseCourse");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "fistDiseaseCourse_version");
	private static String fistdiseasecourse_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"fistDiseaseCourse_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getFistDiseaseCourseJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncDiseaseCourseNo", map.get("NCDISEASECOURSENO"));// 病程记录编号
			json.put("ncRecordTime", map.get("NCRECORDTIME"));// 记录时间
			json.put("ncRecorder", map.get("NCRECORDER"));// 记录者姓名
			json.put("ncRecorderRole", map.get("NCRECORDERROLE"));// 记录者角色
			json.put("ncCourseTime", map.get("NCCOURSETIME"));// 病程时间
			json.put("ncCaseCharacteristics", map.get("NCCASECHARACTERISTICS"));// 病例特点
			json.put("ncPrimaryDiagnosis", map.get("NCPRIMARYDIAGNOSIS"));// 初步诊断
			json.put("ncPrimaryDiagnosisBasis", map.get("NCPRIMARYDIAGNOSISBASIS"));// 初步诊断依据
			json.put("ncDifferentialDiagnosis", map.get("NCDIFFERENTIALDIAGNOSIS"));// 鉴别诊断
			json.put("ncDiagnosisTreatmentPlan", map.get("ncDiagnosisTreatmentPlan"));// 诊疗计划
			json.put("ncDiagnosisPhysician", map.get("ncDiagnosisPhysician"));// 诊断医师
			json.put("ncSuperiorPhysician", map.get("ncSuperiorPhysician"));// 上级医师
			json.put("ncResidentPhysician", map.get("NCRESIDENTPHYSICIAN"));// 住院医师
			json.put("ncFile", map.get("NCFILE"));// 二进制文件
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postFistDiseaseCourse(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(fistdiseasecourse_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 10; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getFistDiseaseCourseJsonInfo(mapInfo);
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

	public static void postFistDiseaseCourseByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil
				.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(fistdiseasecourse_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getFistDiseaseCourseJsonInfo(mapInfo);
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
