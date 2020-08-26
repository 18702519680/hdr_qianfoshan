package com.goodwill.cda.hlht.enums;

import com.goodwill.core.enums.EnumType;

public enum PhyExamEnum implements EnumType {
	/**
	 * 一般状况检查结果
	 */
	YBZKJC {

		@Override
		public String getCode() {
			return "DE04.10.219.00";
		}

		@Override
		public String getLabel() {
			return "一般状况检查结果";
		}

	},
	PFHNM {

		@Override
		public String getCode() {
			return "DE04.10.126.00";
		}

		@Override
		public String getLabel() {
			return "皮肤和黏膜检查结果";
		}

	},
	QSQBLBJ {

		@Override
		public String getCode() {
			return "DE04.10.114.00";
		}

		@Override
		public String getLabel() {
			return "全身浅表淋巴结检查结果";
		}

	},
	TBJQG {

		@Override
		public String getCode() {
			return "DE04.10.261.00";
		}

		@Override
		public String getLabel() {
			return "头部及其器官检查结果";
		}

	},
	JBJC {

		@Override
		public String getCode() {
			return "DE04.10.255.00";
		}

		@Override
		public String getLabel() {
			return "颈部检查结果";
		}

	},
	XBJC {

		@Override
		public String getCode() {
			return "DE04.10.263.00";
		}

		@Override
		public String getLabel() {
			return "胸部检查结果";
		}

	},
	FBJC {

		@Override
		public String getCode() {
			return "2.16.156.10011.2.2.1";
		}

		@Override
		public String getLabel() {
			return "腹部检查结果";
		}

	},
	GMZJ {

		@Override
		public String getCode() {
			return "DE04.10.065.00";
		}

		@Override
		public String getLabel() {
			return "肛门指诊检查结果描述";
		}

	},
	WSZQJC {

		@Override
		public String getCode() {
			return "DE04.10.195.00";
		}

		@Override
		public String getLabel() {
			return "外生殖器检查结果";
		}

	},
	JZJC {

		@Override
		public String getCode() {
			return "DE04.10.093.00";
		}

		@Override
		public String getLabel() {
			return "脊柱检查结果";
		}

	},
	SZJC {

		@Override
		public String getCode() {
			return "DE04.10.179.00";
		}

		@Override
		public String getLabel() {
			return "四肢检查结果";
		}

	},
	SJXTJC {

		@Override
		public String getCode() {
			return "DE05.10.149.00";
		}

		@Override
		public String getLabel() {
			return "神经系统检查结果";
		}

	},
	ZKQK {

		@Override
		public String getCode() {
			return "DE08.10.061.00";
		}

		@Override
		public String getLabel() {
			return "专科情况";
		}

	}
}
