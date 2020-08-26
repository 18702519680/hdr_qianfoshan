package com.goodwill.cda.entity;

import com.goodwill.core.enums.EnumType;

/**
 * @Description
 * 类描述：数据状态枚举
 * DIS:作废      ENB:启用 
 * @author lijiannan
 * @Date 2014年6月18日
 * @modify
 * 修改记录：修改“停用”为“作废”
 * 恢复“作废”为“停用”
 */
public enum DataStatusEnum implements EnumType {

	/**
	 * 停用
	 */
	DISABLE {

		@Override
		public String getCode() {
			return "DIS";
		}

		@Override
		public String getLabel() {
			return "停用";
		}
	},
	/**
	 * 启用
	 */
	ENABLE {

		@Override
		public String getCode() {
			return "ENB";
		}

		@Override
		public String getLabel() {
			return "启用";
		}
	}
}