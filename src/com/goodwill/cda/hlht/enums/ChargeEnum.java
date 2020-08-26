package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;
/**
 * 中药和西药处方的枚举
 * @Date 2017年11月2日
 * @author liuzhi
 */
public enum ChargeEnum implements EnumType{
	
	ZYCF{

		@Override
		public String getCode() {
			return "01";
		}

		@Override
		public String getLabel() {
			return "中药处方";
		}
		
	},
	
	XYCH{

		@Override
		public String getCode() {
			return "02";
		}
		@Override
		public String getLabel() {
			return "西药处方";
		}
		
	},
	
	YPDICT{
		@Override
		public String getCode() {
			return "1.2.156.112636.1.1.2.1.4.12";
		}

		@Override
		public String getLabel() {
			return "药品字典";
		}
		
	}
	
}
