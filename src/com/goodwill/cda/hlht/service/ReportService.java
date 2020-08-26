package com.goodwill.cda.hlht.service;

/**
 * CDA插入Dao
 * @author jibin
 *
 */
public interface ReportService {
	/**
	 * 生成上报  可以使用多线程 生成
	 * 如果不使用多线程生成 ，那么 线程数 输入 小于等于1 的数值就可以 
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 * @param startDate   开始时间
	 * @param endDate     结束时间
	 * @param threadCount 线程数
	 */
	public void genernateReport(String startDate, String endDate, int threadCount);

}
