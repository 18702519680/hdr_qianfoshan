package com.goodwill.cda.hlht.enums;

/**
 * 妇产 Enum 包括 ： 待产记录、阴道分娩记录、剖宫产记录
 * 
 * @Description 类描述：
 * @author liuzhi
 * @Date 2017年11月10日
 * @modify 修改记录：
 *
 */
public enum ParturitionEnum implements ICodeSystemEnum {
	/**
	 * 待产日期时间
	 */
	DCRQSJ {

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
			return "DE06.00.197.00";
		}

		@Override
		public String getLabel() {
			return "待产日期时间";
		}
	},
	/**
	 * 孕次
	 */
	YC {
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
			return "DE04.01.108.00";
		}

		@Override
		public String getLabel() {
			return "孕次";
		}
	},
	/**
	 * 产次
	 */
	CC {
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
			return "DE02.10.002.00";
		}

		@Override
		public String getLabel() {
			return "产次";
		}
	},
	/**
	 * 末次月经日期
	 */
	MCYJQ {

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
			return "DE02.10.051.00";
		}

		@Override
		public String getLabel() {
			return "末次月经日期";
		}
	},
	/**
	 * 受孕形式代码
	 */
	SYXSDM {

		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.2.44";
		}

		@Override
		public String getCodeSystemName() {
			return "受孕形式代码表";
		}

		@Override
		public String getCode() {
			return "DE06.00.261.00";
		}

		@Override
		public String getLabel() {
			return "受孕形式代码";
		}
	},
	/**
	 * 预产期
	 */
	YCQ {

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
			return "DE05.10.098.00";
		}

		@Override
		public String getLabel() {
			return "预产期";
		}
	},
	/**
	 * 产前检查标志
	 */
	CQJCBZ {
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
			return "DE04.10.013.00";
		}

		@Override
		public String getLabel() {
			return "产前检查标志";
		}
	},
	/**
	 * 产前检查异常情况
	 */
	CQJCYCQK {
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
			return "DE05.10.161.00";
		}

		@Override
		public String getLabel() {
			return "产前检查异常情况";
		}
	},
	/**
	 * 此次妊娠特殊情况
	 */
	CCRSTSQK {
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
			return "DE05.10.070.00";
		}

		@Override
		public String getLabel() {
			return "此次妊娠特殊情况";
		}
	},
	/**
	 * 孕前体重（kg）
	 */
	YQTZ {
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
			return "DE04.10.188.00";
		}

		@Override
		public String getLabel() {
			return "孕前体重（kg）";
		}
	},
	/**
	 * 身高（cm）
	 */
	SG {
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
			return "DE04.10.167.00";
		}

		@Override
		public String getLabel() {
			return "身高（cm）";
		}
	},
	/**
	 * 产前体重（kg）
	 */
	CQTZ {
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
			return "DE04.10.188.00";
		}

		@Override
		public String getLabel() {
			return "产前体重（kg）";
		}
	},
	/**
	 * 收缩压
	 */
	SSY {
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
			return "DE04.10.174.00";
		}

		@Override
		public String getLabel() {
			return "收缩压";
		}
	},
	/**
	 * 舒张压
	 */
	SZY {
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
			return "DE04.10.176.00";
		}

		@Override
		public String getLabel() {
			return "舒张压";
		}
	},
	/**
	 * 体温（℃）
	 */
	TW {
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
			return "DE04.10.186.00";
		}

		@Override
		public String getLabel() {
			return "体温（℃）";
		}
	},
	/**
	 * 脉率（次/min）
	 */
	ML {
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
			return "DE04.10.118.00";
		}

		@Override
		public String getLabel() {
			return "脉率（次/min）";
		}
	},
	/**
	 * 呼吸频率（次/min）
	 */
	HXPL {
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
			return "DE04.10.081.00";
		}

		@Override
		public String getLabel() {
			return "呼吸频率（次/min）";
		}
	},
	/**
	 * 既往史
	 */
	JWS {
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
			return "DE02.10.099.00";
		}

		@Override
		public String getLabel() {
			return "既往史";
		}
	},
	/**
	 * 手术史
	 */
	SSS {
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
			return "DE02.10.061.00";
		}

		@Override
		public String getLabel() {
			return "手术史";
		}
	},
	/**
	 * 既往孕产史
	 */
	JWYCS {
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
			return "DE02.10.098.00";
		}

		@Override
		public String getLabel() {
			return "既往孕产史";
		}
	},
	/**
	 * 宫底高度（cm）
	 */
	GDGD {
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
			return "DE04.10.067.00";
		}

		@Override
		public String getLabel() {
			return "宫底高度（cm）";
		}
	},
	/**
	 * 腹围（cm）
	 */
	FW {
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
			return "DE04.10.052.00";
		}

		@Override
		public String getLabel() {
			return "腹围（cm）";
		}
	},
	/**
	 * 胎方位代码
	 */
	TFWDM {
		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.1.106";
		}

		@Override
		public String getCodeSystemName() {
			return "胎方位代码表";
		}

		@Override
		public String getCode() {
			return "DE05.01.044.00";
		}

		@Override
		public String getLabel() {
			return "胎方位代码";
		}
	},
	/**
	 * 胎心率（次/min）
	 */
	TXL {
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
			return "DE04.10.183.00";
		}

		@Override
		public String getLabel() {
			return "胎心率（次/min）";
		}
	},
	/**
	 * 头位难产情况的评估
	 */
	TWNCQKPG {
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
			return "DE05.10.135.00";
		}

		@Override
		public String getLabel() {
			return "头位难产情况的评估";
		}
	},
	/**
	 * 出口横径（cm）
	 */
	CKHJ {
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
			return "DE04.10.247.00";
		}

		@Override
		public String getLabel() {
			return "出口横径（cm）";
		}
	},
	/**
	 * 骶耻外径（cm）
	 */
	DCWJ {
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
			return "DE04.10.175.00";
		}

		@Override
		public String getLabel() {
			return "骶耻外径（cm）";
		}
	},
	/**
	 * 坐骨结节间径（cm）
	 */
	ZGJJJJ {
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
			return "DE04.10.239.00";
		}

		@Override
		public String getLabel() {
			return "坐骨结节间径（cm）";
		}
	},
	/**
	 * 宫缩情况
	 */
	GSQK {
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
			return "DE04.10.245.00";
		}

		@Override
		public String getLabel() {
			return "宫缩情况";
		}
	},
	/**
	 * 宫颈厚度
	 */
	GJHD {
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
			return "DE04.10.248.00";
		}

		@Override
		public String getLabel() {
			return "宫颈厚度";
		}
	},
	/**
	 * 宫口情况
	 */
	GKQK {
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
			return "DE04.10.265.00";
		}

		@Override
		public String getLabel() {
			return "宫口情况";
		}
	},
	/**
	 * 胎膜情况代码
	 */
	TMQKDM {
		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.2.45";
		}

		@Override
		public String getCodeSystemName() {
			return "胎膜情况代码表";
		}

		@Override
		public String getCode() {
			return "DE05.10.155.00";
		}

		@Override
		public String getLabel() {
			return "胎膜情况代码";
		}
	},
	/**
	 * 破膜方式代码
	 */
	PMFSDM {
		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.2.46";
		}

		@Override
		public String getCodeSystemName() {
			return "破膜方式代码表";
		}

		@Override
		public String getCode() {
			return "DE04.10.256.00";
		}

		@Override
		public String getLabel() {
			return "破膜方式代码";
		}
	},
	/**
	 * 先露位置
	 */
	XLWZ {
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
			return "DE04.10.262.00";
		}

		@Override
		public String getLabel() {
			return "先露位置";
		}
	},
	/**
	 * 羊水情况
	 */
	YSQK {
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
			return "DE04.30.062.00";
		}

		@Override
		public String getLabel() {
			return "羊水情况";
		}
	},
	/**
	 * 膀胱充盈标志
	 */
	PGCYBZ {
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
			return "DE04.10.257.00";
		}

		@Override
		public String getLabel() {
			return "膀胱充盈标志";
		}
	},
	/**
	 * 肠胀气标志
	 */
	CZQBZ {
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
			return "DE04.01.123.00";
		}

		@Override
		public String getLabel() {
			return "肠胀气标志";
		}
	},
	/**
	 * 检查方式代码
	 */
	JCFSDM {
		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.2.47";
		}

		@Override
		public String getCodeSystemName() {
			return "检查方式代码表";
		}

		@Override
		public String getCode() {
			return "DE04.50.139.00";
		}

		@Override
		public String getLabel() {
			return "检查方式代码";
		}
	},
	/**
	 * 处置计划
	 */
	CZJH {
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
			return "DE05.10.014.00";
		}

		@Override
		public String getLabel() {
			return "处置计划";
		}
	},
	/**
	 * 计划选取的分娩方式
	 */
	JHXQDFWFS {
		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.1.10";
		}

		@Override
		public String getCodeSystemName() {
			return "分娩方式代码表";
		}

		@Override
		public String getCode() {
			return "DE02.10.011.00";
		}

		@Override
		public String getLabel() {
			return "计划选取的分娩方式";
		}
	},
	/**
	 * 产程记录日期时间
	 */
	CCJLRQSJ {
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
			return "DE09.00.053.00";
		}

		@Override
		public String getLabel() {
			return "产程记录日期时间";
		}
	},
	/**
	 * 产程经过
	 */
	CCJG {
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
			return "DE06.00.190.00";
		}

		@Override
		public String getLabel() {
			return "产程经过";
		}
	},
	/**
	 * 急诊留观病程记录
	 */
	JZLGBCJL {

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
			return "DE06.00.181.00";
		}

		@Override
		public String getLabel() {
			return "急诊留观病程记录";
		}

	},
	/**
	 * 手术操作名称
	 */
	SSCZMC {

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
			return "DE06.00.094.00";
		}

		@Override
		public String getLabel() {
			return "手术及操作名称";
		}

	},
	/**
	 * 介入物名称
	 */
	JRWMC {

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
			return "DE08.50.037.00";
		}

		@Override
		public String getLabel() {
			return "介入物名称";
		}

	},
	/**
	 * 操作方法
	 */
	CZFF {
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
			return "DE06.00.251.00";
		}

		@Override
		public String getLabel() {
			return "操作方法";
		}
	},
	/**
	 * 操作次数
	 */
	CZCS {

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
			return "DE06.00.250.00";
		}

		@Override
		public String getLabel() {
			return "操作次数";
		}

	},
	/**
	 * 抢救措施
	 */
	QJCS {

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
			return "DE06.00.094.00";
		}

		@Override
		public String getLabel() {
			return "抢救措施";
		}

	},
	/**
	 * 抢救开始日期时间
	 */
	QJKSRQSJ {

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
			return "DE06.00.221.00";
		}

		@Override
		public String getLabel() {
			return "抢救开始日期时间";
		}

	},
	/**
	 * 抢救结束日期时间
	 */
	QJJSRQSJ {

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
			return "DE06.00.218.00";
		}

		@Override
		public String getLabel() {
			return "抢救结束日期时间";
		}

	},
	/**
	 * 检查/检验结果
	 */
	JCJYJG {

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
			return "DE04.30.009.00";
		}

		@Override
		public String getLabel() {
			return "检查/检验结果";
		}

	},
	/**
	 * 检查/检验项目名称
	 */
	JCJYXMMC {

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
			return "DE04.30.020.00";
		}

		@Override
		public String getLabel() {
			return "检查/检验项目名称";
		}

	},
	/**
	 * 检查/检验定量结果
	 */
	JCJYDLJG {

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
			return "DE04.30.015.00";
		}

		@Override
		public String getLabel() {
			return "检查/检验定量结果";
		}

	},
	/**
	 * 检查/检验结果代码
	 */
	JCJYJGDM {

		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.2.38";
		}

		@Override
		public String getCodeSystemName() {
			return "检查/检验结果代码表";
		}

		@Override
		public String getCode() {
			return "DE04.30.017.00";
		}

		@Override
		public String getLabel() {
			return "检查/检验结果代码";
		}

	},
	/**
	 * 疾病诊断编码
	 */
	JBZDBM {

		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.3.11";
		}

		@Override
		public String getCodeSystemName() {
			return "ICD-10";
		}

		@Override
		public String getCode() {
			return "DE05.01.024.00";
		}

		@Override
		public String getLabel() {
			return "疾病诊断编码";
		}

	},
	/**
	 * 手术及操作目标部位名称
	 */
	SSJCZMBBWMC {

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
			return "DE06.00.093.00";
		}

		@Override
		public String getLabel() {
			return "手术及操作目标部位名称";
		}

	},
	/**
	 * 患者去向代码
	 */
	HZQXDM {

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
			return "DE06.00.185.00";
		}

		@Override
		public String getLabel() {
			return "患者去向代码";
		}

	},
	/**
	 * 标本状态
	 */
	BBZT {

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
			return "DE04.50.135.00";
		}

		@Override
		public String getLabel() {
			return "标本状态";
		}

	},
	/**
	 * 检查定量结果计量单位
	 */
	JCDLJGJLDW {

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
			return "DE04.30.016.00";
		}

		@Override
		public String getLabel() {
			return "检查定量结果计量单位";
		}

	},
	/**
	 * 检验报告结果
	 */
	JYBGJG {
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
			return "DE04.50.130.00";
		}

		@Override
		public String getLabel() {
			return "检验报告结果";
		}

	},
	/**
	 * 检验报告科室
	 */
	JYBGKS {

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
			return "DE08.10.026.00";
		}

		@Override
		public String getLabel() {
			return "检验报告科室";
		}

	},
	/**
	 * 检验报告机构名称
	 */
	JYBGJGMC {

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
			return "DE08.10.013.00";
		}

		@Override
		public String getLabel() {
			return "检验报告机构名称";
		}

	},
	/**
	 * 检验报告备注
	 */
	JYBGBZ {

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
			return "DE06.00.179.00";
		}

		@Override
		public String getLabel() {
			return "检验报告备注";
		}

	},
	/**
	 * 初诊标志
	 */
	CZBZ {

		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.2.39";
		}

		@Override
		public String getCodeSystemName() {
			return "初诊标志代码表";
		}

		@Override
		public String getCode() {
			return "DE06.00.196.00";
		}

		@Override
		public String getLabel() {
			return "初诊标志";
		}

	},
	/**
	 * 初诊标志代码
	 */
	CZBZDM {

		@Override
		public String getCodeSystem() {
			return "2.16.156.10011.2.3.2.39";
		}

		@Override
		public String getCodeSystemName() {
			return "初诊标志代码表";
		}

		@Override
		public String getCode() {
			return "DE06.00.196.00";
		}

		@Override
		public String getLabel() {
			return "初诊标志代码";
		}

	}

}
