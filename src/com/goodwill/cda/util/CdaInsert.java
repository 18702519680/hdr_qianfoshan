package com.goodwill.cda.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.goodwill.cda.hlht.service.CdaService;
import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.core.utils.PropertiesUtils;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 	类描述：插入hbase
 * @author jibin
 * @Date 2017年11月2日
 * @modify
 *
 */
public class CdaInsert {
	protected static Logger logger = LoggerFactory.getLogger(CdaInsert.class);
	private static String CONFIG_FILE_NAME = "hlht.properties";
	@Autowired
	CdaService cdaService;

	/**
	 * @param patInfos 患者信息list
	 * @param diagList	诊断list
	 * @param DocumentTitle	文书类别
	 * @param AdmissionType	就诊类别（门诊住院)
	 * @param Document	生成CDAxml
	 * @param sourcePk	多个值拼接rowkey（放入sourcePk）
	 */
	public void InserToHbase(List<Map<String, String>> patInfos, List<Map<String, String>> diagList,
			String DocumentCode, String AdmissionType, String Document, String sourcePk) {
		List<Map<String, String>> listCda = new ArrayList<Map<String, String>>();
		Map<String, String> mapHbase = new HashMap<String, String>();
		String PatientID = null, VisitID = null, PatientName = null, InTime = null, OutTime = null, IdentityId = null, AdmissionDepart = null, AdmissionDoctor = null, ROWKEY = null, DiagnosisResult = null;
		String AdmissionType_Code = null;
		String AdmissionType_Name = null;
		//String type=null;
		if (patInfos.isEmpty()) {
			return;
		}
		//门诊
		if ("01".equals(AdmissionType)) {
			AdmissionType_Code = "01";
			AdmissionType_Name = "门诊";
			for (Map<String, String> map : patInfos) {
				PatientID = map.get("OUT_PATIENT_ID");
				VisitID = map.get("VISIT_ID");
				PatientName = map.get("PERSON_NAME");
				InTime = map.get("REGISTING_TIME");//挂号时间
				OutTime = map.get("VISIT_TIME");//就诊时间
				IdentityId = map.get("ID_CARD_NO");
				AdmissionDepart = map.get("VISIT_DEPT_NAME");
				AdmissionDoctor = map.get("VISIT_DOCTOR_NAME");
			}
			if (null == diagList || diagList.isEmpty()) {//如果诊断不存在  读取病案首页出院诊断	
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), VisitID));
				//filters.add(new PropertyFilter("DIAGNOSIS_NUM", "STRING", MatchType.EQ.getOperation(), "1"));
				//filters.add(new PropertyFilter("DIAGNOSIS_TYPE_CODE", "STRING", MatchType.EQ.getOperation(), "3"));
				List<Map<String, String>> listDiag = Xmlutil.formatList(HbaseCURDUtils.findByCondition(
						"HDR_OUT_VISIT_DIAG", HbaseCURDUtils.getRowkeyPrefix(PatientID), filters));
				if (!listDiag.isEmpty()) {
					for (Map<String, String> map : listDiag) {
						DiagnosisResult = StringUtils.isBlank(map.get("DIAGNOSIS_NAME")) ? map.get("DIAGNOSIS_DESC")
								: map.get("DIAGNOSIS_NAME");
						break;
					}
				}
			} else {
				for (Map<String, String> map : diagList) {
					DiagnosisResult = StringUtils.isBlank(map.get("DIAGNOSIS_NAME")) ? map.get("DIAGNOSIS_DESC") : map
							.get("DIAGNOSIS_NAME");
					break;
				}
			}
		} else { //住院
			AdmissionType_Code = "02";
			AdmissionType_Name = "住院";
			for (Map<String, String> map : patInfos) {
				PatientID = map.get("IN_PATIENT_ID");
				VisitID = map.get("VISIT_ID");
				PatientName = map.get("PERSON_NAME");
				InTime = map.get("ADMISSION_TIME");
				OutTime = map.get("DISCHARGE_TIME");
				IdentityId = map.get("ID_CARD_NO");
				AdmissionDepart = map.get("DEPT_ADMISSION_TO_NAME");
				AdmissionDoctor = map.get("ATTENDING_DOCTOR_NAME");

			}
			if (null == diagList || diagList.isEmpty()) { //如果诊断不存在  读取病案首页出院诊断	
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), VisitID));
				filters.add(new PropertyFilter("DIAGNOSIS_NUM", "STRING", MatchType.EQ.getOperation(), "1"));
				filters.add(new PropertyFilter("DIAGNOSIS_TYPE_CODE", "STRING", MatchType.EQ.getOperation(), "3"));
				List<Map<String, String>> listDiag = Xmlutil.formatList(HbaseCURDUtils.findByCondition(
						"HDR_INP_SUMMARY_DIAG", HbaseCURDUtils.getRowkeyPrefix(PatientID), filters));
				if (!listDiag.isEmpty()) {
					for (Map<String, String> map : listDiag) {
						DiagnosisResult = StringUtils.isBlank(map.get("DIAGNOSIS_NAME")) ? map.get("DIAGNOSIS_DESC")
								: map.get("DIAGNOSIS_NAME");
					}
				}
			} else {
				for (Map<String, String> map : diagList) {
					if ("3".equals(map.get("DIAGNOSIS_TYPE_CODE"))) {
						DiagnosisResult = StringUtils.isBlank(map.get("DIAGNOSIS_NAME")) ? map.get("DIAGNOSIS_DESC")
								: map.get("DIAGNOSIS_NAME");
						break;
					}
				}
			}
		}
		//医院OID
		String flag = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "org_oid");
		//区分rowkey的关键
		ROWKEY = HbaseCURDUtils.getRowkeyPrefix(PatientID) + "|" + VisitID + "|" + DocumentCode + "|" + sourcePk + "|"
				+ flag;
		String HealthCardId = IdentityId;
		String DocumentUniqueId = CommonUtils.getUUID();
		String RepositoryUniqueId = DocumentUniqueId;
		String ServerOrganization = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "hospital_name");
		String MimeType = "text/xml";
		String EpisodeID = PatientID;
		String CreateTime = CommonUtils.SystemTiem();
		String DocUrl = "";
		mapHbase.put("PatientID", PatientID);
		mapHbase.put("VisitID", VisitID);
		mapHbase.put("PatientName", PatientName);
		mapHbase.put("InTime", InTime);
		mapHbase.put("OutTime", OutTime);
		mapHbase.put("IdentityId", IdentityId);
		mapHbase.put("AdmissionDepart", AdmissionDepart);
		mapHbase.put("AdmissionDoctor", AdmissionDoctor);
		mapHbase.put("DiagnosisResult", DiagnosisResult);
		mapHbase.put("ROWKEY", ROWKEY);
		//mapHbase.put("rw", ROWKEY);
		mapHbase.put("AdmissionType_Code", AdmissionType_Code);
		mapHbase.put("AdmissionType_Name", AdmissionType_Name);
		mapHbase.put("DocumentCode", DocumentCode);
		mapHbase.put("DocumentTitle", mapping(DocumentCode));
		//mapHbase.put("Document", Document);
		mapHbase.put("HealthCardId", HealthCardId);
		mapHbase.put("DocumentUniqueIdUUID", DocumentUniqueId);//
		mapHbase.put("RepositoryUniqueIdUUID", RepositoryUniqueId);//
		mapHbase.put("DocumentUniqueId", ROWKEY);//给rowkey
		mapHbase.put("RepositoryUniqueId", ROWKEY);//给rowkey
		mapHbase.put("ServerOrganization", ServerOrganization);
		mapHbase.put("MimeType", MimeType);
		mapHbase.put("EpisodeID", EpisodeID);
		//创建时间先判断是否有出院时间，如果有就取出院时间，没有的话取当前时间
		if (StringUtils.isNotBlank(OutTime)) {
			CreateTime = formatDate(OutTime);
		}
		mapHbase.put("CreateTime", CreateTime);
		mapHbase.put("DocUrl", DocUrl);
		mapHbase.put("sourcePk", sourcePk);
		listCda.add(mapHbase);
		HbaseCURDUtils.batchPut("HDR_CDA_INDEX", listCda);//插入索引表
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> mapCda = new HashMap<String, String>();
		mapCda.put("ROWKEY", ROWKEY); // rowkey
		mapCda.put("Document", Document);
		mapCda.put("VISIT_ID", VisitID);
		list.add(mapCda);
		HbaseCURDUtils.batchPut("HDR_CDA", list);//xml插入hbase
		logger.info("患者号==" + PatientID + "==就诊次数==" + VisitID + "==CDA==" + DocumentCode + "==插入hbase成功");
		//插入mysql

		//cdaService.deleteCdaRowkey(ROWKEY);
		//	String formatDate = formatDate(InTime);
		//		cdaService.inserCdaIndex(PatientName, HealthCardId, AdmissionDepart, formatDate, DocumentCode,
		//				mapping(DocumentCode), ROWKEY, AdmissionType_Name);
	}

	public static String mapping(String DocumentCode) {
		String str = "未知";
		if (StringUtils.isBlank(DocumentCode)) {
			return str;
		}
		switch (DocumentCode) {
		case "C0001":
			str = "病历概要";
			break;
		case "C0002":
			str = "门急诊病历";
			break;
		case "C0003":
			str = "急诊留观病历";
			break;
		case "C0004":
			str = "西药处方";
			break;
		case "C0005":
			str = "中药处方";
			break;
		case "C0006":
			str = "检查记录";
			break;
		case "C0007":
			str = "检验记录";
			break;
		case "C0008":
			str = "治疗记录";
			break;
		case "C0009":
			str = "一般手术记录";
			break;
		case "C0010":
			str = "麻醉术前访视记录";
			break;
		case "C0011":
			str = "麻醉记录";
			break;
		case "C0012":
			str = "麻醉术后访视记录";
			break;
		case "C0013":
			str = "输血记录";
			break;
		case "C0014":
			str = "待产记录";
			break;
		case "C0015":
			str = "阴道分娩记录";
			break;
		case "C0016":
			str = "剖宫产记录";
			break;
		case "C0017":
			str = "一般护理记录";
			break;
		case "C0018":
			str = "病危病重护理记录";
			break;
		case "C0019":
			str = "手术护理记录";
			break;
		case "C0020":
			str = "生命体征测量记录";
			break;
		case "C0021":
			str = "出入量记录";
			break;
		case "C0022":
			str = "高值耗材使用记录";
			break;
		case "C0023":
			str = "入院评估";
			break;
		case "C0024":
			str = "护理计划";
			break;
		case "C0025":
			str = "出院评估与指导";
			break;
		case "C0026":
			str = "手术知情同意书";
			break;
		case "C0027":
			str = "麻醉知情同意书";
			break;
		case "C0028":
			str = "输血治疗同意书";
			break;
		case "C0029":
			str = "特殊检查及特殊治疗同意书";
			break;
		case "C0030":
			str = "病危重通知书";
			break;
		case "C0031":
			str = "其他知情告知同意书";
			break;
		case "C0032":
			str = "住院病案首页";
			break;
		case "C0034":
			str = "入院记录";
			break;
		case "C0035":
			str = "24h内入出院记录";
			break;
		case "C0036":
			str = "24h内入院死亡记录";
			break;
		case "C0037":
			str = "首次病程记录";
			break;
		case "C0038":
			str = "日常病程记录";
			break;
		case "C0039":
			str = "上级医师查房记录";
			break;
		case "C0040":
			str = "疑难病例讨论记录";
			break;
		case "C0041":
			str = "交接班记录";
			break;
		case "C0042":
			str = "转科记录";
			break;
		case "C0043":
			str = "阶段小结";
			break;
		case "C0044":
			str = "抢救记录";
			break;
		case "C0045":
			str = "会诊记录";
			break;
		case "C0046":
			str = "术前小结";
			break;
		case "C0047":
			str = "术前讨论";
			break;
		case "C0048":
			str = "术后首次病程记录";
			break;
		case "C0049":
			str = "出院记录";
			break;
		case "C0050":
			str = "死亡记录";
			break;
		case "C0051":
			str = "死亡病例讨论记录";
			break;
		case "C0052":
			str = "住院医嘱";
			break;
		case "C0053":
			str = "出院小结";
			break;
		}
		return str;
	}

	public String formatDate(String time) {
		String intime = CommonUtils.DateFormats(time);
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (intime.length() == 19) {
				return intime;
			} else {
				if (StringUtils.isBlank(time)) {
					intime = "2018-01-01 00:00:00";
				} else {
					intime = sdf.format(sdfs.parse(CommonUtils.DateFormats(time)));
				}
			}
		} catch (ParseException e) {
			intime = time;
			e.printStackTrace();
		}
		return intime;
	}
}
