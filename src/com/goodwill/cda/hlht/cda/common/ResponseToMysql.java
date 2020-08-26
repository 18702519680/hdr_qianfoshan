package com.goodwill.cda.hlht.cda.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.goodwill.cda.util.MysqlUtil;

public class ResponseToMysql {
	protected static Logger logger = LoggerFactory.getLogger(ResponseToMysql.class);
	/**
	 *  反馈报文插入mysql 的数据库
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 */
	public static void ResponseToMysql(String requestString,String responseString,String uuid) {
		logger.info("===============初始化 mysql表hdr_logs_zlsb ，将反馈信息插入 mysql=====================");
		long start = System.currentTimeMillis();
		StringBuilder sql = new StringBuilder();
		String ncResultCode = null;
		String ncResultMsg = null;
		String ncErrorDataList = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String ncTimestampString = df.format(new Date());// 获取当前系统时间
		 try
		    {
		        JSONArray jsonArray= JSONArray.parseArray("["+responseString+"]");
		        for (int i=0; i < jsonArray.size(); i++)    {
		            JSONObject jsonObject = jsonArray.getJSONObject(i);
		            ncResultCode = jsonObject.getString("ncResultCode");
		            ncResultMsg = jsonObject.getString("ncResultMsg");
		            ncErrorDataList = jsonObject.getString("ncErrorDataList");
		        }
		    }
		    catch (Exception e)
		    {
		        e.printStackTrace();
		    }
	
		sql.append("insert into hdr_logs_zlsb (ncrequestMsg,ncResultCode,ncResultMsg,ncErrorDataList,uuid,createdtime,createdby,modifiedtime,modifiedby) values (");
		sql.append("'"+requestString+"'"+",");
		sql.append("'"+ncResultCode+"'"+",");
		sql.append("'"+ncResultMsg+"'"+",");
		sql.append("'"+ncErrorDataList+"'"+",");
		sql.append("'"+uuid+"'"+",");
		sql.append("'"+ncTimestampString+"'"+",");
		sql.append("'"+"1"+"'"+",");
		sql.append("'"+ncTimestampString+"'"+",");
		sql.append("'"+"1"+"'"+")");
		Statement statement = null;
		Connection conn = null;
		try {
			conn = MysqlUtil.GetConnection();
			if (conn == null) {
				for (int i = 0; i < 3; i++) {
					logger.error("获取mysql JDBC链接失败 。。。重新获取。。。");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						logger.error("在获取mysqlJDBC链接 过程中线程进行sleep错误。。。");
						e.printStackTrace();
					}
					try {
						conn = MysqlUtil.GetConnection();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (conn != null) {
						break;
					}
				}
			}
			if (conn == null) {
				logger.error("初始化 mysql表HDR_LOGS_ZLSB ，将上报日志数据数据插入 mysql 失败");
				return;
			}
			statement = conn.createStatement();
			 statement.execute(sql.toString());
		} catch (SQLException e) {
			logger.error("初始化 mysql表HDR_LOGS_ZLSB ，将上报日志数据数据插入 mysql 失败,执行SQL语句错误。。。");
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("=================将生成 上报日志数据数据插入mysql总执行时间====================="
					+ (((end - start) / 1000) / 60) + "分钟");
			logger.info("================ 将生成 上报日志数据数据插入mysql完成====================");
		}
	}
	
	
	
