package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

/**
 * 
 * @Description 类描述：病历文书章节编码枚举
 * @author malongbiao
 * @Date 2017年11月8日
 * @modify 修改记录：
 *
 */
public enum MRDGCodeEnum implements EnumType {

	ZS {
		@Override
		public String getCode() {
			return "S.01";
		}

		@Override
		public String getLabel() {
			return "主诉";
		}
	},

	FZJC {
		@Override
		public String getCode() {
			return "S.05";
		}

		@Override
		public String getLabel() {
			return "辅助检查";
		}
	},
	XBS {

		@Override
		public String getCode() {
			return "S.03";
		}

		@Override
		public String getLabel() {
			return "现病史";
		}

	},
	JWS {

		@Override
		public String getCode() {
			return "S.04";
		}

		@Override
		public String getLabel() {
			return "既往史";
		}

	},
	ZYSZ {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
//			return "S.16";
			return "";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "中医四诊";
		}

	},
	ZLJH {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			//return "S.12";
			return "S.16";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "诊疗计划";
		}

	},
	SWYY {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "SWYY";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "死亡原因";
		}

	},
	GMS {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "DE02.10.022.00";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "过敏史";
		}

	},
	RYQK {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "入院情况";
		}

	},
	GMSS {

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "S.04.006";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "过敏史";
		}

	},
	CYQK {
		@Override
		public String getCode() {
			return "";
		}

		@Override
		public String getLabel() {
			return "出院情况";
		}
	},
	BLTD {
		@Override
		public String getCode() {
			return "S.13";
		}

		@Override
		public String getLabel() {
			return "病例特点";
		}
	},
	ZDYJ {
		@Override
		public String getCode() {
			return "S.14";
		}

		@Override
		public String getLabel() {
			return "诊断依据";
		}
	},
	JBZD {
		@Override
		public String getCode() {
			return "S.15";
		}

		@Override
		public String getLabel() {
			return "鉴别诊断";
		}
	}
}