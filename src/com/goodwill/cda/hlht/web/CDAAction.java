package com.goodwill.cda.hlht.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.goodwill.cda.hlht.constant.ScheduleConstant;
import com.goodwill.cda.hlht.service.CdaService;
import com.goodwill.cda.util.HiveUtil;
import com.goodwill.core.utils.DateUtils;
import com.goodwill.core.utils.json.JsonUtil;
import com.goodwill.core.web.BaseAction;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * 
 * @Description
 * 类描述：
 * @author hanfei
 * @Date 2017年12月8日
 * @modify
 * 修改记录：
 *
 */
public class CDAAction extends BaseAction {

	protected static Logger logger = LoggerFactory.getLogger(CDAAction.class);
	private static final long serialVersionUID = -112221L;
	@Autowired
	CdaService cdaService;

	public void task() throws Exception {
		//如果有 线程在统计 就去不 再次执行 
		if (ScheduleConstant.getFlag() == 0) {
			//TODO  启动线程
			ScheduleConstant.init();
			final String startdate = getParameter("startdate");
			final String enddate = getParameter("enddate");
			new Thread(new Runnable() {
				public void run() {
					Calendar end = Calendar.getInstance();
					Date endToday = DateUtils.convertStringToDate("yyyy-MM-dd", enddate);
					end.setTime(endToday);
					end.set(Calendar.DATE, end.get(Calendar.DATE) + 1);
					cdaService.genernateCDA(startdate, DateUtils.getDate(end.getTime()), 0, null);
				}
			}).start();
		}
		renderJson(JsonUtil.getJSONString("{flag :" + ScheduleConstant.getFlag() + "}"));
		//hbaseToXmlbyPid();
	}

	public void oneCDA() throws Exception {
		if (ScheduleConstant.getFlag() == 0) {
			//TODO  启动线程
			ScheduleConstant.init();
			new Thread(new Runnable() {
				public void run() {
					String patientID = getParameter("patientID");
					String visitID = getParameter("visitID");
					System.out.println(patientID + "====" + visitID);
					cdaService.runOneCDA(patientID, visitID);
				}
			}).start();
		}
		renderJson(JsonUtil.getJSONString("{flag :" + ScheduleConstant.getFlag() + "}"));
	}

	public static void main(String[] args) throws Exception {
		String startTime = "2018-01-02 20:00:00";
		String endTime = "2018-03-08 00:00:00";
		//hbaseToXml(startTime, endTime);
//		hbaseToXmlbyPid();
	}

	

