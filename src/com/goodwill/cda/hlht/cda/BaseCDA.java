package com.goodwill.cda.hlht.cda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.goodwill.cda.util.HbaseCURDUtilsToCDA;
import com.goodwill.cda.util.Xmlutil;

public abstract class BaseCDA {
	public abstract String genTargetDocument(Map<String, String> visitInfo, List<Map<String, String>> diagList);

	/**
	 * @Description
	 * 方法描述:传入patientId,visitId 查询病案首页和病案首页诊断信息，供测试使用
	 * @param patientId
	 * @param visitId
	 * @return
	 */
	
	protected static Object[] getPatInfos(String patientId, String visitId) {
		List<Map<String, String>> patInfos = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyPrefix(
				"HDR_INP_SUMMARY", HbaseCURDUtilsToCDA.getRowkeyPrefix(patientId) + "|" + visitId));
		Map<String, String> patInfo = patInfos.isEmpty() ? new HashMap<String, String>() : patInfos.get(0);
		//获取诊断信息列表
		List<Map<String, String>> diagList = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyPrefix(
				"HDR_INP_SUMMARY_DIAG", HbaseCURDUtilsToCDA.getRowkeyPrefix(patientId) + "|" + visitId));
		Object[] results = new Object[3];
		results[0] = patInfo;
		results[1] = diagList;
		List<Map<String, String>> emrDiagList = Xmlutil.formatList(HbaseCURDUtilsToCDA.findByRowkeyPrefix(
				"HDR_EMR_CONTENT_DIAG", HbaseCURDUtilsToCDA.getRowkeyPrefix(patientId) + "|" + visitId));
		results[2] = emrDiagList;
		return results;
	}

}
