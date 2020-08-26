package com.goodwill.cda.hlht.enums;

/**
 * 用药和用药管理 章节用到的 枚举
 * 
 * @Description 类描述：
 * @author liuzhi
 * @Date 2017年11月5日
 * @modify 修改记录：
 *
 */
public enum MedicineEnum implements ICodeSystemEnum {
	/**
	 * 药物用法代码 及 displayName
	 */
	YWYF {

		@Override
		public String getCode() {
			return "DE06.00.136.00";
		}

		@Override
		public String getLabel() {
			return "药物用法";
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
	 * 药物使用频率 代码及 displayName
	 */
	YWSYPL {

		@Override
		public String getCode() {
			return "DE06.00.133.00";
		}

		@Override
		public String getLabel() {
			return "药物使用频率";
		}

		@Override
		public String getCodeSystem() {

			return "2.16.156.10011.2.3.1.267";
		}

		@Override
		public String getCodeSystemName() {
			return "药物使用频次代码表";
		}

	},
	/**
	 * 药物使用剂量单位 代码及 displayName
	 */
	YWSYJLDW {

		@Override
		public String getCode() {
			return "DE08.50.024.00";
		}

		@Override
		public String getLabel() {
			return "药物使用剂量单位";
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
	 * 药物使用总剂量 代码及 displayName
	 */
	YWSYZJL {

		@Override
		public String getCode() {
			return "DE06.00.135.00";
		}

		@Override
		public String getLabel() {
			return "药物使用总剂量";
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
	 * 中药使用类别代码 代码及 displayName
	 */
	ZYSYLBDM {

		@Override
		public String getCode() {
			return "DE06.00.164.00";
		}

		@Override
		public String getLabel() {
			return "中药使用类别代码";
		}

		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.1.157";
		}

		@Override
		public String getCodeSystemName() {
			return "中药使用类别代码表";
		}

	},
	/**
	 * 药物剂型代码 代码及 displayName
	 */
	YWJXDM {

		@Override
		public String getCode() {
			return "DE08.50.011.00";
		}

		@Override
		public String getLabel() {
			return "药物剂型代码";
		}

		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.1.211";
		}

		@Override
		public String getCodeSystemName() {
			return "药物剂型代码表";
		}
	},
	/**
	 * 术前用药 的代码及displayName
	 */
	SQYY {

		@Override
		public String getCode() {
			return "DE06.00.136.00";
		}

		@Override
		public String getLabel() {
			return "术前用药";
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
	 * 术中用药的 代码 及 displayName
	 */
	SZYY {

		@Override
		public String getCode() {
			return "DE06.00.136.00";
		}

		@Override
		public String getLabel() {
			return "术中用药";
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
	 * 医嘱内容的 代码及displayName
	 */
	YZNR {

		@Override
		public String getCode() {
			return "DE06.00.287.00";
		}

		@Override
		public String getLabel() {
			return "医嘱内容";
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
	 * 中药煎煮方法 的代码及displayName
	 */
	ZYJZFF {

		@Override
		public String getCode() {
			return "DE08.50.047.00";
		}

		@Override
		public String getLabel() {
			return "中药煎煮方法";
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
	 * 中药用药方法 的代码及displayName
	 */
	ZYYYFF {

		@Override
		public String getCode() {
			return "DE06.00.136.00";
		}

		@Override
		public String getLabel() {
			return "中药用药方法";
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
	 * 中药处方医嘱内容 的代码及displayName
	 */
	ZYCFYZNR {

		@Override
		public String getCode() {
			return "DE06.00.287.00";
		}

		@Override
		public String getLabel() {
			return "中药处方医嘱内容";
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
	 * 中药饮片煎煮法 的代码及displayName
	 */
	ZYYPJZF {

		@Override
		public String getCode() {
			return "DE08.50.047.00";
		}

		@Override
		public String getLabel() {
			return "中药饮片煎煮法";
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
	 * 药物规格 的代码及displayName
	 */
	YWGG {

		@Override
		public String getCode() {
			return "DE08.50.043.00";
		}

		@Override
		public String getLabel() {
			return "药物规格";
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
	 * 处方有效天数 的代码及displayName
	 */
	CFYXTS {

		@Override
		public String getCode() {
			return "DE06.00.294.00";
		}

		@Override
		public String getLabel() {
			return "处方有效天数";
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
	 * 处方药品组号 的代码及displayName
	 */
	CFYPZH {

		@Override
		public String getCode() {
			return "DE08.50.056.00";
		}

		@Override
		public String getLabel() {
			return "处方药品组号";
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
	 * 中药饮片处方 的代码及displayName
	 */
	ZYYPCF {

		@Override
		public String getCode() {
			return "DE08.50.049.00";
		}

		@Override
		public String getLabel() {
			return "中药饮片处方";
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
	 * 中药饮片剂数 的代码及displayName
	 */
	ZYYPJS {

		@Override
		public String getCode() {
			return "DE08.50.050.00";
		}

		@Override
		public String getLabel() {
			return "中药饮片剂数";
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
	 * 中药煎煮法 的代码及displayName
	 */
	ZYZJF {

		@Override
		public String getCode() {
			return "DE08.50.047.00";
		}

		@Override
		public String getLabel() {
			return "中药煎煮法";
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
	 * 中药煎煮法 的代码及displayName
	 */
	CFLBDM {

		@Override
		public String getCode() {
			return "DE08.50.032.00";
		}

		@Override
		public String getLabel() {
			return "处方类别代码";
		}

		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.2.40";
		}

		@Override
		public String getCodeSystemName() {
			return "处方类别代码表";
		}

	},
	/**
	 * 处方备注信息 的代码及displayName
	 */
	CFBZXX {

		@Override
		public String getCode() {
			return "DE06.00.179.00";
		}

		@Override
		public String getLabel() {
			return "处方备注信息";
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
	 * 住院病程
	 */
	ZYBC {

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}

		@Override
		public String getCode() {
			return "DE06.00.309.00";
		}

		@Override
		public String getLabel() {
			return "住院病程";
		}

	},
	/**
	 * 辩证论治
	 */
	BZLZ {

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}

		@Override
		public String getCode() {
			return "DE05.10.131.00";
		}

		@Override
		public String getLabel() {
			return "辩证论治";
		}
	},
	/**
	 * 过敏史
	 */
	GMS {

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}

		@Override
		public String getCode() {
			return "DE02.10.022.00";
		}

		@Override
		public String getLabel() {
			return "过敏史";
		}

	},
	/**
	 * 过敏史标志
	 */
	GMSBZ {

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}

		@Override
		public String getCode() {
			return "DE02.10.023.00";
		}

		@Override
		public String getLabel() {
			return "过敏史标志";
		}

	},
	/**
	 * 病情变化情况
	 */
	BQBHQK {

		@Override
		public String getCodeSystem() {
			return null;
		}

		@Override
		public String getCodeSystemName() {
			return null;
		}

		@Override
		public String getCode() {
			return "DE05.10.134.00";
		}

		@Override
		public String getLabel() {
			return "病情变化情况";
		}

	}
}