	/**
	 * 随机读取每个cda文档 存储5份
	 * 
	 * @throws Exception
	 */
	public static void hbaseToXml(String startTime, String endTime) throws Exception {
		logger.info("===========开始导出================");
		Connection conns = HiveUtil.createHiveConnection();
		ResultSet res = null;
		Statement stmt = null;
		List<Sort> listSort = new ArrayList<Sort>();
		String sql = "";
		//		String sql = "select DocumentTitle from hdr_cda_index_ii group by DocumentTitle ";
		//		stmt = conns.createStatement();
		//		res = stmt.executeQuery(sql);
		//		while (res.next()) {
		//			li.add(res.getString("DocumentTitle"));
		//		}
		getDocumentTitle(listSort);
		stmt = conns.createStatement();
		for (Sort sort : listSort) {
			logger.info("==导出文档==" + sort.getName());
			long start = System.currentTimeMillis();
			if (StringUtils.isBlank(sort.getName())) {
				continue;
			}
			//			sql = "select distinct rowkey from  hdr_cda_index_ii where DocumentTitle='" + sort.getName()
			//					+ "' and createtime between '" + startTime + "' and '" + endTime + "' limit 500";
			sql = "select distinct rowkey from  hdr_cda_index_ii where documentcode='" + sort.getId()
					+ "' and createtime between '" + startTime + "' and '" + endTime
					+ "' order by rowkey desc  limit 200";
			logger.info("sql语句==" + sql);
			res = null;
			res = stmt.executeQuery(sql);
			while (res.next()) {
				String[] rks = res.getString("rowkey").split("\\|");
				List<Map<String, String>> list3 = HbaseCURDUtils.findByRowkeyPrefix("HDR_CDA", res.getString("rowkey"));
				System.out.println(res.getString("rowkey"));
				for (Map<String, String> map : list3) {
					String fileName = "d:/CDA-Export2/" + rks[4] + "-" + rks[2] + "-" + rks[3] + "-" + ""
							+ System.currentTimeMillis() + ".xml";
					//					FileWriter fw = new FileWriter(fileName, true);
					//					BufferedWriter bw = new BufferedWriter(fw);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),
							"UTF-8"));
					bw.append(map.get("Document"));
					bw.close();
					//fw.close();
				}
			}
			long end = System.currentTimeMillis();
			logger.info("=====统计生成" + sort.getName() + "cda的时间======" + (((end - start) / 1000) / 60) + "分钟");
		}
		if (res != null) {
			res.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (conns != null) {
			conns.close();
		}
		logger.info("===========导出结束================");
	}

	/**
	 * 
	 * @Description
	 * 方法描述:根据patient_id和visit_id生成CDA
	 * @return 返回类型： void
	 * @param patient_id
	 * @param visit_id
	 * @param flag
	 * @throws Exception
	 */
	public static void hbaseToXml(String patient_id, String visit_id, boolean flag) throws Exception {
		long startTime = System.currentTimeMillis();
		Connection conns = HiveUtil.createHiveConnection();
		ResultSet res = null;
		Statement stmt = null;
		List<Sort> listSort = new ArrayList<Sort>();
		String sql = "";
		getDocumentTitle(listSort);
		stmt = conns.createStatement();
		for (Sort sort : listSort) {
			logger.info("======开始导出=====patient_id==" + patient_id + "===visit_id===" + visit_id + "==导出文档=="
					+ sort.getName());
			long start = System.currentTimeMillis();
			sql = "select distinct rowkey from  hdr_cda_index_ii where documentcode='" + sort.getId()
					+ "' and patientid = '" + patient_id + "' and vid= '" + visit_id + "'";
			logger.info("sql语句==" + sql);
			res = null;
			res = stmt.executeQuery(sql);
			while (res.next()) {
				String[] rks = res.getString("rowkey").split("\\|");
				List<Map<String, String>> list3 = HbaseCURDUtils.findByRowkeyPrefix("HDR_CDA", res.getString("rowkey"));
				System.out.println(res.getString("rowkey"));
				for (Map<String, String> map : list3) {
					File dir = new File("d:\\CDA-Export\\" + sort.getId() + "-" + sort.getName());
					if (!dir.exists()) {
						dir.mkdir();
					}
					String fileName = "d:/CDA-Export/" + sort.getId() + "-" + sort.getName() + "/" + rks[4] + "-"
							+ rks[2] + "-" + rks[3] + "-" + "" + System.currentTimeMillis() + ".xml";
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),
							"UTF-8"));
					bw.append(map.get("Document"));
					bw.close();
					//fw.close();
				}
			}
			long end = System.currentTimeMillis();
			logger.info("=====统计生成" + sort.getName() + "cda的时间======" + (((end - start) / 1000) / 60) + "分钟");
		}
		if (res != null) {
			res.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (conns != null) {
			conns.close();
		}
		long endTime = System.currentTimeMillis();
		logger.info("===========导出一个人的CDA================" + (((endTime - startTime) / 1000) / 60) + "分钟");
	}

	public static List<Sort> getDocumentTitle(List<Sort> listSort) {
		listSort.add(new Sort("C0001", "病历概要"));
		listSort.add(new Sort("C0002", "门急诊病历"));
		listSort.add(new Sort("C0003", "急诊留观病历"));
		listSort.add(new Sort("C0004", "西药处方"));
		listSort.add(new Sort("C0005", "中药处方"));
		listSort.add(new Sort("C0006", "检查报告"));
		listSort.add(new Sort("C0007", "检验报告"));
		listSort.add(new Sort("C0008", "治疗记录"));
		listSort.add(new Sort("C0009", "一般手术记录"));
		listSort.add(new Sort("C0010", "麻醉术前访视记录"));
		listSort.add(new Sort("C0011", "麻醉记录"));
		listSort.add(new Sort("C0012", "麻醉术后访视记录"));
		listSort.add(new Sort("C0013", "输血记录"));
		listSort.add(new Sort("C0014", "待产记录"));
		listSort.add(new Sort("C0015", "阴道分娩记录"));
		listSort.add(new Sort("C0016", "剖宫产记录"));
		listSort.add(new Sort("C0017", "一般护理记录"));
		listSort.add(new Sort("C0018", "病危病重护理记录"));
		listSort.add(new Sort("C0019", "手术护理记录"));
		listSort.add(new Sort("C0020", "生命体征测量记录"));
		listSort.add(new Sort("C0021", "出入量记录"));
		listSort.add(new Sort("C0022", "高值耗材使用记录"));
		listSort.add(new Sort("C0023", "入院评估"));
		listSort.add(new Sort("C0024", "护理计划"));
		listSort.add(new Sort("C0025", "出院评估与指导"));
		listSort.add(new Sort("C0026", "手术知情同意书"));
		listSort.add(new Sort("C0027", "麻醉知情同意书"));
		listSort.add(new Sort("C0028", "输血治疗同意书"));
		listSort.add(new Sort("C0029", "特殊检查及特殊治疗同意书"));
		listSort.add(new Sort("C0030", "病危重通知书"));
		listSort.add(new Sort("C0031", "其他知情告知同意书"));
		listSort.add(new Sort("C0032", "住院病案首页"));
		listSort.add(new Sort("C0034", "入院记录"));
		listSort.add(new Sort("C0035", "24h内入出院记录"));
		listSort.add(new Sort("C0036", "24h内入院死亡记录"));
		listSort.add(new Sort("C0037", "首次病程记录"));
		listSort.add(new Sort("C0038", "日常病程记录"));
		listSort.add(new Sort("C0039", "上级医师查房记录"));
		listSort.add(new Sort("C0040", "疑难病例讨论记录"));
		listSort.add(new Sort("C0041", "交接班记录"));
		listSort.add(new Sort("C0042", "转科记录"));
		listSort.add(new Sort("C0043", "阶段小结"));
		listSort.add(new Sort("C0044", "抢救记录"));
		listSort.add(new Sort("C0045", "会诊记录"));
		listSort.add(new Sort("C0046", "术前小结"));
		listSort.add(new Sort("C0047", "术前讨论"));
		listSort.add(new Sort("C0048", "术后首次病程记录"));
		listSort.add(new Sort("C0049", "出院记录"));
		listSort.add(new Sort("C0050", "死亡记录"));
		listSort.add(new Sort("C0051", "死亡病例讨论记录"));
		listSort.add(new Sort("C0052", "住院医嘱"));
		listSort.add(new Sort("C0053", "出院小结"));
		return listSort;
	}

	public static void hbaseToXmlbyPid() throws Exception {
		List<Perseon> list = new ArrayList<Perseon>();
		addPidAndVid(list);
		for (Perseon perseon : list) {
			hbaseToXml(perseon.getPatientid(), perseon.getVid(), true);
		}
	}

	public static List<Perseon> addPidAndVid(List<Perseon> list) {
		list.add(new Perseon("001477891000", "1"));
		list.add(new Perseon("001494226000", "1"));
		list.add(new Perseon("001333369000", "3"));
		list.add(new Perseon("00A442290200", "2"));
		list.add(new Perseon("00A444817400", "3"));
		list.add(new Perseon("000001036200", "2"));
		list.add(new Perseon("000330676100", "2"));
		list.add(new Perseon("000482470700", "4"));
		list.add(new Perseon("000645350600", "54"));
		list.add(new Perseon("000743049500", "1"));
		list.add(new Perseon("000045188500", "5"));
		list.add(new Perseon("001536887500", "1"));
		list.add(new Perseon("001543777500", "1"));

		return list;
	}
}

class Perseon {
	private String patientid;
	private String vid;

	public Perseon() {
		super();
	}

	public Perseon(String patientid, String vid) {
		super();
		this.patientid = patientid;
		this.vid = vid;
	}

	public String getPatientid() {
		return patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

}

class Sort {
	private String id;
	private String name;

	public Sort() {
		super();
	}

	public Sort(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
