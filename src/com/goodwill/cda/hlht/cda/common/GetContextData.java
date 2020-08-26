package com.goodwill.cda.hlht.cda.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.goodwill.cda.util.HbaseCURDUtilsToCDA;
import com.goodwill.cda.util.Xmlutil;
import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;

/**
 * 公共解析文书数据类
 * 
 * @author wulibing
 */
public class GetContextData {
	
	/**
	 * 
	 * @Description 方法描述:获取入院记录里面的传染病，手术，输血，过敏，预防接种
	 * @param str
	 * @return 返回类型： Map<String, String>
	 */
public static Map<String, String> getInRecord(String str) {
		
		String temp=str.replace("；", ",").replace("，", ",").replace(";", ",").replace("。", ",");;
		String jbsString = null;//疾病史
		String crbString = null;//传染病史
		String sssString = "";//手术史
		String sxsString = null;//输血史
		String gmsString = null;//过敏史
		String yfjzsString= null;//预防接种
		String regex = "[,]";
		String[] arr1 = temp.split(regex);
		 Map<String, String> mapInRecordS = new HashMap();
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i].contains("病史")&&!arr1[i].contains("传染")) {
				jbsString = arr1[i];
				mapInRecordS.put("jbsString", jbsString);
			}
			else if (arr1[i].contains("传染病")) {
				crbString = arr1[i];
				mapInRecordS.put("crbString", crbString);
			}
			else if (arr1[i].contains("手术")) {
				sssString =sssString + arr1[i];
				mapInRecordS.put("sssString", sssString);
			}
			else if (arr1[i].contains("输血")) {
				sxsString = arr1[i];
				mapInRecordS.put("sxsString", sxsString);
			} else if (arr1[i].contains("过敏")) {
				gmsString = arr1[i];
				mapInRecordS.put("gmsString", gmsString);
			} else if (arr1[i].contains("预防")) {
				yfjzsString = arr1[i];
				mapInRecordS.put("yfjzsString", yfjzsString);
			}
		}
		return mapInRecordS;
		}


/**
 * @Description 方法描述:输入文本及两个字符，截取文本中这两个字符中间的字符
 * @param str1,str2,type  type为1：结果不包括str1,str2。type为0：结果包括str1,str2
 * @return 返回类型： String
 */
public static String getSubString(String context,String str1,String str2,int type) {
	String str  =null;
	String regex="\\"+str1+"(.*?)\\"+str2;
	Pattern str_p = Pattern.compile(regex);// 正则表达式
	Matcher str_m = str_p.matcher(context.replace(" ", ""));
	while (str_m.find()) {
		str = str_m.group(type).replace("：", "").replace(":", "");
	}
	return str;
}

/**
 * @Description 方法描述:患者id,从护理表中获取体重信息
 * @param patientId String
 * @return 返回类型： String
 */
public static String getWeightValue(String patientId) {
List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
filters.add(new PropertyFilter("VITAL_TYPE_NAME", "STRING", MatchType.EQ.getOperation(), "体重"));
List<Map<String, String>> list = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByCondition("HDR_VITAL_MEASURE",
		HbaseCURDUtilsToCDA.getRowkeyPrefix(patientId), filters));
String weight_value =list.get(0).get("VITAL_SIGN_VALUE");
return weight_value;
}

/**
 * @Description 方法描述:患者id,vid 获取出院医嘱
 * @param patientId String
 * @return 返回类型： String
 */
public static String getcyyz(String patientId,String visitId) {
List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), visitId));
filters.add(new PropertyFilter("MR_CLASS_CODE", "STRING", MatchType.EQ.getOperation(), "Out_Record"));
List<Map<String, String>> list = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByCondition("HDR_EMR_CONTENT",
		HbaseCURDUtilsToCDA.getRowkeyPrefix(patientId), filters));
String mr_content_text =list.get(0).get("MR_CONTENT_TEXT");
String cyyzString=getSubString(mr_content_text, "出院医嘱", "签名", 1);
return cyyzString;
}

}
