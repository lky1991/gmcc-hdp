package com.ftpService.util;

import org.json.simple.JSONObject;

/**
* @ClassName: resultJson
* @Description: 工具类用于创建json对象
* @author  wally@hudongpai.com
* @date 2016-6-26 下午03:19:35
 */

@SuppressWarnings("unchecked")
public class ResultJson {
	
	/**
	* @Title: createResultMJSON
	* @Description: 创建一个json对象用于返回给客服端
	* @param resultcode表示返回的类型标识符
	* @return JSONObject    
	* @throws
	 */
	public JSONObject createResultJSON(Integer resultcode) {
		JSONObject jo = new JSONObject();
		try {
			jo.put("resultCode", resultcode);
			jo.put("resultMsg", ResultCode.getErrmsg(resultcode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
}
