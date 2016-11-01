package com.ftpService.dao;

import com.ftpService.model.ftpUser;

public interface ftpUserMapper {
	

	/**
	* @Description: 删除ftp账号
	* @param userid  用户名
	* @return int    返回类型
	*/
    int deleteByPrimaryKey(String userid);
    
    
    /**
    * @Description:  添加ftp账号
    * @param record   用户对象
    * @return int    返回类型
    */
    int insert(ftpUser record);
    
    
    /**
    * @Description: 查询ftp账号
    * @param  userid 账号名称
    * @return ftpUser    返回类型
    */
    ftpUser selectByPrimaryKey(String userid);
    
    
    /**
    * @Description: 查询ftp账号
    * @param  homedirectory  ftp账号的home目录
    * @return ftpUser    返回类型
    */
    ftpUser selectByHomeDirectory(String homedirectory);
    
    /**
    * @Description: 更新ftp账号
    * @param record
    * @return int    返回类型
    */
    int updateByPrimaryKey(ftpUser record);
}