package com.ftpService.model;

import java.util.Date;

/**
* @ClassName: 临时ftp用户的基本信息
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author wally@hudongpai.com
* @date 2016-6-30 上午11:17:00
 */
public class userInfo {
    private String ftpAccount;

    private Date registorTime;

    private Integer expire;

    public String getFtpAccount() {
        return ftpAccount;
    }

    public void setFtpAccount(String ftpAccount) {
        this.ftpAccount = ftpAccount == null ? null : ftpAccount.trim();
    }

    public Date getRegistorTime() {
        return registorTime;
    }

    public void setRegistorTime(Date registorTime) {
        this.registorTime = registorTime;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

	@Override
	public String toString() {
		return "userInfo [ftpAccount=" + ftpAccount + ", registorTime=" + registorTime + ", expire=" + expire + "]";
	}
    
}