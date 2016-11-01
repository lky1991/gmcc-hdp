package com.ftpService.test;


import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ftpService.dao.ftpUserMapper;
import com.ftpService.model.ftpUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoConfig.xml")
public class testFtpUser {
	@Autowired
	private ftpUserMapper fMapper;
	
	@Test
	@Ignore
	public void testSelectBySid(){
		System.err.println(fMapper.selectByPrimaryKey("lky"));
	}
	
	@Test
	public void testSelectByDirectory(){
		System.err.println(fMapper.selectByHomeDirectory("/home/wally"));
	}
	
	@Test
	@Ignore
	public void testAddUser(){
		ftpUser user=new ftpUser();
		user.setUserid("lky");
		user.setUserpassword("lky");
		user.setHomedirectory("./res/home/lky");
		user.setEnableflag(true);
		user.setWritepermission(false);
		user.setDownloadrate(0);
		user.setIdletime(0);
		user.setMaxloginnumber(0);
		user.setMaxloginperip(0);
		user.setUploadrate(0);
		
		assertEquals("插入失败",1,fMapper.insert(user));
	}
	
	
	@Test
	@Ignore
	public void testDeleteUser(){
		assertEquals("删除错误",1,fMapper.deleteByPrimaryKey("lky"));
	}
	
	@Test
	@Ignore
	public void testUpdateUser(){
		ftpUser user=fMapper.selectByPrimaryKey("lky");
		user.setUserpassword("hdp");
		fMapper.updateByPrimaryKey(user);
		System.err.println("更新数据成功");
	}
	

	
}
