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
 * @Description 类描述：实验室检验记录 上传每次归档后，当次住院的实验室检验信息。本接口与【实验室检验详细记录】是1：多的关系。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：laboratoryTest 版本号：1.0.1
 * @modify 修改记录：
 * 
 */
public class REPORT17 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT17.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "laboratoryTest");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "laboratoryTest_version");
	private static String laboratoryTest_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"laboratoryTest_tablename");

	public static void main(String[] args) throws Exception {
		postLaboratoryTest("2017-10-10","2017-10-13");
	}

	public static JSONArray getLaboratoryTestJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncReportTypeCode", map.get("NCREPORTTYPECODE"));// 报告单类别代码
			json.put("ncReportTypeName", map.get("NCREPORTTYPENAME"));// 报告单类别名称
			json.put("ncReportTypeCodeL", map.get("NCREPORTTYPECODEL"));// 报告单类别代码（本院）
			json.put("ncReportTypeNameL", map.get("NCREPORTTYPENAMEL"));// 报告单类别名称（本院）
			json.put("ncApplyDepartmentCode", map.get("NCAPPLYDEPARTMENTCODE"));// 申请科室代码
			json.put("ncApplyDepartmentName", map.get("NCAPPLYDEPARTMENTNAME"));// 申请科室名称
			json.put("ncApplyDepartmentCodeL", map.get("NCAPPLYDEPARTMENTCODEL"));// 申请科室代码（本院）
			json.put("ncApplyDepartmentNameL", map.get("NCAPPLYDEPARTMENTNAMEL"));// 申请科室名称（本院）
			json.put("ncApplyNo", map.get("NCAPPLYNO"));// 申请单号
			json.put("ncApplyDocumentName", map.get("NCAPPLYDOCUMENTNAME"));// 申请单据名称
			json.put("ncSamplingTime", map.get("NCSAMPLINGTIME"));// 采样时间
			json.put("ncReceivingTime", map.get("NCRECEIVINGTIME"));// 接收时间
			json.put("ncTestTime", map.get("NCTESTTIME"));// 检验时间
			json.put("ncReportTime", map.get("NCREPORTTIME"));// 报告时间
			json.put("ncSpecimenNo", map.get("NCSPECIMENNO"));// 标本号
			json.put("ncSpecimenName", map.get("NCSPECIMENNAME"));// 标本名称
			json.put("ncTestItemCode", map.get("NCTESTITEMCODE"));// 检验-项目代码
			json.put("ncTestItemName", map.get("NCTESTITEMNAME"));// 检验-项目名称
			json.put("ncLocalTestItemCode", map.get("NCLOCALTESTITEMCODE"));// 院内检验-项目代码
			json.put("ncLocalTestItemName", map.get("NCLOCALTESTITEMNAME"));// 院内检验-项目名称
			json.put("ncTestDepartmentCode", map.get("NCTESTDEPARTMENTCODE"));// 检验科室代码
			json.put("ncTestDepartmentName", map.get("NCTESTDEPARTMENTNAME"));// 检验科室名称
			json.put("ncTestDepartmentCodeL", map.get("NCTESTDEPARTMENTCODEL"));// 检验科室代码（本院）
			json.put("ncTestDepartmentNameL", map.get("NCTESTDEPARTMENTNAMEL"));// 检验科室名称（本院）
			json.put("ncHospDepartmentCode", map.get("NCHOSPDEPARTMENTCODE"));// 住院科室代码
			json.put("ncHospDepartmentName", map.get("NCHOSPDEPARTMENTNAME"));// 住院科室名称
			json.put("ncHospDepartmentCodeL", map.get("NCHOSPDEPARTMENTCODEL"));// 住院科室代码（本院）
			json.put("ncHospDepartmentNameL", map.get("NCHOSPDEPARTMENTNAMEL"));// 住院科室名称（本院）
			json.put("ncTestInstitutionCode", map.get("NCTESTINSTITUTIONCODE"));// 检验机构代码
			json.put("ncTestInstitutionName", map.get("NCTESTINSTITUTIONNAME"));// 检验机构名称
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postLaboratoryTest(String startDate, String endDate) {
		// 将从配置文件中得到的日期反转，跟rowkey一样
		String start = new StringBuffer(startDate.replaceAll("-", "")).reverse().toString();
		String end = new StringBuffer(endDate.replaceAll("-", "")).reverse().toString();

		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("NCREPORTTIME", "STRING", MatchType.GE.getOperation(), startDate + " 00:00:00"));
		filters.add(new PropertyFilter("NCREPORTTIME", "STRING", MatchType.LE.getOperation(), endDate + " 23:59:59"));
		/**
		 * 在HDR_LABREPORT_ZLSB表中 rowkey 是按照入院时间 来开头的所以 查询 过滤 是按照入院时间查询的
		 */
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(laboratoryTest_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 10; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getLaboratoryTestJsonInfo(mapInfo);
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

	public static void postLaboratoryTestByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(laboratoryTest_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getLaboratoryTestJsonInfo(mapInfo);
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
