package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * 生命体征类型
 * @author peijiping
 * @Date 2017年11月2日
 */
public enum VitalSignTypeEnum implements EnumType {

	WEIGHT {
		@Override
		public String getCode() {
			return "DE04.10.188.00";
		}

		@Override
		public String getLabel() {
			return "体重（kg）";
		}
	},
	WEIGHT_YQ {
		@Override
		public String getCode() {
			return "DE04.10.188.00";
		}

		@Override
		public String getLabel() {
			return "孕前体重（kg）";
		}
	},
	WEIGHT_CQ {
		@Override
		public String getCode() {
			return "DE04.10.188.00";
		}

		@Override
		public String getLabel() {
			return "产前体重（kg）";
		}
	},
	WEIGHT_RY {
		@Override
		public String getCode() {
			return "DE04.10.019.00";
		}

		@Override
		public String getLabel() {
			return "入院体重";
		}
	},
	WEIGHT_CS {
		@Override
		public String getCode() {
			return "DE04.10.019.00";
		}

		@Override
		public String getLabel() {
			return "出生体重";
		}
	},
	HEIGHT {
		@Override
		public String getCode() {
			return "DE04.10.167.00";
		}

		@Override
		public String getLabel() {
			return "身高";
		}
	},
	BP_SS {
		@Override
		public String getCode() {
			return "DE04.10.174.00";
		}

		@Override
		public String getLabel() {
			return "收缩压";
		}
	},
	BP_SZ {
		@Override
		public String getCode() {
			return "DE04.10.176.00";
		}

		@Override
		public String getLabel() {
			return "舒张压";
		}
	},
	RESPIRATOR {
		@Override
		public String getCode() {
			return "DE04.10.082.00";
		}

		@Override
		public String getLabel() {
			return "呼吸频率（次/min）";
		}
	},
	PULSE {
		@Override
		public String getCode() {
			return "DE04.10.118.00";
		}

		@Override
		public String getLabel() {
			return "脉率（次/min）";
		}
	},
	TEMPERATURE {
		@Override
		public String getCode() {
			return "DE04.10.186.00";
		}

		@Override
		public String getLabel() {
			return "体温（℃）";
		}
	},
	BOS {
		@Override
		public String getCode() {
			return "DE04.50.149.00";
		}

		@Override
		public String getLabel() {
			return "血氧饱和度（%）";
		}
	},
	HEARTRATE {
		@Override
		public String getCode() {
			return "DE04.10.206.00";
		}

		@Override
		public String getLabel() {
			return "心率";
		}
	},
	HEARTRATE_QBQ {
		@Override
		public String getCode() {
			return "DE04.10.206.00";
		}

		@Override
		public String getLabel() {
			return "起搏器心率（次/min）";
		}
	},
	ABDCIR {
		@Override
		public String getCode() {
			return "DE04.10.052.00";
		}

		@Override
		public String getLabel() {
			return "腹围（cm）";
		}
	},
	RESPIRATORYRATE {
		@Override
		public String getCode() {
			return "DE04.10.081.00";
		}

		@Override
		public String getLabel() {
			return "呼吸频率（次/min）";
		}
	},
	RESPIRATORYFLAG {
		@Override
		public String getCode() {
			return "DE06.00.239.00";
		}

		@Override
		public String getLabel() {
			return "使用呼吸机标志";
		}
	}

}