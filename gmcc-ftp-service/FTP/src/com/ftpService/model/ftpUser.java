package com.ftpService.model;

/**
* @ClassName: ftpUser  
* @Description: ftp用户对于FTP server的权限管理
* @author wally@hudongpai.com
* @date 2016-6-27 上午11:44:31
 */
public class ftpUser {

	private String userid;

    private String userpassword;

    private String homedirectory;

    private Boolean enableflag;

    private Boolean writepermission;

    private Integer idletime;

    private Integer uploadrate;

    private Integer downloadrate;

    private Integer maxloginnumber;

    private Integer maxloginperip;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword == null ? null : userpassword.trim();
    }

    public String getHomedirectory() {
        return homedirectory;
    }

    public void setHomedirectory(String homedirectory) {
        this.homedirectory = homedirectory == null ? null : homedirectory.trim();
    }

    public Boolean getEnableflag() {
        return enableflag;
    }

    public void setEnableflag(Boolean enableflag) {
        this.enableflag = enableflag;
    }

    public Boolean getWritepermission() {
        return writepermission;
    }

    public void setWritepermission(Boolean writepermission) {
        this.writepermission = writepermission;
    }

    public Integer getIdletime() {
        return idletime;
    }

    public void setIdletime(Integer idletime) {
        this.idletime = idletime;
    }

    public Integer getUploadrate() {
        return uploadrate;
    }

    public void setUploadrate(Integer uploadrate) {
        this.uploadrate = uploadrate;
    }

    public Integer getDownloadrate() {
        return downloadrate;
    }

    public void setDownloadrate(Integer downloadrate) {
        this.downloadrate = downloadrate;
    }

    public Integer getMaxloginnumber() {
        return maxloginnumber;
    }

    public void setMaxloginnumber(Integer maxloginnumber) {
        this.maxloginnumber = maxloginnumber;
    }

    public Integer getMaxloginperip() {
        return maxloginperip;
    }

    public void setMaxloginperip(Integer maxloginperip) {
        this.maxloginperip = maxloginperip;
    }

	@Override
	public String toString() {
		return "ftpUser [userid=" + userid + ", userpassword=" + userpassword
				+ ", homedirectory=" + homedirectory + ", enableflag="
				+ enableflag + ", writepermission=" + writepermission
				+ ", idletime=" + idletime + ", uploadrate=" + uploadrate
				+ ", downloadrate=" + downloadrate + ", maxloginnumber="
				+ maxloginnumber + ", maxloginperip=" + maxloginperip + "]";
	}
    
}