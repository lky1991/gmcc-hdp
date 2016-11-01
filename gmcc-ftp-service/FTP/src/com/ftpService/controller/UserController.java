package com.ftpService.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftpService.service.IUserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Inject
	private IUserService uService;
	
	//添加ftp临时用户
	@RequestMapping(value = "adduser.do", produces = "text/json;charset=UTF-8", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String addUser(String company, String department,String application, String username,String homedirectory,String expire) {
		return uService.addUser(company, department, application, username, homedirectory, expire);
	}

	//删除ftp临时用户
	@RequestMapping(value = "deleteuser.do", produces = "text/json;charset=UTF-8", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String deleteUser(String company, String department,String application, String username,String userid) {
		return uService.deleteUser(company, department, application, username, userid);
	}
	
	
	//获取token
	@RequestMapping(value = "gettoken.do", produces = "text/json;charset=UTF-8", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getToken() {
		return uService.getToken();
	}
	
	//创建开发者目录
	@RequestMapping(value = "adddeveloper.do", produces = "text/json;charset=UTF-8", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String addDeveloper(String company, String department,String application, String username) {
		return uService.addDeveloperUser(company, department, application, username);
	}
	
	//查询目录
	@RequestMapping(value = "searchdirectory.do", produces = "text/json;charset=UTF-8", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String searchDirectory(String company, String department,String application, String username) {
		return uService.searchDirctory(company, department, application, username);
	}
	
	
	
}
