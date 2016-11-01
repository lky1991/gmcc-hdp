package com.ftpService.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import org.junit.Test;


/**
* @ClassName: SerializingUtil
* @Description: 序列化工具类，负责byte[]和Object之间的相互转换.
* @author wally@hudongpai.com
* @date 2016-6-27 上午11:01:23
*/
public class SerializingUtil {
	public static Logger logger = Logger.getLogger("SerializingUtil");
	/**
	 * 功能简述: 对实体Bean进行序列化操作.
	 * @param source待转换的实体
	 * @return 转换之后的字节数组
	 * @throws Exception
	 */
	public static byte[] serialize(Object source) {
		ByteArrayOutputStream byteOut = null;
		ObjectOutputStream ObjOut = null;
		try {
			byteOut = new ByteArrayOutputStream();
			ObjOut = new ObjectOutputStream(byteOut);
			ObjOut.writeObject(source);
			ObjOut.flush();
		} catch (IOException e) {
			logger.info(source.getClass().getName() + " serialized error !");
		} finally {
			try {
				if (null != ObjOut) {
					ObjOut.close();
				}
			} catch (IOException e) {
				ObjOut = null;
			}
		}
		return byteOut.toByteArray();
	}

	/**
	 * 功能简述: 将字节数组反序列化为实体Bean
	 * @param source需要进行反序列化的字节数组
	 * @return 反序列化后的实体Bean
	 * @throws Exception
	 */
	public static Object deserialize(byte[] source) {
		ObjectInputStream ObjIn = null;
		Object retVal = null;
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(source);
			ObjIn = new ObjectInputStream(byteIn);
			retVal = ObjIn.readObject();
		} catch (Exception e) {
			logger.info("deserialized error  !");
		} finally {
			try {
				if (null != ObjIn) {
					ObjIn.close();
				}
			} catch (IOException e) {
				ObjIn = null;
			}
		}
		return retVal;
	}
	
	@Test
	public void test(){
		
		
	}
}