	public static String getErrorDataList(String responseString) throws JSONException {
		String ncErrorDataList = null;
		JSONArray jsonArray= JSONArray.parseArray(responseString);
		  for (int i=0; i < jsonArray.size(); i++)    {
	            JSONObject jsonObject = jsonArray.getJSONObject(i);
	             ncErrorDataList = jsonObject.getString("ncErrorDataList");
	        }
		return ncErrorDataList;
		
	}
	//通过反馈报文中lineno得到rowkey
	public static String getRowkeyByLineno(String requestString,int lineno) throws JSONException {
		String rowkey = null;
		JSONObject jSONObject = JSONObject.parseObject(requestString);
		JSONArray jsonArray= jSONObject.getJSONArray("dataList");
	            rowkey = jsonArray.getJSONObject(lineno).getString("rowKey");
	            
		  
		return rowkey;
		
	}
	//将反馈报文中错误的具体详细数据插入到日志表
	public static void ErrorDetailsToMysql(String requestString,String responseString,String uuid,String functionname) throws JSONException, SQLException {
		logger.info("===============初始化 mysql表hdr_logs_detail_zlsb ，将反馈信息插入 mysql=====================");
		long start = System.currentTimeMillis();
		int lineno;
		String rowkey= null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String timestampstring = df.format(new Date());// 获取当前系统时间
		String req_text;
		String resp_text;
			try {
				JSONObject jSONObject_req = JSONObject.parseObject(requestString);
				JSONArray jsonArray_req= jSONObject_req.getJSONArray("dataList");
				//List<JSONObject> jsonArray_resp = MergeJsonUtil.getMergeJson(responseString);
		        for (int i=0; i < jsonArray_req.size(); i++)    {
		            StringBuilder sql = new StringBuilder();
		            lineno = i;
		            req_text = jsonArray_req.get(i).toString();
		            
		            JSONObject originalJsonData = JSONObject.parseObject(responseString);
		    		JSONArray dataArray = originalJsonData.getJSONArray("ncErrorDataList");
		    		//调用成功标志
					String repost_flag = originalJsonData.getString("ncResultCode").equals("0")?"Y":"N";
		    		
		            resp_text=MergeJsonUtil.getMergeJson(responseString,lineno).toString();
		            rowkey = getRowkeyByLineno(requestString,lineno);
		            sql.append("insert into hdr_logs_detail_zlsb (rowkey,lineno,req_text,resp_text,uuid,repostflag,function_name,createdtime,createdby) values (");
					sql.append("'"+rowkey+"'"+",");
					sql.append("'"+(lineno+1)+"'"+",");
					sql.append("'"+req_text+"'"+",");
					sql.append("'"+resp_text+"'"+",");
					sql.append("'"+uuid+"'"+",");
					sql.append("'"+repost_flag+"'"+",");
					sql.append("'"+functionname+"'"+",");
					sql.append("'"+timestampstring+"'"+",");
					sql.append("'"+"1"+"'"+");");
					Statement statement = null;
		    		Connection conn = null;
		    		conn = MysqlUtil.GetConnection();
					statement = conn.createStatement();
					statement.execute(sql.toString());
					conn.close();
		        }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				long end = System.currentTimeMillis();
				logger.info("=================将生成 上报日志数据数据插入mysql总执行时间====================="
						+ (((end - start) / 1000) / 60) + "分钟");
				logger.info("================ 将生成 上报日志数据数据插入mysql完成====================");
			}
			
        }
	
	//得到需要重新上报的数据
	
	public static List<Map<String, String>> getReGenReportData() {
		
		logger.info("===============初始化 mysql表HDR_LOGS_DETAIL_ZLSB ，获取需要重新上报的数据=====================");
		long start = System.currentTimeMillis();
		StringBuilder sql = new StringBuilder();
		List<Map<String, String>> listSumm = new ArrayList<Map<String, String>>();
		sql.append("select * from hdr_logs_detail_zlsb a where a.reposttimes<=5 and a.repostflag='N' GROUP BY (rowkey)");
		Statement statement = null;
		Connection conn = null;
		ResultSet res= null;
		try {
			conn = MysqlUtil.GetConnection();
			if (conn == null) {
				for (int i = 0; i < 3; i++) {
					logger.error("获取mysql JDBC链接失败 。。。重新获取。。。");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						logger.error("在获取mysqlJDBC链接 过程中线程进行sleep错误。。。");
						e.printStackTrace();
					}
					try {
						conn = MysqlUtil.GetConnection();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (conn != null) {
						break;
					}
				}
			}
			if (conn == null) {
				logger.error("初始化 mysql表HDR_LOGS_DETAIL_ZLSB ，获取需要重新上报的数据 失败");
				return null;
			}
			 statement = conn.createStatement();
			res= statement.executeQuery(sql.toString());
			while (res.next()) {
				String rowkey = res.getString("rowkey");
				String functionname = res.getString("function_name");
				Map<String, String> map = new HashMap<String, String>();
				map.put("ROWKEY", rowkey);
				map.put("FUNCTIONNAME", functionname);
				listSumm.add(map);
			}
			
		} catch (SQLException e) {
			logger.error("初始化 mysql表HDR_LOGS_DETAIL_ZLSB ，获取需要重新上报的数据 失败");
			e.printStackTrace();
		} 
		finally {
			logger.info("================ 获取需要重新上报的数据完成====================");
		}
		return listSumm;
	}
	
