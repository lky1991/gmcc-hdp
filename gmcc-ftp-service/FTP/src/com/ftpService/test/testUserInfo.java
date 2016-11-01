package com.ftpService.test;


import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ftpService.dao.userInfoMapper;
import com.ftpService.model.userInfo;
import com.ftpService.util.RandomCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoConfig.xml")
public class testUserInfo {
	@Autowired
	private userInfoMapper infoMapper;
	
	@Test
	@Ignore
	public void testSelectBySid(){
		System.err.println(infoMapper.selectByPrimaryKey("vgATVy0Q"));
	}
	
	
	@Test
	@Ignore
	public void testAddUser(){
		userInfo user=new userInfo();
		user.setExpire(7);
		user.setFtpAccount(RandomCode.getFtpAccount());
		
		assertEquals("插入失败",1,infoMapper.insert(user));
	}
	
	
	@Test
	public void testDeleteUser(){
		assertEquals("删除失败",1,infoMapper.deleteByPrimaryKey("vgATVy0Q"));
	}
	
	@Test
	@Ignore
	public void testUpdateUser(){
		
	}
	

	
}
