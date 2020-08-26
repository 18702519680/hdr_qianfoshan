package com.goodwill.cda.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Description 类描述：字典工具类
 * @author liuzhi
 * @Date 2017年11月16日
 * @modify 修改记录：hanfei  统一字典工具类
 *
 */
public class DictUtisTools {
	/**
	 * @Description
	 * 方法描述:获取性别对照字典
	 Map<String, String> sexMap = DictUtilsXHW.getSexMap(map.get("SEX_CODE"), map.get("SEX_CODE"));
	 * @return 返回类型： Map<String,String>
	 * @param oriSexCode
	 * @param oriSexName
	 * @return
	 */
	public static Map<String, String> getSexMap(String oriSexCode, String oriSexName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.sex, oriSexCode, oriSexName);
		if (result.isEmpty()) {
			result.put("code", "0");
			result.put("name", "未知的性别");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取婚姻状态对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriMaritalCode
	 * @param oriMaritalName
	 * @return
	 */
	public static Map<String, String> getMaritalMap(String oriMaritalCode, String oriMaritalName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.marital, oriMaritalCode, oriMaritalName);
		if (result.isEmpty()) {
			result.put("code", "90");
			result.put("name", "未说明的婚姻状况");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取民族的对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriNationalCode
	 * @param oriNationalName
	 * @return
	 */
	public static Map<String, String> getNationalMap(String oriNationalCode, String oriNationalName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.national, oriNationalCode, oriNationalName);
		if (result.isEmpty()) {
			result.put("code", "01");
			result.put("name", "汉族");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取从业状况(个人身体)代码表(GB/T 2261.4)对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriOccCode
	 * @param oriOccName
	 * @return
	 */
	public static Map<String, String> getOccupationMap(String oriOccCode, String oriOccName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.GBT2261_4_2003, oriOccCode, oriOccName);
		if (result.isEmpty()) {
			result.put("code", "90");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取RH血型对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriRhCode
	 * @param oriRhName
	 * @return
	 */
	public static Map<String, String> getRHBloodMap(String oriRhCode, String oriRhName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0450020, oriRhCode, oriRhName);
		if (result.isEmpty()) {
			result.put("code", "3");
			result.put("name", "不详");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取ABO血型对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriAboCode
	 * @param oriAboName
	 * @return
	 */
	public static Map<String, String> getAboBloodMap(String oriAboCode, String oriAboName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0450005, oriAboCode, oriAboName);
		if (result.isEmpty()) {
			result.put("code", "5");
			result.put("name", "不详");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取诊断ICD10对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriDiagCode
	 * @param oriDiagName
	 * @return
	 */
	public static Map<String, String> getIcd10Map(String oriDiagCode, String oriDiagName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.diagnosis, oriDiagCode, oriDiagName);
		if (result.isEmpty()) {
			result.put("code", StringUtils.isBlank(oriDiagCode) ? "-" : oriDiagCode);
			result.put("name", StringUtils.isBlank(oriDiagName) ? "-" : oriDiagName);
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取患者类型对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriPatTypeCode
	 * @param oriPatTypeName
	 * @return
	 */
	public static Map<String, String> getPatTypeMap(String oriPatTypeCode, String oriPatTypeName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0900404, oriPatTypeCode, oriPatTypeName);
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取病情转归对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriBqzgCode
	 * @param oriBqzgName
	 * @return
	 */
	public static Map<String, String> getBQZG(String oriBqzgCode, String oriBqzgName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0510010, oriBqzgCode, oriBqzgName);
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取中医病证分类与代码表对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriZybzflCode
	 * @param oriZybzflName
	 * @return
	 */
	public static Map<String, String> getZYBZFLMap(String oriZybzflCode, String oriZybzflName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.GBT15657_1995, oriZybzflCode, oriZybzflName);
		if (result.isEmpty()) {
			result.put("code", "ZVV");
			result.put("name", "中医病证分类");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取ICD9手术名称对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriOperCode
	 * @param oriOperName
	 * @return
	 */
	public static Map<String, String> getOperMap(String oriOperCode, String oriOperName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.operation, oriOperCode, oriOperName);
		if (result.isEmpty()) {
			result.put("code", StringUtils.isBlank(oriOperCode) ? "-" : oriOperCode);
			result.put("name", StringUtils.isBlank(oriOperName) ? "-" : oriOperName);
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取中药使用类别代码表对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriZysylbCode
	 * @param oriZysylbName
	 * @return
	 */
	public static Map<String, String> getZYSYLBMap(String oriZysylbCode, String oriZysylbName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600101, oriZysylbCode, oriZysylbName);
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取医疗保险类别代码表对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriYlbxlbCode
	 * @param oriYlbxlbName
	 * @return
	 */
	public static Map<String, String> getYLBXLBMap(String oriYlbxlbCode, String oriYlbxlbName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0201204, oriYlbxlbCode, oriYlbxlbName);
		if (result.isEmpty()) {
			result.put("code", "99");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取医疗付费方式代码表对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriYlfffsCode
	 * @param oriYlfffsName
	 * @return
	 */
	public static Map<String, String> getYLFFFSMap(String oriYlfffsCode, String oriYlfffsName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0710005, oriYlfffsCode, oriYlfffsName);
		if (result.isEmpty()) {
			result.put("code", "99");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取初诊标志代码表对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriCzbbCode
	 * @param oriCzbbName
	 * @return
	 */
	public static Map<String, String> getCzbbMap(String oriCzbbCode, String oriCzbbName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060019600, oriCzbbCode, oriCzbbName);
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取医嘱项目类型代码表对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriOrderClassCode
	 * @param oriOrderClassName
	 * @return
	 */
	public static Map<String, String> getOrderClassMap(String oriOrderClassCode, String oriOrderClassName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600229, oriOrderClassCode, oriOrderClassName);
		if (result.isEmpty()) {
			result.put("code", "99");
			result.put("name", "其他医嘱");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取专业技术职务代码表对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriZyjszwCode
	 * @param oriZyjszwName
	 * @return
	 */
	public static Map<String, String> getZYJSZWMap(String oriZyjszwCode, String oriZyjszwName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0830005, oriZyjszwCode, oriZyjszwName);
		if (result.isEmpty()) {
			result.put("code", "3");
			result.put("name", "中级");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取用药途径代码表对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriWayCode
	 * @param oriWayName
	 * @return
	 */
	public static Map<String, String> getPharmacyWayMap(String oriWayCode, String oriWayName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600102, oriWayCode, oriWayName);
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他用药途径");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取药物剂型对照字典
	 * @return 返回类型： Map<String,String>
	 * @param oriYwjxCode
	 * @param oriYwjxName
	 * @return
	 */
	public static Map<String, String> getYWJXMap(String oriYwjxCode, String oriYwjxName) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0850002, oriYwjxCode, oriYwjxName);
		if (result.isEmpty()) {
			result.put("code", "99");
			result.put("name", "其他剂型(空心胶囊,绷带,纱布,胶布)");
		}
		return result;
	}

	/**
	 * 获取护理等级
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 *            院内code
	 * @param name
	 *            院内名称
	 * @return
	 */
	public static Map<String, String> getHLDJ(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600220, code, name);
		if (result.isEmpty()) {
			result.put("code", "4");
			result.put("name", "三级护理");
		}
		return result;
	}

	/**
	 * 世界各国和地区名称代码(GB/T 2659)
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 *            院内code
	 * @param name
	 *            院内名称
	 * @return
	 */
	public static Map<String, String> getSJGGHDYMC(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.GBT2659, code, name);
		if (result.isEmpty()) {
			result.put("code", "156");
			result.put("name", "中国");
		}
		return result;
	}

	/**
	 * 入院途径代码表
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getRYTJDM(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0900403, code, name);
		if (result.isEmpty()) {
			result.put("name", "其他");
			result.put("code", "9");
		}
		return result;
	}

	/**
	 * 老年人自理能力代码 默认返回 code 为 1 可自理
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 *            院内code
	 * @param name
	 *            院内 名称
	 * @return
	 */
	public static Map<String, String> getLNRZLNL(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE051012200, code, name);
		if (result.isEmpty()) {
			result.put("name", "完全自理");
			result.put("code", "1");
		}
		return result;
	}

	/**
	 * 饮酒频率代码表 默认偶尔 code为：2
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getYJPL(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0300104, code, name);
		if (result.isEmpty()) {
			result.put("name", "从不");
			result.put("code", "1");
		}
		return result;
	}

	/**
	 * 离院方式代码表 默认 其他 code为：9
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getLYFS(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600226, code, name);
		if (result.isEmpty()) {
			result.put("name", "其他");
			result.put("code", "9");
		}
		return result;
	}

	/**
	 * 住院者疾病状态代码表
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getZYZJBZT(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0501001, code, name);
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * 入院病情代码表
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getRYBQ(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0510019, code, name);
		if (result.isEmpty()) {
			result.put("code", "3");
			result.put("name", "情况不明");
		}
		return result;
	}

	/**
	 * 手术切口类别代码表 如果没有默认返回 0类切口 --- 有手术，但体表无切口或腔镜手术切口
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getSSQKLB(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0510022, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "0类切口");
		}
		return result;
	}

	/**
	 * 医嘱类别代码表 如果没有默认返回 其他医嘱
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getYZLB(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060028600, code, name);
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * 中医病证分类与代码表（ GB/T 15657-1995）
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getZYBZFL(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.GBT156571995, code, name);
		return result;
	}

	/**
	 * 转科记录类型代码表
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getZKJL(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060031400, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "转入记录");
		}
		return result;
	}

	/**
	 * 手术切口愈合等级代码表
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getSSQKYHDJ(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0510023, code, name);
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * 家庭关系代码表(GB/T 4761)
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getJTGX(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.GBT47612008, code, name);
		if (result.isEmpty()) {
			result.put("code", "8");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * 吸烟状况代码表
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getXYZK(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE030007300, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "从不吸烟");
		}
		return result;
	}

	/**
	 * 营养状态代码表 默认返回 良好
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getYYZT(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE051009700, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "良好");
		}
		return result;
	}

	/**
	 * 发育程度代码表
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getFYCD(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE051002200, code, name);
		return result;
	}

	/**
	 * 学历代码 默认返回其他
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getXLDM(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.GBT46582006, code, name);
		if (result.isEmpty()) {
			result.put("code", "90");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * 心理状况代码表 默认返回其他
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getXLZK(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0510002, code, name);
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * 饮食情况代码 默认返回 其他
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getYSQK(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE030008000, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "良好");
		}
		return result;
	}

	/**
	 * 隔离种类
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getGLZL(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600222, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "消化道隔离");
		}
		return result;
	}

	/**
	 * 安全护理代码表
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getAQHL(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060017800, code, name);
		return result;
	}

	/**
	 * 心理护理代码表
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getXLHL(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060028300, code, name);
		return result;
	}

	/**
	 * 护理类型代码表 默认返回 其他 code 为 9
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getHLLX(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600221, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "基础护理");
		}
		return result;
	}

	/**
	 * 饮食指导代码表 默认 返回其他 code 为 99
	 * 
	 * @Description 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getYSZD(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600224, code, name);
		if (result.isEmpty()) {
			result.put("code", "99");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * 获取检验项目 代码 
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getJYXMDM(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE043001503, code, name);
		if (result.isEmpty()) {
			result.put("code", "3");
			result.put("name", "不确定");
		}
		return result;
	}

	/**
	 * 获取 病案质量代码 代码 
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getBAZL(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE090010300, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "甲");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取手术对照字典
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getMZFS(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600103, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "全身麻醉");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:获取操作部位代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getOperPosition(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.position, code, name);
		if (result.isEmpty()) {
			result.put("code", "99");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:麻醉方法代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getAnesMethod(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600103, code, name);
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他麻醉方法");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:随访方式代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getFollowUpMethod(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600207, code, name);
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:随访周期建议代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getRecomFollowUp(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600208, code, name);
		if (result.isEmpty()) {
			result.put("code", "99");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:药物使用频次代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getDrugFreq(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.drugcoding, code, name);
		if (result.isEmpty()) {
			result.put("code", "99");
			result.put("name", "其它");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:手术级别代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getOperLevel(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0510024, code, name);
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "一级手术");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:手术体位代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getOperTW(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600223, code, name);
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:胎方位代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getFetalPosition(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0501007, code, name);
		if (result == null || result.isEmpty()) {
			result.put("code", "99");
			result.put("name", "不详");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:分娩方式代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getDeliveryMode(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0210003, code, name);
		if (result == null || result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}

		return result;
	}

	/**
	 * @Description
	 * 方法描述:护理等级代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getNurseLevel(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600220, code, name);
		return result;
	}

	/**
	 * @Description
	 * 方法描述:处方类别代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> gePprescriptionClass(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE085003200, code, name);//DE085003200
		return result;
	}

	/**
	 * @Description
	 * 方法描述:患者类型代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getPatType(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0900404, code, name);//CV0900404
		return result;
	}

	/**
	 * @Description
	 * 方法描述:麻醉中西医标识代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getAnesCWFlag(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060030700, code, name);//DE060030700
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "西医麻醉");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:检查/检验结果代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getExamLabResult(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE043001506, code, name);//DE043001506
		if (result.isEmpty()) {
			result.put("code", "3");
			result.put("name", "不确定");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:输血品种代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getBloodVarieties(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0450021, code, name);//CV0450021
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:美国麻醉医师协会(ASA)分级标准代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getASALevel(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0510021, code, name);//CV0510021
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "I");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:呼吸类型代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getBreathType(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060020800, code, name);//DE060020800
		if (result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "自主呼吸");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:麻醉合并症标志代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getAnsComplication(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE050107700, code, name);//DE050107700
		if (result.isEmpty()) {
			result.put("code", "3");
			result.put("name", "不确定");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:输血史标识代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getBloodHisFlag(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060010600, code, name);//DE060010600
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "未说明");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:输血性质代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getBloodProperty(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE045014700, code, name);//DE045014700
		if (result.isEmpty()) {
			result.put("code", "2");
			result.put("name", "常规");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:输血反应类型代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getBloodReactionType(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0501040, code, name);//CV0501040
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:受孕形式代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getConceptionForm(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060026100, code, name);//DE060026100
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:胎膜情况代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getFetalMembranes(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE051015500, code, name);//DE051015500
		if (result == null || result.isEmpty()) {
			result.put("code", "2");
			result.put("name", "未破");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:破膜方式代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getMembraneBreaking(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE041025600, code, name);//DE041025600
		if (result == null || result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "自然");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:检查方式代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getExamMethod(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE045013900, code, name);//DE045013900
		if (result == null || result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "阴道检查");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:会阴裂伤情况代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getPerinealLaceration(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0501010, code, name);//CV0501010
		if (result == null || result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "无裂伤");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:Apgar评分间隔时间代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getApgarScoreInterval(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060021500, code, name);//DE060021500
		if (result.isEmpty()) {
			result.put("code", "3");
			result.put("name", "10分钟");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:分娩结局代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getDeliveryOutcome(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060021500, code, name);//DE060002600
		if (result == null || result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "活产");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:新生儿异常情况代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getNeonatalAbnormalities(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0510020, code, name);//CV0510020
		if (result.isEmpty()) {
			result.put("code", "9");
			result.put("name", "其他");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:饮食情况代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getDietSituation(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE030008000, code, name);//DE030008000
		return result;
	}


	/**
	 * @Description
	 * 方法描述:气管护理代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getTracheaNursing(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060022900, code, name);//DE060022900
		if (result == null || result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "翻身拍背");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:心理护理代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getMentalNursing(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060028300, code, name);//DE060028300
		if (result == null || result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "根据病人心理状况施行心理护理");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:安全护理代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getSafetyNursing(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.DE060017800, code, name);//DE060017800
		if (result == null || result.isEmpty()) {
			result.put("code", "1");
			result.put("name", "勤巡视病房");
		}
		return result;
	}

	/**
	 * @Description
	 * 方法描述:隔离种类代码表
	 * @return 返回类型： Map<String,String>
	 * @param code
	 * @param name
	 * @return
	 */
	public static Map<String, String> getIsolationSpecies(String code, String name) {
		Map<String, String> result = DictUtils.getFinalDict(DictCode.CV0600220, code, name);//CV0600222
		return result;
	}

}
