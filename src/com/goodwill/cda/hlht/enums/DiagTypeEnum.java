package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * @Description
 * 类描述：诊断类型枚举
 * @author xiehongwei
 * @Date 2017年11月1日
 * @modify
 * 修改记录：
 *
 */
public enum DiagTypeEnum implements EnumType {

	CBZDXY {
		@Override
		public String getCode() {
			return "01";
		}

		@Override
		public String getLabel() {
			return "初步诊断-西医诊断";
		}
	},
	CBZDZY {
		@Override
		public String getCode() {
			return "02";
		}

		@Override
		public String getLabel() {
			return "初步诊断-中医";
		}
	},
	
	XZZDXY {
		@Override
		public String getCode() {
			return "03";
		}

		@Override
		public String getLabel() {
			return "修正诊断-西医诊断";
		}
	},
	XZZDZY {
		@Override
		public String getCode() {
			return "04";
		}

		@Override
		public String getLabel() {
			return "修正诊断-中医";
		}
	},
	QDZDXY {
		@Override
		public String getCode() {
			return "05";
		}

		@Override
		public String getLabel() {
			return "确定诊断-西医诊断";
		}
	},
	QDZDZY {
		@Override
		public String getCode() {
			return "06";
		}

		@Override
		public String getLabel() {
			return "确定诊断-中医";
		}
	},
	BCZDXY {
		@Override
		public String getCode() {
			return "07";
		}

		@Override
		public String getLabel() {
			return "补充诊断-西医诊断";
		}
	}
	,
	BCZDZY {
		@Override
		public String getCode() {
			return "08";
		}

		@Override
		public String getLabel() {
			return "补充诊断-中医";
		}
	}
	,
	JBZD {
		@Override
		public String getCode() {
			return "09";
		}

		@Override
		public String getLabel() {
			return "疾病诊断";
		}
	}
	,
	SQZD {
		@Override
		public String getCode() {
			return "10";
		}

		@Override
		public String getLabel() {
			return "术前诊断";
		}
	},
	SHZD {
		@Override
		public String getCode() {
			return "11";
		}

		@Override
		public String getLabel() {
			return "术后诊断";
		}
	},
	QTXYZD {
		@Override
		public String getCode() {
			return "12";
		}

		@Override
		public String getLabel() {
			return "其他西医诊断";
		}
	}
}
