package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * @Description
 * 类描述：DE以及对应显示名称接口
 * @author xiehongwei
 * @Date 2017年11月9日
 * @modify
 * 修改记录：
 *
 */
public interface IDeCodeDisplayEnum extends EnumType {
	public String getDeCode();

	public String getDeDisplayName();
}
