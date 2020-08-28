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
 * @Description 类描述：病案首页基本信息
 * @author 吴黎兵
 * @Date 2020年6月11日 方法名：medicalRecordFirstPage 版本号：1.0.0
 * @modify 修改记录：
 */
public class REPORT02 {
	protected static Logger logger = LoggerFactory.getLogger(REPORT02.class);
	private static String CONFIG_FILE_NAME = "report2.properties";
	private static String function_name = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "medicalRecordFirstPage");
	private static String version = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"medicalRecordFirstPage_version");
	private static String medicalrecordfirstpage_tablename = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME,"medicalRecordFirstPage_tablename");

	public static void main(String[] args) throws Exception {
		postMedicalRecordFirstPage("2013-01-05", "2013-01-17");
	}

	public static JSONArray getMedicalRecordFirstPageJsonInfo(Map<String, String> map) {
		JSONObject json = new JSONObject();
		try {
			json.put("rowKey", map.get("ROWKEY"));// rowKey
			json.put("ncMedicalRecordNo", map.get("NCMEDICALRECORDNO"));// 病案号
			json.put("ncDischargeTime", map.get("NCDISCHARGETIME"));// 出院时间
			json.put("ncHealthCardNo", map.get("NCHEALTHCARDNO"));// 健康卡号
			json.put("ncHospitalizationTimes", map.get("NCHOSPITALIZATIONTIMES"));// 住院次数
			json.put("ncAdmissionTime", map.get("NCADMISSIONTIME"));// 入院时间
			json.put("ncHospitalizationNo", map.get("NCHOSPITALIZATIONNO"));// 住院号
			json.put("ncName", map.get("NCNAME"));// 姓名
			json.put("ncGenderCode", map.get("NCGENDERCODE"));// 性别代码
			json.put("ncBirthDate", map.get("NCBIRTHDATE"));// 出生日期
			json.put("ncAgeYear", map.get("NCAGEYEAR"));// 年龄（年）
			json.put("ncAgeMonth", map.get("NCAGEMONTH"));// 年龄（月）
			json.put("ncAgeDay", map.get("NCAGEDAY"));// 年龄（天）
			json.put("ncNationalityCode", map.get("NCNATIONALITYCODE"));// 国籍代码
			json.put("ncNeonatalBirthWeight", map.get("NCNEONATALBIRTHWEIGHT"));// 新生儿出生体重(g)
			json.put("ncNeonatalBirthWeight2", map.get("NCNEONATALBIRTHWEIGHT2"));// 新生儿出生体重(g)2
			json.put("ncNeonatalBirthWeight3", map.get("NCNEONATALBIRTHWEIGHT3"));// 新生儿出生体重(g)3
			json.put("ncNeonatalBirthWeight4", map.get("NCNEONATALBIRTHWEIGHT4"));// 新生儿出生体重(g)4
			json.put("ncNeonatalBirthWeight5", map.get("NCNEONATALBIRTHWEIGHT5"));// 新生儿出生体重(g)5
			json.put("ncNeonatalAdmittedWeight", map.get("NCNEONATALADMITTEDWEIGHT"));// 新生儿入院体重(g)
			json.put("ncBirthPlaceProvinceCode", map.get("NCBIRTHPLACEPROVINCECODE"));// 出生地-省（自治区、直辖市）
			json.put("ncBirthPlaceCityCode", map.get("NCBIRTHPLACECITYCODE"));// 出生地-市（地区、州）
			json.put("ncBirthPlaceCountyCode", map.get("NCBIRTHPLACECOUNTYCODE"));// 出生地-县（区）
			json.put("ncBirthPlaceDetail", map.get("NCBIRTHPLACEDETAIL"));// 出生地-详细
			json.put("ncBirthPlaceAll", map.get("NCBIRTHPLACEALL"));// 出生地（完整）
			json.put("ncAncestralHomeProvinceCode", map.get("NCANCESTRALHOMEPROVINCECODE"));// 籍贯-省（自治区、直辖市）
			json.put("ncAncestralHomeCityCode", map.get("NCANCESTRALHOMECITYCODE"));// 籍贯-市（地区、州）
			json.put("ncAncestralHomeCountyCode", map.get("NCANCESTRALHOMECOUNTYCODE"));// 籍贯-县（区）
			json.put("ncAncestralHomeAll", map.get("NCANCESTRALHOMEALL"));// 籍贯（完整）
			json.put("ncNationCode", GetCommonData.GetCodeFromMysql(map.get("NCNATIONCODE"),"","RC035"));// 民族代码
			json.put("ncIdCardNo", map.get("NCIDCARDNO"));// 身份证号
			json.put("ncOccupationTypeCode", map.get("NCOCCUPATIONTYPECODE"));// 职业类别代码
			json.put("ncMarriageStatusCode", map.get("NCMARRIAGESTATUSCODE"));// 婚姻状况代码
			json.put("ncCurrentAddressProvinceCode", map.get("NCCURRENTADDRESSPROVINCECODE"));// 现地址-省（自治区、直辖市）
			json.put("ncCurrentAddressCityCode", map.get("NCCURRENTADDRESSCITYCODE"));// 现地址-市（地区、州）
			json.put("ncCurrentAddressCountyCode", map.get("NCCURRENTADDRESSCOUNTYCODE"));// 现地址-县（区）
			json.put("ncCurrentAddressDetail", map.get("NCCURRENTADDRESSDETAIL"));// 现地址-详细
			json.put("ncCurrentAddressAll", map.get("NCCURRENTADDRESSALL"));// 现地址（完整）
			json.put("ncCurrentAddressPhone", map.get("NCCURRENTADDRESSPHONE"));// 现地址电话
			json.put("ncDomicileAddressProvinceCode", map.get("NCDOMICILEADDRESSPROVINCECODE"));// 户口地址-省（自治区、直辖市）
			json.put("ncDomicileAddressCityCode", map.get("NCDOMICILEADDRESSCITYCODE"));// 户口地址-市（地区、州）
			json.put("ncDomicileAddressCountyCode", map.get("NCDOMICILEADDRESSCOUNTYCODE"));// 户口地址-县（区）
			json.put("ncDomicileAddressDetail", map.get("NCDOMICILEADDRESSDETAIL"));// 户口地址-详细
			json.put("ncDomicileAddressAll", map.get("NCDOMICILEADDRESSALL"));// 户口地址（完整）
			json.put("ncSchoolName", map.get("NCSCHOOLNAME"));// 工作单位或学校名称
			json.put("ncSchoolAddressProvinceCode", map.get("NCSCHOOLADDRESSPROVINCECODE"));// 学校地址-省（自治区、直辖市）
			json.put("ncSchoolAddressCityCode", map.get("NCSCHOOLADDRESSCITYCODE"));// 学校地址-市（地区、州）
			json.put("ncSchoolAddressCountyCode", map.get("NCSCHOOLADDRESSCOUNTYCODE"));// 学校地址-县（区）
			json.put("ncSchoolAddressDetail", map.get("NCSCHOOLADDRESSDETAIL"));// 学校地址-详细
			json.put("ncSchoolAddressAll", map.get("NCSCHOOLADDRESSALL"));// 学校地址（完整）
			json.put("ncSchoolPhone", map.get("NCSCHOOLPHONE"));// 学校电话
			json.put("ncFatherName", map.get("NCFATHERNAME"));// 父亲姓名
			json.put("ncFatherIdCard", map.get("NCFATHERIDCARD"));// 父亲身份证号
			json.put("ncFatherProvinceCode", map.get("NCFATHERPROVINCECODE"));// 父亲地址-省（自治区、直辖市）
			json.put("ncFatherCityCode", map.get("NCFATHERCITYCODE"));// 父亲地址-市（地区、州）
			json.put("ncFatherCountyCode", map.get("NCFATHERCOUNTYCODE"));// 父亲地址-县（区）
			json.put("ncFatherDetail", map.get("NCFATHERDETAIL"));// 父亲地址-详细
			json.put("ncFatherAddressAll", map.get("NCFATHERADDRESSALL"));// 父亲地址（完整）
			json.put("ncFatherPhone", map.get("NCFATHERPHONE"));// 父亲电话
			json.put("ncMotherName", map.get("NCMOTHERNAME"));// 母亲姓名
			json.put("ncMotherIdCard", map.get("NCMOTHERIDCARD"));// 母亲身份证号
			json.put("ncMotherProvinceCode", map.get("NCMOTHERPROVINCECODE"));// 母亲地址-省（自治区、直辖市）
			json.put("ncMotherCityCode", map.get("NCMOTHERCITYCODE"));// 母亲地址-市（地区、州）
			json.put("ncMotherCountyCode", map.get("NCMOTHERCOUNTYCODE"));// 母亲地址-县（区）
			json.put("ncMotherDetail", map.get("NCMOTHERDETAIL"));// 母亲地址-详细
			json.put("ncMotherAddressAll", map.get("NCMOTHERADDRESSALL"));// 母亲地址（完整）
			json.put("ncMotherPhone", map.get("NCMOTHERPHONE"));// 母亲电话
			json.put("ncContactOneName", map.get("NCCONTACTONENAME"));// 联系人1姓名
			json.put("ncContactOneIdCard", map.get("NCCONTACTONEIDCARD"));// 联系人1身份证号
			json.put("ncContactOneRelationshipCode", map.get("NCCONTACTONERELATIONSHIPCODE"));// 联系人1与患者关系代码
			json.put("ncContactOneRelationship", map.get("NCCONTACTONERELATIONSHIP"));// 联系人1与患者关系
			json.put("ncContactOneProvinceCode", map.get("NCCONTACTONEPROVINCECODE"));// 联系人1地址-省（自治区、直辖市）
			json.put("ncContactOneCityCode", map.get("NCCONTACTONECITYCODE"));// 联系人1地址-市（地区、州）
			json.put("ncContactOneCountyCode", map.get("NCCONTACTONECOUNTYCODE"));// 联系人1地址-县（区）
			json.put("ncContactOneDetail", map.get("NCCONTACTONEDETAIL"));// 联系人1地址-详细
			json.put("ncContactOneAddressAll", map.get("NCCONTACTONEADDRESSALL"));// 联系人1地址（完整）
			json.put("ncContactOnePhone", map.get("NCCONTACTONEPHONE"));// 联系人1电话
			json.put("ncContactTwoName", map.get("NCCONTACTTWONAME"));// 联系人2姓名
			json.put("ncContactTwoIdCard", map.get("NCCONTACTTWOIDCARD"));// 联系人2身份证号
			json.put("ncContactTwoRelationshipCode", map.get("NCCONTACTTWORELATIONSHIPCODE"));// 联系人2与患者关系代码
			json.put("ncContactTwoRelationship", map.get("NCCONTACTTWORELATIONSHIP"));// 联系人2与患者关系
			json.put("ncContactTwoProvinceCode", map.get("NCCONTACTTWOPROVINCECODE"));// 联系人2地址-省（自治区、直辖市）
			json.put("ncContactTwoCityCode", map.get("NCCONTACTTWOCITYCODE"));// 联系人2地址-市（地区、州）
			json.put("ncContactTwoCountyCode", map.get("NCCONTACTTWOCOUNTYCODE"));// 联系人2地址-县（区）
			json.put("ncContactTwoDetail", map.get("NCCONTACTTWODETAIL"));// 联系人2地址-详细
			json.put("ncContactTwoAddressAll", map.get("NCCONTACTTWOADDRESSALL"));// 联系人2地址（完整）
			json.put("ncContactTwoPhone", map.get("NCCONTACTTWOPHONE"));// 联系人2电话
			json.put("ncAboBloodTypeCode", map.get("NCABOBLOODTYPECODE"));// ABO血型代码
			json.put("ncRhBloodTypeCode", map.get("NCRHBLOODTYPECODE"));// Rh血型代码
			json.put("ncIsDrugAllergic", map.get("NCISDRUGALLERGIC"));// 是否药物过敏
			json.put("ncAllergicDrugName", map.get("NCALLERGICDRUGNAME"));// 过敏药物名称
			json.put("ncMedicalPaymentTypeCode", map.get("NCMEDICALPAYMENTTYPECODE"));// 医疗付费方式代码
			json.put("ncCancel", map.get("NCCANCEL"));// 取消区分
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray a = new JSONArray();
		a.element(json.toString());
		return a;
	}

	public static void postMedicalRecordFirstPage(String startDate, String endDate) {
		// 将从配置文件中得到的日期反转，跟rowkey一样
		String start = new StringBuffer(startDate.replaceAll("-", "")).toString();
		String end = new StringBuffer(endDate.replaceAll("-", "")).toString();
		
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("NCDISCHARGETIME", "STRING", MatchType.GE.getOperation(), startDate.replaceAll("-", "") + "000000"));
		filters.add(new PropertyFilter("NCDISCHARGETIME", "STRING", MatchType.LE.getOperation(), endDate.replaceAll("-", "") + "235959"));
		/**
		 * 在HDR_PATIENT_ZLSB表中 rowkey 是按照入院时间 来开头的所以 查询 过滤 是按照入院时间查询的
		 */
		listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey(medicalrecordfirstpage_tablename, start, end, filters));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < 2; i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getMedicalRecordFirstPageJsonInfo(mapInfo);
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
	
	public static void postMedicalRecordFirstPageByrowkey(String rowkey) {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> listGet = new ArrayList<String>();
		listGet.add(rowkey);
		String[] columns = null;
		filters.add(new PropertyFilter("rowKey", "STRING", MatchType.EQ.getOperation(), rowkey));
		listSumm = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyList(medicalrecordfirstpage_tablename, listGet, columns));
		JSONArray json = new JSONArray();
		JSONArray jsonall = new JSONArray();
		JSONObject jsonallarr = new JSONObject();
		if (listSumm.size() > 0) {
			try {
				for (int i = 0; i < listSumm.size(); i++) {
					Map<String, String> mapInfo = listSumm.get(i);
					json = getMedicalRecordFirstPageJsonInfo(mapInfo);
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
