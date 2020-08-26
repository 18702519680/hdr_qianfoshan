package com.goodwill.cda.hlht.cda.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MergeJsonUtil {
	public static List<Object> getMergeJson(String jsonStr, int lineno) {
		JSONObject originalJsonData = JSONObject.parseObject(jsonStr);
		JSONArray dataArray = originalJsonData.getJSONArray("ncErrorDataList");
		List<JSONObject> jsonObjectList = new ArrayList<>();
		// 将原始数据中的json数组中的json对象存到一个list集合中
		for (Object o : dataArray) {
			jsonObjectList.add((JSONObject) o);
		}
		// 对集合中的json对象进行分组
		// 然后返回一个map集合，key代表组名，value代表该组中的数据
		Map<Object, List<Object>> groupByLineNumData = jsonObjectList.stream()
				.collect(Collectors.groupingBy(new Function<Object, Object>() {
					@Override
					public Object apply(Object x) {
						return ((JSONObject) x).getString("ncLineNo");
					}
				}));
		return groupByLineNumData.get(String.valueOf(lineno+1));
	}

}
