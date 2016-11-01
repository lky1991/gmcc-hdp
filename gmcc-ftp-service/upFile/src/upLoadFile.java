import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class upLoadFile {

	/**
	 * Description: 向FTP服务器指定的目录上传文件
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param path FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 */
	 
	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);//连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			
			ftp.setControlEncoding("utf-8");   
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.changeWorkingDirectory(path);
			ftp.storeFile(filename, input);		
			
			
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	
	public static void main(String[] args) {
		try {
			InputStream in=new FileInputStream(new File("C:\\cmccfs\\helloword.txt"));
//			String dir=File.separator+"cmccfs"+File.separator+"互动派"+File.separator+"数据业务部"+File.separator+"移动项目"+File.separator+"小赵"+File.separator+"ddd"+File.separator;
			String dir="ddd";
			System.err.println(uploadFile("vm1",2121, "Mh2SBalc","0394115",dir,"hello7.txt",in));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
