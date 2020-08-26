package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

public enum VisitEffectiveTimeEnum implements EnumType {
	TYPE1 {

		@Override
		public String getCode() {
			return "01";
		}

		@Override
		public String getLabel() {
			return "<effectiveTime/>";
		}

	},
	TYPE2 {

		@Override
		public String getCode() {
			return "02";
		}

		@Override
		public String getLabel() {
			return "<effectiveTime nullFlavor=\"NI\"/>";
		}

	},
	TYPE3 {

		@Override
		public String getCode() {
			return "03";
		}

		@Override
		public String getLabel() {
			return "<effectiveTime value=\"20170927081200\"/>";
		}

	},
	TYPE4 {

		@Override
		public String getCode() {
			return "04";
		}

		@Override
		public String getLabel() {
			return "<effectiveTime xsi:type=\"IVL_TS\" value=\"20111216152138\"/> ";
		}

	},
	TYPE5 {

		@Override
		public String getCode() {
			return "05";
		}

		@Override
		public String getLabel() {
			return "<!--就诊时间--><effectiveTime><!--入院日期--><low value=\"20170927080500\"/>"
					+ "<!--出院日期--><high value=\"20171015104100\"/></effectiveTime>";
		}

	},
	TYPE6 {

		@Override
		public String getCode() {
			return "06";
		}

		@Override
		public String getLabel() {
			return "<!--就诊时间--><effectiveTime><!--入院日期--><low value=\"20170720115600\"/><!--实际住院天数--><width value=\"4\" unit=\"天\"/></effectiveTime>";
		}

	}
}
