package com.goodwill.cda.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.core.utils.PropertiesUtils;

/**
 * @Description
 * 类描述：拼接电子病历查询条件Filters
 * @author hanfei
 * @Date 2018年9月29日
 * @modify
 * 修改记录：
 * 
 */
public class FiltersUtils {
	//互联互通配置文件
	private static String CONFIG_FILE_NAME = "filters.properties";
	// 章节和数据元配置文件
	private static String DATA_ELEMENT = "dataElement.properties";

	public static void main(String[] args) {
		String dataElement = dataElement("疾病史（含外伤）");
		System.out.println(dataElement);
	}

	/**
	 * 
	 * @Description
	 * 方法描述:拼接电子病历查询条件Filters
	 * @return 返回类型： List<PropertyFilter>
	 * @param filters
	 * @param cda 需要获取那个CDA的名称
	 * @return
	 */
	public static List<PropertyFilter> addFilters(List<PropertyFilter> filters, String cda) {
		String cdaFilter = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, cda);
		if (StringUtils.isBlank(cdaFilter)) {
			return filters;
		}
		if (cdaFilter.indexOf(";") > -1) {
			String[] cdas = cdaFilter.split(";");
			for (String str : cdas) {
				String[] strCda = str.split("\\|");
				filters.add(new PropertyFilter(strCda[0], "STRING", strCda[1], strCda[2]));
			}
		} else {
			String[] cdas = cdaFilter.split("\\|");
			filters.add(new PropertyFilter(cdas[0], "STRING", cdas[1], cdas[2]));
		}
		return filters;
	}

	/**
	 * 
	 * @Description
	 * 方法描述: 解决各个现场电子病历模板节点不统一的问题
	 * @return 返回类型： String
	 * @param data
	 * @return
	 */
	public static String dataElement(String data) {
		String val = PropertiesUtils.getPropertyValue(DATA_ELEMENT, data);
		if (StringUtils.isBlank(val)) {
			return data;
		}
		return val;
	}
}
