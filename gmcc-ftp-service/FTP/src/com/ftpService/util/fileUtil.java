package com.ftpService.util;

import static org.junit.Assert.*;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;

import org.junit.Ignore;
import org.junit.Test;

/**
* @ClassName: fileUtil  
* @Description: 文件操作的工具类
* @author wally@hudongpai.com
* @date 2016-6-27 下午05:01:20
 */
public class fileUtil {

	/**
	* @Description: 创建文件目录
	* @param  path  目录名称
	* @return boolean    返回类型
	 */
	public static boolean createDirectory(String path){
		boolean flag=true;
		File file=new File(path);
		if(!file.exists() && !file.isDirectory()){
			file.mkdirs();
		}else{
			flag=false;
		}
		return flag;
	}
	
	/**
	* @Description: 返回某一路径下所有文件
	* @param  path  路径名
	* @return LinkedList<String>    返回类型
	 */
	public static  LinkedList<String> getAllFiles(String path){
		LinkedList<String> result = new LinkedList<String>();
		LinkedList<File> list = new LinkedList<File>();
		
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			
			for (File file2 : files) {
				if (file2.isDirectory()) {
					list.add(file2);
				} else {
					result.add(file2.getName());
				}
			}
			
			File temp_file;
			while (!list.isEmpty()) {
				temp_file = list.removeFirst();
				files = temp_file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						list.add(file2);
					} else {
						result.add(file2.getName());
					}
				}
			}
		} else {
			System.err.println("文件夹不存在");
		}
		return result;
	}
	
	@Test
	@Ignore
	public void test(){
//		assertEquals(true,createDirectory("C:\\Program Files\\lky\\a\\b"));
//		LinkedList<String> temp=getAllFiles("C:\\cmccfs\\互动派");
//		for(String item:temp){
//			System.err.println(item);
//		}
		if(System.getProperties().getProperty("os.name").startsWith("Win")){
			System.err.println("windows");
		}else{
			System.err.println("Linux");
		}
	}
	
	@Test
	public void test1(){
		
		
	}
}
