///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.lgh.ftp;
//
//import com.lgh.util.Printer;
//import com.lgh.util.logging.LogUtil;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import sun.net.TelnetInputStream;
//import sun.net.TelnetOutputStream;
//import sun.net.ftp.FtpClient;
//
///**
// * FTP�ļ��ϴ�������
// * notice:
// * ֮����ÿ�ζ�Ҫ����һ��ftp��������Ŀ¼���·��ص���Եĸ�Ŀ¼��
// * ��������ϴε�FtpClient���������ǰ��FTP��Ŀ¼����������Ҫ��
// * Ŀ¼��������FTP�ϴ������ļ�ʱ�����ÿ�ζ����µ�¼һ��FTP
// * @author lgh
// */
//@SuppressWarnings("unchecked")
//public class FTPClientSun {
//
//    private FtpClient ftpClient;
//    private String ip;
//    private int port;
//    private String username;
//    private String password;
//
//    public FTPClientSun() {
//    }
//
//    public FTPClientSun(String ip, int port, String username, String password) {
//        this.ip = ip;
//        this.port = port;
//        this.username = username;
//        this.password = password;
//    }
//
//    /**
//     * ��Ҫ���ݵ��ļ�
//     * @param list
//     * @return
//     */
//    private List needBackFile(List list, String relativeName) {
//        List fileNames = new ArrayList();
//        for (int i = 0; i < list.size(); i++) {
//            String temp = (String) list.get(i);
//            if (temp.indexOf(relativeName) > 0) {
//                fileNames.add(temp);
//            }
//        }
//        return fileNames;
//    }
//
//    public static void main(String[] args) {
//        File file = new File("c://howard.log");
//        //FTPClient client = new FTPClient("42.0.191.178", 21, "xmcsr", "xmccb");
//        FTPClientSun client = new FTPClientSun("192.168.110.128", 21, "weblogic", "weblogic");
//        try {
////            client.downloadFile("CRM/ccbcrm/", "D://", "CRMClientLog.log", "CRMClientLog.log");
//            client.uploadFile("/home/weblogic", "c://", "howard.log");
//            List list = client.getList("/home/weblogic", false);
//            String day = "2009";
//            LogUtil.info("****************");
//
//            Printer.printList(client.needBackFile(list, day));
//        } catch (Exception ex) {
//            Logger.getLogger(FTPClientSun.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /**
//     * �ر�FTP���� 
//     * @throws java.lang.Exception
//     */
//    public void closeServer() throws Exception {
//        ftpClient.closeServer();
//    }
//
//    /**
//     * ����ftp������
//     * @param ip
//     * @param port
//     * @param user
//     * @param pwd
//     * @return
//     * @throws Exception
//     */
//    public boolean connectServer(String ip, int port, String user, String pwd)
//            throws Exception {
//        boolean isSuccess = false;
//        try {
//            ftpClient = new FtpClient();
//            ftpClient.openServer(ip, port);
//            ftpClient.login(user, pwd);
//            isSuccess = true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return isSuccess;
//    }
//
//    /**
//     * ���Զ���µ�Ŀ¼
//     * @param remotePath Զ��Ŀ¼
//     * @param fullPath �Ƿ���Ҫ����·��
//     * @return
//     */
//    public List getList(String remotePath, boolean fullPath) {
//        List list = new ArrayList();
//        try {
//            if (connectServer(ip, port, username, password)) {
//                BufferedReader br = new BufferedReader(new InputStreamReader(ftpClient.nameList(remotePath)));
//                String temp = ftpClient.getResponseString();
//                LogUtil.info(temp);
//                String readLine = null;
//                int lastIndex;
//                //ȥ��remotePath�ĺ�׺��������'/',Ҳ�п�������������
//                if ((lastIndex = remotePath.lastIndexOf("/")) > 0||(lastIndex = remotePath.lastIndexOf("//")) > 0
//                    ||(lastIndex = remotePath.lastIndexOf("\\")) > 0||(lastIndex = remotePath.lastIndexOf(File.separator)) > 0) {
//                    remotePath = remotePath.substring(0, lastIndex);
//                }
//                while ((readLine = br.readLine()) != null) {
//
//                    if (!fullPath) {
//                        list.add(readLine.substring(remotePath.length() + 1, readLine.length()));
//                        LogUtil.info(readLine.substring(remotePath.length() + 1, readLine.length()));
//                    } else {
//                        list.add(readLine);
//                        LogUtil.info(readLine);
//                    }
//                }
//                ftpClient.closeServer();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return list;
//    }
//
//    /**
//     * �����ļ�
//     * @param remotePath
//     * @param localPath
//     * @param filename
//     * @throws Exception
//     */
//    public void downloadFile(String remotePath, String localPath, String remoteFileName, String localFileName) throws Exception {
//        try {
//            if (connectServer(ip, port, username, password)) {
//                if (remotePath.length() != 0) {
//                    ftpClient.cd(remotePath);
//                }
//                ftpClient.binary();
//                TelnetInputStream is = ftpClient.get(remoteFileName);
//                File fp = new File(localPath);
//                if (!fp.exists()) {
//                    fp.mkdirs();
//                }
//                File file_out = new File(localPath + File.separator + localFileName);
//                FileOutputStream os = new FileOutputStream(file_out);
//                byte[] bytes = new byte[1024];
//                int readBye;
//                while ((readBye = is.read(bytes)) != -1) {
//                    os.write(bytes, 0, readBye);
//                }
//                is.close();
//                os.close();
//                ftpClient.closeServer();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    /**
//     * �ϴ��ļ�
//     * @param remotePath
//     * @param localPath
//     * @param filename
//     * @throws Exception
//     */
//    @SuppressWarnings({"unchecked","fallthrough"})
//
//
//    public void uploadFile(String remotePath, String localPath, String filename) throws Exception {
//        try {
//            if (connectServer(ip, port, username, password)) {
//                if (remotePath.length() != 0) {
//                    ftpClient.cd(remotePath);
//                }
//                ftpClient.binary();
//                TelnetOutputStream os = ftpClient.put(filename);
//                File file_in = new File(localPath + File.separator + filename);
//                FileInputStream is = new FileInputStream(file_in);
//                byte[] bytes = new byte[1024];
//                int readBye;
//                while ((readBye = is.read(bytes)) != -1) {
//                    os.write(bytes, 0, readBye);
//                }
//                is.close();
//                os.close();
//                ftpClient.closeServer();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    /**
//     * @return the ip
//     */
//    public String getIp() {
//        return ip;
//    }
//
//    /**
//     * @param ip the ip to set
//     */
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    /**
//     * @return the port
//     */
//    public int getPort() {
//        return port;
//    }
//
//    /**
//     * @param port the port to set
//     */
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    /**
//     * @return the username
//     */
//    public String getUsername() {
//        return username;
//    }
//
//    /**
//     * @param username the username to set
//     */
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    /**
//     * @return the password
//     */
//    public String getPassword() {
//        return password;
//    }
//
//    /**
//     * @param password the password to set
//     */
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}
