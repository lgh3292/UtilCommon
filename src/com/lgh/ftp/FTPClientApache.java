package com.lgh.ftp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;    
import java.io.InputStream;
   
   
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;   
import org.apache.commons.net.ftp.FTPReply;

import com.lgh.util.logging.LogUtil;
/**   
 * 使用commons的net包进行ftp链接.   
 * 相关包：commons-net-1.4.1.jar ; commons-io-1.2.jar;jakarta-oro-2.0.8.jar测试通过.可以列出ftp上的文件   
 * 通过把ftp服务器上的文件流连接到outSteam及可以把文件下载到本机的目录..限制如果目录为中文则需要处理.最好使用英文文件名   
 * @author    
 * 
 * 
  
 * @create 2010-12-06   
 *   
 */  


public class FTPClientApache {    
    
    private static final Log logger = LogFactory.getLog(FTPClientApache.class);
    
    private String hostName;

    private int serverPort;

    private String serverPath;

    private String userName;

    private String password;
    
    private boolean binaryMode = true;

    private boolean passiveMode = false;
    
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static Log getLogger() {
        return logger;
    }

    public  boolean putFile( String remoteFile,    String localFile, boolean delFile){    
        
        boolean flag = false;    
         FTPClient ftp = new FTPClient();    
         InputStream input = null;
         
        try {    
            input = new FileInputStream(new File(localFile));
            int reply;   
             ftp.connect(this.hostName, this.serverPort);//连接FTP服务器    
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器    
             ftp.login(this.userName, this.password);//登录    
             
              reply = ftp.getReplyCode();    
            if (!FTPReply.isPositiveCompletion(reply)) {    
                 ftp.disconnect();    
                 logger.error("failed to connect to the FTP Server:");
                return flag;    
             }
            else{
                if (binaryMode) {
                    ftp.setFileType(FTP.BINARY_FILE_TYPE);
                } else {
                    ftp.setFileType(FTP.ASCII_FILE_TYPE);
                }
                if (passiveMode) {
                    ftp.enterLocalPassiveMode();
                } else {
                    ftp.enterRemotePassiveMode();
                }
            }
             ftp.changeWorkingDirectory(serverPath);    
             ftp.storeFile(remoteFile, input);             
             input.close();    
             ftp.logout(); 
             
             if (delFile) { //删除本地文件
                    (new File(localFile)).delete();
                    logger.debug("delete " + localFile);
                }
             flag = true;    
         } catch (IOException e) {    
             logger.debug(e.getMessage());
         } finally {    
            if (ftp.isConnected()) {    
                try {    
                     ftp.disconnect();    
                 } catch (IOException ioe) {  
                     logger.debug("Could not connect to server. " + ioe);
                 }    
             }    
         }    
        return flag;    
    }
    
    
    public static void main(String[] args) {    
        try {    
            FileInputStream in=new FileInputStream(new File("c://howard.log"));    
           boolean flag = uploadFile("192.168.110.128", 21, "weblogic", "weblogic", "/home/weblogic", "Hello.java", in);    
           LogUtil.info(flag);    
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        }      
    }    
    
    public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {    
        boolean success = false;    
         FTPClient ftp = new FTPClient();    
        try {    
            int reply;    
             ftp.connect(url, port);//连接FTP服务器    
            
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器    
             ftp.login(username, password);//登录    
             reply = ftp.getReplyCode();    
            if (!FTPReply.isPositiveCompletion(reply)) {    
                 ftp.disconnect();    
                return success;    
             }    
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
    
}     



