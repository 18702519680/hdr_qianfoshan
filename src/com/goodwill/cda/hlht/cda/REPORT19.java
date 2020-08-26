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
 * @Description 类描述：住院医嘱 上传每次归档后，当次住院的住院医嘱。包含出院带药等。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：hospitalizationOrder 版本号：1.0.1
 * @modify 修改记录：
 * 
 */
public class REPORT19 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT19.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hospitalizationOrder");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hospitalizationOrder_version");
	private static String hospitalizationorder_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"hospitalizationOrder_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getHospitalizationOrderJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncBedNo", map.get("NCBEDNO"));// 住院床号
			json.put("ncOrderNo", map.get("NCORDERNO"));// 医嘱号
			json.put("ncPrescribedDepartmentCode", map.get("NCPRESCRIBEDDEPARTMENTCODE"));// 医嘱开立科室代码
			json.put("ncPrescribedDepartmentName", map.get("NCPRESCRIBEDDEPARTMENTNAME"));// 医嘱开立科室名称
			json.put("ncPrescribedDepartmentCodeL", map.get("NCPRESCRIBEDDEPARTMENTCODEL"));// 医嘱开立科室代码（本院）
			json.put("ncPrescribedDepartmentNameL", map.get("NCPRESCRIBEDDEPARTMENTNAMEL"));// 医嘱开立科室名称（本院）
			json.put("ncPrescribedTime", map.get("NCPRESCRIBEDTIME"));// 医嘱开立时间
			json.put("ncExcuteDepartmentCode", map.get("NCEXCUTEDEPARTMENTCODE"));// 执行科室代码
			json.put("ncExcuteDepartmentName", map.get("NCEXCUTEDEPARTMENTNAME"));// 执行科室名称
			json.put("ncExcuteDepartmentCodeL", map.get("NCEXCUTEDEPARTMENTCODEL"));// 执行科室代码（本院）
			json.put("ncExcuteDepartmentNameL", map.get("NCEXCUTEDEPARTMENTNAMEL"));// 执行科室名称（本院）
			json.put("ncOrderGroupNo", map.get("NCORDERGROUPNO"));// 医嘱组号
			json.put("ncOrderTypeCode", map.get("NCORDERTYPECODE"));// 医嘱类别代码
			json.put("ncOrderTypeName", map.get("NCORDERTYPENAME"));// 医嘱类别名称
			json.put("ncOrderItemCategoryCode", map.get("NCORDERITEMCATEGORYCODE"));// 医嘱项目分类代码
			json.put("ncOrderItemCategoryName", map.get("NCORDERITEMCATEGORYNAME"));// 医嘱项目分类名称
			json.put("ncOrderItemCategoryCodeL", map.get("NCORDERITEMCATEGORYCODEL"));// 医嘱项目分类代码（本院）
			json.put("ncOrderItemCategoryNameL", map.get("NCORDERITEMCATEGORYNAMEL"));// 医嘱项目分类名称（本院）
			json.put("ncLocalOrderTypeCode", map.get("NCLOCALORDERTYPECODE"));// 院内医嘱明细代码
			json.put("ncLocalOrderTypeName", map.get("NCLOCALORDERTYPENAME"));// 院内医嘱明细名称
			json.put("ncOrderStartTime", map.get("NCORDERSTARTTIME"));// 医嘱开始日期时间
			json.put("ncOrderEndTime", map.get("NCORDERENDTIME"));// 医嘱停止日期时间
			json.put("ncDrugTypeCode", map.get("NCDRUGTYPECODE"));// 药物类型代码
			json.put("ncDrugTypeName", map.get("NCDRUGTYPENAME"));// 药物类型名称
			json.put("ncDrugTypeCodeL", map.get("NCDRUGTYPECODEL"));// 药物类型代码（本院）
			json.put("ncDrugTypeNameL", map.get("NCDRUGTYPENAMEL"));// 药物类型名称（本院）
			json.put("ncDrugFormulationCode", map.get("NCDRUGFORMULATIONCODE"));// 药物剂型代码
			json.put("ncDrugFormulationName", map.get("NCDRUGFORMULATIONNAME"));// 药物剂型名称
			json.put("ncDrugFormulationCodeL", map.get("NCDRUGFORMULATIONCODEL"));// 药物剂型代码（本院）
			json.put("ncDrugFormulationNameL", map.get("NCDRUGFORMULATIONNAMEL"));// 药物剂型名称（本院）
			json.put("ncTcmTypeCode", map.get("NCTCMTYPECODE"));// 中药类别代码
			json.put("ncTcmTypeName", map.get("NCTCMTYPENAME"));// 中药类别名称
			json.put("ncTcmTypeCodeL", map.get("NCTCMTYPECODEL"));// 中药类别代码（本院）
			json.put("ncTcmTypeNameL", map.get("NCTCMTYPENAMEL"));// 中药类别名称（本院）
			json.put("ncFootnote", map.get("NCFOOTNOTE"));// 草药脚注
			json.put("ncDrugName", map.get("NCDRUGNAME"));// 药物商品名
			json.put("ncDrugCommonName", map.get("NCDRUGCOMMONNAME"));// 药物通用名称
			json.put("ncDrugSpec", map.get("NCDRUGSPEC"));// 药品规格
			json.put("ncDrugQuantity", map.get("NCDRUGQUANTITY"));// 发药数量
			json.put("ncDrugUnit", map.get("NCDRUGUNIT"));// 发药数量单位
			json.put("ncDrugFrequency", map.get("NCDRUGFREQUENCY"));// 药物使用-频率
			json.put("ncDrugTotalDosage", map.get("NCDRUGTOTALDOSAGE"));// 药物使用-总剂量
			json.put("ncDrugFractionDose", map.get("NCDRUGFRACTIONDOSE"));// 药物使用-次剂量
			json.put("ncDrugFractionDoseUnit", map.get("NCDRUGFRACTIONDOSEUNIT"));// 药物使用-剂量单位
			json.put("ncDrugApproachCode", map.get("NCDRUGAPPROACHCODE"));// 药物使用-途径代码
			json.put("ncDrugApproachName", map.get("NCDRUGAPPROACHNAME"));// 药物使用-途径名称
			json.put("ncDrugApproachCodeL", map.get("NCDRUGAPPROACHCODEL"));// 药物使用-途径代码（本院）
			json.put("ncDrugApproachNameL", map.get("NCDRUGAPPROACHNAMEL"));// 药物使用-途径名称（本院）
			json.put("ncDrugStartTime", map.get("NCDRUGSTARTTIME"));// 用药开始时间
			json.put("ncDrugEndTime", map.get("NCDRUGENDTIME"));// 用药停止时间
			json.put("ncDrugDays", map.get("NCDRUGDAYS"));// 用药天数
			json.put("ncCourse", map.get("NCCOURSE"));// 疗程时间
			json.put("ncIsShinTestAllergic", map.get("NCISSHINTESTALLERGIC"));// 皮试判别(是否过敏)
			json.put("ncIsMainDrug", map.get("NCISMAINDRUG"));// 是否主药
			json.put("ncIsBasicDrug", map.get("NCISBASICDRUG"));// 是否基本药物
			json.put("ncDrugManufacture", map.get("NCDRUGMANUFACTURE"));// 药品生产厂家
			json.put("ncDrugApprovalNumber", map.get("NCDRUGAPPROVALNUMBER"));// 药品批准文号
			json.put("ncDrugBatchNO", map.get("NCDRUGBATCHNO"));// 药品生产批号
			json.put("ncFile", map.get("NCFILE"));// 二进制文件
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postHospitalizationOrder(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(hospitalizationorder_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 10; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getHospitalizationOrderJsonInfo(mapInfo);
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
				.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(hospitalizationorder_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getHospitalizationOrderJsonInfo(mapInfo);
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
