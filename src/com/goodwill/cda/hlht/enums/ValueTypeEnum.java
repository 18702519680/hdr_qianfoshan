package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * @Description
 * 类描述：结果类型枚举
 * @author xiehongwei
 * @Date 2017年11月5日
 * @modify
 * 修改记录：
 *
 */
public enum ValueTypeEnum implements EnumType {
	/**
	 * 字符型
	 */
	ST {

		@Override
		public String getCode() {
			return "ST";
		}

		@Override
		public String getLabel() {
			return "字符型";
		}

	},
	/**
	 * 数值型
	 */
	PQ {

		@Override
		public String getCode() {
			return "PQ";
		}

		@Override
		public String getLabel() {
			return "数值型";
		}

	},
	/**
	 * 布尔型
	 */
	BL {

		@Override
		public String getCode() {
			return "BL";
		}

		@Override
		public String getLabel() {
			return "布尔型";
		}

	},
	/**
	 * 值域
	 */
	CD {

		@Override
		public String getCode() {
			return "CD";
		}

		@Override
		public String getLabel() {
			return "值域型";
		}

	},
	/**
	 * 时间
	 */
	TS {

		@Override
		public String getCode() {
			return "TS";
		}

		@Override
		public String getLabel() {
			return "时间型";
		}

	},
	/**
	 * ED
	 */
	ED {

		@Override
		public String getCode() {
			return "ED";
		}

		@Override
		public String getLabel() {
			return "ED";
		}

	},
	/**
	 * INT
	 */
	INT {

		@Override
		public String getCode() {
			return "INT";
		}

		@Override
		public String getLabel() {
			return "数字型";
		}

	}
}
