package com.goodwill.cda.hlht.gen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.goodwill.cda.hlht.cda.REPORT01;
import com.goodwill.cda.hlht.cda.common.ResponseToMysql;
import com.goodwill.cda.hlht.comm.CommonApplication;
import com.goodwill.cda.hlht.service.ReportService;
import com.goodwill.cda.util.CommonUtils;
import com.goodwill.core.utils.DateUtils;
import com.goodwill.core.utils.PropertiesUtils;

@Service
public class ReportTask implements Job {
	protected static Logger logger = LoggerFactory.getLogger(com.goodwill.cda.hlht.gen.ReportTask.class);
	private static String CONFIG_FILE_NAME = "report.properties";
	@Autowired
	private ReportService reportService;

	/**
	 * ---------------------------------------------------------------- 
	 * 增量定时执行调用的方法
	 * ----------------------------------------------------------------
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

	}

	public void getApplicationContext() {
		ApplicationContext app = CommonApplication.getApplicationContext();
		reportService = app.getBean(ReportService.class);
	}

	public static void main(String[] args) {
		ReportTask reportTask = new ReportTask();
		reportTask.getApplicationContext();
		reportTask.timing();
	}

	/**
	 * 定时 生成CDA的方法 quartz 定时生成
	 * 该方法 生成 一段时间内的上报信息 ,利用多线程 跑任务 
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 */
	public void timing() {
		System.out.println("ccc");
		/*==================多线程============================*/
		//获取自定义线程数  如果没有获取到默认线程数为2
		String thread_count = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "thread_count");
		int threadCount = 2;
		if (StringUtils.isNotBlank(thread_count)) {
			threadCount = Integer.parseInt(thread_count);
		}
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(threadCount);
		try {
			//			String startTime = "2017-12-01";
			//			String endTime = "2018-03-27";
			String startTime = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "intime");
			String endTime = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "outtime");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			List<String> timeList = getBetweenDates(df.parse(startTime), df.parse(endTime));
			int remainder = timeList.size() % 5;
			for (int i = 0; i < timeList.size() - remainder; i += 5) {
				logger.info("======开始时间===" + timeList.get(i) + "===结束时间===" + timeList.get(i + 4));
				String startdate = timeList.get(i);
				String enddate = timeList.get(i + 4);
				newFixedThreadPool.submit(new Thread(new TestThread1(startdate, enddate, reportService), "线程:"
						+ timeList.get(i)));
			}
			if (remainder > 0) {
				logger.info("======最后开始时间===" + timeList.get(timeList.size() - remainder) + "===最后结束时间==="
						+ timeList.get(timeList.size() - 1));
				newFixedThreadPool.submit(new Thread(new TestThread1(timeList.get(timeList.size() - remainder), timeList
						.get(timeList.size() - 1), reportService), "线程:" + timeList.get(timeList.size() - 1)));
			}
		} catch (Exception e) {
			logger.error("=====ReportTask获取时间列表报错=====" + e.getMessage());
			e.printStackTrace();
		} finally {
			newFixedThreadPool.shutdown();
		}
	}

	/**
	 * 增量生成上报  quartz 调用
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 */
	public void execute() {
		String day = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "day");
		int startDay = -7;
		if (StringUtils.isNotBlank(day) && CommonUtils.isNumeric00(day) && day.contains("-")) {
			startDay = Integer.parseInt(day);
		}
		//获取自定义线程数  如果没有获取到默认线程数为2
		String thread_count = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "thread_count");
		int threadCount = 2;
		if (StringUtils.isNotBlank(thread_count)) {
			threadCount = Integer.parseInt(thread_count);
		}
		getreportService(startDay, 0, threadCount);
	}

	
	/**
	 *   quartz 调用
	 * @Description
	 * 方法描述:将错误上报数据重新上报
	 * @return 返回类型： void
	 */
	public static void rePostExecute() {
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		listSumm=ResponseToMysql.getReGenReportData();
		if (listSumm.size()>0) {
			for(int i=0;i<listSumm.size();i++) {
				Map<String, String> map=listSumm.get(i);
					switch(map.get("FUNCTIONNAME")){
					case "patientBasicInfo":
						REPORT01.postAllPatientBasicInfoByrowkey(map.get("ROWKEY"));
						System.out.println("调用完成");
					    break;
					}
			}
		}
		
	}


	/**
	 * 增量生成上报
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 * @param startNO	开始时间往前或往后推的天数
	 * @param endNO		结束时间往前或往后推的天数
	 * @param threadCount	线程数
	 * @param flag      判断门诊或者住院的标志位
	 */
	public void getreportService(int startNO, int endNO, int threadCount) {
		Date today = new Date();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(today);
		startDate.set(Calendar.DATE, startDate.get(Calendar.DATE) + startNO);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(today);
		endDate.set(Calendar.DATE, endDate.get(Calendar.DATE) + endNO);
		String start = DateUtils.getDate(startDate.getTime());
		String end = DateUtils.getDate(endDate.getTime());
		reportService.genernateReport(start,end,threadCount);
	}

	/** 
	 * 计算俩个日期 中间时间
	 * @Description
	 * 方法描述:
	 * @return 返回类型： List<String>
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	private static List<String> getBetweenDates(Date start, Date end) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<String> result = new ArrayList<String>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);
		tempStart.add(Calendar.DAY_OF_YEAR, 1);
		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		while (tempStart.before(tempEnd)) {
			result.add(df.format(tempStart.getTime()));
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}
}

class TestThread1 implements Runnable {
	private String startTime = null;
	private String endTime = null;
	private ReportService reportService;

	public TestThread1(String startTime, String endTime, ReportService reportService) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.reportService = reportService;
	}

	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println("============" + name + "===============");
		System.out.println(this.startTime + "==" + name + "==" + this.endTime);
		Calendar startDate = Calendar.getInstance();
		Date start = DateUtils.convertStringToDate("yyyy-MM-dd", startTime);
		startDate.setTime(start);
		Calendar endDate = Calendar.getInstance();
		Date end = DateUtils.convertStringToDate("yyyy-MM-dd", endTime);
		endDate.setTime(end);
		endDate.set(Calendar.DATE, endDate.get(Calendar.DATE) + 1);
		reportService.genernateReport(DateUtils.getDate(startDate.getTime()), DateUtils.getDate(endDate.getTime()), 0);
		System.out.println("============" + name + "休眠结束===========");
	}
}
