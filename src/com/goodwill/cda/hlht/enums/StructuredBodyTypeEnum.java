package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

public enum StructuredBodyTypeEnum implements EnumType {
	ZS {
		@Override
		public String getCode() {
			return "DE04.01.119.00";
		}

		@Override
		public String getLabel() {
			return "主诉";
		}
	},
	XBS {
		@Override
		public String getCode() {
			return "DE02.10.071.00";
		}

		@Override
		public String getLabel() {
			return "现病史";
		}
	},
	YFJZS {
		@Override
		public String getCode() {
			return "DE04.10.174.00";
		}

		@Override
		public String getLabel() {
			return "预防接种史";
		}
	},
	SXS {
		@Override
		public String getCode() {
			return "DE02.10.100.00";
		}

		@Override
		public String getLabel() {
			return "输血史";
		}
	},
	GRS {
		@Override
		public String getCode() {
			return "DE02.10.097.00";
		}

		@Override
		public String getLabel() {
			return "个人史";
		}
	},
	YJS {
		@Override
		public String getCode() {
			return "DE02.10.102.00";
		}

		@Override
		public String getLabel() {
			return "月经史";
		}
	},
	JZS {
		@Override
		public String getCode() {
			return "DE02.10.102.00";
		}

		@Override
		public String getLabel() {
			return "家族史";
		}
	},
	JBS {
		@Override
		public String getCode() {
			return "DE02.10.026.00";
		}

		@Override
		public String getLabel() {
			return "疾病史（含外伤）";
		}
	},
	CRBS {
		@Override
		public String getCode() {
			return "DE02.10.008.00";
		}

		@Override
		public String getLabel() {
			return "传染病史";
		}
	},
	HYS {
		@Override
		public String getCode() {
			return "DE02.10.098.00";
		}

		@Override
		public String getLabel() {
			return "婚育史";
		}
	},
	GMS {
		@Override
		public String getCode() {
			return "DE02.10.022.00";
		}

		@Override
		public String getLabel() {
			return "过敏史";
		}
	},
	SSS {
		@Override
		public String getCode() {
			return "DE02.10.061.00";
		}

		@Override
		public String getLabel() {
			return "手术史";
		}
	},
	YBJKBZ {
		@Override
		public String getCode() {
			return "DE05.10.031.00";
		}

		@Override
		public String getLabel() {
			return "一般健康状况标志";
		}
	},
	HZCRXBZ {
		@Override
		public String getCode() {
			return "DE05.10.119.00";
		}

		@Override
		public String getLabel() {
			return "患者传染性标志";
		}
	}

}
