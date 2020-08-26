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
 * @Description 类描述：住院费用（总计） 上传每次归档后，当次住院的总费用，各项费用的总计金额。
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：hospitalizationCost 版本号：1.0.0
 * @modify 修改记录：
 * 
 */
public class REPORT20 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT20.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hospitalizationCost");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hospitalizationCost_version");
	private static String hospitalizationcost_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,
			"hospitalizationCost_tablename");

	public static void main(String[] args) throws Exception {
	}

	public static JSONArray getHospitalizationCostJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncTotalPayment", map.get("NCTOTALPAYMENT"));// 总费用
			json.put("ncPersonalPayment", map.get("NCPERSONALPAYMENT"));// 自付金额
			json.put("ncGeneralMedicalServices", map.get("NCGENERALMEDICALSERVICES"));// 一般医疗服务费
			json.put("ncGeneralTreatmentFee", map.get("NCGENERALTREATMENTFEE"));// 一般治疗操作费
			json.put("ncNursingCareFee", map.get("NCNURSINGCAREFEE"));// 护理费
			json.put("ncOtherServices", map.get("NCOTHERSERVICES"));// 综合医疗服务类其他费用
			json.put("ncPathologicalDiagnosisFee", map.get("NCPATHOLOGICALDIAGNOSISFEE"));// 病理诊断费
			json.put("ncLaboratoryDiagnosisFee", map.get("NCLABORATORYDIAGNOSISFEE"));// 实验室诊断费
			json.put("ncImagingDiagnosisFee", map.get("NCIMAGINGDIAGNOSISFEE"));// 影像学诊断费
			json.put("ncClinicalDiagnosticFee", map.get("NCCLINICALDIAGNOSTICFEE"));// 临床诊断项目费
			json.put("ncNonsurgicalTreatmentFee", map.get("NCNONSURGICALTREATMENTFEE"));// 非手术治疗项目费
			json.put("ncClinicalPhysicalTherapyFee", map.get("NCCLINICALPHYSICALTHERAPYFEE"));// 临床物理治疗费
			json.put("ncSurgeryTreatmentFee", map.get("NCSURGERYTREATMENTFEE"));// 手术治疗费
			json.put("ncAnesthesiaFee", map.get("NCANESTHESIAFEE"));// 麻醉费
			json.put("ncSurgeryFee", map.get("NCSURGERYFEE"));// 手术费
			json.put("ncRehabilitationFee", map.get("NCREHABILITATIONFEE"));// 康复费
			json.put("ncTcmTreatmentFee", map.get("NCTCMTREATMENTFEE"));// 中医治疗费
			json.put("ncWesternMedicineFee", map.get("NCWESTERNMEDICINEFEE"));// 西药费
			json.put("ncAntibacterialDrugFee", map.get("NCANTIBACTERIALDRUGFEE"));// 抗菌药物费用
			json.put("ncChinesePatentDrugFee", map.get("NCCHINESEPATENTDRUGFEE"));// 中成药费
			json.put("ncChineseHerbFee", map.get("NCCHINESEHERBFEE"));// 中草药费
			json.put("ncBloodFee", map.get("NCBLOODFEE"));// 血费
			json.put("ncAlbuminFee", map.get("NCALBUMINFEE"));// 白蛋白类制品费
			json.put("ncGlobulinFee", map.get("NCGLOBULINFEE"));// 球蛋白类制品费
			json.put("ncBloodCoagulationFactorFee", map.get("NCBLOODCOAGULATIONFACTORFEE"));// 凝血因子类制品费
			json.put("ncCytokineFee", map.get("NCCYTOKINEFEE"));// 细胞因子类制品费
			json.put("ncExamDisposableSupplyFee", map.get("NCEXAMDISPOSABLESUPPLYFEE"));// 检查用一次性医用材料费
			json.put("ncTreatDisposableSupplyFee", map.get("NCTREATDISPOSABLESUPPLYFEE"));// 治疗用一次性医用材料费
			json.put("ncSurgeryDisposableSupplyFee", map.get("NCSURGERYDISPOSABLESUPPLYFEE"));// 手术用一次性医用材料费
			json.put("ncOtherFee", map.get("NCOTHERFEE"));// 其他费
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postHospitalizationCost(String startDate, String endDate) {
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
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(hospitalizationcost_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 10; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getHospitalizationCostJsonInfo(mapInfo);
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

	public static void postHospitalizationCostByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil
				.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(hospitalizationcost_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getHospitalizationCostJsonInfo(mapInfo);
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
