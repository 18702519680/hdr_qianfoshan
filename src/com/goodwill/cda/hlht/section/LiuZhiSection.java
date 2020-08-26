package com.goodwill.cda.hlht.section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.goodwill.cda.hlht.enums.ChargeEnum;
import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictCode;
import com.goodwill.cda.util.DictUtils;
import com.goodwill.cda.util.DictUtisTools;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.core.utils.PropertiesUtils;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * 用于生成患者基本信息
 * 
 * @author liuzhi
 */
public class LiuZhiSection {

	private static final String propertiesFile = "hlht.properties";

	public static void main(String[] args) {
		// 查询 数据
		List<Map<String, String>> base = HbaseCURDUtils.findByRowkeyPrefix("HDR_OUT_VISIT",
				HbaseCURDUtils.getRowkeyPrefix("000622000000") + "|" + "1");
		Map<String, String> baseInfo = base.size() > 0 ? base.get(0) : new HashMap<String, String>();
		String info = LiuZhiSection.createPatientBaseInfo(baseInfo);
		System.out.println(info);
	}

	/**
	 * 生成患者基本信息 门（急）诊号标识 在map中传递 INP_NO 住院号标识 在map中传递 OUTP_NO 检查报告单号标识 在map中传递
	 * REPORT_NO 电子申请单编号 在map中传递 OUTP_NO 标本编号标识 在map中传递 SAMPLE_NO
	 * 
	 * @author liuzhi
	 * @param baseInfo
	 *            住院或者门诊号
	 * @return
	 */
	public static String createPatientBaseInfo(Map<String, String> baseInfo) {

		StringBuffer bf = new StringBuffer("<recordTarget typeCode=\"RCT\" contextControlCode=\"OP\">");
		bf.append("<patientRole classCode=\"PAT\">");
		// 门（急）诊号标识
		if (baseInfo.containsKey("OUTP_NO")) {
			bf.append("<id root=\"2.16.156.10011.1.11\" extension=\""
					+ (baseInfo.containsKey("OUTP_NO") ? baseInfo.get("OUTP_NO") : "000000") + "\"/>");
		}
		// 住院号标识
		if (baseInfo.containsKey("INP_NO")) {
			bf.append("<id root=\"2.16.156.10011.1.12\" extension=\""
					+ (baseInfo.containsKey("INP_NO") ? baseInfo.get("INP_NO") : "000000") + "\"/>");
		}
		// 检查报告单号标识
		if (baseInfo.containsKey("REPORT_NO")) {
			String rep_no = baseInfo.get("REPORT_NO");
			if (StringUtils.isBlank(rep_no)) {
				rep_no = "-";
			} else {
				rep_no = rep_no.length() > 20 ? rep_no.substring(0, 20) : rep_no;
			}
			bf.append("<id root=\"2.16.156.10011.1.32\" extension=\"" + baseInfo.get("REPORT_NO") + "\"/>");
		}
		// 知情同意书编码 TODO 知情同意书编号的 字段名称
		if (baseInfo.containsKey("INFORMED_NO")) {
			bf.append("<!-- 知情同意书编号 -->");
			String inf_no = baseInfo.get("INFORMED_NO");
			if (StringUtils.isBlank(inf_no)) {
				inf_no = "-";
			} else {
				inf_no = inf_no.length() > 20 ? inf_no.substring(0, 20) : inf_no;
			}
			bf.append("<id root=\"2.16.156.10011.1.34\" extension=\"" + inf_no + "\"/>");
		}
		// 电子申请单编号
		if (baseInfo.containsKey("APPLY_NO")) {
			String apply_no = baseInfo.get("APPLY_NO");
			if (StringUtils.isBlank(apply_no)) {
				apply_no = "-";
			} else {
				apply_no = apply_no.length() > 20 ? apply_no.substring(0, 20) : apply_no;
			}
			bf.append("<id root=\"2.16.156.10011.1.24\" extension=\"" + apply_no + "\"/>");
		}
		// 标本编号标识
		if (baseInfo.containsKey("SAMPLE_NO")) {
			String report_no = baseInfo.get("REPORT_NO");
			if (StringUtils.isBlank(report_no)) {
				report_no = "-";
			} else {
				report_no = report_no.length() > 20 ? report_no.substring(0, 20) : report_no;
			}
			bf.append("<id root=\"2.16.156.10011.1.14\" extension=\"" + report_no + "\"/>");
		}
		// 处方编号 父医嘱唯一标识， 门诊对应于处方号，一个处方上可以有多个药品医嘱
		if (baseInfo.containsKey("PARENT_ORDER_NO")) {
			bf.append("<id root=\"2.16.156.10011.1.20\" extension=\""
					+ (StringUtils.isBlank(baseInfo.get("PARENT_ORDER_NO")) ? "000000" : baseInfo
							.get("PARENT_ORDER_NO")) + "\"/>");
		}
		bf.append("<patient classCode=\"PSN\" determinerCode=\"INSTANCE\">");
		// 下面的是身份证号 如果没有 可以不添加
		if (baseInfo.containsKey("ID_CARD_NO")) {
			bf.append("<id root=\"2.16.156.10011.1.3\" extension=\"" + baseInfo.get("ID_CARD_NO") + "\"/>");
		} else {
			bf.append("<id root=\"2.16.156.10011.1.3\" extension=\"-\"/>");
		}
		// 患者姓名必须有
		bf.append("<name>"
				+ (baseInfo.containsKey("PERSON_NAME") ? baseInfo.get("PERSON_NAME").toString().trim() : "不详")
				+ "</name>");
		// administrativeGenderCode 节点是必须的
		Map<String, String> sexMap = DictUtisTools.getSexMap(baseInfo.get("SEX_CODE"), baseInfo.get("SEX_NAME"));
		bf.append("<administrativeGenderCode code=\"" + sexMap.get("code") + "\" displayName=\"" + sexMap.get("name")
				+ "\" codeSystem=\"2.16.156.10011.2.3.3.4\" codeSystemName=\"生理性别代码表(GB/T 2261.1)\"/>");
		// 年龄值
		String ageValue = baseInfo.get("AGE_VALUE");
		if (StringUtils.isBlank(ageValue)) {
			ageValue = baseInfo.get("AGE");
		}
		//年龄单位
		String ageUnit = baseInfo.get("AGE_UNIT");
		if (StringUtils.isBlank(ageUnit)) {
			ageUnit = baseInfo.get("AGE");
		}
		bf.append("<age unit=\"" + CommonUtils.getStrUnit(ageUnit) + "\"  value=\"" + CommonUtils.getStrValue(ageValue)
				+ "\"/>");
		// 下面 的节点 是必须的
		bf.append("</patient>");

		bf.append("<providerOrganization>");

		bf.append("<id root=\"2.16.156.10011.1.5\" extension=\""
				+ PropertiesUtils.getPropertyValue(propertiesFile, "hospital_id") + "\"/>");
		bf.append("<name>" + PropertiesUtils.getPropertyValue(propertiesFile, "hospital_name") + "</name>");
		bf.append("</providerOrganization>");
		bf.append("</patientRole>");
		bf.append("</recordTarget>");
		return bf.toString();
	}

