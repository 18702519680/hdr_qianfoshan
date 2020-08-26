package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * 门诊和住院 信息表 枚举
 * 
 * @author liuzhi
 */
public enum BaseInfoEnum implements EnumType {

	MZXX {

		@Override
		public String getCode() {
			return "HDR_OUT_VISIT";
		}

		@Override
		public String getLabel() {
			return "门诊就诊表";
		}

	},
	ZYXX {
		@Override
		public String getCode() {
			return "HDR_INP_SUMMARY";
		}

		@Override
		public String getLabel() {
			return "住院基本信息";
		}

	},
	MZZD {

		@Override
		public String getCode() {
			return "HDR_OUT_VISIT_DIAG";
		}

		@Override
		public String getLabel() {
			return "门诊诊断表";
		}

	},
	ZYZD {

		@Override
		public String getCode() {
			return "HDR_INP_SUMMARY_DIAG";
		}

		@Override
		public String getLabel() {
			return "住院诊断表";
		}

	}
}
