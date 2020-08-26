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
 * @Description 类描述：住院费用（明细） 上传每次归档后，当次住院的详细费用。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：hospitalizationCostDetail 版本号：1.0.1
 * @modify 修改记录：
 * 
 */
public class REPORT21 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT21.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"hospitalizationCostDetail");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"hospitalizationCostDetail_version");
	private static String hospitalizationcostdetail_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"hospitalizationCostDetail_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getHospitalizationCostDetailJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncFeeNo", map.get("NCFEENO"));// 费用项目序号
			json.put("ncFeeTypeCode", map.get("NCFEETYPECODE"));// 费用项目类别代码
			json.put("ncFeeTypeCodeL", map.get("NCFEETYPECODEL"));// 费用项目类别代码（本院）
			json.put("ncFeeTypeNameL", map.get("NCFEETYPENAMEL"));// 费用项目类别名称（本院）
			json.put("ncFeeItemName", map.get("NCFEEITEMNAME"));// 收费项目名称
			json.put("ncSpecifications", map.get("NCSPECIFICATIONS"));// 项目规格
			json.put("ncUnit", map.get("NCUNIT"));// 单位
			json.put("ncPrice", map.get("NCPRICE"));// 单价
			json.put("ncQuantity", map.get("NCQUANTITY"));// 数量
			json.put("ncTotalPrice", map.get("NCTOTALPRICE"));// 费用金额
			json.put("ncBillDepartmentCode", map.get("NCBILLDEPARTMENTCODE"));// 开单科室代码
			json.put("ncBillDepartmentName", map.get("NCBILLDEPARTMENTNAME"));// 开单科室名称
			json.put("ncBillDepartmentCodeL", map.get("NCBILLDEPARTMENTCODEL"));// 开单科室代码（本院）
			json.put("ncBillDepartmentNameL", map.get("NCBILLDEPARTMENTNAMEL"));// 开单科室名称（本院）
			json.put("ncExcuteDepartmentCode", map.get("NCEXCUTEDEPARTMENTCODE"));// 执行科室代码
			json.put("ncExcuteDepartmentName", map.get("NCEXCUTEDEPARTMENTNAME"));// 执行科室名称
			json.put("ncExcuteDepartmentCodeL", map.get("NCEXCUTEDEPARTMENTCODEL"));// 执行科室代码（本院）
			json.put("ncExcuteDepartmentNameL", map.get("NCEXCUTEDEPARTMENTNAMEL"));// 执行科室名称（本院）
			json.put("ncBillingTime", map.get("NCBILLINGTIME"));// 收费时间
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postHospitalizationCostDetail(String startDate, String endDate) {
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
				.formatList(HbaseCURDUtils.findByRowkey(hospitalizationcostdetail_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 10; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getHospitalizationCostDetailJsonInfo(mapInfo);
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

	public static void postHospitalizationCostDetailByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(
				HbaseCURDUtilsToCDA.findByRowkeyList(hospitalizationcostdetail_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getHospitalizationCostDetailJsonInfo(mapInfo);
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
