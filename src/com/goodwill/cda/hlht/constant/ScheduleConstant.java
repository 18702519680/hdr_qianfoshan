package com.goodwill.cda.hlht.constant;

/**
 * CDA生成情况统计
 * @Description
 * 类描述：
 * @author liuzhi
 * @Date 2017年12月11日
 * @modify
 * 修改记录：
 *
 */
public class ScheduleConstant {
	private ScheduleConstant() {
		throw new RuntimeException("不容许实例化！");
	}

	private static Integer OutPatientTotal = 1;

	private static Integer InSummaryTotal = 1;

	private static Integer OutPatientSchedule = 0;

	private static Integer InSummarySchedule = 0;
	//标记是否有线程在 执行 生成CDA文档
	private static Integer flag = 0;//0  表示没有线程在执行，1 表示有线程在执行

	/**
	 * 初始化数据
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 */
	public static void init() {
		OutPatientTotal = 1;
		InSummaryTotal = 1;
		OutPatientSchedule = 0;
		InSummarySchedule = 0;
		flag = 1;
	}

	public static void setOutPatientTotal(Integer total) {
		if (total == 0) {
			total = 1;
		}
		OutPatientTotal = total;
	}

	public static Integer getOutPatientTotal() {
		return new Integer(OutPatientTotal);
	}

	public static void setInSummaryTotal(Integer total) {
		if (total == 0) {
			total = 1;
		}
		InSummaryTotal = total;
	}

	public static Integer getInSummaryTotal() {
		return new Integer(InSummaryTotal);
	}

	public static void setOutPatientSchedule(Integer schedule) {
		OutPatientSchedule = schedule;
	}

	public static Integer getOutPatientSchedule() {
		return new Integer(OutPatientSchedule);
	}

	public static void setInSummarySchedule(Integer schedule) {
		InSummarySchedule = schedule;
	}

	public static Integer getInSummarySchedule() {
		return new Integer(InSummarySchedule);
	}

	public static Integer getFlag() {
		return flag;
	}

	public static void setFlag(Integer flag) {
		ScheduleConstant.flag = flag;
	}

}
