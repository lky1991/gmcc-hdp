package com.ftpService.service.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftpService.dao.ftpUserMapper;
import com.ftpService.dao.userInfoMapper;
import com.ftpService.model.ftpUser;
import com.ftpService.model.userInfo;
import com.ftpService.service.IUserService;
import com.ftpService.util.RandomCode;
import com.ftpService.util.ReadConfigUtil;
import com.ftpService.util.ResultCode;
import com.ftpService.util.ResultJson;
import com.ftpService.util.fileUtil;

@SuppressWarnings("unchecked")
@Service
public class userService implements IUserService {
	private static Log log = LogFactory.getLog(userService.class);
	public static String basePath = File.separator+"cmccfs";
	private ReadConfigUtil readConfig=new ReadConfigUtil("ftp.properties", true);

	@Autowired
	private ftpUserMapper ftpMapper;

	@Autowired
	private userInfoMapper infoMapper;
	
	private ResultJson resultJson = new ResultJson();// 用于创建返回前端的结果

	/**
	 * @Title: addUser
	 * @Description: 添加ftp用户
	 * @param userid 用户名
	 * @param password 用户密码
	 * @param homedirectory 用户的主目录
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String addUser(String company, String department, String application, String username, String homedirectory,String expire) {
		JSONObject result = new JSONObject();

		try {
			if (company.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.COMPANY_EMPTY);

			} else if (department.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.DEPARTMENT_EMPTY);

			} else if (application.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.APPLICATION_EMPTY);
			} else if (username.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.USERNAME_EMPTY);
			} else if (homedirectory.isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.HOMRDIRCTORY_EMPTY);
			}else if(!isNumeric(expire)){
				result=resultJson.createResultJSON(ResultCode.ILLEGAL_NUMBER);
			}
			else {
				String objectPath = basePath + File.separator + company + File.separator + department + File.separator + application + File.separator + username;
				
				if (null == ftpMapper.selectByHomeDirectory(objectPath)) {
					result = resultJson.createResultJSON(ResultCode.lIMITED_PERMISSION);
				} else {
					fileUtil.createDirectory(objectPath + File.separator + homedirectory);// 创建目录
					
					ftpUser fUser = new ftpUser();
					String account = RandomCode.getFtpAccount();// 创建ftp账号
					while (null != ftpMapper.selectByPrimaryKey(account)) {
						account = RandomCode.getFtpAccount();
					}

					fUser.setUserid(account);
					fUser.setUserpassword(RandomCode.getFtpPassword());
					fUser.setHomedirectory(objectPath + File.separator + homedirectory);
					fUser.setEnableflag(true);
					fUser.setWritepermission(false);
					fUser.setIdletime(0);
					fUser.setUploadrate(0);
					fUser.setDownloadrate(0);
					fUser.setMaxloginnumber(10);
					fUser.setMaxloginperip(10);

					userInfo uInfo = new userInfo();
					if (expire.trim().isEmpty()) {
						uInfo.setExpire(7);// 默认有效期
					} else {
						uInfo.setExpire(Integer.valueOf(expire));
					}
					uInfo.setFtpAccount(account);

					ftpMapper.insert(fUser);
					infoMapper.insert(uInfo);

					String internal_ip = readConfig.getValue("ftp.internal.ip");// 内网ip
					String external_ip = readConfig.getValue("ftp.external.ip");// 外网ip
					String ip = null;
					if (internal_ip.trim().isEmpty()) {
						ip = external_ip;
					} else {
						ip = internal_ip;
					}

					result = resultJson.createResultJSON(ResultCode.SUCCESS);
					result.put("account", fUser.getUserid());
					result.put("password", fUser.getUserpassword());
					result.put("ip", ip);
					result.put("port", readConfig.getValue("ftp.port"));
				}
			}

			return result.toString();
		} catch (Exception e) {
			log.info("添加ftp临时用户异常！！！！");
			result = resultJson.createResultJSON(ResultCode.UNKNOWERROR);
			return result.toString();
		}
	}

	/**
	 * @Title: deleteUser
	 * @Description: 删除ftp用户
	 * @param userid 用户名
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String deleteUser(String company, String department,String application, String username,String userid) {
		JSONObject result = new JSONObject();
		
		try {
			if (company.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.COMPANY_EMPTY);

			} else if (department.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.DEPARTMENT_EMPTY);
			} else if (application.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.APPLICATION_EMPTY);
			} else if (username.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.USERNAME_EMPTY);
			} else if (userid.isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.USERID_EMPTY);
			} else {
				String objectPath = basePath +File.separator + company + File.separator + department + File.separator + application + File.separator + username;

				if (null == ftpMapper.selectByHomeDirectory(objectPath)) {
					result = resultJson.createResultJSON(ResultCode.lIMITED_PERMISSION);
				} else {
					if (null == ftpMapper.selectByPrimaryKey(userid)) {// 判断该用户是否存在
						result = resultJson.createResultJSON(ResultCode.USERID_NO_EXIST);
					} else {
						ftpMapper.deleteByPrimaryKey(userid);
						infoMapper.deleteByPrimaryKey(userid);
						result = resultJson.createResultJSON(ResultCode.SUCCESS);
					}
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.info("删除用户异常！！！！");
			result = resultJson.createResultJSON(ResultCode.UNKNOWERROR);
			return result.toString();
		}
	}

	/**
	 * @Title: addDeveloperUser
	 * @Description: 创建开发者目录
	 * @param company
	 *            公司名
	 * @param department
	 *            部门
	 * @param application
	 *            应用
	 * @param username
	 *            用户名
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String addDeveloperUser(String company, String department, String application, String username) {
		JSONObject result = new JSONObject();

		try {
			if (company.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.COMPANY_EMPTY);

			} else if (department.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.DEPARTMENT_EMPTY);

			} else if (application.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.APPLICATION_EMPTY);
			} else if (username.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.USERNAME_EMPTY);
			} else {
				String objectPath = basePath + File.separator + company + File.separator + department + File.separator + application + File.separator + username;
				if (!fileUtil.createDirectory(objectPath)) {
					result = resultJson.createResultJSON(ResultCode.DIRCTORY_EXIST);
				} else {
					ftpUser fUser = new ftpUser();

					String account = RandomCode.getFtpAccount();// ftp账号
					while (null != ftpMapper.selectByPrimaryKey(account)) {
						account = RandomCode.getFtpAccount();
					}

					String password = RandomCode.getFtpPassword();// ftp密码
					fUser.setUserid(account);
					fUser.setUserpassword(password);
					fUser.setHomedirectory(objectPath);
					fUser.setEnableflag(true);
					fUser.setWritepermission(true);
					fUser.setIdletime(0);
					fUser.setUploadrate(0);
					fUser.setDownloadrate(0);
					fUser.setMaxloginnumber(10);
					fUser.setMaxloginperip(10);

					ftpMapper.insert(fUser);
					
					
					String internal_ip = readConfig.getValue("ftp.internal.ip");// 内网ip
					String external_ip = readConfig.getValue("ftp.external.ip");// 外网ip
					String ip = null;
					if (internal_ip.trim().isEmpty()) {
						ip = external_ip;
					} else {
						ip = internal_ip;
					}

					
					result = resultJson.createResultJSON(ResultCode.SUCCESS);
					result.put("account", account);
					result.put("password", password);
					result.put("ip", ip);
					result.put("port", readConfig.getValue("ftp.port"));
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.info("创建开发者目录失败！！！！");
			result = resultJson.createResultJSON(ResultCode.UNKNOWERROR);
			return result.toString();
		}

	}

	/**
	 * @Title: searchDirctory
	 * @Description: 目录查询
	 * @param company
	 *            公司名
	 * @param department
	 *            部门
	 * @param application
	 *            应用
	 * @param username
	 *            用户名
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String searchDirctory(String company, String department, String application, String username) {
		JSONObject result = new JSONObject();

		try {
			if (company.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.COMPANY_EMPTY);

			} else if (department.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.DEPARTMENT_EMPTY);

			} else if (application.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.APPLICATION_EMPTY);
			} else if (username.trim().isEmpty()) {
				result = resultJson.createResultJSON(ResultCode.USERNAME_EMPTY);
			} else {
				String objectPath = basePath + File.separator + company + File.separator + department +File.separator + application + File.separator + username;
				if (null == ftpMapper.selectByHomeDirectory(objectPath)) {
					result = resultJson.createResultJSON(ResultCode.lIMITED_PERMISSION);
				} else {
					LinkedList<String> files = fileUtil.getAllFiles(objectPath);
					JSONArray array = new JSONArray();
					for (String item : files) {
						array.add(item);
					}

					result = resultJson.createResultJSON(ResultCode.SUCCESS);
					result.put("files", array);
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.info("查询目录失败！！！！");
			result = resultJson.createResultJSON(ResultCode.UNKNOWERROR);
			return result.toString();
		}
	}

	@Override
	public String getToken() {
		JSONObject result = new JSONObject();

		try {
			String token = RandomCode.getToken();

			result = resultJson.createResultJSON(ResultCode.SUCCESS);
			result.put("token", token);

			return result.toString();
		} catch (Exception e) {
			log.info("token生成失败！！！！");
			result = resultJson.createResultJSON(ResultCode.UNKNOWERROR);
			return result.toString();
		}
	}
	
	/**
	* @Description: 判断字符串中是否有非数字的字符
	* @param str
	* @return boolean    返回类型
	 */
	private  boolean isNumeric(String str) {
		str = str.trim();
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
