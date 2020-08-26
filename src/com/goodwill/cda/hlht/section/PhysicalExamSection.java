package com.goodwill.cda.hlht.section;

import com.goodwill.cda.util.CommonUtils;

/**
 * @Description
 * 类描述：体格检查处理章节
 * @author xiehongwei
 * @Date 2017年11月5日
 * @modify
 * 修改记录：
 *
 */
public class PhysicalExamSection {

	/**
	 * @Description
	 * 方法描述:获取简单一句话描述的体格检查的component 2 3 可以用
	 * @return 返回类型： String
	 * @param phyExamResultStr
	 * @return
	 */
	public static String getSimplePhyExamComp(String phyExamResultStr) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(OtherSection.componementHead("29545-1", "PHYSICAL EXAMINATION"));
		sbf.append(BaseSection.getSimpleSTEntry("DE04.10.258.00", "体格检查",
				CommonUtils.formatIsblank(phyExamResultStr, "无")));
		sbf.append(OtherSection.endSectionComp());
		return sbf.toString();
	}

}
