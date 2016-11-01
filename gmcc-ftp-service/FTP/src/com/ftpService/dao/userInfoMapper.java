package com.ftpService.dao;

import com.ftpService.model.userInfo;

public interface userInfoMapper {
    int deleteByPrimaryKey(String ftpAccount);

    int insert(userInfo record);

    userInfo selectByPrimaryKey(String ftpAccount);

    int updateByPrimaryKey(userInfo record);
}