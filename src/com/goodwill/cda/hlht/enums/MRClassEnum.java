package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * 
 * @Description 类描述：病历文书类型枚举
 * @author malongbiao
 * @Date 2017年11月8日
 * @modify 修改记录：
 *
 */
public enum MRClassEnum implements EnumType {

	RYJL {
		@Override
		public String getCode() {
			return "EMR09.00.01";
		}

		@Override
		public String getLabel() {
			return "入院记录";
		}
	},
	/**
	 * 日常病程记录 的code
	 */
	RCBCJL {

		@Override
		public String getCode() {
			return "EMR10.00.02";
		}

		@Override
		public String getLabel() {
			return "日常病程记录";
		}

	},
	/**
	 * 门(急)诊病历
	 */
	MJZBL {

		@Override
		public String getCode() {
			return "EMR02.00.01";
		}

		@Override
		public String getLabel() {
			return "门(急)诊病历";
		}

	},
	/**
	 * 术前小结 
	 */
	SQXJ {

		@Override
		public String getCode() {
			return "EMR10.00.10";
		}

		@Override
		public String getLabel() {
			return "术前小结";
		}

	},
	/**
	 * 特殊检查及治疗同意书
	 */
	TSJCJTSZL {

		@Override
		public String getCode() {
			return "EMR07.00.02";
		}

		@Override
		public String getLabel() {
			return "特殊检查及治疗同意书";
		}

	}

}
