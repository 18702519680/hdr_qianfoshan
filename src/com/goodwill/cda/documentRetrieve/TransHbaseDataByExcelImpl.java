package com.goodwill.cda.documentRetrieve;

import org.apache.hadoop.hbase.client.HBaseAdmin;

import com.goodwill.hadoop.hbase.HConnectionFactory;

/**
 * @class 接口实现类
 * @author 计彬
 * @Date 2016年9月14日
 *281071
 */
public class TransHbaseDataByExcelImpl extends TransHbaseDataByExcel {
	public static void main(String[] args) {
		TransHbaseDataByExcelImpl t = new TransHbaseDataByExcelImpl();
	}

	//获取导入数据长度，由于数据过大会导致插入execl表会内存溢出，默认三万条数据插入一张表
	@Override
	public Long getExportBatchSize() {
		return 30000l;
	}

	//重载方法，自定义长度 插入一张execl表格
	public Long getExportBatchSize(long size) {
		return size;
	}

	//生成插入数据长度，由于导入数据过大 有可能会内存溢出，默认三万条数据插入一次
	@Override
	public Long getImportBatchSize() {

		return 30000l;
	}

	//重载方法，自定义长度插入一次
	public Long getImportBatchSize(long size) {

		return size;
	}

	//生成rowkey前缀,每张表按13中rowkey前缀进行导数
	@Override
	public String[] getRKPrefixs() {
		String str = "2345,3456,5678,6789,7890,0000,0002,0003,0004,0005,0006";
		return str.split(",");
	}

	//重载方法，自定义rowkey进行导数
	public String[] getRKPrefixs(String... str) {
		return str;
	}

	//获取全部表名称
	@SuppressWarnings("resource")
	@Override
	public String[] getTableNames() {
		HBaseAdmin admin;

		try {
			admin = new HBaseAdmin(HConnectionFactory.getInstance());
			@SuppressWarnings("deprecation")
			String table[] = admin.getTableNames();
			return table;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//重载方法，自定义传入表名
	public String[] getTableNames(String... str) {
		return str;
	}

	//根据传入的文件夹名称 就行读取execl表数据,默认在c盘
	@Override
	public String getImportDir(String str) {
		return "c://" + str + "//";
	}

}
