package com.goodwill.cda.hlht.enums;

public enum EmrSectionEnum implements IDeCodeDisplayEnum {

	/**
	 * 现病史
	 * @author peijiping
	 *
	 */
	XBS {
		@Override
		public String getCode() {
			return "10164-2";
		}

		@Override
		public String getLabel() {
			return "HISTORY OF PRESENT ILLNESS";
		}

		@Override
		public String getDeCode() {
			return "DE02.10.071.00";
		}

		@Override
		public String getDeDisplayName() {
			return "现病史";
		}
	},
	/**
	 * 预防免疫史
	 * @author peijiping
	 *
	 */
	YFMYS {
		@Override
		public String getCode() {
			return "11369-6";
		}

		@Override
		public String getLabel() {
			return "HISTORY OF IMMUNIZATIONS";
		}

		@Override
		public String getDeCode() {
			return "DE02.10.101.00";
		}

		@Override
		public String getDeDisplayName() {
			return "预防接种史";
		}
	},
	/**
	 * 过敏史
	 * @author peijiping
	 *
	 */
	GMS {
		@Override
		public String getCode() {
			return "48765-2";
		}

		@Override
		public String getLabel() {
			return "Allergies, adverse reactions, alerts";
		}

		@Override
		public String getDeCode() {
			return null;
		}

		@Override
		public String getDeDisplayName() {
			return "过敏史";
		}
	},
	/**
	 * 主诉
	 * @author peijiping
	 *
	 */
	ZS {
		@Override
		public String getCode() {
			return "11450-4";
		}

		@Override
		public String getLabel() {
			return "PROBLEM LIST";
		}

		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.01.119.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "主诉";
		}
	},
	/**
	 * 既往史
	 * 
	 */
	JWS {
		@Override
		public String getCode() {
			return "11348-0";
		}

		@Override
		public String getLabel() {
			return "HISTORY OF PAST ILLNESS";
		}

		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "既往史";
		}
	},
	/**
	 * 个人史
	 * @author peijiping
	 *
	 */
	GRS {
		@Override
		public String getCode() {
			return "29762-2";
		}

		@Override
		public String getLabel() {
			return "Social history";
		}

		@Override
		public String getDeCode() {
			return "DE02.10.097.00";
		}

		@Override
		public String getDeDisplayName() {
			return "个人史";
		}
	},
	/**
	 * 月经史
	 * @author peijiping
	 *
	 */
	YJS {
		@Override
		public String getCode() {
			return "49033-4";
		}

		@Override
		public String getLabel() {
			return "Menstrual History";
		}

		@Override
		public String getDeCode() {
			return "DE02.10.102.00";
		}

		@Override
		public String getDeDisplayName() {
			return "月经史";
		}
	},
	/**
	 * 家族史
	 * @author peijiping
	 *
	 */
	JZS {
		@Override
		public String getCode() {
			return "10157-6";
		}

		@Override
		public String getLabel() {
			return "HISTORY OF FAMILY MEMBER DISEASES";
		}

		@Override
		public String getDeCode() {
			return "DE02.10.103.00";
		}

		@Override
		public String getDeDisplayName() {
			return "家族史";
		}
	},
	/**
	 * 症状
	 * @author peijiping
	 *
	 */
	ZZ {
		@Override
		public String getCode() {
			return "11450-4";
		}

		@Override
		public String getLabel() {
			return "PROBLEM LIST";
		}

		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	SXS {

		@Override
		public String getDeCode() {
			return "DE02.10.100.00";
		}

		@Override
		public String getDeDisplayName() {
			return "输血史";
		}

		@Override
		public String getCode() {
			return "56836-0";
		}

		@Override
		public String getLabel() {
			return "History of blood transfusion";
		}

	},
	SSS {

		@Override
		public String getDeCode() {
			return "DE02.10.061.00";
		}

		@Override
		public String getDeDisplayName() {
			return "手术史";
		}

		@Override
		public String getCode() {
			return null;
		}

		@Override
		public String getLabel() {
			return null;
		}

	},
	HYS {

		@Override
		public String getDeCode() {
			return "DE02.10.098.00";
		}

		@Override
		public String getDeDisplayName() {
			return null;
		}

		@Override
		public String getCode() {
			return null;
		}

		@Override
		public String getLabel() {
			return "婚育史";
		}

	},
	CRBS {

		@Override
		public String getDeCode() {
			return "DE02.10.008.00";
		}

		@Override
		public String getDeDisplayName() {
			return null;
		}

		@Override
		public String getCode() {
			return null;
		}

		@Override
		public String getLabel() {
			return "传染病史";
		}

	},

	//入院情况
	RYQK {

		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "RYQK";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "入院情况";
		}

	},
	CYQK {

		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "CYQK";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "出院情况";
		}

	},
	CYYZ {

		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "cyyz";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "出院医嘱";
		}

	},
	//疾病史（含外伤）
	JBWSS {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE02.10.026.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "疾病史（含外伤）";
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	//生命体征
	SMTZ {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "8716-3";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "VITAL SIGNS";
		}
	},
	//体格检查
	TGJC {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return "29545-1";
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "PHYSICAL EXAMINATION";
		}
	},
	//体温
	TW {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.186.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "体温（℃）";
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	//脉率
	MB {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.118.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "脉率（次/min）";
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	//呼吸
	HX {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.082.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "呼吸频率（次/min）";
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	//收缩压
	SSY {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.174.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "收缩压";
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	//舒张压
	SZY {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.176.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "舒张压";
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	//身高
	SG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.167.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "身高（cm）";
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	//体重
	TZ {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.188.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "体重（kg）";
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	//一般状况检查结果
	YBZKJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.219.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "一般状况检查结果";
		}
	},
	//皮肤和黏膜检查结果
	PFNMJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.126.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "皮肤和黏膜检查结果";
		}
	},
	//全身浅表淋巴结检查结果
	QSJBLBJJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.114.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "全身浅表淋巴结检查结果";
		}
	},
	//头部及其器官检查结果
	TBJQQGJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.261.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "头部及其器官检查结果";
		}
	},
	//颈部检查结果
	JBJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.255.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "颈部检查结果";
		}
	},
	//胸部检查结果
	XBJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.263.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "胸部检查结果";
		}
	},
	//腹部检查结果
	FBJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.046.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "腹部检查结果";
		}
	},
	//肛门指诊检查结果描述
	GMZZJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.065.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "肛门指诊检查结果描述";
		}
	},
	//外生殖器检查结果
	WSZQJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.195.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "外生殖器检查结果";
		}
	},
	//脊柱检查结果
	JZJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.093.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "脊柱检查结果";
		}
	},
	//四肢检查结果
	SZJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.10.179.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "四肢检查结果";
		}
	},
	//神经系统检查结果
	SJXTJCJG {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE05.10.149.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "神经系统检查结果";
		}
	},
	//专科情况
	ZKQK {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE08.10.061.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "专科情况";
		}
	},
	//辅助检查
	FZJC {
		@Override
		public String getDeCode() {
			// TODO Auto-generated method stub
			return "DE04.30.009.00";
		}

		@Override
		public String getDeDisplayName() {
			// TODO Auto-generated method stub
			return "辅助检查";
		}

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return "辅助检查结果";
		}
	}
}
