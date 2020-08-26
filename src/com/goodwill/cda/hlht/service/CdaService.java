package com.goodwill.cda.hlht.service;

/**
 * CDA插入Dao
 * @author jibin
 *
 */
public interface CdaService {
	/**
	 * 生成CDA 住院 和 门诊  可以使用多线程 生成
	 * 如果不使用多线程生成 ，那么 线程数 输入 小于等于1 的数值就可以 
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 * @param startDate   开始时间
	 * @param endDate     结束时间
	 * @param threadCount 线程数
	 * @param flag  区分门诊和住院
	 */
	public void genernateCDA(String startDate, String endDate, int threadCount, String flag);

	public void runOneCDA(String patientID, String visitID);
}
