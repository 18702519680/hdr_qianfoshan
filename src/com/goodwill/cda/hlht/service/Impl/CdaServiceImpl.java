package com.goodwill.cda.hlht.service.Impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.goodwill.cda.entity.CdaIndexEntity;
import com.goodwill.cda.hlht.cda.REPORT01;
import com.goodwill.cda.hlht.comm.CommonApplication;
import com.goodwill.cda.hlht.constant.ScheduleConstant;
import com.goodwill.cda.hlht.dao.CdaDao;
import com.goodwill.cda.hlht.service.CdaService;
import com.goodwill.cda.hlht.service.Impl.CdaServiceImpl.VisitTypeEnum;
import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtils;
import com.goodwill.cda.util.HiveUtil;
import com.goodwill.cda.util.Xmlutil;
import com.goodwill.core.enums.EnumType;
import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.core.utils.DateUtils;
import com.goodwill.core.utils.PropertiesUtils;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * CDA插入Dao
 * @author jibin
 *
 */
@Service
public class CdaServiceImpl implements CdaService {
	private static String CONFIG_FILE_NAME = "hlht.properties";
	protected static Logger logger = LoggerFactory.getLogger(CdaServiceImpl.class);
	@Autowired
	CdaDao cdaDao;

	public static void main(String[] args) throws ParseException {
		CdaServiceImpl service = new CdaServiceImpl();
		service.getApplicationContext();
		service.deleteHiveCda();
//		service.hiveToMysql("", "");
		//service.createInpSummaryCDA();
	}

	/**
	 * 生成CDA
	 */
	public void genernateCDA(String startDate, String endDate, int threadCount, String flag) {
		
		//患者基本信息
		REPORT01.postAllPatientBasicInfo(startDate,endDate);
	}

	/**
	 * 获取时间  时间格式为 yyyy-MM-dd,根据 传递的days 往前或者往后 推 多少天的时间
	 * @Description
	 * 方法描述:
	 * @return 返回类型： String
	 * @return
	 */
	private String getTimeString(String date, int days) {
		Calendar sDate = Calendar.getInstance();
		Date startToDate = DateUtils.convertStringToDate("yyyy-MM-dd", date);
		sDate.setTime(startToDate);
		sDate.set(Calendar.DATE, sDate.get(Calendar.DATE) + days);
		return DateUtils.getDate(sDate.getTime());
	}

