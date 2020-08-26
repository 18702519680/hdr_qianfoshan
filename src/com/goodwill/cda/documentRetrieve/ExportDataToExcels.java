package com.goodwill.cda.documentRetrieve;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goodwill.core.orm.MatchType;
import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * @Description
 * 类描述：从HBase中导出数据到EXCEL
 * @author jibin
 * @Date 2016年8月26日
 * @modify
 * 修改记录：
 *
 */
public class ExportDataToExcels {
	protected static Logger logger = LoggerFactory.getLogger(ExportDataToExcels.class);

	public static void tableCount(String str) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("c:\\Result.txt"), true));
		writer.write(str);
		writer.newLine();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		WriteSourceData2Excels("HDR_IN_ORDER", "newDate", "cf", 20000, null);
		if (6 == 6) {
			return;
		}
		//HDR_PATIENT,HDR_OUT_VISIT,HDR_PAT_ADT,HDR_INP_SUMMARY,HDR_OUT_CHARGE,HDR_IN_ORDER,HDR_ORDER_EXE,
		//HDR_EXAM_REPORT,HDR_LAB_REPORT,HDR_LAB_REPORT_DETAIL,HDR_EMR_CONTENT
		//WriteSourceData2Excels("HDR_PAT_VISIT_DEAD", "newDate", "cf", 1000, null);
		String str = "HDR_INFECTION_WARN,HDR_INFECTION_INTERVENE,HDR_CRITICAL_VALUES,HDR_LCP_PATIENT_VISIT";
		String a[] = str.split(",");
		for (String b : a) {
			System.out.println(b);
			WriteSourceData2Excels("HDR_CDA", "newDate", "cf", 20000, null);
		}
		if (0 == 0) {
			return;
		}
		//000858420200
		String pat = filse("src/main/resources/OID/PATIENT.txt");
		String b[] = pat.split(",");

		//for (int j = 0; j < a.length; j++) {
		List<Map<String, String>> source = new ArrayList<Map<String, String>>();
		for (int i = 0; i < b.length; i++) {
			List<PropertyFilter> filtersCounts = new ArrayList<PropertyFilter>();
			filtersCounts.add(new PropertyFilter("EXAM_PERFORM_TIME", "STRING", MatchType.EQ.getOperation(),
					"2017-01-01"));
			List<Map<String, String>> sourceDatas = HbaseCURDUtils.findByCondition("HDR_EXAM_REPORT",
					HbaseCURDUtils.getRowkeyPrefix(b[i]), filtersCounts);
			if (sourceDatas != null) {
				source.addAll(sourceDatas);
			}
			if (source.size() > 10000) { //source里面放了15000条了，这时候处理一下生成一个excel，然后把source值为空
				pat(source, "newDate", "EXAM_PERFORM_TIME", i);
				System.out.println(source.size() + "pid====" + b[i]);
				source = new ArrayList<Map<String, String>>();
				System.gc();
			}
			/*if (i > 14630) {
				return;
			}*/
		}
		if (!source.isEmpty()) {
			pat(source, "newDate", "EXAM_PERFORM_TIME", source.size());
		}

		//}
		int bb = 0;
		if (bb == 0) {
			return;
		}
		/*String cc = filse("src/main/resources/OID/OIDD.txt");
		String str[] = cc.split(",");
		for (int i = 0; i < str.length; i += 3) {
			System.out.println(str[i + 2]);
			WriteSourceData2Excels("HDR_DICT_ITEM", str[i + 2] + "|", "HDR_PATIEN", null);
		}*/

	}

	public static void pat(List<Map<String, String>> sourceDatas, String files, String tableName, int c)
			throws Exception {
		TransHbaseDataByExcelImpl t = new TransHbaseDataByExcelImpl();
		FileOutputStream outputStream = null;
		//先生成workBook和Sheet
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rowNum = 0;
		//遍历结果集合，每个Map生成一个ROW并将信息放入
		Iterator<Map<String, String>> itors = sourceDatas.iterator();
		while (itors.hasNext()) {
			dealWithOneRow(sheet.createRow(rowNum), itors.next());
			//先生成workBook和Sheet
			//遍历结果集合，每个Map生成一个ROW并将信息放入						
			//if (count % batchSize == 0) {
			//}
			rowNum++;
		}
		outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + c + ".xlsx");
		wb.write(outputStream);
		outputStream.flush();
		//生成导出流，将WORKBOOK写入到文件
		/*	outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + index + ".xlsx");
			wb.write(outputStream);
			outputStream.flush();*/
		outputStream.close();
		wb.close();
		System.gc();
		//将数据写入到EXCEL中
		System.out.println("完成========================");

	}

	/**
	 * 按条件进行数据抽取
	 * @param rowkey
	 * @throws IOException
	 * @throws Exception
	 */
	public static void exportfindByRowkeyEmr(String rowkey) throws IOException, Exception {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("HDR_MSG_LOG", "STRING", MatchType.GE.getOperation(), "2017-01-03 01:40:19"));
		//filters.add(new PropertyFilter("MR_CONTENT_HTML", "STRING", MatchType.NE.getOperation(), "null"));
		List<Map<String, String>> sourceDatas = HbaseCURDUtils.findByCondition("HDR_MSG_LOG", " 0110|2017", filters);
		System.out.println(sourceDatas.size());
		if (sourceDatas.size() == 0) {
			return;
		}
		//将数据写入到EXCEL中
		WriteSourceData2Excels("HDR_MSG_LOG", rowkey, "HDR_DICT", null);
		System.gc();
		System.out.println("完成");
	}

	/**
	 * @Description
	 * 方法描述:全表按列族将结果写入指定EXCEL文件中
	 * @return 返回类型： void
	 * @param fileName表名   family列族  size大小(只能是正整数)
	 * @param sourceDatas
	 * @throws Exception 
	 */
	public static void exportfindAllByRowkeyPrefixEXCEL() throws Exception {
		TransHbaseDataByExcelImpl t = new TransHbaseDataByExcelImpl();
		String[] tableName = t.getTableNames();//获取全部表名
		for (int j = 74; j < tableName.length; j++) {
			System.out.println(tableName[j]);
			System.out.println("表名位置：------------->" + j);
			WriteSourceData2Excels(tableName[j], "newDate", "cf", 2000, null);
		}

	}

	/**
	 * @Description
	 * 方法描述：按rowkey查询所有表数据===================================================
	 * @return 返回类型： void
	 * @parameter 表名 列族 大小
	 * @param fileName
	 * @param sourceDatas
	 * @throws IOException
	 */
	public static void exportfindByRowkeyPrefixEXCEL() throws Exception {
		TransHbaseDataByExcelImpl t = new TransHbaseDataByExcelImpl();
		String[] tableName = t.getTableNames();//获取全部表名
		String[] rowkey = t.getRKPrefixs();//获取rowkey前缀
		//for (int j = 30; j < tableName.length; j++) {
		//System.out.println(tableName[j]);
		//System.out.println("表名位置：------------->" + j);
		for (int k = 0; k < rowkey.length; k++) {
			//System.out.println(rowkey[k]);
			WriteSourceData2Excels("HDR_LAB_REPORT_DETAIL".trim(), rowkey[k], "HDR_PATIEN", null);
		}
		//}

	}

	/**
	 * @Description
	 * 方法描述:按PID将结果写入指定EXCEL文件中,
	 * @return 返回类型： void
	 * @param fileName表名    str传入的pid字符串
	 * @param sourceDatas
	 * @throws Exception 
	 */
	public static void WriteSourceData2ExcelsPID(String str, String files, List<PropertyFilter> filters)
			throws Exception {
		TransHbaseDataByExcelImpl tt = new TransHbaseDataByExcelImpl();
		String[] strs = str.split(",");
		String[] tableNames = tt.getTableNames();//获取全部表名
		for (int tab = 166; tab < tableNames.length; tab++) {
			System.out.println("表名" + tableNames[tab]);
			for (int i = 0; i < strs.length; i++) {
				TransHbaseDataByExcelImpl t = new TransHbaseDataByExcelImpl();
				int index = 0;
				//HbaseCURDUtils.getRowkeyPrefix(patient_id)  与hbase 中rowkey反转不一样，自己截取反转rowkey
				List<Map<String, String>> sourceDatas = HbaseCURDUtils.findByRowkeyPrefix(tableNames[tab],
						HbaseCURDUtils.getRowkeyPrefix(strs[i]));

				System.out.println(sourceDatas.size());
				if (sourceDatas.size() == 0) {
					continue;
				}
				long batchSize = t.getExportBatchSize();
				FileOutputStream outputStream = null;
				//先生成workBook和Sheet
				Workbook wb = new XSSFWorkbook();
				Sheet sheet = wb.createSheet();
				int rowNum = 0;
				//遍历结果集合，每个Map生成一个ROW并将信息放入
				Iterator<Map<String, String>> itors = sourceDatas.iterator();
				while (itors.hasNext()) {
					dealWithOneRow(sheet.createRow(rowNum), itors.next());
					//先生成workBook和Sheet
					int count = 0;
					//遍历结果集合，每个Map生成一个ROW并将信息放入						
					count++;
					if (count % batchSize == 0) {
						outputStream = new FileOutputStream(t.getImportDir(files) + tableNames[tab] + "-" + strs[i]
								+ "-" + index + ".xlsx");
						wb.write(outputStream);
						outputStream.flush();
						index++;
						wb = new XSSFWorkbook();
						sheet = wb.createSheet();
						outputStream = new FileOutputStream(t.getImportDir(files) + tableNames[tab] + "-" + strs[i]
								+ "-" + index + ".xlsx");
					}
					rowNum++;
				}
				//生成导出流，将WORKBOOK写入到文件
				outputStream = new FileOutputStream(t.getImportDir(files) + tableNames[tab] + "-" + strs[i] + "-"
						+ index + ".xlsx");
				wb.write(outputStream);
				outputStream.flush();
				outputStream.close();
				wb.close();
				System.gc();
				//将数据写入到EXCEL中
				System.out.println("完成");
			}
		}
	}

	/**
	 * @Description
	 * 方法描述:按rowkey将结果写入指定EXCEL文件中
	 * @return 返回类型： void
	 * @param fileName
	 * @param sourceDatas
	 * @throws Exception 
	 */
	public static void WriteSourceData2Excels(String tableName, String rowKeyPrefix, String files,
			List<PropertyFilter> filters) throws Exception {
		TransHbaseDataByExcelImpl t = new TransHbaseDataByExcelImpl();
		int index = 0;
		//List<PropertyFilter> filter = new ArrayList<PropertyFilter>();
		//filters.add(new PropertyFilter("HDR_MSG_LOG", "STRING", MatchType.GE.getOperation(), "2017-01-03 01:40:19"));
		//filters.add(new PropertyFilter("MR_CONTENT_HTML", "STRING", MatchType.NE.getOperation(), "null"));
		List<Map<String, String>> sourceDatas = HbaseCURDUtils.findByRowkeyPrefix("HDR_MSG_LOG", "0112|2017");
		System.out.println(sourceDatas.size());
		if (sourceDatas.size() == 0) {
			return;
		}

		/*if (sourceDatas.size() > 40000) {
			sourceDatas = sourceDatas.subList(1, 38000);
		}*/
		long batchSize = t.getExportBatchSize();
		FileOutputStream outputStream = null;
		//先生成workBook和Sheet
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rowNum = 0;
		//遍历结果集合，每个Map生成一个ROW并将信息放入
		Iterator<Map<String, String>> itors = sourceDatas.iterator();

		while (itors.hasNext()) {

			dealWithOneRow(sheet.createRow(rowNum), itors.next());
			//先生成workBook和Sheet
			int count = 0;
			//遍历结果集合，每个Map生成一个ROW并将信息放入						
			count++;
			if (count % batchSize == 0) {
				outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + rowKeyPrefix + "-"
						+ index + ".xlsx");
				wb.write(outputStream);
				outputStream.flush();
				index++;
				wb = new XSSFWorkbook();
				sheet = wb.createSheet();
				outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + rowKeyPrefix + "-"
						+ index + ".xlsx");
			}
			rowNum++;
		}
		//生成导出流，将WORKBOOK写入到文件
		outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + rowKeyPrefix + "-" + index
				+ ".xlsx");
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();
		wb.close();
		System.gc();
		//将数据写入到EXCEL中
		System.out.println("完成");

	}

	/**
	 * @Description
	 * 方法描述:重载！按列族将结果写入指定EXCEL文件中
	 * @return 返回类型： void
	 * @param fileName表名   family列族  size大小(只能是正整数)
	 * @param sourceDatas
	 * @throws Exception 
	 */
	public static void WriteSourceData2Excels(String tableName, String files, String family, int size,
			List<PropertyFilter> filters) throws Exception {

		TransHbaseDataByExcelImpl t = new TransHbaseDataByExcelImpl();
		int index = 0;
		List<Map<String, String>> sourceDatas = HbaseCURDUtils.findAll(tableName, family, size);
		System.out.println(sourceDatas.size());
		if (sourceDatas.size() == 0) {
			return;
		}
		long batchSize = t.getExportBatchSize();
		FileOutputStream outputStream = null;
		//先生成workBook和Sheet
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rowNum = 0;
		//遍历结果集合，每个Map生成一个ROW并将信息放入
		Iterator<Map<String, String>> itors = sourceDatas.iterator();
		int count = 0;
		while (itors.hasNext()) {
			dealWithOneRow(sheet.createRow(rowNum), itors.next());
			//先生成workBook和Sheet
			//遍历结果集合，每个Map生成一个ROW并将信息放入						
			count++;
			if (count % batchSize == 0) {
				outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + "-" + index + ".xlsx");
				wb.write(outputStream);
				outputStream.flush();
				index++;
				wb = new XSSFWorkbook();
				sheet = wb.createSheet();
				outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + "-" + index + ".xlsx");
			}
			rowNum++;
		}
		//生成导出流，将WORKBOOK写入到文件
		outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + "-" + index + ".xlsx");
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();
		wb.close();
		System.gc();
		//将数据写入到EXCEL中
		System.out.println("完成");
	}

	/**
	 * @Description
	 * 方法描述:重载！按条件进行抽取导入execl表中
	 * @return 返回类型： void
	 * @param fileName表名   rowkey
	 * EQ("=", "等于"), LIKE("like", "相似"), GT(">", "大于"), GE(">=", "大于等于"), LT("<", "小于"), LE("<=", "小于等于"), NE("<>",
		"不等于"), IN("in", "包含"), NOTIN("nin", "不包含"), ISNULL("isnull", "为空"), NOTNULL("notnull", "为空"), INLIKE(
		"inlike", "IN相似"), NOTLIKE("nlike", "不包含相似"), STARTWITH("sw", "以此开始"), ENDWITH("ew", "以此结束");
	 * @param sourceDatas
	 * @throws Exception 
	 */
	public static void WriteSourceData2Excels(String tableName, String files, String rowkey, MatchType Type,
			List<PropertyFilter> filters) throws Exception {
		filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter(tableName, "STRING", MatchType.ISNULL.getOperation(), "null"));
		filters.add(new PropertyFilter(tableName, "STRING", MatchType.ISNULL.getOperation(), "null"));
		List<Map<String, String>> sourceDatas = HbaseCURDUtils.findByCondition(tableName, rowkey, filters);
		TransHbaseDataByExcelImpl t = new TransHbaseDataByExcelImpl();
		int index = 0;
		System.out.println(sourceDatas.size());
		if (sourceDatas.size() == 0) {
			return;
		}
		long batchSize = t.getExportBatchSize();
		FileOutputStream outputStream = null;
		//先生成workBook和Sheet
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rowNum = 0;
		//遍历结果集合，每个Map生成一个ROW并将信息放入
		Iterator<Map<String, String>> itors = sourceDatas.iterator();
		while (itors.hasNext()) {
			dealWithOneRow(sheet.createRow(rowNum), itors.next());
			//先生成workBook和Sheet
			int count = 0;
			//遍历结果集合，每个Map生成一个ROW并将信息放入						
			count++;
			if (count % batchSize == 0) {
				outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + "-" + index + ".xlsx");
				wb.write(outputStream);
				outputStream.flush();
				index++;
				wb = new XSSFWorkbook();
				sheet = wb.createSheet();
				outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + "-" + index + ".xlsx");
			}
			rowNum++;
		}
		//生成导出流，将WORKBOOK写入到文件
		outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + "-" + index + ".xlsx");
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();
		wb.close();
		System.gc();
		//将数据写入到EXCEL中
		System.out.println("完成");

	}

	/**
	 * @Description
	 * 方法描述:将指定Map的值放入到指定的ROW中去
	 * @return 返回类型： void
	 * @param row
	 * @param sourceData
	 */
	private static void dealWithOneRow(Row row, Map<String, String> sourceData) {
		int cellNo = 0;
		//遍历Map，将KEY放入第一个CELL，将VALUE放入第二个CELL
		for (Map.Entry<String, String> entry : sourceData.entrySet()) {
			row.createCell(cellNo).setCellValue(entry.getKey());
			row.createCell(cellNo + 1).setCellValue(entry.getValue());
			cellNo += 2;
		}
	}

	public static String filse(String fileName) {

		FileInputStream fos;
		BufferedReader br = null;
		String aa = null;
		String bb = "";
		try {
			//fos = new FileInputStream("c:\\aaa.txt");
			fos = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fos, "utf-8");
			br = new BufferedReader(isr);
			while ((aa = br.readLine()) != null) {
				bb += aa.trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bb;
	}

	/**
	 * @Description
	 * 方法描述:重载！按列族将结果写入指定EXCEL文件中
	 * @return 返回类型： void
	 * @param fileName表名   family列族  size大小(只能是正整数)
	 * @param sourceDatas
	 * @throws Exception 
	 */
	public static void WriteSourceData2ExcelsList(String tableName, String files, String family, int size,
			List<PropertyFilter> filters) throws Exception {
		TransHbaseDataByExcelImpl t = new TransHbaseDataByExcelImpl();
		List<Map<String, String>> sourceDatas = HbaseCURDUtils.findAll(tableName, family, size);
		System.out.println(sourceDatas.size());
		if (sourceDatas.size() == 0) {
			return;
		}

		FileOutputStream outputStream = null;
		//先生成workBook和Sheet
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();

		int b = 0;
		for (int i = 0; i < sourceDatas.size(); i++) {
			sourceDatas = sourceDatas.subList(b, b + 30000);
			for (Map<String, String> map : sourceDatas) {
				dealWithOneRow(sheet.createRow(b), map);
				b++;
			}

			outputStream = new FileOutputStream(t.getImportDir(files) + tableName + "-" + System.currentTimeMillis()
					+ ".xlsx");
			wb.write(outputStream);
			outputStream.flush();
		}

		//生成导出流，将WORKBOOK写入到文件
		outputStream.close();
		wb.close();
		System.gc();
		//将数据写入到EXCEL中
		System.out.println("完成");
	}
}
