package com.ftpService.util;

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
* @ClassName: JsonOperUtil
* @Description: json工具类
* @author wally@hudongpai.com
* @date 2016-6-27 上午11:04:37
 */
 
 
@SuppressWarnings("unchecked")
public class JsonUtil {

	/**
	 * @Title: getJsonArray
	 * @Description: 将字符串转化为json数组
	 * @param str
	 * @return
	 * @return 返回类型
	 */
	public static JSONArray getJsonArray(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}
		JSONParser parser = new JSONParser();
		try {
			return (JSONArray) (parser.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* @Title: getJsonObject
	* @Description: 将字符串转化为json对象
	* @param str
	 */
	public static JSONObject getJsonObject(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}
		JSONParser parser = new JSONParser();
		try {
			return (JSONObject) (parser.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* @Title: printJsonObject
	* @Description: 输出json对象的每一项
	* @param jsonObj   
	 */
	public static void printJsonObject(JSONObject jsonObj) {
		if (jsonObj == null) {
			return;
		}
		Set<String> keySet = jsonObj.keySet();
		for (String key : keySet) {
			System.out.println(key + "====>" + jsonObj.get(key));
		}
	}

	/**
	* @Title: fillResult
	* @Description: 填充返回前端的json
	* @param result
	 */
	public static JSONObject fillResult(String... result) {
		JSONObject json = new JSONObject();
		for (int i = 0; i < result.length; i += 2) {
			json.put(result[i], result[i + 1]);
		}
		return json;
	}
}
