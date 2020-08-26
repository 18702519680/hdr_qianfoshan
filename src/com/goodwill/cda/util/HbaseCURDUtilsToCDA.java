package com.goodwill.cda.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.goodwill.core.orm.PropertyFilter;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * Helper class featuring methods for Hbase table handling and exception translation. 
 * 
 * @author Costin Leau
 */
/**
 * @Description
 * 类描述：
 * @author malongbiao
 * @Date 2015年10月25日
 * @modify
 * 修改记录：
 * 
 */
public class HbaseCURDUtilsToCDA extends HbaseCURDUtils {

	/**
	 * @Description
	 * 方法描述: 先根据rowkey前缀进行过滤，然后按照condition条件进行筛选数据
	 * propertyFilter的matchtype支持：=,>,>=,<,<=,like,nlike,<>,in,nin(not in),sw(startWith),ew(endWith)
	 * @param tablename 表名称
	 * @param rowkeyprefix rowkey前缀
	 * @param filters 过滤条件 PorpertyFilter的List
	 * @param columns 需要输出的列名List
	 * @return 返回值 list 存放键值对map
	 */
	public static List<Map<String, String>> findByCondition(String tablename, String rowkeyprefix,
			List<PropertyFilter> filters, final String... columns) {

		byte[] startRow = (rowkeyprefix).getBytes(getCharset());
		byte[] stopRow = (rowkeyprefix + "}").getBytes(getCharset());
		@SuppressWarnings("deprecation")
		HTableInterface htable = null;
		// Filter filter = new PrefixFilter(Bytes.toBytes(rowkeyprefix));
		Scan scan = new Scan();
		scan.setStartRow(startRow);
		scan.setStopRow(stopRow);

		for (String column : columns) {
			scan.addColumn(Bytes.toBytes(getDefaultFamilyName()), column.getBytes(getCharset()));
		}
		//查询条件添加到返回列中
		if (columns != null && columns.length > 0) {
			for (PropertyFilter filter : filters) {
				scan.addColumn(Bytes.toBytes(getDefaultFamilyName()), filter.getPropertyName().getBytes(getCharset()));
			}
		}
		//过滤条件
		if (filters != null && filters.size() > 0) {
			scan.setFilter(genHbaseFilters(filters));
		}

		ResultScanner scanner = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			htable = HbaseCURDUtilsToCDA.getHTable(tablename);
			scanner = htable.getScanner(scan);
			for (Result result : scanner) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(ROWKEY, new String(result.getRow()));
				for (Cell cell : result.rawCells()) {
					map.put(new String(CellUtil.cloneQualifier(cell)),
							takeOutSpecialString(new String(CellUtil.cloneValue(cell), getCharset())));
				}
				list.add(map);
			}
		} catch (IOException e) {
			logger.error("通过htable获取Scan发生IO错误!", e);
			System.err.println(e);
		} finally {
			scan = null;
			if (scanner != null)
				scanner.close();
			HbaseCURDUtilsToCDA.releaseTable(htable);
		}
		return list;
	}

	/**
	 * 去掉文本中的特殊字符串 比如 ：&amp; &lt; 等
	 * @Description
	 * 方法描述:
	 * @return 返回类型： String
	 * @param text
	 * @return
	 */
	public static String takeOutSpecialString(String text) {
		if (StringUtils.isBlank(text)) {
			return text;
		}
		text = text.replaceAll("[↑↓]", "");
		text = text.replaceAll("&lt;", "");
		text = text.replaceAll("&gt;", "");
		text = text.replaceAll("&nbsp;", "");
		text = text.replaceAll("&amp;", "");
		text = text.replaceAll(">", "");
		text = text.replaceAll("<", "");
		//		text = text.replaceAll("\\[", " ");
		//		text = text.replaceAll("]", "");
		text = text.replaceAll("\\(", "");
		text = text.replaceAll("\\)", "");
		return text;
	}
}