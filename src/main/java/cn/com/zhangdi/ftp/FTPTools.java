package cn.com.zhangdi.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

public class FTPTools {
    private String strRemoteHost;
    private String strUserName;
    private String strPassword;
    private int nPort = 21;

    public FTPTools(String strRemoteHost, String strUserName, String strPassword) {
        super();
        this.strRemoteHost = strRemoteHost;
        this.strUserName = strUserName;
        this.strPassword = strPassword;
    }

    public FTPTools(String strRemoteHost, String strUserName, String strPassword, int nPort) {
        super();
        this.strRemoteHost = strRemoteHost;
        this.strUserName = strUserName;
        this.strPassword = strPassword;
        this.nPort = nPort;
    }

    /***
     * 上传单个文件
     *
     * @param delSrc
     *            上传完成后是否要删除本地文件
     * @param strRemotePath
     *            远端文件目录
     * @param fileSrc
     *            待上传的文件
     */
    public void upload(boolean delSrc, String strRemotePath, File fileSrc) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(strRemoteHost, nPort);
            ftp.login(strUserName, strPassword);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                System.out.println("FTP server refused connection.");
            } else {
                // 设置参数，必须在login之后进行se操作，否则会有问题
                ftp.enterLocalPassiveMode();
                ftp.setBufferSize(1024);
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftp.setControlEncoding("UTF-8");

                // 目录不存在
                if (!ftp.changeWorkingDirectory(strRemotePath)) {
                    // 创建目录
                    if (createDirectory(strRemotePath, ftp)) {
                        if (ftp.changeWorkingDirectory(strRemotePath)) {
                            if (ftp.storeFile(fileSrc.getName(), (new FileInputStream(fileSrc)))) {
                                System.out.println("upload success: [" + fileSrc.getName() + "]");
                                // 删除本地文件
                                if (delSrc) {
                                    fileSrc.delete();
                                }
                            } else {
                                System.out.println("upload fail: [" + fileSrc.getName() + "]");
                            }
                        }
                    } else {
                        System.out.println("create FTP path fail!");
                    }
                } else {
                    if (ftp.changeWorkingDirectory(strRemotePath)) {
                        if (ftp.storeFile(fileSrc.getName(), (new FileInputStream(fileSrc)))) {
                            System.out.println("upload success: [" + fileSrc.getName() + "]");
                            // 删除本地文件
                            if (delSrc) {
                                fileSrc.delete();
                            }
                        } else {
                            System.out.println("upload fail: [" + fileSrc.getName() + "]");
                        }
                    }
                }
                ftp.logout();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * 上传单个文件
     *
     * @param delSrc
     *            上传成功后是否要删除本地文件
     * @param strRemotePath
     *            远端目录
     * @param fileSrc
     *            待上传的目录
     * @param strRename
     *            上传的文件名
     */
    public void upload(boolean delSrc, String strRemotePath, File fileSrc, String strRename) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(strRemoteHost, nPort);
            ftp.login(strUserName, strPassword);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                System.out.println("FTP server refused connection.");
            } else {
                // 设置参数，必须在login之后进行se操作，否则会有问题
                ftp.enterLocalPassiveMode();
                ftp.setBufferSize(1024);
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftp.setControlEncoding("UTF-8");
                // 目录不存在
                if (!ftp.changeWorkingDirectory(strRemotePath)) {
                    // 创建目录
                    if (createDirectory(strRemotePath, ftp)) {
                        if (ftp.changeWorkingDirectory(strRemotePath)) {
                            if (ftp.storeFile(strRename, (new FileInputStream(fileSrc)))) {
                                System.out.println("upload success: [" + fileSrc.getName() + "]");
                                // 删除本地文件
                                if (delSrc) {
                                    fileSrc.delete();
                                }
                            } else {
                                System.out.println("upload fail: [" + fileSrc.getName() + "]");
                            }
                        }
                    } else {
                        System.out.println("create FTP path fail!");
                    }
                }
                ftp.logout();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * ftp上传文件列表
     *
     * @param delSrc
     *            上传成功后是否要删除本地文件
     * @param strRemotePath
     *            远端文件目录
     * @param listFileSrc
     *            待上传的文件列表
     */
    public void upload(boolean delSrc, String strRemotePath, List<File> listFileSrc) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(strRemoteHost, nPort);
            ftp.login(strUserName, strPassword);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                System.out.println("FTP server refused connection.");
            } else {
                // 设置参数，必须在login之后进行se操作，否则会有问题
                ftp.enterLocalPassiveMode();
                ftp.setBufferSize(1024);
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftp.setControlEncoding("UTF-8");
                // 目录不存在
                if (!ftp.changeWorkingDirectory(strRemotePath)) {
                    // 创建目录
                    if (createDirectory(strRemotePath, ftp)) {
                        if (ftp.changeWorkingDirectory(strRemotePath)) {
                            for (File fileSrc : listFileSrc) {
                                if (ftp.storeFile(fileSrc.getName(), (new FileInputStream(fileSrc)))) {
                                    System.out.println("upload success: [" + fileSrc.getName() + "]");
                                    if (delSrc) {
                                        fileSrc.delete();
                                    }
                                } else {
                                    System.out.println("upload fail: [" + fileSrc.getName() + "]");
                                }
                            }
                        }
                    } else {
                        System.out.println("create FTP path fail!");
                    }
                }
                ftp.logout();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * ftp上传文件列表
     *
     * @param delSrc
     *            删除本地目录
     * @param strRemotePath
     *            远端文件目录
     * @param aFileSrc
     *            待上传的文件列表
     */
    public void upload(boolean delSrc, String strRemotePath, File[] aFileSrc) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(strRemoteHost, nPort);
            ftp.login(strUserName, strPassword);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                System.out.println("FTP server refused connection.");
            } else {
                // 设置参数，必须在login之后进行se操作，否则会有问题
                ftp.enterLocalPassiveMode();
                ftp.setBufferSize(1024);
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftp.setControlEncoding("UTF-8");

                // 目录不存在
                if (!ftp.changeWorkingDirectory(strRemotePath)) {
                    // 创建目录
                    if (createDirectory(strRemotePath, ftp)) {
                        if (ftp.changeWorkingDirectory(strRemotePath)) {
                            for (File fileSrc : aFileSrc) {
                                if (ftp.storeFile(fileSrc.getName(), (new FileInputStream(fileSrc)))) {
                                    System.out.println("upload success: [" + fileSrc.getName() + "]");
                                    if (delSrc) {
                                        fileSrc.delete();
                                    }
                                } else {
                                    System.out.println("upload fail: [" + fileSrc.getName() + "]");
                                }
                            }
                        }
                    } else {
                        System.out.println("create FTP path fail!");
                    }
                }
                ftp.logout();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * 创建ftp目录
     *
     * @param strRemotePath
     * @param ftp
     * @return
     */
    private boolean createDirectory(String strRemotePath, FTPClient ftp) {
        String[] astrRemotePath = strRemotePath.split("/");
        String strPathNow = "";
        try {
            for (String strPath : astrRemotePath) {
                strPathNow += "/" + strPath;
                if (!ftp.changeWorkingDirectory(strPathNow)) {
                    if (!ftp.makeDirectory(strPathNow)) {
                        System.out.println("create FTP path [" + strPathNow + "] fail!");
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
