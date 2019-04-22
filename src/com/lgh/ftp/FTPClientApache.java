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
 * ʹ��commons��net������ftp����.   
 * ��ذ���commons-net-1.4.1.jar ; commons-io-1.2.jar;jakarta-oro-2.0.8.jar����ͨ��.�����г�ftp�ϵ��ļ�   
 * ͨ����ftp�������ϵ��ļ������ӵ�outSteam�����԰��ļ����ص�������Ŀ¼..�������Ŀ¼Ϊ��������Ҫ����.���ʹ��Ӣ���ļ���   
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
             ftp.connect(this.hostName, this.serverPort);//����FTP������    
            //�������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������    
             ftp.login(this.userName, this.password);//��¼    
             
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
             
             if (delFile) { //ɾ�������ļ�
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
             ftp.connect(url, port);//����FTP������    
            
            //�������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������    
             ftp.login(username, password);//��¼    
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



