package com.goodwill.cda.hlht.cda.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.goodwill.core.utils.PropertiesUtils;
import com.ncpcs.security.utils.MD5Util;

public class HttpClientPost {
	private static String CONFIG_FILE_NAME = "report.properties";

	public static String post(String ncMethodString, String ncVersionString, String jsonData) throws Exception {
		// 获取默认的请求客户端
		CloseableHttpClient client = HttpClients.createDefault();
		// 通过HttpPost来发送post请求
		String reporturl = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "report_url");
		HttpPost httpPost = new HttpPost(reporturl);
		/*
		 * post带参数开始
		 */
		// 第三步：构造list集合，往里面丢数据
		String appSecretString = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "app_secret");
		String ncAppidString = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "appid");//APPID
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String ncTimestampString = df.format(new Date());// 获取当前系统时间
		String ncHistoryString = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "history_type");//数据类型
		String ncCancelString = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "cancel_type");//取消区分
		//生成随机数
		Random r = new Random();
		StringBuilder rs = new StringBuilder();
		for (int i = 0; i < 14; i++) {
			rs.append(r.nextInt(10));
		}
		String ncNonceString = rs.toString();
		String ncBranchIDString = PropertiesUtils.getPropertyValue(CONFIG_FILE_NAME, "branch_id");//分院id
		String mergeStr = MD5Util.getMergeParam(ncAppidString, ncMethodString, ncTimestampString, ncVersionString,
				ncHistoryString, ncCancelString, ncNonceString, ncBranchIDString, appSecretString);
		String ncSignString = MD5Util.getMD5Digest(mergeStr);

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		BasicNameValuePair ncAppid = new BasicNameValuePair("ncAppid", ncAppidString);// APPID
		BasicNameValuePair ncMethod = new BasicNameValuePair("ncMethod", ncMethodString);// 接口名
		BasicNameValuePair ncTimestamp = new BasicNameValuePair("ncTimestamp", ncTimestampString);// 调用时间
		BasicNameValuePair ncVersion = new BasicNameValuePair("ncVersion", ncVersionString);// 接口版本
		BasicNameValuePair ncHistory = new BasicNameValuePair("ncHistory", ncHistoryString);// 数据类型
		BasicNameValuePair ncCancel = new BasicNameValuePair("ncCancel", ncCancelString);// 取消区分
		BasicNameValuePair ncNonce = new BasicNameValuePair("ncNonce", ncNonceString);// 随机数
		BasicNameValuePair ncBranchID = new BasicNameValuePair("ncBranchID", ncBranchIDString);// 分院id
		BasicNameValuePair ncData = new BasicNameValuePair("ncData", jsonData);// 数据实体
		BasicNameValuePair ncSign = new BasicNameValuePair("ncSign", ncSignString);// 签名

		list.add(ncAppid);
		list.add(ncMethod);
		list.add(ncTimestamp);
		list.add(ncVersion);
		list.add(ncHistory);
		list.add(ncCancel);
		list.add(ncNonce);
		list.add(ncBranchID);
		list.add(ncSign);
		list.add(ncData);
		// 第二步：我们发现Entity是一个接口，所以只能找实现类，发现实现类又需要一个集合，集合的泛型是NameValuePair类型
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"UTF-8");
		// 第一步：通过setEntity 将我们的entity对象传递过去
		httpPost.setEntity(formEntity);
		
		/*
		 * post带参数结束
		 */
		// HttpEntity
		// 是一个中间的桥梁，在httpClient里面，是连接我们的请求与响应的一个中间桥梁，所有的请求参数都是通过HttpEntity携带过去的
		// 通过client来执行请求，获取一个响应结果
		CloseableHttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String str = EntityUtils.toString(entity, "UTF-8");
		System.out.println(str);
		// 关闭
		response.close();
		client.close();
		return str;
	}
}
