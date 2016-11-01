package com.ftpService.util;

import org.junit.Test;

/**
* @ClassName: RandomCode
* @Description: 生成特定条件下的随机字符串
* @author wally@hudongpai.com
* @date 2016-6-27 上午10:52:53
*/
public class RandomCode {
	
	private static char[] code = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'0' };
	
	private static char[] num = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
	
	/**
	* @Description: 生成token(16位字符串)
	* @return String    返回类型
	*/
	public static  String getToken(){
		String str = "";
		for(Integer i=0;i<16;i++){
			str += code[(int)(Math.random()*62)];
		}
		return str;
	}
	
	/**
	* @Description: 生成 ftp账号
	* @return String    返回类型
	 */
	public static String getFtpAccount(){
		String str = "";
		for(Integer i=0;i<8;i++){
			str += code[(int)(Math.random()*62)];
		}
		return str;
	}
	
	/**
	* @Description: 生成ftp密码
	* @return String    返回类型
	 */
	public static String getFtpPassword(){
		String str = "";
		for(Integer i=0;i<7;i++){
			str += num[(int)(Math.random()*10)];
		}
		return str;
	}
	
	@Test
	public void test(){
		System.err.println(getToken());
		System.err.println(getFtpAccount());
		System.err.println(getFtpPassword());
	}
	
}