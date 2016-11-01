package com.ftpService.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


@SuppressWarnings("rawtypes")
public class getLocalIp {
	
	private static getLocalIp single=null;
	
	public static getLocalIp getInstance(){
		if(single==null){
			single=new getLocalIp();
		}
		return single;
	}
	
	public String getIp(){
		String result=null;
	
		Enumeration allNetInterfaces=null;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						if(!ip.getHostAddress().equals("127.0.0.1"))
						result=ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
