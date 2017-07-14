package cn.com.zhangdi.system;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemTools
{
	/***
	 * 获取本机hostname
	 */
	public void getHostName()
	{
		try
		{
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println(inetAddress.getHostName());
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}
}
