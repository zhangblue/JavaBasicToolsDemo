package cn.com.zhangdi.ftp;

import java.io.File;

public class TestFTP {
    public static void main(String[] args) {
        FTPTools ftp = new FTPTools("192.168.3.153", "ftp_user", "ftp_user");
        ftp.upload(false, "/home/ftp_user/", new File("test.txt"));
    }
}
