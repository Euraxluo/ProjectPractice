package com.mmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName FTPUtil
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-1 下午9:25
 */
public class FTPUtil {
    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

    private String ip;
    private int post;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int post, String user, String pwd) {
        this.ip = ip;
        this.post = post;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * @description 连接ftp服务器并上传
     * @param [fileList]
     * @return boolean
     * @author Euraxluo
     * @date 19-1-1
     */
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp,21,ftpUser,ftpPass);
        logger.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile("img",fileList);
        logger.info("成功连接FTP服务器,并且结束上传,上传结果:{}");
        return result;
    }
    /**
     * @description 具体的文件上传
     * @param [remotePath, fileList]
     * @return boolean
     * @author Euraxluo
     * @date 19-1-1
     */
    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean uploaded = true;//上传状态true
        FileInputStream fis = null;
        //连接ftp文件服务器
        if(connectServer(this.ip,this.post,this.user,this.pwd)){
            try {
                ftpClient.changeWorkingDirectory(remotePath);//切换相对路径
                ftpClient.setBufferSize(1024);//缓冲池
                ftpClient.setControlEncoding("UTF-8");//字符集编码
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);//文件类型
                ftpClient.enterLocalPassiveMode();//打开本地被动模式(服务器开启了被动模式的设置)
                for(File fileItem : fileList){
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fis);//打开文件流,开始上传
                }
            } catch (IOException e) {
                logger.error("上传文件異常",e);
                uploaded = false;//上传失败,改变状态为false
            }finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }
    /**
     * @description 连接ftp服务器
     * @param [ip, port, user, pwd]
     * @return boolean
     * @author Euraxluo
     * @date 19-1-1
     */
    private boolean connectServer(String ip,int port,String user,String pwd){
        boolean isSuccess = false;//返回状态
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user,pwd);
        } catch (IOException e) {
            logger.error("连接ftp服务器異常",e);
        }
        return isSuccess;
    }
    public static String getFtpIp() {
        return ftpIp;
    }

    public static void setFtpIp(String ftpIp) {
        FTPUtil.ftpIp = ftpIp;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    public static void setFtpUser(String ftpUser) {
        FTPUtil.ftpUser = ftpUser;
    }

    public static String getFtpPass() {
        return ftpPass;
    }

    public static void setFtpPass(String ftpPass) {
        FTPUtil.ftpPass = ftpPass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
