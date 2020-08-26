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
 * @Description 类描述：实验室检验详细记录
 *              上传每次归档后，上传每次归档后，当次住院的实验室检验详细信息。当一次检验有多个子项时，分别记录在不同的数据行上。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：laboratoryTestDetail 版本号：1.0.1
 * @modify 修改记录：
 * 
 */
public class REPORT18 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT18.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "laboratoryTestDetail");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "laboratoryTestDetail_version");
	private static String laboratorytestdetail_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"laboratoryTestDetail_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getLaboratoryTestDetailJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncApplyNo", map.get("NCAPPLYNO"));// 申请单号
			json.put("ncTestMethod", map.get("NCTESTMETHOD"));// 检验方法
			json.put("ncReferenceLowValue", map.get("NCREFERENCELOWVALUE"));// 参考低值
			json.put("ncReferenceHighValue", map.get("NCREFERENCEHIGHVALUE"));// 参考高值
			json.put("ncReferenceRange", map.get("NCREFERENCERANGE"));// 参考值范围
			json.put("ncUnit", map.get("NCUNIT"));// 检验-计量单位
			json.put("ncQuantitiveResult", map.get("NCQUANTITIVERESULT"));// 检验-结果(数值)
			json.put("ncQualitativeResult", map.get("NCQUALITATIVERESULT"));// 检验-结果(定性)
			json.put("ncReportNo", map.get("NCREPORTNO"));// 报告单号
			json.put("ncReportTime", map.get("NCREPORTTIME"));// 报告时间
			json.put("ncTestItemCode", map.get("NCTESTITEMCODE"));// 检验-项目代码
			json.put("ncTestItemName", map.get("NCTESTITEMNAME"));// 检验-项目名称
			json.put("ncTestItemCodeL", map.get("NCTESTITEMCODEL"));// 检验-项目代码（本院）
			json.put("ncTestItemNameL", map.get("NCTESTITEMNAMEL"));// 检验-项目名称（本院）
			json.put("ncTestSubItemCode", map.get("NCTESTSUBITEMCODE"));// 检验-子项目明细代码
			json.put("ncTestSubItemChineseName", map.get("NCTESTSUBITEMCHINESENAME"));// 检验-子项目明细名称（中文）
			json.put("ncTestSubItemEnglishName", map.get("NCTESTSUBITEMENGLISHNAME"));// 检验-子项目明细名称（英文）
			json.put("ncAbnormalFlag", map.get("NCABNORMALFLAG"));// 检查/检验结果异常标识
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postLaboratoryTestDetail(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(laboratorytestdetail_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 10; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getLaboratoryTestDetailJsonInfo(mapInfo);
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

	public static void postLaboratoryTestDetailByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil
				.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(laboratorytestdetail_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getLaboratoryTestDetailJsonInfo(mapInfo);
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
