package cn.com.zhangdi.sftp;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * sftp 工具
 */
public class SFTPTool {
    private String strHostAddress = "";
    private String strUserName = "";
    private String strPassword = "";
    private int nPort = 22;

    private ChannelSftp sftp = null;
    private Channel channel = null;
    private Session sshSession = null;

    public SFTPTool(String strHostAddress, String strUserName, String strPassword) {
        super();
        this.strHostAddress = strHostAddress;
        this.strUserName = strUserName;
        this.strPassword = strPassword;
    }

    public SFTPTool(String strHostAddress, String strUserName, String strPassword, int nPort) {
        super();
        this.strHostAddress = strHostAddress;
        this.strUserName = strUserName;
        this.strPassword = strPassword;
        this.nPort = nPort;
    }

    /***
     * 链接sftp服务器
     *
     * @return
     */
    public boolean connect() {
        boolean bFlage = false;
        JSch jsch = new JSch();
        try {
            jsch.getSession(strUserName, strHostAddress, nPort);
            sshSession = jsch.getSession(strUserName, strHostAddress, nPort);
            sshSession.setPassword(strPassword);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            bFlage = true;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return bFlage;
    }

    /***
     * 上传文件
     *
     * @param directory
     *            上传目录
     * @param uploadFile
     *            要上传的文件
     */
    public void upload(String directory, String uploadFile) {
        try {
            sftp.cd(directory);
            File file = new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName(), ChannelSftp.OVERWRITE);
            System.out.println("上传成功");
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * 下载文件
     *
     * @param directory
     *            下载目录
     * @param downloadFile
     *            下载的文件
     * @param saveFile
     *            存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
            System.out.println("下载成功");
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * 删除文件
     *
     * @param directory
     *            要删除的文件所在的目录
     * @param deleteFile
     *            要删除的文件
     */
    public void delete(String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
            System.out.println("删除成功");
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /***
     * 列出目录下的文件
     *
     * @param directory
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    /***
     * 关闭连接
     *
     */
    public void disconnect() {
        if (null != sftp && sftp.isConnected()) {
            sftp.disconnect();
        }
        if (null != channel && channel.isConnected()) {
            channel.disconnect();
        }
        if (null != sshSession && sshSession.isConnected()) {
            sshSession.disconnect();
        }
    }
}
