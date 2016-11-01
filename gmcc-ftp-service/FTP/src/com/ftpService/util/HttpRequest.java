package com.ftpService.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.Test;

/**
* @ClassName: HttpRequest
* @Description: get，post请求
* @author wally@hudongpai.com
* @date 2016-6-27 上午11:05:46
 */

public class HttpRequest {

	/**
	* @Title: sendGet
	* @Description: get请求
	* @param url
	* @param param
	 */
	public static String sendGet(String url, Map<String, String> param) {
		BufferedReader bReader = null;
		StringBuffer sBuffer = new StringBuffer();
		String realUrl = url;

		try {
			
			if (param.size() > 0) {
				realUrl += "?";
				for (Entry<String, String> entry : param.entrySet()) {
					realUrl += entry.getKey() + "=" + entry.getValue() + "&";
				}
				realUrl = realUrl.substring(0, realUrl.length() - 1);
			}

			URL urlString = new URL(realUrl);
			URLConnection urlConnection = urlString.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection.setRequestMethod("GET");// 设置请求方法
			httpURLConnection.setConnectTimeout(30000);// 连接主机超时时间
			httpURLConnection.setReadTimeout(30000);// 读取数据超时时间
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置请求数据的格式
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");// 设置接收数据的编码

			// 判断连接是否异常
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}
			
			System.out.println(httpURLConnection.getResponseCode());
			for(Entry<String, List<String>>entry:httpURLConnection.getHeaderFields().entrySet()){
				System.out.println(entry.getKey()+"--------->"+entry.getValue());
			}

			bReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
			String line = null;
			while ((line = bReader.readLine()) != null) {
				sBuffer.append(line);
			}
		} catch (Exception e) {
			System.out.println("get 请求发生错误!!!");
			e.printStackTrace();
		} finally {
			if (bReader != null) {
				try {
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sBuffer.toString();
	}

    /**
    * @Title: sendPost
    * @Description: post请求
    * @param url
    * @param param
     */
	public static String sendPost(String url, Map<String, String> param) {
		BufferedReader bReader = null;
		OutputStreamWriter out = null;
		StringBuffer sBuffer = new StringBuffer();
		String parameterData ="";

		try {
			if (param.size() > 0) {
				for (Entry<String, String> entry : param.entrySet()) {
					parameterData += entry.getKey() + "=" + entry.getValue() + "&";
				}
				parameterData = parameterData.substring(0, parameterData.length() - 1);
			}

			URL realUrl = new URL(url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl.openConnection();

			httpURLConnection.setConnectTimeout(30000);
			httpURLConnection.setReadTimeout(30000);
			httpURLConnection.setRequestMethod("POST");

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setInstanceFollowRedirects(true);

			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置请求参数的格式
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");// 接收数据的编码
			
			if((parameterData.trim().length()>0) && (!parameterData.equals(""))){
				out = new OutputStreamWriter(httpURLConnection.getOutputStream(), "utf-8");
				out.write(parameterData);
				out.flush();
			}
			

			// 判断连接是否异常
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}
			
			System.out.println(httpURLConnection.getResponseCode());
			for(Entry<String, List<String>>entry:httpURLConnection.getHeaderFields().entrySet()){
				System.out.println(entry.getKey()+"--------->"+entry.getValue());
			}
			
//			bReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
//			String line = null;
//			while ((line = bReader.readLine()) != null) {
//				sBuffer.append(line);
//			}
			
			InputStream in=httpURLConnection.getInputStream();
			byte []data=new byte[httpURLConnection.getContentLength()];
			int offset=0;
			while(offset<in.available()){
				offset+=in.read(data, offset, in.available()-offset);
				System.out.println(offset);
			}
			sBuffer.append(new String(data,"utf-8"));
		} catch (Exception e) {
			System.out.println("post 请求失败！！！");
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (bReader != null) {
					bReader.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return sBuffer.toString();
	}

	@Test
	@Ignore
	public void testGet() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("tid", "1");
		System.out.println(sendGet("http://localhost:8080/shareboxes/record/getrecord/tid.do", param));
	}

	@Test
	public void testPost() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("company", "互动派");
		param.put("department", "数据业务部");
		param.put("application", "移动应用");
		param.put("username", "骆魁永");
		
		System.out.println(sendPost(" http://localhost:8081/FTP/user/adddeveloper.do", param));
	}

}