	//错误数据重调时反馈报文处理
	public static void RePostResponseToMysql(String requestString,String responseString,String rowkey) {
		logger.info("===============初始化 mysql表hdr_logs_zlsb ，将反馈信息插入 mysql=====================");
		long start = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String timestampstring = df.format(new Date());// 获取当前系统时间
		ResultSet res= null;
		Statement statement = null;
		Connection conn = null;
		String times=null;
		String req_text=null;
		String resp_text=null;
		try {
			conn = MysqlUtil.GetConnection();
			if (conn == null) {
				for (int i = 0; i < 3; i++) {
					logger.error("获取mysql JDBC链接失败 。。。重新获取。。。");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						logger.error("在获取mysqlJDBC链接 过程中线程进行sleep错误。。。");
						e.printStackTrace();
					}
					try {
						conn = MysqlUtil.GetConnection();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (conn != null) {
						break;
					}
				}
			}
			if (conn == null) {
				logger.error("初始化 mysql表HDR_LOGS_ZLSB ，将上报日志数据数据插入 mysql 失败");
				return;
			}
			//调用成功标志
			JSONObject originalJsonData = JSONObject.parseObject(responseString);
			String repost_flag = originalJsonData.getString("ncResultCode").equals("0")?"Y":"N";
	        
			//先查出当前重调次数，请求报文，反馈报文
			 String query_sql="select * from hdr_logs_detail_zlsb w where w.rowkey='"+rowkey+"'";
			 statement = conn.createStatement();
			 res=statement.executeQuery(query_sql);
			 //conn.close();
			 while (res.next()) {
				 times=res.getString("reposttimes");
				  req_text=res.getString("req_text");
				  resp_text=res.getString("resp_text");
				}
			 //将重新调用的请求信息，反馈信息在之前的信息后面拼接
			 int times1=Integer.parseInt(times)+1;
			 StringBuilder update_sql = new StringBuilder();
			 update_sql.append("update hdr_logs_detail_zlsb  set ");
			 update_sql.append("reposttimes='"+times1+"',");
			 update_sql.append("req_text='"+req_text+"第"+times1+"次重新调用的请求报文："+requestString+"调用时间:"+timestampstring+";',");
			 update_sql.append("resp_text='"+resp_text+"第"+times1+"次重新调用的反馈报文"+responseString+"反馈时间:"+timestampstring+";',");
			 update_sql.append("repostflag='"+repost_flag+"',");
			 update_sql.append("modifiedby='1'"+",");
			 update_sql.append("modifiedtime='"+timestampstring+"' ");
			 update_sql.append("where rowkey='"+rowkey+"'");
			 statement = conn.createStatement();
			 statement.execute(update_sql.toString());
			 conn.close();
		} catch (SQLException e) {
			logger.error("初始化 mysql表HDR_LOGS_ZLSB ，将上报日志数据数据插入 mysql 失败,执行SQL语句错误。。。");
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("=================将生成 上报日志数据数据插入mysql总执行时间====================="
					+ (((end - start) / 1000) / 60) + "分钟");
			logger.info("================ 将生成 上报日志数据数据插入mysql完成====================");
		}
	}

}
