package com.goodwill.cda.documentRetrieve;

/**
 * @class 定义接口
 * @author 计彬
 * @Date 2016年9月14日
 *281071
 */
public abstract class TransHbaseDataByExcel {
	public abstract Long  getExportBatchSize();
	public abstract Long getImportBatchSize();
	public abstract String[] getRKPrefixs();
	public abstract String[] getTableNames();
	public abstract String getImportDir(String str);
}
