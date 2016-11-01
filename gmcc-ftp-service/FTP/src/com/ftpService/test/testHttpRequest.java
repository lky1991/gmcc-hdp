package com.ftpService.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.ftpService.util.HttpRequest;

public class testHttpRequest {
	/**
	* @Description: 测试（目录创建）
	 */
	@Test
	@Ignore
	public void testCreateDirectory() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("company", "省移动");
		param.put("department", "数据业务部");
		param.put("application", "能力开放平台项目");
		param.put("username", "小李");
		
		System.out.println(HttpRequest.sendPost(" http://vm1:8081/FTP/user/adddeveloper.do", param));
	}
	
	/**
	* @Description: 测试（Token的获取）
	*/
	@Test
	@Ignore
	public void testGetToken(){
		Map<String, String> param = new HashMap<String, String>();
		System.out.println(HttpRequest.sendPost(" http://vm1:8081/FTP/user/gettoken.do", param));
	}
	
	/**
	* @Description: 测试（目录查询）
	*/
	@Test
	@Ignore
	public void testSearchDirectory(){
		Map<String, String> param = new HashMap<String, String>();
		param.put("company", "互动派");
		param.put("department", "数据业务部");
		param.put("application", "电信项目");
		param.put("username", "小王");
		
		System.out.println(HttpRequest.sendPost(" http://vm1:8081/FTP/user/searchdirectory.do", param));
	}
	
	
	/**
	* @Description: 测试(添加临时ftp用户)
	 */
	@Test
	public void testAddFtpUser(){
		Map<String, String> param = new HashMap<String, String>();
		param.put("company", "省移动");
		param.put("department", "数据业务部");
		param.put("application", "能力开放平台项目");
		param.put("username", "小李");
		param.put("homedirectory","ddd");
		param.put("expire", "");
		
		System.out.println(HttpRequest.sendPost(" http://vm1:8081/FTP/user/adduser.do", param));
	}
	
	
	/**
	* @Description: 测试（删除临时的ftp用户）
	*/
	@Test
	@Ignore
	public void testDeleteFtpUser(){
		Map<String, String> param = new HashMap<String, String>();
		param.put("company", "互动派");
		param.put("department", "数据业务部");
		param.put("application", "华为项目");
		param.put("username", "小赵");
		param.put("userid","R9g4BzDZ");
		
		System.out.println(HttpRequest.sendPost(" http://vm1:8081/FTP/user/deleteuser.do", param));
	}
	
	

}
