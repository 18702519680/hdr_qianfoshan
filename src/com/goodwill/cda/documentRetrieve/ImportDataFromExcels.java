package com.goodwill.cda.documentRetrieve;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * @Description
 * 类描述：将之前导出的EXCEL中数据恢复到集群中去
 * @author 计彬
 * @Date 2016年8月31日
 * @modify
 * 修改记录：
 */
public class ImportDataFromExcels {
	protected static Logger logger = LoggerFactory.getLogger(ImportDataFromExcels.class);

	public static void toImg() {

		File fileDir = new File("c://xml//");
		File[] files = fileDir.listFiles();
		int index = 0;
		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			}
			System.out.println(file.getName());
			String fileName = file.getName();
			/*if (!fileName.endsWith(".png")) {
				continue;
			}
			fileName = fileName.substring(0, fileName.indexOf("."));
			//当已经读取过的execl文件,将其名称改的,防止重复读取存入
			File file2 = new File("c:\\newDate\\" + file.getName()); //指定文件名及路径     
			String filename = file2.getAbsolutePath();
			if (filename.indexOf(".") >= 0) {
				index++;
				filename = filename.substring(0, filename.lastIndexOf(":"));
				String bb = file.getName().substring(file.getName().indexOf("newDate") + 1,
						file.getName().lastIndexOf("."));
				file2.renameTo(new File(filename + ":\\newDate\\"
						+ file.getName().replace(bb, index + "").replace("png", "jpg"))); //改名    

				System.out.println("导入成功");
			}*/
			//System.gc();

		}
	}

	public static void main(String[] args) throws Exception {

		int count = 0;//已导入
		int leng = 0;//剩余未导入
		long fileSize = 0;//execl总大小
		long singleFileSize = 0;//单个文件大小 
		int time = 0;//计算导入时间
		long startTime = 0;//开始时间
		long endTime = 0;//结束时间
		long timePoor = 0;//时间差

		//HBaseAdmin admin = new HBaseAdmin(HConnectionFactory.getInstance());
		File fileDir = new File("d:\\new\\");
		File[] files = fileDir.listFiles();
		leng = files.length;//总共文件个数
		for (File file : files) {
			fileSize += file.length();//execl总大小
		}
		fileSize = fileSize / 1024;
		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			}
			String fileName = file.getName();
			if (!fileName.endsWith(".xlsx")) {
				continue;
			}
			if (fileName.indexOf("!") != -1) {
				//continue;
				//} else {
				startTime = System.currentTimeMillis();
				singleFileSize = file.length() / 1024;//单个文件大小kb
				//List<Map<String, String>> sourceData = new ArrayList<Map<String, String>>();
				fileName = fileName.substring(0, fileName.indexOf("!"));
				/*	if ("HDR_EMR_CONTENT".equals(fileName)) {
						continue;
						sourceData = sourceDatas.subList(0, 10000);
						putSourceDatas2Hbase(fileName, sourceData, 100);
						//sourceDatas=new ArrayList<Map<String, String>>();
						sourceData = sourceDatas.subList(10000, 20000);
						putSourceDatas2Hbase(fileName, sourceData, 100);
						//sourceDatas=new ArrayList<Map<String, String>>();
						sourceData = sourceDatas.subList(20000, sourceDatas.size() - 3);
						putSourceDatas2Hbase(fileName, sourceData, 100);

					}*/
				/*List<Map<String, String>> sourceDatas = readSourceDatasFromExcel(file);
				if (!admin.tableExists(fileName)) {//判断是否存在
					@SuppressWarnings("deprecation")
					HTableDescriptor tableDesc = new HTableDescriptor(fileName);
					tableDesc.addFamily(new HColumnDescriptor("cf"));
					admin.createTable(tableDesc);
				}
				*/
				fileSize -= singleFileSize;
				//putSourceDatas2Hbase(fileName, sourceDatas, 100);
				endTime = System.currentTimeMillis();
				timePoor = (endTime - startTime) / 1000;//当前文件耗时时间
				//当已经读取过的execl文件,将其名称改的,防止重复读取存入
				File file2 = new File("d:\\new\\" + file.getName()); //指定文件名及路径     
				String filename = file2.getAbsolutePath();
				if (filename.indexOf(".") >= 0) {
					filename = filename.substring(0, filename.lastIndexOf(":"));
					file2.renameTo(new File(filename + ":\\new\\" + file.getName().replace("!", "-"))); //改名    
					//time = (int) ((fileSize / (singleFileSize / timePoor)) - timePoor);
					//System.out.println("速度" + c + "秒" + "平均导入速度" + singleFileSize / c);
					count++;
					leng--;
					System.out.println("导入成功:" + file2.getName() + "已导入:" + count + "个！！！" + "剩余未导入:" + leng + "个！！！"
							+ "预计" + time + "秒导入完成");
				}
				System.gc();//
			}
		}
		System.out.println("导入成功！");
		//admin.close();
	}

	/**
	 * @Description
	 * 方法描述:将数据按batchSize批量放入tableName对应的HBase表中去
	 * @return 返回类型： void
	 * @param tableName
	 * @param sourceDatas
	 * @param batchSize
	 */
	private static void putSourceDatas2Hbase(String tableName, List<Map<String, String>> sourceDatas, int batchSize) {
		List<Put> puts = new ArrayList<Put>();
		int putsNum = 0;
		for (int i = 0; i < sourceDatas.size(); i++) {
			//将Map对象转换为PUT对象，并放入puts中，并将putsNum增加1
			Put put = mapToPut(sourceDatas.get(i));
			if (put != null) {
				puts.add(put);
				putsNum++;
			}
			//如果批数符合要求，则进行处理
			if (putsNum % batchSize == 0) {
				//对批进行保存
				putsToHbase(tableName, puts);
				//重新生成puts
				puts = new ArrayList<Put>();
			}
		}
		putsToHbase(tableName, puts);
	}

	/**
	 * @Description
	 * 方法描述:重写putsToHbase方法，可以自定义刷新方式和级别等设置
	 * @return 返回类型： void
	 * @param tableName
	 * @param puts
	 */
	private static void putsToHbase(String tableName, List<Put> puts) {
		HTableInterface htable = null;
		try {
			htable = HbaseCURDUtils.getHTable(tableName);
			htable.setAutoFlush(false, false);
			htable.setWriteBufferSize(1024 * 1024 * 20);
			htable.put(puts);
			htable.flushCommits();
		} catch (Exception e) {
			logger.error("通过htable写入缓存发生IO错误!", e);
		} finally {
			HbaseCURDUtils.releaseTable(htable);
		}
	}

	@SuppressWarnings("unused")
	private static long emptyMap = 0;

	private static Put mapToPut(Map<String, String> data) {
		//先判断是否有ROWKEY，没有ROWKEY的不做处理，返回NULL
		String rk = data.get("ROWKEY");
		if (StringUtils.isBlank(rk)) {
			emptyMap++;
			System.out.println("rowkey为空，略过一个Map");
			return null;
		}
		//ROWKEY不为空，生成PUT对象
		Put put = new Put(Bytes.toBytes(rk));
		//是否有内容进入PUT标识
		boolean added = false;
		String key = null;
		String value = null;
		for (Map.Entry<String, String> entry : data.entrySet()) {
			key = entry.getKey();
			//判断此ENTRY的KEY不为空并且部位ROWKEY
			if (StringUtils.isBlank(key) || "ROWKEY".equals(key)) {
				continue;
			}
			value = entry.getValue();
			//判断此ENTRY的VALUE不为空
			if (StringUtils.isBlank(value)) {
				continue;
			}
			//放入PUT并将放入PUT标识置为true
			put.add(Bytes.toBytes("cf"), Bytes.toBytes(key), Bytes.toBytes(value));
			if (!added) {
				added = true;
			}
		}
		//有内容放入的情况下，返回封装的PUT对象；否则返回NULL
		if (added) {
			return put;
		} else {
			emptyMap++;
		}
		return null;
	}

	/**
	 * @Description
	 * 方法描述:从EXCEL中获取数据并封装为Java的集合 List<Map<String,String>> 返回
	 * @return 返回类型： List<Map<String,String>>
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException 
	 */
	private static List<Map<String, String>> readSourceDatasFromExcel(File file) throws IOException,
			InvalidFormatException {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		//读取Excel文件，并生成WorkBook对象
		FileInputStream fis = new FileInputStream(file);
		Workbook wb = new XSSFWorkbook(fis);
		//new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);
		Row row = null;
		String key = null;
		String value = null;
		//依次遍历每一行，生成record对象，放入到result中去
		for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
			Map<String, String> record = new HashMap<String, String>();
			row = sheet.getRow(i);
			Iterator<Cell> itor = row.cellIterator();
			//依次遍历此ROW的每两个CELL，第一个为Key，第二个为Value
			while (itor.hasNext()) {
				key = itor.next().getStringCellValue();
				if (StringUtils.isBlank(key)) {
					//如果Key为空，则跳过value，直接进行下一键值对判断
					if (itor.hasNext()) {
						itor.next();
					}
					continue;
				}
				value = itor.next().getStringCellValue();
				if (StringUtils.isBlank(value)) {
					continue;
				}
				record.put(key, value);
			}
			result.add(record);
		}
		wb.close();
		return result;
	}
}
