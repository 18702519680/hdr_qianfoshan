package com.goodwill.cda.hlht.service.Impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goodwill.cda.hlht.constant.ScheduleConstant;
import com.goodwill.cda.hlht.service.Impl.CdaServiceImpl.VisitTypeEnum;
import com.goodwill.cda.hlht.web.Person;
import com.goodwill.cda.util.HbaseCURDUtilsToCDA;
import com.goodwill.cda.util.HiveUtil;
import com.goodwill.cda.util.Xmlutil;
import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * 生成住院的CDA
 * @Description
 * 类描述：
 * @author liuzhi
 * @Date 2018年1月5日
 * @modify
 * 修改记录：
 */
public class CreateCDA {
	private static Logger logger = LoggerFactory.getLogger(CreateCDA.class);

	/**
	 * 直接执行main方法  可以生成添加的患者的CDA
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 * @param args
	 */
	public static void main(String[] args) {
		for (Person map : getPerson()) {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), map.getVid()));
			List<Map<String, String>> listSumm = null;
			//先根据pid删除患者相关的CDA
			//deleteHiveCda(map.getPid());
			/**********住院***********/
			listSumm = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_INP_SUMMARY",
					HbaseCURDUtils.getRowkeyPrefix(map.getPid()), filters));
			if (!listSumm.isEmpty()) {
				new CreateCDA().createSummary(listSumm);
			} else {
				/**********门诊***********/
				listSumm = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_OUT_VISIT",
						HbaseCURDUtilsToCDA.getRowkeyPrefix(map.getPid()), filters));
				if (!listSumm.isEmpty()) {
					new CreateCDA().creatOutCDA(listSumm);
				}
			}
		}
	}

	/**
	 * 添加患者的pid和vid
	 * @Description
	 * 方法描述:
	 * @return 返回类型： List<Person>
	 * @param list
	 * @return
	 */
	public static List<Person> getPerson() {
		List<Person> list = new ArrayList<Person>();
		list.add(new Person("001547814700", "1"));
		return list;
	}

	/**
	 * 创建CDA 
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 * @param personList  传递 门诊或者住院的人列表
	 * @param type        门诊或者住院的类型  门诊为01 住院为02
	 */
	public void create(List<Map<String, String>> personList, String type) {
		if (type == null) {
			logger.error("========调用创建CDA文档方法 传递的就诊类型参数为null==========");
			return;
		}
		if (type.equals(VisitTypeEnum.ZY.getCode())) {
			logger.info("========" + Thread.currentThread().getName() + ":生成住院CDA,该线程需要生成的人数为：" + personList.size()
					+ "==============");
			createSummary(personList);
		} else if (type.equals(VisitTypeEnum.MZ.getCode())) {
			logger.info("========" + Thread.currentThread().getName() + ":生成门诊CDA,该线程需要生成的人数为：" + personList.size()
					+ "==============");
			creatOutCDA(personList);
		}
	}

	/**
	 * 生成住院的CDA
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 * @param listSumm
	 */
	public void createSummary(List<Map<String, String>> listSumm) {
		if (listSumm == null) {
			return;
		}
		//循环病案首页生成CDA
		for (int i = 0; i < listSumm.size(); i++) {
			long startCDA = System.currentTimeMillis();
			logger.info("线程" + Thread.currentThread().getName() + ":住院患者总人数====" + listSumm.size() + "====当前患者="
					+ (i + 1));
			// add  by  liuzhi  2017-12-11  增加 设置 住院生成CDA 第几个人 
			ScheduleConstant.setInSummarySchedule(1);
			Map<String, String> mapInfo = listSumm.get(i);
			String pid = mapInfo.get("IN_PATIENT_ID");
			String vid = mapInfo.get("VISIT_ID");
			if (StringUtils.isBlank(pid) || StringUtils.isBlank(vid)) {
				continue;
			}
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
			List<Map<String, String>> listBASY = HbaseCURDUtils.findByCondition("HDR_INP_SUMMARY",
					HbaseCURDUtils.getRowkeyPrefix(pid), filters);
			if (listBASY.isEmpty()) {
				continue;
			}
			Map<String, String> map = listBASY.get(0);
			pid = map.get("IN_PATIENT_ID");
			vid = map.get("VISIT_ID");
			if (StringUtils.isBlank(pid) || StringUtils.isBlank(vid)) {
				continue;
			}
			List<Map<String, String>> listDiag = Xmlutil.formatList(HbaseCURDUtils.findByCondition(
					"HDR_INP_SUMMARY_DIAG", HbaseCURDUtils.getRowkeyPrefix(pid), filters));
			//死亡病例讨论记录
			//new SD51CDA().getDeathCasesDiscussionRecordss(pid, vid);
			
			
			long endCDA = System.currentTimeMillis();
			logger.info("=================生成一个人的住院CDA总执行时间" + ((endCDA - startCDA) / 1000) + "秒=====================");
		}
	}

	/**
	 * 生成门诊的CDA
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 * @param outList
	 */
	private void creatOutCDA(List<Map<String, String>> outList) {
		if (outList == null) {
			return;
		}
		for (int i = 0; i < outList.size(); i++) {
			long startCDA = System.currentTimeMillis();
			logger.info("线程" + Thread.currentThread().getName() + ":门诊患者总人数=" + outList.size() + "====当前患者=" + (i + 1));
			//  add  by liuzhi  2017-12-11  增加设置 门诊 生成第几个人
			ScheduleConstant.setOutPatientSchedule(1);
			Map<String, String> mapout = outList.get(i);
			String pid = mapout.get("OUT_PATIENT_ID");
			String vid = mapout.get("VISIT_ID");
			if (StringUtils.isBlank(pid) || StringUtils.isBlank(vid)) {
				continue;
			}
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("VISIT_ID", "STRING", MatchType.EQ.getOperation(), vid));
			List<Map<String, String>> listDiag = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByCondition(
					"HDR_OUT_VISIT_DIAG", HbaseCURDUtilsToCDA.getRowkeyPrefix(pid), filters));
			// 如果门诊没有诊断 则不生成CDA
			if (listDiag.isEmpty() || null == listDiag) {
				continue;
			}
			// 获取患者基本信息
			List<Map<String, String>> listOutInfo = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByCondition(
					"HDR_OUT_VISIT", HbaseCURDUtilsToCDA.getRowkeyPrefix(pid), filters));
			if (listOutInfo.isEmpty()) {
				continue;
			}
			Map<String, String> map = listOutInfo.get(0);
			// 关联出身份证
			List<PropertyFilter> filtersPat = new ArrayList<PropertyFilter>();
			filtersPat.add(new PropertyFilter("VISIT_TYPE_CODE", "STRING", MatchType.EQ.getOperation(), "01"));
			List<Map<String, String>> listPat = Xmlutil.formatList(HbaseCURDUtils.findByCondition("HDR_PATIENT",
					HbaseCURDUtils.getRowkeyPrefix(map.get("OUT_PATIENT_ID")), filtersPat, new String[] { "ID_CARD_NO",
							"SEX_CODE", "SEX_NAME", "DATE_OF_BIRTH" }));
			if (!listPat.isEmpty()) {
				map.put("ID_CARD_NO", listPat.get(0).get("ID_CARD_NO"));
				map.put("SEX_CODE", listPat.get(0).get("SEX_CODE"));
				map.put("SEX_NAME", listPat.get(0).get("SEX_NAME"));
				map.put("DATE_OF_BIRTH", listPat.get(0).get("DATE_OF_BIRTH"));
			}
			long endCDA = System.currentTimeMillis();
			logger.info("=================生成一个人的门诊CDA总执行时间" + ((endCDA - startCDA) / 1000) + "秒=====================");
		}
	}

	/**
	 * 
	 * @Description
	 * 方法描述:删除HDR_CDA和HDR_CDA_INDEX中的数据
	 * @return 返回类型： void
	 */
	public static void deleteHiveCda(String pid) {
		String sql = "select * from hdr_cda_index_ii where patientid='" + pid + "'";
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
}
