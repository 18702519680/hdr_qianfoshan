package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

public enum StructuredBodyENGTypeEnum implements EnumType {

	ZS {
		@Override
		public String getCode() {
			return "10154-3";
		}

		@Override
		public String getLabel() {
			return "CHIEF COMPLAINT";
		}
	},
	XBS {
		@Override
		public String getCode() {
			return "10164-2";
		}

		@Override
		public String getLabel() {
			return "HISTORY OF PRESENT ILLNESS";
		}
	},
	JWS {
		@Override
		public String getCode() {
			return "10164-2";
		}

		@Override
		public String getLabel() {
			return "HISTORY OF PAST ILLNESS";
		}
	},
	YFJZS {
		@Override
		public String getCode() {
			return "11369-6";
		}

		@Override
		public String getLabel() {
			return "HISTORY OF IMMUNIZATIONS";
		}
	},
	SXS {
		@Override
		public String getCode() {
			return "56836-0";
		}

		@Override
		public String getLabel() {
			return "History of blood transfusion";
		}
	},
	GRS {
		@Override
		public String getCode() {
			return "29762-2";
		}

		@Override
		public String getLabel() {
			return "Social history";
		}
	},
	YJS {
		@Override
		public String getCode() {
			return "49033-4";
		}

		@Override
		public String getLabel() {
			return "Menstrual History";
		}
	},
	JZS {
		@Override
		public String getCode() {
			return "10157-6";
		}

		@Override
		public String getLabel() {
			return "HISTORY OF FAMILY MEMBER DISEASES";
		}
	}
}
