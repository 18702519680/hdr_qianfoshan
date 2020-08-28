package com.goodwill.cda.hlht.cda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.goodwill.cda.hlht.cda.common.GetCommonData;
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
 * @Description 类描述：患者基本信息
 * @author 吴黎兵
 * @Date 2020年6月10日 方法名：patientBasicInfo 版本号：1.0.0
 * @modify 修改记录：
 */
public class REPORT01 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT01.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "patientBasicInfo");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "patientBasicInfo_version");
	private static String patientbasicinfo_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"patientBasicInfo_tablename");

	public static void main(String[] args) throws Exception {
		postAllPatientBasicInfo("2019-10-19", "2019-10-21");
	}

	public static JSONArray getPatientBasicJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncName", map.get("NCNAME"));// 姓名
			// json.put("ncGenderCode",DictUtils.getFinalDict("RC001",map.get("NCGENDERCODE")));//性别代码
			json.put("ncGenderCode", GetCommonData.GetCodeFromMysql("",map.get("NCGENDERCODE"),"RC001"));// 性别代码
			json.put("ncBirthDate", map.get("NCBIRTHDATE"));// 出生日期
			json.put("ncOccupationTypeCode", map.get("NCOCCUPATIONTYPECODE"));// 职业类别代码
			json.put("ncNationalityCode", GetCommonData.GetCodeFromMysql("",map.get("NCNATIONALITYCODE"),"RC085"));// 国籍代码
			json.put("ncNationCode", GetCommonData.GetCodeFromMysql("",map.get("NCNATIONCODE"),"RC035"));// 民族代码
			json.put("ncEducationCode", map.get("NCEDUCATIONCODE"));// 文化程度代码
			json.put("ncEducation", map.get("NCEDUCATION"));// 文化程度
			json.put("ncCredentialTypeCode", map.get("NCCREDENTIALTYPECODE"));// 证件类型代码
			json.put("ncCredentialTypeCodeL", map.get("NCCREDENTIALTYPECODEL"));// 证件类型代码（本院）
			json.put("ncCredentialTypeNameL", map.get("NCCREDENTIALTYPENAMEL"));// 证件类型名称（本院）
			json.put("ncCredentialNo", map.get("NCCREDENTIALNO"));// 证件号码
			json.put("ncHealthCardNo", map.get("NCHEALTHCARDNO"));// 健康卡号
			json.put("ncInsuranceCardNo", map.get("NCINSURANCECARDNO"));// 医保卡号
			json.put("ncDomicileAddressProvinceCode", map.get("NCDOMICILEADDRESSPROVINCECODE"));// 户籍地址-省（自治区、直辖市）
			json.put("ncDomicileAddressCityCode", map.get("NCDOMICILEADDRESSCITYCODE"));// 户籍地址-市（地区、州）
			json.put("ncDomicileAddressCountyCode", map.get("NCDOMICILEADDRESSCOUNTYCODE"));// 户籍地址-县（区）
			json.put("ncDomicileAddressDetail", map.get("NCDOMICILEADDRESSDETAIL"));// 户籍地址-详细
			json.put("ncDomicileAddressAll", map.get("NCDOMICILEADDRESSALL"));// 户籍地址（完整）
			json.put("ncCurrentAddressProvinceCode", map.get("NCCURRENTADDRESSPROVINCECODE"));// 现地址-省（自治区、直辖市）
			json.put("ncCurrentAddressCityCode", map.get("NCCURRENTADDRESSCITYCODE"));// 现地址-市（地区、州）
			json.put("ncCurrentAddressCountyCode", map.get("NCCURRENTADDRESSCOUNTYCODE"));// 现地址-县（区）
			json.put("ncCurrentAddressDetail", map.get("NCCURRENTADDRESSDETAIL"));// 地址-详细
			json.put("ncCurrentAddressAll", map.get("NCCURRENTADDRESSALL"));// 现地址（完整）
			json.put("ncContactName", map.get("NCCONTACTNAME"));// 联系人姓名
			json.put("ncContactRelationshipCode", map.get("NCCONTACTRELATIONSHIPCODE"));// 联系人与患者关系代码
			json.put("ncContactRelationship", map.get("NCCONTACTRELATIONSHIP"));// 联系人与患者关系
			json.put("ncContactIdCard", map.get("NCCONTACTIDCARD"));// 联系人身份证号
			json.put("ncContactPhone", map.get("NCCONTACTPHONE"));// 联系人电话
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postAllPatientBasicInfo(String startDate, String endDate) {
		// 将从配置文件中得到的日期反转，跟rowkey一样
		String start = new StringBuffer(startDate.replaceAll("-", "")).toString();
		String end = new StringBuffer(endDate.replaceAll("-", "")).toString();

		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("CYSJ", "STRING", MatchType.GE.getOperation(), startDate.replaceAll("-", "") + "000000"));
		filters.add(new PropertyFilter("CYSJ", "STRING", MatchType.LE.getOperation(), endDate.replaceAll("-", "") + "235959"));
		/**
		 * 在HDR_PATIENT_ZLSB表中 rowkey 是按照出院时间 反转来开头的所以 查询 过滤 是按照入院时间查询的
		 */
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(patientbasicinfo_tablename, start, end, filters));
		
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			// TODO:这里先取10条数据，到时候记得改成listSumm.size()
			try {
				for (int i = 0; i < 2; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getPatientBasicJsonInfo(mapInfo);
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

	public static void postAllPatientBasicInfoByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		String[] columns = null;
		listGet.add(rowkey);
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(patientbasicinfo_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getPatientBasicJsonInfo(mapInfo);
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
