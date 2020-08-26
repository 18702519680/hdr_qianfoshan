package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * @Description
 * 类描述：患者头部id信息对应的枚举
 * @author xiehongwei
 * @Date 2017年11月6日
 * @modify
 * 修改记录：
 *
 */
public enum PatIdEleEnum implements EnumType {
	OUTP_NO {

		@Override
		public String getCode() {
			return "OUTP_NO";
		}

		@Override
		public String getLabel() {
			return "2.16.156.10011.1.11";
		}

	},
	INP_NO {
		@Override
		public String getCode() {
			return "INP_NO";
		}

		@Override
		public String getLabel() {
			return "2.16.156.10011.1.12";
		}
	},
	REPORT_NO {
		@Override
		public String getCode() {
			return "REPORT_NO";
		}

		@Override
		public String getLabel() {
			return "2.16.156.10011.1.32";
		}
	},
	APPLY_NO {
		@Override
		public String getCode() {
			return "APPLY_NO";
		}

		@Override
		public String getLabel() {
			return "2.16.156.10011.1.24";
		}
	},
	SAMPLE_NO {
		@Override
		public String getCode() {
			return "SAMPLE_NO";
		}

		@Override
		public String getLabel() {
			return "2.16.156.10011.1.14";
		}
	},
	PARENT_ORDER_NO {
		@Override
		public String getCode() {
			return "PARENT_ORDER_NO";
		}

		@Override
		public String getLabel() {
			return "2.16.156.10011.1.20";
		}
	},
	ID_CARD_NO {
		@Override
		public String getCode() {
			return "ID_CARD_NO";
		}

		@Override
		public String getLabel() {
			return "2.16.156.10011.1.3";
		}
	},
	OUT_PATIENT_ID {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "OUT_PATIENT_ID";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "2.16.156.10011.1.11";
		}

	},
	CARD_NO {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "CARD_NO";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "2.16.156.10011.1.11";
		}

	},
	INFORMED_NO {
		@Override
		public String getCode() {
			return "INFORMED_NO";
		}

		@Override
		public String getLabel() {
			return "2.16.156.10011.1.34";
		}
	}
}
