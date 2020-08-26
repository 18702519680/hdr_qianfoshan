package com.goodwill.cda.quartz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SimpleTrigger;
import org.springframework.stereotype.Service;

/**
 * @Description
 * 类描述：定时执行CDA生成任务
 * @author jibin
 * @Date 2017年10月12日
 * @modify
 * 修改记录：
 */
@Service
public class DmsTimedTaskJob implements ServletContextListener {
	@SuppressWarnings("unused")
	private static final SimpleTrigger CronTrigger = null;
	//private static String CONFIG_FILE_NAME = "hlht.properties";

	public DmsTimedTaskJob() {
		try {
			addJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			addJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addJob() throws Exception {
		/*//通过SchedulerFactory来获取一个调度器
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();

			//引进作业程序
			JobDetail jobDetail = new JobDetail("jobDetail-s1", "jobDetailGroup-s1", CDATask.class);
			//new一个触发器
			//SimpleTrigger simpleTrigger = new SimpleTrigger("simpleTrigger", "triggerGroup-s1");
			CronTrigger trigger = new CronTrigger("Test", null, "0 "
					+ PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "execution_time").split(",")[0] + " "
					+ PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "execution_time").split(",")[1] + " * * ?"); //表示每天9点执行
			//设置作业启动时间
			Calendar cal = Calendar.getInstance();
			trigger.setStartTime(cal.getTime());
			//设置作业执行次数
			//simpleTrigger.setRepeatCount(-1);  //测试时用10，-1表示无限次

			//作业和触发器设置到调度器中
			scheduler.scheduleJob(jobDetail, trigger);

			//启动调度器
			scheduler.start();
		} catch (SchedulerException e) {
			System.out.println("启动定时任务失败！");
		}*/
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			//addJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
