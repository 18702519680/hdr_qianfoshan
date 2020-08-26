package com.goodwill.cda.hlht.enums;

/**
 * @Description 类描述：注意事项的枚举
 * @author liuzhi
 * @Date 2017年11月5日
 * @modify 修改记录：
 *
 */
public enum AnnouncementsEnum implements ICodeSystemEnum {
	/**
	 * 注意事项的code 和 displayName
	 */
	ZYSX {

		@Override
		public String getCode() {
			return "DE09.00.119.00";
		}

		@Override
		public String getLabel() {
			return "注意事项";
		}

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}
	},
	/**
	 * 手术要点的code 和displayName
	 */
	SSYD {

		@Override
		public String getCode() {
			return "DE06.00.254.00";
		}

		@Override
		public String getLabel() {
			return "手术要点";
		}

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}
	},
	/**
	 * 术前准备的code 和 displayName
	 */
	SQZB {

		@Override
		public String getCode() {
			return "DE06.00.271.00";
		}

		@Override
		public String getLabel() {
			return "术前准备";
		}

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}
	},
	/**
	 * 诊疗计划
	 */
	ZLJH {
		@Override
		public String getCode() {
			return "DE05.01.025.00";
		}

		@Override
		public String getLabel() {
			return "诊疗计划";
		}

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}
	},
	/**
	 * 法治法则
	 */
	FZFZ {
		@Override
		public String getCode() {
			return "DE06.00.300.00";
		}

		@Override
		public String getLabel() {
			return "法治法则";
		}

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}
	},
	IN_VISIT_TYPE {

		@Override
		public String getCodeSystem() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCodeSystemName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "02";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "住院";
		}

	}
}
