package com.goodwill.cda.hlht.section;

import com.goodwill.cda.util.CommonUtils;

/**
 * 手术操作章节
 * @author peijiping
 *
 */
public class OperationSection {

	/**
	 * 的手术者和麻醉者
	 * @param code
	 * @param name
	 * @return
	 */
	public static String Performer(String code, String name) {
		StringBuffer xml = new StringBuffer();
		xml.append("<performer>");
		xml.append("<assignedEntity> ");
		xml.append("<id root=\"2.16.156.10011.1.4\" extension=\"" + CommonUtils.formatIsblank(code, "-") + "\"/>  ");
		xml.append("<assignedPerson> ");
		xml.append("<name>" + CommonUtils.formatIsblank(name, "-") + "</name> ");
		xml.append("</assignedPerson> ");
		xml.append("</assignedEntity> ");
		xml.append("</performer>  ");
		return xml.toString();
	}

}
