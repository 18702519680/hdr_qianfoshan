package com.goodwill.cda.hlht.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CDADate {

	private static List<Map<String, String>> dateList = null;

	private static String NowDate = null;

	static {
		dateList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("start", "2018-03-14");
		map.put("end", "2018-03-15");
		dateList.add(map);
		Map<String, String> map0 = new HashMap<String, String>();
		map0.put("start", "2018-03-15");
		map0.put("end", "2018-03-16");
		dateList.add(map0);
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("start", "2018-03-16");
		map1.put("end", "2018-03-17");
		dateList.add(map1);
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("start", "2018-03-17");
		map2.put("end", "2018-03-18");
		dateList.add(map2);
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("start", "2018-03-18");
		map3.put("end", "2018-03-19");
		dateList.add(map3);

	}

	public static Map<String, String> getDate() {
		Map<String, String> map = null;
		if (dateList.size() > 0) {
			map = dateList.get(0);
			dateList.remove(0);
			setNowDate(map.get("start"));
		}
		return map;
	}

	public static String getNowDate() {
		Random random = new Random();
		int fen = Math.abs(random.nextInt()) % 60;
		Random random2 = new Random();
		int miao = Math.abs(random2.nextInt()) % 60;
		String nowDate = NowDate + " 21:" + (fen >= 10 ? fen : "0" + fen) + ":" + (miao >= 10 ? miao : "0" + miao);
		return nowDate;
	}

	public static void setNowDate(String nowDate) {
		NowDate = nowDate;
	}

}
