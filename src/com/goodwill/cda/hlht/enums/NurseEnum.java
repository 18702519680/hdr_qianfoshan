package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * 护理文书枚举类
 * @author jibin
 *
 */
public enum NurseEnum implements EnumType {

	TZ {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "tz";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "体重";
		}

	},
	TW {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "tw";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "体温";
		}

	},
	XL {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "xl";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "心率";
		}

	},
	HXPL {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "hxpl";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "呼吸频率";
		}

	},
	SSY {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "ssy";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "收缩压";
		}

	},
	SZY {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "szy";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "舒张压";
		}

	},
	XTJC {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "血糖检测";
		}

	},
	YSQK {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "饮食情况";
		}

	},
	HLDY {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "护理等级";
		}

	},
	HLLX {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "护理类型";
		}

	},
	HLJG {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "护理观察结果";
		}

	},
	HLMC {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "护理观察名称";
		}

	},
	CZMC {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "护理操作名称";
		}

	},
	CZJG {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "护理操作结果";
		}

	},
	HXJ {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "呼吸机监护项目";
		}

	}

}
