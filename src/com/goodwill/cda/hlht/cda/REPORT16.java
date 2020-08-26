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
 * @Description 类描述：影像学检查 上传每次归档后，当次住院的影像检查记录信息。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：imageologyExam 版本号：1.0.2
 * @modify 修改记录：
 * 
 */
public class REPORT16 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT16.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "imageologyExam");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "imageologyExam_version");
	private static String imageologyexam_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"imageologyExam_tablename");

	public static void main(String[] args) throws Exception {
		postImageologyExam("2018-07-10","2018-07-20");
	}

	public static JSONArray getImageologyExamJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncSendToTestDate", map.get("NCSENDTOTESTDATE"));// 送检日期
			json.put("ncSendDepartmentCode", map.get("NCSENDDEPARTMENTCODE"));// 送检科室代码
			json.put("ncSendDepartmentName", map.get("NCSENDDEPARTMENTNAME"));// 送检科室名称
			json.put("ncSendDepartmentCodeL", map.get("NCSENDDEPARTMENTCODEL"));// 送检科室代码（本院）
			json.put("ncSendDepartmentNameL", map.get("NCSENDDEPARTMENTNAMEL"));// 送检科室名称（本院）
			json.put("ncExamSite", map.get("NCEXAMSITE"));// 检查部位
			json.put("ncExamTypeCode", map.get("NCEXAMTYPECODE"));// 检查类别代码
			json.put("ncExamTypeName", map.get("NCEXAMTYPENAME"));// 检查类别名称
			json.put("ncExamTypeCodeL", map.get("NCEXAMTYPECODEL"));// 检查类别代码（本院）
			json.put("ncExamTypeNameL", map.get("NCEXAMTYPENAMEL"));// 检查类别名称（本院）
			json.put("ncLocalExamItemCode", map.get("NCLOCALEXAMITEMCODE"));// 院内检查项目代码
			json.put("ncLocalExamItemName", map.get("NCLOCALEXAMITEMNAME"));// 院内检查项目名称
			json.put("ncImagingFindings", map.get("NCIMAGINGFINDINGS"));// 影像表现
			json.put("ncDiagnosis", map.get("NCDIAGNOSIS"));// 诊断
			json.put("ncReportDate", map.get("NCREPORTDATE"));// 报告日期
			json.put("ncReporter", map.get("NCREPORTER"));// 报告医生
			json.put("ncReviewer", map.get("NCREVIEWER"));// 审核医生
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;	}

	public static void postImageologyExam(String startDate, String endDate) {
		// 将从配置文件中得到的日期反转，跟rowkey一样
		String start = new StringBuffer(startDate.replaceAll("-", "")).reverse().toString();
		String end = new StringBuffer(endDate.replaceAll("-", "")).reverse().toString();
		
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("ncReportDate", "STRING", MatchType.GE.getOperation(), startDate + " 00:00:00"));
		filters.add(new PropertyFilter("ncReportDate", "STRING", MatchType.LE.getOperation(), endDate + " 23:59:59"));
		/**
		 * 在HDR_EXAMREPORT_ZLSB表中 rowkey 是按照上报时间反转来开头的所以 查询 过滤 是按照上报时间查询的
		 */
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(imageologyexam_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getImageologyExamJsonInfo(mapInfo);
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

	public static void postImageologyExamByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(imageologyexam_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getImageologyExamJsonInfo(mapInfo);
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
