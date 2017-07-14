package cn.com.zhangdi.zip4j;

public class TestZip
{
	public static void main(String[] args)
	{
		ZipUtil zipUtil = new ZipUtil();
		String name = zipUtil.zip("/Users/zhangdi/test_fold/Log_Dpi.txt", "/Users/zhangdi/test_fold/", "123456");
		System.out.println(name);
	}
}
