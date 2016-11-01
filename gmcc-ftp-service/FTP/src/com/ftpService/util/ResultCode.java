package com.ftpService.util;

/**
* @ClassName: resultCode
* @Description: 工具类用于结果码到返回信息的映射
* @author wally@hudongpai.com
* @date 2016-6-26 下午03:12:06
*/
 
public class ResultCode {

	public static Integer SUCCESS = 0;
	public static Integer UNKNOWERROR = -1;
	
	
	//用户错误码10XXX
	public static Integer USERID_EMPTY = 10001;//用户名为空
	public static Integer USERID_EXIST=10002;//用户名已经存在
	public static Integer USERID_NO_EXIST=10003;//用户名不存在
	public static Integer PASSWORD_EMPTY=10004;//用户密码为空
	public static Integer HOMRDIRCTORY_EMPTY=10005;//用户主目录为空	
	
	public static Integer COMPANY_EMPTY=10006;//公司名为空
	public static Integer DEPARTMENT_EMPTY=10007;//部门名为空
	public static Integer APPLICATION_EMPTY=10008;//应用名为空
	public static Integer USERNAME_EMPTY=10009;//用户名为空
	public static Integer DIRCTORY_EXIST=10010;//目录已存在
	public static Integer lIMITED_PERMISSION=10011;//权限受限
	public static Integer ILLEGAL_NUMBER=10012;//非法数字

	
	
	
	

	// 根据resultCode返回相应的信息
	public static String getErrmsg(Integer sign) {
		String errmsg = "";
		switch (sign) {
			case 0:errmsg = "success";break;
			case -1:errmsg = "unknown error";break;
			
			//用户类
			case 10001:errmsg="userid is empty";break;
			case 10002:errmsg="userid exists";break;
			case 10003:errmsg="userid doesn't exist";break;
			case 10004:errmsg="password is empty";break;
			case 10005:errmsg="homedirctory is empty";break;
			case 10006:errmsg="company is empty";break;
			case 10007:errmsg="department is empty";break;
			case 10008:errmsg="application is empty";break;
			case 10009:errmsg="username is empty";break;
			case 10010:errmsg="directory exists";break;
			case 10011:errmsg="insufficient privilege";break;
			case 10012:errmsg="illegal number";break;
		
		}

		return errmsg;
	}

}
