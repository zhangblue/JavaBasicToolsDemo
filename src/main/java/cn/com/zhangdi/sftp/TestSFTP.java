package cn.com.zhangdi.sftp;

import com.jcraft.jsch.SftpException;

import java.util.Vector;

public class TestSFTP
{
	public static void main(String[] args)
	{
		TestSFTP tsft = new TestSFTP();
		tsft.testDelete();
	}

	// 测试上传
	private void testUpload()
	{
		SFTPTool sftptool = new SFTPTool("192.168.90.1", "user1", "user1", 22);

		if (sftptool.connect())
		{
			sftptool.upload("/testsftp", "/Users/zhangdi/test_fold/Log_Dpi.zip");
		}
		sftptool.disconnect();
	}

	// 测试下载
	private void testDownload()
	{
		SFTPTool sftptool = new SFTPTool("192.168.90.1", "user1", "user1", 22);

		if (sftptool.connect())
		{
			sftptool.download("/testsftp", "Log_Dpi.zip", "/Users/zhangdi/test_fold/ttt.zip");
		}

		sftptool.disconnect();
	}

	// 测试删除
	private void testDelete()
	{
		SFTPTool sftptool = new SFTPTool("192.168.90.1", "user1", "user1", 22);
		if (sftptool.connect())
		{
			sftptool.delete("/testsftp", "Log_Dpi.zip");
		}
		sftptool.disconnect();
	}

	// 测试列表
	private void testLS()
	{
		SFTPTool sftptool = new SFTPTool("192.168.90.1", "user1", "user1", 22);

		if (sftptool.connect())
		{
			try
			{
				Vector vv = sftptool.listFiles("/testsftp");
				System.out.println("文件列表=" + vv.size());
				if (vv != null)
				{
					for (int ii = 0; ii < vv.size(); ii++)
					{
						// System.out.println("file name =" +
						// vv.elementAt(ii).toString());
						Object obj = vv.elementAt(ii);
						if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry)
						{
							System.out.println(((com.jcraft.jsch.ChannelSftp.LsEntry) obj).getAttrs());
						}
					}
				}

			} catch (SftpException e)
			{
				e.printStackTrace();
			}
		}
		sftptool.disconnect();
	}
}