	/**
	 * 用药章节
	 * 
	 * @param 门诊费用信息
	 * @param 中药和西药标识
	 *            调用枚举类 ChargeEnum.ZYCH.getCode() 或者 ChargeEnum.XYCH.getCode()
	 * @return
	 */
	public static String getMedition(List<Map<String, String>> infoMapList, String flag) {
		StringBuffer bf = new StringBuffer("<component>");
		bf.append("<section>");
		bf.append("<code code=\"10160-0\" displayName=\"HISTORY OF MEDICATION USE\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		bf.append("<text/>");

		/******************** 处方条目开始 ********************************/
		for (int i = 0; i < infoMapList.size(); i++) {
			Map<String, String> infoMap = infoMapList.get(i);
			bf.append("<entry>");
			bf.append("<substanceAdministration classCode=\"SBADM\" moodCode=\"EVN\">");
			bf.append("<text/>");
			// 用药途径
			// 映射 默认值 需要
			if (!StringUtils.isBlank(infoMap.get("PHARMACY_WAY_CODE"))
					&& !StringUtils.isBlank(infoMap.get("PHARMACY_WAY_NAME"))) {
				// TODO 药物使用途径
				String code = infoMap.get("PHARMACY_WAY_CODE");
				String name = infoMap.get("PHARMACY_WAY_NAME");
				Map<String, String> finalDict = DictUtils.getFinalDict(DictCode.CV0600102, code, name);
				if (!StringUtils.isBlank(finalDict.get("code")) && !StringUtils.isBlank(finalDict.get("name"))) {
					bf.append("<routeCode code=\"" + finalDict.get("code") + "\" displayName=\""
							+ finalDict.get("name").replace("<", "").replace(">", "")
							+ "\" codeSystem=\"2.16.156.10011.2.3.1.158\" codeSystemName=\"用药途径代码表\"/>");
				} else {
					// 添加默认
					bf.append("<routeCode code=\"9\" displayName=\"其他用药途径\" codeSystem=\"2.16.156.10011.2.3.1.158\" codeSystemName=\"用药途径代码表\"/>");
				}
			} else {
				bf.append("<routeCode code=\"9\" displayName=\"其他用药途径\" codeSystem=\"2.16.156.10011.2.3.1.158\" codeSystemName=\"用药途径代码表\"/>");
			}
			// 用药剂量
			if (infoMap.containsKey("DOSAGE_VALUE")) {
				// 单次用药剂量 如果没有 不加  DRUG_AMOUNT_UNIT
				if (!StringUtils.isBlank(infoMap.get("DOSAGE_VALUE"))
						&& !StringUtils.isBlank(infoMap.get("DOSAGE_UNIT"))) {
					try {
						String dosage_value = CommonUtils.format2(infoMap.get("DOSAGE_VALUE"));
						if (dosage_value.length() > 5) {
							String[] array = dosage_value.split("\\.");
							if (array[0].length() > 2) {
								array[0] = "99";
							}
							dosage_value = array[0] + "." + array[1];
							dosage_value = dosage_value.length() > 5 ? dosage_value.substring(0, 5) : dosage_value;
						}
						bf.append("<doseQuantity value=\"" + dosage_value + "\" unit=\"" + infoMap.get("DOSAGE_UNIT")
								+ "\"/>");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} else {
				bf.append("<doseQuantity value=\"0.00\" unit=\"g\"/>");
			}
			// 用药频率 TODO 查询用药频率字典 计斌对字典 如果为空
			if (infoMap.containsKey("FREQUENCY_NAME")) {
				String code = infoMap.get("FREQUENCY_CODE");
				String name = infoMap.get("FREQUENCY_NAME");
				Map<String, String> finalDict = DictUtils.getFinalDict(DictCode.drugcoding, code, name);
				if (!StringUtils.isBlank(finalDict.get("code")) && !StringUtils.isBlank(finalDict.get("name"))) {
					bf.append("<rateQuantity>");
					bf.append("<translation code=\"" + finalDict.get("code") + "\" displayName=\""
							+ finalDict.get("name") + "\"/>");
					bf.append("</rateQuantity>");
				} else {
					bf.append("<rateQuantity>");
					bf.append("<translation code=\"99\" displayName=\"其他 \"/>");
					bf.append("</rateQuantity>");
				}
			} else {
				bf.append("<rateQuantity>");
				bf.append("<translation code=\"99\" displayName=\"其他 \"/>");
				bf.append("</rateQuantity>");
			}
			// TODO 药物剂型 怎么取 暂时没有 可能数据规范中需要加上一列 再抽数据
			if (infoMap.containsKey("CHARGE_CODE")) {
				// 药物剂型 非必须 ******
				// 拿到CHARGE_CODE
				String charge_code = infoMap.get("CHARGE_CODE");
				if (!StringUtils.isBlank(charge_code)) {
					// 用charge_CODE 查询dosage ，到标准字典里查询
					String dosage = getDosage(charge_code);
					String name = infoMap.get("CHARGE_NAME");
					// TODO 调用字典
					Map<String, String> finalDict = DictUtils.getFinalDict(DictCode.CV0850002, dosage, name);
					if (!StringUtils.isBlank(finalDict.get("name")) && !StringUtils.isBlank(finalDict.get("code"))) {
						// TODO 拿到dosage 调用计斌的字典 返回标准的药品剂型 数据
						bf.append("<administrationUnitCode code=\""
								+ finalDict.get("code")
								+ "\" displayName=\""
								+ finalDict.get("name")
								+ "\" codeSystem=\"2.16.156.10011.2.3.1.211\" codeSystemName=\"药物剂型代码表\"></administrationUnitCode>");
					} else {
						// 加上默认
						bf.append("<administrationUnitCode code=\"99\" displayName=\"其他剂型(空心胶囊,绷带,纱布,胶布)\" codeSystem=\"2.16.156.10011.2.3.1.211\" codeSystemName=\"药物剂型代码表\"></administrationUnitCode>");
					}
				}
			} else {
				bf.append("<administrationUnitCode code=\"99\" displayName=\"其他剂型(空心胶囊,绷带,纱布,胶布)\" codeSystem=\"2.16.156.10011.2.3.1.211\" codeSystemName=\"药物剂型代码表\"></administrationUnitCode>");
			}
			/******************** 药品名称 ****************************/
			// 药品名称 药品 名称 对应 CHARGE_NAME
			bf.append("<consumable>");
			bf.append("<manufacturedProduct>");
			bf.append("<manufacturedLabeledDrug>");
			bf.append("<code/>");
			// 药品名称
			String drug_name = StringUtils.isBlank(infoMap.get("CHARGE_NAME")) ? "无" : infoMap.get("CHARGE_NAME");
			bf.append("<name>" + drug_name + "</name>");
			bf.append("</manufacturedLabeledDrug>");
			bf.append("</manufacturedProduct>");
			bf.append("</consumable>");
			/******************** 药品名称结束 ****************************/

			/*************** 药物规格 ********************************************/
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE08.50.043.00\" displayName=\"药物规格\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			// 药物规格
			if (infoMap.containsKey("SPECIFICATION")) {
				String spec = StringUtils.isBlank(infoMap.get("SPECIFICATION")) ? "无" : infoMap.get("SPECIFICATION");
				bf.append("<value xsi:type=\"ST\">" + spec + "</value>");
			}
			bf.append("</observation>");
			bf.append("</entryRelationship>");
			/*************** 药物规格 ********************************************/

			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE06.00.135.00\" displayName=\"药物使用总剂量\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			// 药物使用剂量 必须 的 取总剂量
			//TODO  如果取 TOTAL_DOSAGE_UNIT 取不到取CHARGE_AMOUNT_UNIT  如果费用是负数 则不加 改药品  入口也的加上
			try {
				String value = StringUtils.isBlank(infoMap.get("TOTAL_DOSAGE_VALUE")) ? "0.00" : CommonUtils
						.format2(infoMap.get("TOTAL_DOSAGE_VALUE").toString());
				String unit = StringUtils.isBlank(infoMap.get("TOTAL_DOSAGE_UNIT")) ? StringUtils.isBlank(infoMap
						.get("CHARGE_AMOUNT_UNIT")) ? "-" : infoMap.get("CHARGE_AMOUNT_UNIT") : infoMap
						.get("TOTAL_DOSAGE_UNIT");
				bf.append("<value xsi:type=\"PQ\" value=\"" + value + "\" unit=\"" + unit + "\"/>");
				bf.append("</observation>");
				bf.append("</entryRelationship>");
			} catch (Exception e) {
				e.printStackTrace();
			}

			/********************* 处方条目结束 *****************************************/
			bf.append("</substanceAdministration>");
			bf.append("</entry>");
		}

		/*********************** 处方有效天数（非必须） ******************************/
		// 中药 和西药 默认值 中药 7天 西药 3天
		String days = "3";
		if (ChargeEnum.ZYCF.getCode().equals(flag)) {
			days = "7";
		}
		bf.append("<entry>");
		bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		bf.append("<code code=\"DE06.00.294.00\" displayName=\"处方有效天数\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		bf.append("<value xsi:type=\"PQ\" value=\"" + days + "\" unit=\"天\"/>");
		bf.append("</observation>");
		bf.append("</entry>");
		/******************** 处方有效天数结束 **************************************/

		/************************ 处方药品组号（非必须） ************************************/
		bf.append("<entry>");
		bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		bf.append("<code code=\"DE08.50.056.00\" displayName=\"处方药品组号\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		bf.append("<value xsi:type=\"INT\" value=\"" + infoMapList.get(0).get("PARENT_ORDER_NO") + "\"/>");
		bf.append("</observation>");
		bf.append("</entry>");
		/************************ 处方药品组号结束 ************************************/

		/*********************** 中药饮片处方（非必须 ） *********************************************/
		if (ChargeEnum.ZYCF.getCode().equals(flag)) {
			bf.append("<entry>");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE08.50.049.00\" displayName=\"中药饮片处方\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"ST\">有</value>");
			// 中药饮片剂数
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN \">");
			bf.append("<code code=\"DE08.50.050.00\" displayName=\"中药饮片剂数\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"PQ\" value=\"" + infoMapList.size() + "\" unit=\"剂\"/>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");
			// 中药饮片煎煮法
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN \">");
			bf.append("<code code=\"DE08.50.047.00\" displayName=\"中药煎煮法\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"ST\">包煮</value>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");

			// 中药用药方法
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN \">");
			bf.append("<code code=\"DE06.00.136.00\" displayName=\"中药用药法\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"ST\">包煎</value>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");
			bf.append("</observation>");
			bf.append("</entry>");
			/*******************************************************************/
			/******************** 处方类别代码 ********************************************/
			bf.append("<entry>");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE08.50.032.00\" displayName=\"处方类别代码\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"CD\" code=\"1\" codeSystem=\"2.16.156.10011.2.3.2.40\" codeSystemName=\"处方类别代码表\" displayName=\"中药饮片处方\"/>");
			bf.append("</observation>");
			bf.append("</entry>");
		} else {
			bf.append("<entry>");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE06.00.179.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"处方备注信息\"/>");
			bf.append("<value xsi:type=\"ST\">无</value>");
			bf.append("</observation>");
			bf.append("</entry>");
		}
		/********************* 处方类别代码 ***************************************************/
		bf.append("</section>");
		bf.append("</component>");
		return bf.toString();
	}

	/**
	 * 如果没有 用药 添加默认的中药西药用药
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： String
	 * @param isChinese
	 *            是否是中药处方
	 * @return
	 */
	public static String nullMedition(Boolean isChinese) {
		StringBuffer bf = new StringBuffer("<component>");
		bf.append("<section>");
		bf.append("<code code=\"10160-0\" displayName=\"HISTORY OF MEDICATION USE\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		bf.append("<text/>");

		/******************** 处方条目开始 ********************************/
		bf.append("<entry>");
		bf.append("<substanceAdministration classCode=\"SBADM\" moodCode=\"EVN\">");
		bf.append("<text/>");
		// 用药途径
		bf.append("<routeCode code=\"9\" displayName=\"其他用药途径\" codeSystem=\"2.16.156.10011.2.3.1.158\" codeSystemName=\"用药途径代码表\"/>");
		// 用药剂量
		bf.append("<doseQuantity value=\"0.00\" unit=\"g\"/>");
		// 用药频率
		bf.append("<rateQuantity>");
		bf.append("<translation code=\"99\" displayName=\"其他\"/>");
		bf.append("</rateQuantity>");
		// 药物剂型
		bf.append("<administrationUnitCode code=\"99\" displayName=\"其他剂型(空心胶囊,绷带,纱布,胶布)\" codeSystem=\"2.16.156.10011.2.3.1.211\" codeSystemName=\"药物剂型代码表\"></administrationUnitCode>");

		// 药品名称 药品 名称 对应 CHARGE_NAME
		bf.append("<consumable>");
		bf.append("<manufacturedProduct>");
		bf.append("<manufacturedLabeledDrug>");
		bf.append("<code/>");
		bf.append("<name>无</name>");
		bf.append("</manufacturedLabeledDrug>");
		bf.append("</manufacturedProduct>");
		bf.append("</consumable>");

		/******************** 药品名称 ****************************/

		/******************** 药品名称结束 ****************************/

		/*************** 药物规格 ********************************************/
		bf.append("<entryRelationship typeCode=\"COMP\">");
		bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		bf.append("<code code=\"DE08.50.043.00\" displayName=\"药物规格\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		// 药物规格
		bf.append("<value xsi:type=\"ST\">无</value>");
		bf.append("</observation>");
		bf.append("</entryRelationship>");
		/*************** 药物规格 ********************************************/

		bf.append("<entryRelationship typeCode=\"COMP\">");
		bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		bf.append("<code code=\"DE06.00.135.00\" displayName=\"药物使用总剂量\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		// 药物使用剂量 必须 的 取总剂量
		bf.append("<value xsi:type=\"PQ\" value=\"0.00\" unit=\"无\"/>");
		bf.append("</observation>");
		bf.append("</entryRelationship>");

		/********************* 处方条目结束 *****************************************/
		bf.append("</substanceAdministration>");
		bf.append("</entry>");

		/*********************** 处方有效天数（非必须） ******************************/
		// 中药 和西药 默认值 中药 7天 西药 3天
		String days = "3";
		if (isChinese) {
			days = "7";
		}
		bf.append("<entry>");
		bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		bf.append("<code code=\"DE06.00.294.00\" displayName=\"处方有效天数\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		bf.append("<value xsi:type=\"PQ\" value=\"" + days + "\" unit=\"天\"/>");
		bf.append("</observation>");
		bf.append("</entry>");
		/******************** 处方有效天数结束 **************************************/

		/************************ 处方药品组号（非必须） ************************************/
		bf.append("<entry>");
		bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		bf.append("<code code=\"DE08.50.056.00\" displayName=\"处方药品组号\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		bf.append("<value xsi:type=\"INT\" value=\"1\"/>");
		bf.append("</observation>");
		bf.append("</entry>");
		/************************ 处方药品组号结束 ************************************/

		/*********************** 中药饮片处方（非必须 ） *********************************************/
		if (isChinese) {
			bf.append("<entry>");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE08.50.049.00\" displayName=\"中药饮片处方\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"ST\">无</value>");
			// 中药饮片剂数
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN \">");
			bf.append("<code code=\"DE08.50.050.00\" displayName=\"中药饮片剂数\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"PQ\" value=\"0\" unit=\"剂\"/>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");
			// 中药饮片煎煮法
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN \">");
			bf.append("<code code=\"DE08.50.047.00\" displayName=\"中药煎煮法\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"ST\">无</value>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");

			// 中药用药方法
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN \">");
			bf.append("<code code=\"DE06.00.136.00\" displayName=\"中药用药法\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"ST\">无</value>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");
			bf.append("</observation>");
			bf.append("</entry>");
			/*******************************************************************/
			/******************** 处方类别代码 ********************************************/
			bf.append("<entry>");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE08.50.032.00\" displayName=\"处方类别代码\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"CD\" code=\"1\" codeSystem=\"2.16.156.10011.2.3.2.40\" codeSystemName=\"处方类别代码表\" displayName=\"中药饮片处方\"/>");
			bf.append("</observation>");
			bf.append("</entry>");
		} else {
			bf.append("<entry>");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE06.00.179.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"处方备注信息\"/>");
			bf.append("<value xsi:type=\"ST\">无</value>");
			bf.append("</observation>");
			bf.append("</entry>");
		}
		/********************* 处方类别代码 ***************************************************/
		bf.append("</section>");
		bf.append("</component>");
		return bf.toString();
	}

	/**
	 * 获取手术或分娩后天数
	 * 
	 * @param num
	 *            天数
	 * @param unit
	 *            单位
	 * @return
	 */
	public static String getDocumentManagement(String num, String unit) {
		StringBuffer bf = new StringBuffer();
		// bf.append("<!--签名日期时间：DE09.00.053.00 -->");
		// bf.append("<time value=\"" + time + "\"/>");
		// bf.append("<signatureCode/>");
		// bf.append("<assignedEntity>");
		// bf.append("<id root=\"2.16.156.10011.1.4\" extension=\"" + id +
		// "\"/>");
		// bf.append("<code displayName=\"护士\"/>");
		// bf.append("<!--姓名-->");
		// bf.append("<name>贾伟杰</name>");
		// bf.append("</assignedEntity>");
		// bf.append("</authenticator>");
		bf.append("<documentationOf>");
		bf.append("<serviceEvent>");
		bf.append("<code/>");
		bf.append("<effectiveTime>");
		bf.append("<!--手术或分娩后天数-->");
		bf.append("<width value=\"" + num + "\" unit=\"" + unit + "\"/>");
		bf.append("</effectiveTime>");
		bf.append("</serviceEvent>");
		bf.append("</documentationOf>");
		return bf.toString();
	}

	/*******************************************************************/
	/**
	 * @param 传递chargecode
	 * @return 返回一个 dosage
	 */
	public static String getDosage(String chargeCode) {

		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<Map<String, String>> list = HbaseCURDUtils.findByCondition("HDR_DICT_ITEM", ChargeEnum.YPDICT.getCode()
				+ "|" + chargeCode, filters);
		String dosage = null;
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			String dos = map.get(dosage);
			if (!StringUtils.isBlank(dos)) {
				dosage = dos;
				break;
			}
		}
		return dosage;
	}
	/**
	 * 用药章节 针对南通妇幼特有
	 * 
	 * @param 门诊费用信息
	 * @param 中药和西药标识
	 *            调用枚举类 ChargeEnum.ZYCH.getCode() 或者 ChargeEnum.XYCH.getCode()
	 * @return
	 */
	public static String getMedition_NTFY(List<Map<String, String>> infoMapList, String flag) {
		StringBuffer bf = new StringBuffer("<component>");
		bf.append("<section>");
		bf.append("<code code=\"10160-0\" displayName=\"HISTORY OF MEDICATION USE\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/>");
		bf.append("<text/>");

		/******************** 处方条目开始 ********************************/
		for (int i = 0; i < infoMapList.size(); i++) {
			Map<String, String> infoMap = infoMapList.get(i);
			bf.append("<entry>");
			bf.append("<substanceAdministration classCode=\"SBADM\" moodCode=\"EVN\">");
			bf.append("<text/>");
			// 用药途径
			String name = infoMap.get("ADMINISTRATION");
			Map<String, String> finalDict = DictUtisTools.getPharmacyWayMap("-", name);
			bf.append("<routeCode code=\"" + finalDict.get("code") + "\" displayName=\""
					+ finalDict.get("name").replace("<", "").replace(">", "")
					+ "\" codeSystem=\"2.16.156.10011.2.3.1.158\" codeSystemName=\"用药途径代码表\"/>");
			// 用药剂量
			if (infoMap.containsKey("SINGEDOSE")) {
				// 单次用药剂量 如果没有 不加  DRUG_AMOUNT_UNIT
				if (!StringUtils.isBlank(infoMap.get("SINGEDOSE"))
						&& !StringUtils.isBlank(infoMap.get("SINGEDOSEUNIT"))) {
					try {
						String dosage_value = CommonUtils.format2(infoMap.get("SINGEDOSE"));
						if (dosage_value.length() > 5) {
							String[] array = dosage_value.split("\\.");
							if (array[0].length() > 2) {
								array[0] = "99";
							}
							dosage_value = array[0] + "." + array[1];
							dosage_value = dosage_value.length() > 5 ? dosage_value.substring(0, 5) : dosage_value;
						}
						bf.append("<doseQuantity value=\"" + dosage_value + "\" unit=\"" + infoMap.get("SINGEDOSEUNIT")
								+ "\"/>");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} else {
				bf.append("<doseQuantity value=\"0.00\" unit=\"g\"/>");
			}
			// 用药频率 TODO 查询用药频率字典 计斌对字典 如果为空
			Map<String, String> drugFreq = DictUtisTools.getDrugFreq("-", infoMap.get("FREQUENCY"));
			bf.append("<rateQuantity>");
			bf.append("<translation code=\"" + drugFreq.get("code") + "\" displayName=\"" + drugFreq.get("name")
					+ "\"/>");
			bf.append("</rateQuantity>");
			// 药物剂型
			Map<String, String> ywjxMap = DictUtisTools.getYWJXMap("-", infoMap.get("DOSAGE"));
			bf.append("<administrationUnitCode code=\"" + ywjxMap.get("code") + "\" displayName=\""
					+ ywjxMap.get("name")
					+ "\" codeSystem=\"2.16.156.10011.2.3.1.211\" codeSystemName=\"药物剂型代码表\"></administrationUnitCode>");
			/******************** 药品名称 ****************************/
			// 药品名称 药品 名称 对应 CHARGE_NAME
			bf.append("<consumable>");
			bf.append("<manufacturedProduct>");
			bf.append("<manufacturedLabeledDrug>");
			bf.append("<code/>");
			// 药品名称
			String drug_name = StringUtils.isBlank(infoMap.get("DRUGNAME")) ? "-" : infoMap.get("DRUGNAME");
			bf.append("<name>" + drug_name + "</name>");
			bf.append("</manufacturedLabeledDrug>");
			bf.append("</manufacturedProduct>");
			bf.append("</consumable>");
			/******************** 药品名称结束 ****************************/

			/*************** 药物规格 ********************************************/
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE08.50.043.00\" displayName=\"药物规格\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			// 药物规格
			bf.append("<value xsi:type=\"ST\">" + infoMap.get("DRUGSPEC") + "</value>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");
			/*************** 药物规格 ********************************************/

			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE06.00.135.00\" displayName=\"药物使用总剂量\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			// 药物使用剂量 必须 的 取总剂量
			//TODO  如果取 TOTAL_DOSAGE_UNIT 取不到取CHARGE_AMOUNT_UNIT  如果费用是负数 则不加 改药品  入口也的加上
			try {
				String value = CommonUtils.format2(infoMap.get("TOTALDOSE"));
				String unit =  infoMap.get("TOTALDOSEUNIT");
				bf.append("<value xsi:type=\"PQ\" value=\"" + value + "\" unit=\"" + unit + "\"/>");
				bf.append("</observation>");
				bf.append("</entryRelationship>");
			} catch (Exception e) {
				e.printStackTrace();
			}

			/********************* 处方条目结束 *****************************************/
			bf.append("</substanceAdministration>");
			bf.append("</entry>");
		}

		/*********************** 处方有效天数（非必须） ******************************/
		// 中药 和西药 默认值 中药 7天 西药 3天
		String days = "0";
		String clinicno = "-";
		if (!infoMapList.isEmpty()) {
			days = infoMapList.get(0).get("CLINICDAYS");
			clinicno = infoMapList.get(0).get("CLINICNO");
		}
		bf.append("<entry>");
		bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		bf.append("<code code=\"DE06.00.294.00\" displayName=\"处方有效天数\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		bf.append("<value xsi:type=\"PQ\" value=\"" + days + "\" unit=\"天\"/>");
		bf.append("</observation>");
		bf.append("</entry>");
		/******************** 处方有效天数结束 **************************************/

		/************************ 处方药品组号（非必须） ************************************/
		bf.append("<entry>");
		bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		bf.append("<code code=\"DE08.50.056.00\" displayName=\"处方药品组号\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
		bf.append("<value xsi:type=\"INT\" value=\"" + clinicno + "\"/>");
		bf.append("</observation>");
		bf.append("</entry>");
		/************************ 处方药品组号结束 ************************************/

		/*********************** 中药饮片处方（非必须 ） *********************************************/
		if (ChargeEnum.ZYCF.getCode().equals(flag)) {
			bf.append("<entry>");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE08.50.049.00\" displayName=\"中药饮片处方\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"ST\">有</value>");
			// 中药饮片剂数
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN \">");
			bf.append("<code code=\"DE08.50.050.00\" displayName=\"中药饮片剂数\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"PQ\" value=\"" + infoMapList.size() + "\" unit=\"剂\"/>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");
			// 中药饮片煎煮法
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN \">");
			bf.append("<code code=\"DE08.50.047.00\" displayName=\"中药煎煮法\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"ST\">包煮</value>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");

			// 中药用药方法
			bf.append("<entryRelationship typeCode=\"COMP\">");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN \">");
			bf.append("<code code=\"DE06.00.136.00\" displayName=\"中药用药法\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"ST\">包煎</value>");
			bf.append("</observation>");
			bf.append("</entryRelationship>");
			bf.append("</observation>");
			bf.append("</entry>");
			/*******************************************************************/
			/******************** 处方类别代码 ********************************************/
			bf.append("<entry>");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE08.50.032.00\" displayName=\"处方类别代码\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\"/>");
			bf.append("<value xsi:type=\"CD\" code=\"1\" codeSystem=\"2.16.156.10011.2.3.2.40\" codeSystemName=\"处方类别代码表\" displayName=\"中药饮片处方\"/>");
			bf.append("</observation>");
			bf.append("</entry>");
		} else {
			bf.append("<entry>");
			bf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
			bf.append("<code code=\"DE06.00.179.00\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"处方备注信息\"/>");
			bf.append("<value xsi:type=\"ST\">无</value>");
			bf.append("</observation>");
			bf.append("</entry>");
		}
		/********************* 处方类别代码 ***************************************************/
		bf.append("</section>");
		bf.append("</component>");
		return bf.toString();
	}

}
