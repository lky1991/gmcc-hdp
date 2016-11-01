package com.ftpService.test;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.Test;

import com.ftpService.util.upLoadFile;

/**
 * @ClassName: testUpLoadFile
 * @Description: 测试文件上传
 * @author wally@hudongpai.com
 * @date 2016-7-1 上午09:31:47
 */
public class testUpLoadFile {

	@Test
	public void testUpLoad() {
			String hostname = "vm1";// ftp的hostname
			int port = 2121; // ftp的端口号
			String ftpAccount = "Mh2SBalc";// ftp的账号
			String ftpPassword = "0394115";// ftp的密码
			String dir = "ddd";// 待上传的目录
			String localFileName="test.txt";//本地文件名
			String fileName = "2.txt";// 上传后文件的名称
			InputStream in = testUpLoadFile.class.getClassLoader().getResourceAsStream(localFileName);
			assertEquals("上传失败",true,upLoadFile.uploadFileToDir(hostname, port, ftpAccount, ftpPassword, dir, fileName, in));
	}

}
