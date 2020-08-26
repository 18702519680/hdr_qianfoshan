package com.goodwill.cda.hlht.section;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.goodwill.cda.hlht.enums.ValueTypeEnum;
import com.goodwill.cda.util.CommonUtils;

/**
 * @Description
 * 类描述：常规Section处理方法提取
 * @author xiehongwei
 * @Date 2017年11月5日
 * @modify
 * 修改记录：
 *
 */
public class BaseSection {

	/**
	 * @Description
	 * 方法描述:获取obaservation节点信息
	 * @return 返回类型： String
	 * @param deCode
	 * @param displayName obasevation的显示名称
	 * @param effectiveTime 节点不需要传null,需要但是没有值填 "",两个值传入yyyyMMddHHmmss|yyyyMMddHHmmss
	 * @param valueType ST、BL、PQ、TS、CD
	 * @param value1 TS、ED、ST、BL、PQ的值，CD的code
	 * @param value2 PQ的单位，CD的name
	 * @param codeSystem CD类型时使用
	 * @param codeSystemName CD类型时使用
	 * @return
	 */
	public static String getTargetObservation(String deCode, String displayName, String effectiveTime,
			String valueType, String value1, String value2, String codeSystem, String codeSystemName) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<observation classCode=\"OBS\" moodCode=\"EVN\">");
		sbf.append("<code code=\"" + deCode
				+ "\" codeSystem=\"2.16.156.10011.2.2.1\" codeSystemName=\"卫生信息数据元目录\" displayName=\"" + displayName
				+ "\"/>");
		//时间显示 1 不显示；2显示节点不填值；3填入一个时间；4填入两个时间
		if (effectiveTime == null) {
			//不填任何字符，不做处理
		} else if (StringUtils.isBlank(effectiveTime)) {
			//传入一个空串，表示此节点必须有但是不用填值
			sbf.append("<effectiveTime/>");
		} else if (effectiveTime.indexOf("|") > -1) {
			//传入|分隔的两个时间
			sbf.append("<effectiveTime>");
			String[] ets = effectiveTime.split("\\|");
			if (ets.length == 1) {//
				sbf.append("<low value=\"" + CommonUtils.DateFormats(ets[0]) + "\"/>");
			} else {
				if (StringUtils.isNotBlank(ets[0])) {
					sbf.append("<low value=\"" + CommonUtils.DateFormats(ets[0]) + "\"/>");
				}
				if (StringUtils.isNotBlank(ets[1])) {
					sbf.append("<high value=\"" + CommonUtils.DateFormats(ets[1]) + "\"/>");
				}
			}
			sbf.append("</effectiveTime>");
		} else {
			sbf.append("<effectiveTime value = \"" + CommonUtils.DateFormats(effectiveTime) + "\"/>");
		}

		//value有多种类型，ST PQ BL CD
		if (ValueTypeEnum.ST.getCode().equals(valueType)) {
			sbf.append("<value xsi:type=\"ST\">" + CommonUtils.formatIsblank(value1, "无") + "</value>");
		} else if (ValueTypeEnum.BL.getCode().equals(valueType)) {
			sbf.append("<value xsi:type=\"BL\" value=\"" + CommonUtils.formatIsblank(value1, "无") + "\"/>");
		} else if (ValueTypeEnum.PQ.getCode().equals(valueType)) {
			sbf.append("<value xsi:type=\"PQ\" value=\"" + value1 + "\" unit=\"" + value2 + "\" />");
		} else if (ValueTypeEnum.CD.getCode().equals(valueType)) {
			sbf.append("<value xsi:type=\"CD\" code=\"" + value1 + "\" displayName=\"" + value2 + "\" codeSystem=\""
					+ codeSystem + "\" codeSystemName=\"" + codeSystemName + "\"/>  ");
		} else if (ValueTypeEnum.TS.getCode().equals(valueType)) {
			if ("8".equals(value2)) {
				value1 = CommonUtils.formatBirthday(value1);
			} else if ("14".equals(value2)) {
				value1 = CommonUtils.DateFormats(value1);
			}
			sbf.append("<value xsi:type=\"TS\" value=\"" + CommonUtils.DateFormats(value1) + "\"/>");
		} else if (ValueTypeEnum.ED.getCode().equals(valueType)) {
			sbf.append("<value xsi:type=\"ED\">" + CommonUtils.formatIsblank(value1, "无") + "</value>");
		} else if (ValueTypeEnum.INT.getCode().equals(valueType)) {
			sbf.append("<value xsi:type=\"INT\">" + CommonUtils.formatIsblank(value1, "0") + "</value>");
		}
		sbf.append("</observation>");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:获取带entry的单个relationship
	 * @return 返回类型： String
	 * @param withEntry
	 * @return
	 */
	public static String getEntryRelationShips(String deCode, String displayName, String effectiveTime,
			String valueType, String value1, String value2, String codeSystem, String codeSystemName,
			List<String> entryRelationShips, boolean withEntry) {
		StringBuffer sbf = new StringBuffer();
		if (withEntry) {
			sbf.append("<entry>");
		}
		sbf.append("<!-- " + displayName + " -->");
		//先拼接entry的主干部分
		sbf.append(getTargetObservation(deCode, displayName, effectiveTime, valueType, value1, value2, codeSystem,
				codeSystemName, entryRelationShips));
		if (withEntry) {
			sbf.append("</entry>");
		}
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:获取obaservation节点信息
	 * @return 返回类型： String
	 * @param deCode
	 * @param displayName obasevation的显示名称
	 * @param effectiveTime 节点不需要传null,需要但是没有值填 "",两个值传入yyyyMMddHHmmss|yyyyMMddHHmmss
	 * @param valueType ST、BL、PQ、TS、CD
	 * @param value1 TS、ED、ST、BL、PQ的值，CD的code
	 * @param value2 PQ的单位，CD的name
	 * @param codeSystem CD类型时使用
	 * @param codeSystemName CD类型时使用
	 * @param otherXml 需要嵌入的xml，位置放置在observation结束位置之前
	 * @return
	 */
	public static String getTargetObservation(String deCode, String displayName, String effectiveTime,
			String valueType, String value1, String value2, String codeSystem, String codeSystemName,
			List<String> otherXml) {
		String targetObserStr = getTargetObservation(deCode, displayName, effectiveTime, valueType, value1, value2,
				codeSystem, codeSystemName);
		targetObserStr = targetObserStr.replace("</observation>", "");
		StringBuffer sbf = new StringBuffer(targetObserStr);
		//拼接当前位置拼接relationship或者麻醉医师
		for (String other : otherXml) {
			sbf.append(other);
		}

		sbf.append("</observation>");
		return sbf.toString();
	}

	/**
	 * @Description
	 * 方法描述:拼接最简单的entry，传入参数见 getTargetObservation()方法
	 * @return 返回类型： String
	 * @param deCode
	 * @param displayName obasevation的显示名称
	 * @param effectiveTime 节点不需要传null,需要但是没有值填 "",两个值传入yyyyMMddHHmmss|yyyyMMddHHmmss
	 * @param valueType ST、BL、PQ、TS、CD
	 * @param value1 TS、ST、BL、PQ的值，CD的code
	 * @param value2 PQ的单位，CD的name
	 * @param codeSystem CD类型时使用
	 * @param codeSystemName CD类型时使用
	 * @return
	 */
	public static String getSimpleEntry(String deCode, String displayName, String effectiveTime, String valueType,
			String value1, String value2, String codeSystem, String codeSystemName) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<!---" + displayName + " -->");
		sbf.append("<entry>");
		sbf.append(getTargetObservation(deCode, displayName, effectiveTime, valueType, value1, value2, codeSystem,
				codeSystemName));
		sbf.append("</entry>");
		return sbf.toString();
	}


	/**
	 * @Description
	 * 方法描述:获取文本展示的entry
	 * @return 返回类型： String
	 * @param deCode
	 * @param displayName
	 * @param valueStr
	 * @return
	 */
	public static String getSimpleSTEntry(String deCode, String displayName, String valueStr) {
		return getSimpleEntry(deCode, displayName, null, ValueTypeEnum.ST.getCode(), valueStr, null, null, null);
	}

	/**
	 * @Description
	 * 方法描述:拼接最简单的entryRelationship，传入参数见 getTargetObservation()方法
	 * @return 返回类型： String
	 * @param deCode
	 * @param displayName obasevation的显示名称
	 * @param effectiveTime 节点不需要传null,需要但是没有值填 "",两个值传入yyyyMMddHHmmss|yyyyMMddHHmmss
	 * @param valueType ST、BL、PQ、TS、CD
	 * @param value1 TS、ST、BL、PQ的值，CD的code
	 * @param value2 PQ的单位，CD的name
	 * @param codeSystem CD类型时使用
	 * @param codeSystemName CD类型时使用
	 * @return
	 */
	public static String getSimpleEntryRelationShip(String deCode, String displayName, String effectiveTime,
			String valueType, String value1, String value2, String codeSystem, String codeSystemName) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<!--- " + displayName + " -->");
		//sbf.append("<entryRelationship typeCode=\"COMP\" moodCode=\"EVN\">");
		//TODO xhw 需要注意下这里 entryRelationship是不是只有下面的格式？
		sbf.append("<entryRelationship typeCode=\"COMP\">");
		sbf.append(getTargetObservation(deCode, displayName, effectiveTime, valueType, value1, value2, codeSystem,
				codeSystemName));
		sbf.append("</entryRelationship>");
		return sbf.toString();
	}

	public static String getSimpleSTEntryRelationShip(String deCode, String displayName, String valueStr) {
		return getSimpleEntryRelationShip(deCode, displayName, null, ValueTypeEnum.ST.getCode(), valueStr, null, null,
				null);
	}



	/**
	 * @Description
	 * 方法描述:字符串替换，如Entry中的typecode等
	 * @return 返回类型： String
	 * @param targetStr
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public static String replaceExtraStr(String targetStr, String oldStr, String newStr) {
		return targetStr.replace(oldStr, newStr);
	}
}
