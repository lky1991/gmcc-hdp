package com.ftpService.service;

public interface IUserService {
	/** 
	* @Description: 添加ftp临时用户
	* @param  company 	公司名
	* @param  department 部门
	* @param  application 应用来源
	* @param  username  操作人
	* @param  homedirectory 用户的主目录
	* @return String    返回类型
	*/
	public String addUser(String company, String department,String application, String username,String homedirectory,String  expire);
	
	
	
	/**
	* @Description: 删除ftp临时用户
	* @param company  公司名
	* @param department 相关部门
	* @param application  来源应用
	* @param username	 操作人
	* @param homedirectory  目标文件
	* @param userid		 ftp账号
	* @return String    返回类型
	 */
	public String deleteUser(String company, String department,String application, String username,String userid);
	
	
	/**
	* @Description: 添加开发者用户
	* @param company 公司名称
	* @param  department 相关部门
	* @param  application  来源应用
	* @param  username		操作人
	* @return String    返回类型
	*/
	public String addDeveloperUser(String company,String department,String application,String username);
	
	
	/**
	* @Description: 查看目录中所有文件
	* @param company 公司名称
	* @param  department 相关部门
	* @param  application  来源应用
	* @param  username		操作人
	* @return String    返回类型
	*/
	public String searchDirctory(String company,String department,String application,String username);
	
	/**
	* @Description: 获取token
	* @return String    返回类型
	*/
	public String getToken();
}
