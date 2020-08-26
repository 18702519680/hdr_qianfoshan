package com.goodwill.cda.hlht.service.Impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.goodwill.cda.hlht.cda.REPORT01;
import com.goodwill.cda.hlht.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	protected static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

	public static void main(String[] args) throws ParseException {
		ReportServiceImpl service = new ReportServiceImpl();
		service.genernateReport("2020-06-07","2020-06-12",5);
	}

	/**
	 * 上报
	 */
	public void genernateReport(String startDate, String endDate, int threadCount) {
		logger.info("===开始时间==" + startDate + "，====结束时间==" + endDate);
			genReport(startDate, endDate, threadCount);
	}
	



	/**
	 * 生成上报
	 * 
	 * @throws ParseException
	 */
	private void genReport(String startDate, String endDate, int threadCount) {
			//先根据开始时间和结束时间，查询上报信息
			//编目时间 为 开始时间 ，和 结束时间 
		
		//患者基本信息
		REPORT01.postAllPatientBasicInfo(startDate,endDate);

	}
	
	

		
}
