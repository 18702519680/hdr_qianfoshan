package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * 
 * @Description 类描述：风险章节的枚举
 * @author liuzhi
 * @Date 2017年11月5日
 * @modify 修改记录：
 */
public enum RiskEnum implements EnumType {
	/**
	 * 手术中的风险 代码和displayName
	 */
	SSZFX {

		@Override
		public String getCode() {
			return "DE05.10.162.00";
		}

		@Override
		public String getLabel() {
			return "手术中可能出现的意外及风险";
		}
	},
	/**
	 * 手术后的风险 代码和 displayName
	 */
	SSHFX {

		@Override
		public String getCode() {
			return "DE05.01.075.00";
		}

		@Override
		public String getLabel() {
			return "手术后可能出现的意外以及风险";
		}
	},
	/**
	 * 麻醉中、麻醉后风险 代码和 displayName
	 */
	MZFX {

		@Override
		public String getCode() {
			return "DE05.01.075.00";
		}

		@Override
		public String getLabel() {
			return "麻醉中，麻醉后可能产发生的意外及并发症";
		}
	},
	/**
	 * 输血的风险 代码和 displayName
	 */
	SXFX {

		@Override
		public String getCode() {
			return "DE06.00.130.00";
		}

		@Override
		public String getLabel() {
			return "输血风险及可能发生的不良后果";
		}
	},
	/**
	 * 特殊检查及特殊治疗的风险 代码和 displayName
	 */
	TSJCFX {

		@Override
		public String getCode() {
			return "DE05.01.075.00";
		}

		@Override
		public String getLabel() {
			return "特殊检查及特殊治疗可能引起的并发症及风险";
		}
	}

}