	/**
	 * 生成住院患者CDA
	 * 
	 * @throws ParseException
	 */
	private void genInpCDA(String startDate, String endDate, int threadCount) {
		logger.info("=================开始生成住院CDA=====================" + threadCount);
		//判断 1 是查询hbase ，2 是查询hive
		String falg = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "falg");
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		if (falg.equals("1")) {
			//先根据开始时间和结束时间，查询住院病案首页信息
			//编目时间 为 开始时间 ，和 结束时间 
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("CATALOG_TIME", "STRING", MatchType.GE.getOperation(), startDate
					+ " 00:00:00"));
			filters.add(new PropertyFilter("CATALOG_TIME", "STRING", MatchType.LE.getOperation(), endDate + " 23:59:59"));
			String start = getTimeString(startDate, -30);
			start = start.replaceAll("-", "");
			String end = endDate.replaceAll("-", "");
			/**
			 * 在HDR_INP_SUMMARY_DATE表中 rowkey 是按照出院时间 来开头的，而每天 都需要 有归档编目 ，所以 查询 过滤 是按照归档日期查询的
			 * 当时归档的时候 是归档 开始时间 往前推一个月的出院的人，因此 查询 的出院 日期 为 ： 开始时间 往前 推 30天
			 */
			listSumm = Xmlutil.formatList(HbaseCURDUtils.findByRowkey("HDR_INP_SUMMARY_DATE", start, end, filters));
		} else {
			Connection conns = HiveUtil.createHiveConnection();
			ResultSet res = null;
			Statement stmt = null;
			String sql = "select * from hdr_inp_summary_w t where t.discharge_time>='" + startDate
					+ " 00:00:00' and t.discharge_time<='" + endDate + " 23:59:59'";
			try {
				stmt = conns.createStatement();
				res = stmt.executeQuery(sql);
				while (res.next()) {
					String pid = res.getString("IN_PATIENT_ID");
					String vid = res.getString("VISIT_ID");
					Map<String, String> map = new HashMap<String, String>();
					map.put("IN_PATIENT_ID", pid);
					map.put("VISIT_ID", vid);
					listSumm.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("=================需要生成住院CDA的人数为：" + listSumm.size() + "=====================");
		//add by  liuzhi  2017-12-11  增加 设置 住院 需要生成CDA的人数 
		ScheduleConstant.setInSummaryTotal(listSumm.size());
		if (listSumm.isEmpty()) {
			ScheduleConstant.setInSummarySchedule(1);
		} else {
			startThreadToCreateCDA(listSumm, threadCount, VisitTypeEnum.ZY);
		}
	}

	/**
	 * 生成门诊患者CDA
	 * 
	 * @throws ParseException
	 */
	private void genOutpCDA(String startDate, String endDate, int threadCount) {
		logger.info("=================开始生成门诊的CDA文档=====================" + threadCount);
		//判断 1 是查询hbase ，2 是查询hive
		String falg = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "falg");
		List<Map<String, String>> listVisit = new ArrayList<Map<String, String>>();
		if (falg.equals("1")) {
			List<PropertyFilter> filters1 = new ArrayList<PropertyFilter>();
			String start = startDate.replaceAll("-", "");
			String end = endDate.replaceAll("-", "");
			// 开始时间向前推一天 
			List<Map<String, String>> list = HbaseCURDUtils.findByRowkey("HDR_OUT_VISIT_DATE", start, end, filters1);
			listVisit = Xmlutil.formatList(list);
		} else {
			Connection conns = HiveUtil.createHiveConnection();
			ResultSet res = null;
			Statement stmt = null;
			String sql = "select * from hdr_out_visit_w t where t.visit_time>='" + startDate
					+ " 00:00:00' and  t.visit_time<='" + endDate + " 23:59:59'";
			try {
				stmt = conns.createStatement();
				res = stmt.executeQuery(sql);
				while (res.next()) {
					String pid = res.getString("OUT_PATIENT_ID");
					String vid = res.getString("VISIT_ID");
					Map<String, String> map = new HashMap<String, String>();
					map.put("OUT_PATIENT_ID", pid);
					map.put("VISIT_ID", vid);
					listVisit.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("======需要生成门诊CDA的总数======" + listVisit.size());
		//add by  liuzhi  2017-12-11  增 加设置门诊需要生成的人数
		ScheduleConstant.setOutPatientTotal(listVisit.size());
		if (listVisit.isEmpty()) {
			ScheduleConstant.setOutPatientSchedule(1);
		} else {
			startThreadToCreateCDA(listVisit, threadCount, VisitTypeEnum.MZ);
		}
	}

	/**
	 * 启动线程开始生成CDA
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 * @param personList
	 * @param threadCount
	 * @param visitType
	 */
	public void startThreadToCreateCDA(List<Map<String, String>> personList, int threadCount, VisitTypeEnum visitType) {
		//将查询出的数据 等分 按照线程数等分
		if (threadCount > 1) {
			List<List<Map<String, String>>> listSp = CommonUtils.splitList(personList, threadCount * 5);
			long start = System.currentTimeMillis();
			if (visitType.getCode().equals(VisitTypeEnum.ZY.getCode())) {
				logger.info("=================生成住院====CDA开始=====================");
			} else if (visitType.getCode().equals(VisitTypeEnum.MZ.getCode())) {
				logger.info("=================生成门诊====CDA开始=====================");
			}
			ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(threadCount);
			CountDownLatch cdl = new CountDownLatch(listSp.size());
			try {
				for (int i = 0; i < listSp.size(); i++) {
					try {
						newFixedThreadPool.submit(new Thread(new CDAThread(listSp.get(i), visitType, cdl), "生成"
								+ visitType.getLabel() + "CDA线程" + i));
					} catch (Exception e) {
						logger.error("=====生成" + visitType.getLabel() + "CDA 启动线程 报错 =====" + e.getMessage());
						logger.error("启动的线程为 ：" + i + "，没有执行的人 为：" + listSp.get(i));
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				logger.error("=====CDATask获取时间列表报错=====" + e.getMessage());
				e.printStackTrace();
			} finally {
				newFixedThreadPool.shutdown();
				try {
					cdl.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				long end = System.currentTimeMillis();
				if (visitType.getCode().equals(VisitTypeEnum.ZY.getCode())) {
					logger.info("=================统计住院====CDA总执行时间====================="
							+ (((end - start) / 1000) / 60) + "分钟");
				} else if (visitType.getCode().equals(VisitTypeEnum.MZ.getCode())) {
					logger.info("=================统计门诊====CDA总执行时间====================="
							+ (((end - start) / 1000) / 60) + "分钟");
				}
			}
		} else {
			long start = System.currentTimeMillis();
			logger.info("=================生成CDA开始=====================");
			CreateCDA cs = new CreateCDA();
			cs.create(personList, visitType.getCode());
			long end = System.currentTimeMillis();
			logger.info("=================统计CDA总执行时间=====================" + (((end - start) / 1000) / 60) + "分钟");
		}
	}

	/**
	 *  将生成 CDA文档 统计结果插入mysql 的数据库
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 */
	private void hiveToMysql(String startTime, String endTime) {
		logger.info("===============初始化 mysql表hdr_cda_count_new1 ，将生成的CDA历史统计数据插入 mysql=====================");
		long start = System.currentTimeMillis();
		StringBuilder sql = new StringBuilder();
		sql.append("select Substr(createtime,0,10)as time,count(*) as counts,DocumentCode, DocumentTitle ");
		sql.append("  from hdr_cda_index_ii  ");
		Boolean whereFlag = false;
		if (StringUtils.isNotBlank(startTime)) {
			sql.append(" where Substr(createtime,0,10) >= '" + startTime + "'  ");
			whereFlag = true;
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append((whereFlag ? " and " : " where "));
			sql.append(" Substr(createtime,0,10) <= '" + endTime + "'  ");
		}
		sql.append("  group by Substr(createtime,0,10),DocumentCode,DocumentTitle ");
		Connection conns = HiveUtil.createHiveConnection();
		ResultSet res = null;
		Statement stmt = null;
		try {
			if (conns == null) {
				for (int i = 0; i < 3; i++) {
					logger.error("获取hive JDBC链接失败 。。。重新获取。。。");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						logger.error("在获取hiveJDBC链接 过程中线程进行sleep错误。。。");
						e.printStackTrace();
					}
					try {
						conns = HiveUtil.createHiveConnection();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (conns != null) {
						break;
					}
				}
			}
			if (conns == null) {
				logger.error("初始化 mysql表hdr_cda_count ，将生成的CDA历史统计数据插入 mysql 失败");
				return;
			}
			stmt = conns.createStatement();
			res = stmt.executeQuery(sql.toString());
			List<CdaIndexEntity> list = new ArrayList<CdaIndexEntity>();
			while (res.next()) {
				CdaIndexEntity entity = new CdaIndexEntity();
				entity.setDocumentCode(res.getString("DocumentCode"));
				entity.setDocumentCount(res.getString("counts"));
				entity.setDocumentTime(res.getString("time"));
				entity.setDocumentTitle(res.getString("DocumentTitle"));
				list.add(entity);
			}
			//先将表清空，然后将数据插入
			this.cdaDao.deleteCda(startTime, endTime);
			this.cdaDao.saveUpdate(list);
		} catch (SQLException e) {
			logger.error("初始化 mysql表hdr_cda_count ，将生成的CDA历史统计数据插入 mysql 失败,执行SQL语句错误。。。");
			e.printStackTrace();
		} finally {
			DictUtils.closeResultSet(res);
			DictUtils.closeStatement(stmt);
			DictUtils.closeConnection(conns);
			long end = System.currentTimeMillis();
			logger.info("=================将生成 CDA文档 统计结果插入mysql总执行时间====================="
					+ (((end - start) / 1000) / 60) + "分钟");
			logger.info("================ 将生成 CDA文档 统计结果插入mysql完成====================");
		}
	}

	/**
	 * 
	 * @Description
	 * 方法描述:删除HDR_CDA和HDR_CDA_INDEX中的数据
	 * @return 返回类型： void
	 */
	public void deleteHiveCda() {
		String sql = "select * from hdr_cda_index_ii where patientid='0000544388'";
		Connection conns = HiveUtil.createHiveConnection();
		ResultSet res = null;
		Statement stmt = null;
		List<String> li = new ArrayList<String>();
		try {
			stmt = conns.createStatement();
			res = stmt.executeQuery(sql);
			while (res.next()) {
				String row = res.getString("rowkey");
				li.add(row);
			}
			HbaseCURDUtils.deleteByRowkeyList("HDR_CDA_INDEX", li);
			HbaseCURDUtils.deleteByRowkeyList("HDR_CDA", li);
			System.out.println("删除完成" + li.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description
	 * 方法描述:删除HDR_CDA和HDR_CDA_INDEX中的数据
	 * @return 返回类型： void
	 */
	public void deleteHiveCda1() {
		String sql = "SELECT * from hdr_cda_index_ii WHERE createtime >='2018-11-23 00:00:00' and  createtime <='2018-11-23 23:59:59'";
		Connection conns = HiveUtil.createHiveConnection();
		ResultSet res = null;
		Statement stmt = null;
		List<String> li = new ArrayList<String>();
		List<String> lii = new ArrayList<String>();
		try {
			stmt = conns.createStatement();
			res = stmt.executeQuery(sql);
			while (res.next()) {
				String row = res.getString("rowkey");
				li.add(row);
			}
			for (String string : li) {
				String sqlCDA = "SELECT * from hdr_cda_ii WHERE rowkey ='" + string + "'";
				stmt = conns.createStatement();
				res = stmt.executeQuery(sqlCDA);
				if (!res.next()) {
					lii.add(string);
				}
			}
			HbaseCURDUtils.deleteByRowkeyList("HDR_CDA_INDEX", lii);
			System.out.println("删除完成=====" + lii.size());
			//			HbaseCURDUtils.deleteByRowkeyList("HDR_CDA", li);
			//System.out.println("删除完成" + li.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void getApplicationContext() {
		ApplicationContext app = CommonApplication.getApplicationContext();
		cdaDao = app.getBean(CdaDao.class);
	}

	public enum VisitTypeEnum implements EnumType {
		MZ {
			@Override
			public String getCode() {
				return "01";
			}

			@Override
			public String getLabel() {
				return "门诊";
			}

		},
		ZY {
			@Override
			public String getCode() {
				return "02";
			}

			@Override
			public String getLabel() {
				return "住院";
			}
		}
	}

	public void createInpSummaryCDA() {
		String pid = "000001100000";
		String vid = "1";
		/*List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
		List<Map<String, String>> listSumm = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_INP_SUMMARY",
				HbaseCURDUtils.getRowkeyPrefix(pid), filters));
		CreateCDA cs = new CreateCDA();
		cs.create(listSumm, VisitTypeEnum.ZY.getCode());*/
		runOneCDA(pid, vid);
	}

	@Override
	public void runOneCDA(String patientID, String visitID) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), visitID));
		List<Map<String, String>> listSumm = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_INP_SUMMARY",
				HbaseCURDUtils.getRowkeyPrefix(patientID), filters));
		if (!listSumm.isEmpty()) {
			new CreateCDA().create(listSumm, VisitTypeEnum.ZY.getCode());
		}
		List<Map<String, String>> listOut = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_OUT_VISIT",
				HbaseCURDUtils.getRowkeyPrefix(patientID), filters));
		if (!listOut.isEmpty()) {
			new CreateCDA().create(listOut, VisitTypeEnum.MZ.getCode());
		}
	}
}

/**
 * 线程
 * @Description
 * 类描述：
 * @author liuzhi
 * @Date 2018年1月5日
 * @modify
 * 修改记录：
 *
 */
class CDAThread implements Runnable {
	protected static Logger logger = LoggerFactory.getLogger(CDAThread.class);
	private List<Map<String, String>> personList = null;
	private VisitTypeEnum visitType = null;
	private CountDownLatch cdl;

	public CDAThread(List<Map<String, String>> personList, VisitTypeEnum visitType, CountDownLatch cdl) {
		this.personList = personList;
		this.visitType = visitType;
		this.cdl = cdl;
	}

	public void run() {
		CreateCDA cs = new CreateCDA();
		cs.create(personList, visitType.getCode());
		cdl.countDown();
	}
}