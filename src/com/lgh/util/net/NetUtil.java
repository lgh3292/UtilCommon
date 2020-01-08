/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.DefaultMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import com.lgh.util.Util;
import com.lgh.util.logging.LogUtil;

/**
 *
 * @author Administrator
 */
public class NetUtil {
	public static final int NETWORK_SUCCESS=1;
	public static final int NETWORK_FAIL=2;
	

	/**
	 * get string content from inputStream
	 * @param is
	 * @return
	 */
	 public static String getStringByRead(InputStream is,String charsetName) {
	        InputStreamReader r = null;
	        try {
	        	StringBuffer sb = new StringBuffer();
	            r = new InputStreamReader(is, charsetName);
	            char[] buffer = new char[128];
	            int length = -1;
	            while ((length = r.read(buffer)) != -1) {
	                sb.append(new String(buffer, 0, length));
	            }
	            return sb.toString();
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        	
	        } finally {
	            try {
	                r.close();
	            } catch (Exception ex) {
	            	ex.printStackTrace();
	            }
	        }
	        return null;
	}

	 
	public static byte[] getArraysByRead(InputStream is,String charsetName){
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			int readByte = -1;
            while((readByte=is.read())!=-1){
            	outStream.write(readByte);
            }
            return outStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * get string content from InputStream
	 * @param is
	 * @return
	 */
	public static String getStringByReadLine(InputStream is) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String readLine = null;
		StringBuffer sb = new StringBuffer();
		try {
			while((readLine=br.readLine())!=null){
				sb.append(readLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	public static StringBuffer getPageContentByURL(String url) throws Exception {
	    	StringBuffer sb =new StringBuffer();
	    	HttpClient http = new HttpClient();
			GetMethod getMethod = new GetMethod(url);
			http.getParams().setContentCharset("UTF-8");
			getMethod.addRequestHeader("Accept", "*/*");
			int responseCode = http.executeMethod(getMethod);
			if (responseCode == 200) {
				sb = NetUtil.getStringByReadLine(getMethod.getResponseBodyAsStream(),Charset.forName("utf-8"));
				getMethod.releaseConnection();
			} else {
				throw new Exception("Html info not error!"+ responseCode);
			}
			
			return sb;
	}
	/**
	 * get string content from InputStream
	 * @param is
	 * @return
	 */
	public static StringBuffer getStringByReadLine(InputStream is,Charset charset) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is,charset));
		String readLine = null;
		StringBuffer sb = new StringBuffer();
		try {
			while((readLine=br.readLine())!=null){
				sb.append(readLine);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb;
	}
    /**
     * return the num by Data packge's type
     * @param flux
     * @param type
     * @return
     */
    public static int changeFluxUnit(int flux, String type) {
        if (type.equalsIgnoreCase("KB")) {
            return flux >> 10;
        } else if (type.equalsIgnoreCase("MB")) {
            return flux >> 20;
        } else if (type.equalsIgnoreCase("GB")) {
            return flux >> 30;
        } else {
            try {
                throw new Exception("can't find the properties type!");
            } catch (Exception ex) {
                Logger.getLogger(NetUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return flux;
    }


    /**
     * get the current net flux
     * @return
     */
    public static Map<String, Integer> getNetFlux() {
        String os = System.getProperty("os.name");
        Map<String, Integer> map = new HashMap<String, Integer>();
//        LogUtil.info("os.name:" + os);
        if (os != null) {
            if (os.startsWith("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("netstat", "-e");
                Process process = null;
                try {
                    process = pb.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String readLine = null;
                    while ((readLine = br.readLine()) != null) {
                        if (readLine.indexOf("Bytes") >= 0) {
//                            LogUtil.info(readLine);
                            String[] temp = readLine.split("[\\s]+");
                            map.put("Received", Integer.valueOf(temp[1]));
                            map.put("Sent", Integer.valueOf(temp[2]));
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return map;
    }

    
    /**
     * ping the host:if we can get the reply,then the host is accessable
     * @param host
     * @return
     */
    public static boolean accessable(String host) {
        String os = System.getProperty("os.name");
        System.out.println(os);
        if (os != null) {
            if (os.startsWith("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("ping", host);
                Process process = null;
                try {
                    process = pb.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String readLine = null;
                    while ((readLine = br.readLine()) != null) {
                        if (readLine.toUpperCase().indexOf("REPLY FROM")>=0) {
                        	return true;
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return false;
    }
    /**
     * get the network card's mac
     * @return
     */
    public static String getMACAddress() {

        String address = "";
        String os = System.getProperty("os.name");
        LogUtil.info(os);
        if (os != null) {
            if (os.startsWith("Windows")) {
                try {
                    ProcessBuilder pb = new ProcessBuilder("ipconfig", "/all");
                    Process p = pb.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.indexOf("Physical Address") != -1) {
                            int index = line.indexOf(":");
                            address = line.substring(index + 1);
                            break;
                        }
                    }
                    br.close();
                    return address.trim();
                } catch (IOException e) {
                }
            } else if (os.startsWith("Linux")) {
                try {
                    ProcessBuilder pb = new ProcessBuilder("ifconfig");
                    Process p = pb.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        int index = line.indexOf("Ӳ����ַ");
                        if (index != -1) {
                            address = line.substring(index + 4);
                            break;
                        }
                    }
                    br.close();
                    return address.trim();
                } catch (IOException ex) {
                    Logger.getLogger(NetUtil.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return address;
    }
    
    
    /**
     * 
     * @param webGEID
     * @return
     * @throws Exception
     */
    public static void get_Citi_Global_Directory_Image(String webGEID) throws Exception  {
    	HttpClient http = new HttpClient();
    	String url = "http://globaldirectory.citigroup.net/globaldir_new/GDIR_Result_Detail.aspx?webGEID="+webGEID;;
    	GetMethod getMethod = new GetMethod(url);
    	http.getParams().setContentCharset("UTF-8");
    	getMethod.addRequestHeader("Accept","*/*");
    	int responseCode = http.executeMethod(getMethod);
    	System.out.println(responseCode);
    	if (responseCode == 200) {
    		
    	}
    }
    
 
	
	 
	/**
	 *  ping net work
	 */
	public static int pingNetWork(){
		BufferedReader br = null;
		try {
			 Process pp = 	Runtime.getRuntime().exec("ping www.baidu.com");
			 br = new BufferedReader(new InputStreamReader(pp.getInputStream()));
			 String readLine = null;
			 while((readLine=br.readLine())!=null){
				 if(readLine.contains("Reply from")){
					 return NETWORK_SUCCESS; 
				 }else if(readLine.contains("could not found")){
					 return NETWORK_FAIL;
				 }
			 }
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return NETWORK_FAIL;
	}
	
	
	public static int testNetWork(final HttpClient http){
		try {
			GetMethod getMethod = new GetMethod("http://www.baidu.com");
			http.getParams().setContentCharset("GBK");
			getMethod.addRequestHeader("Accept", "*/*");
			http.getHttpConnectionManager().getParams().setSoTimeout(2000);
			getMethod.getParams().setSoTimeout(2000);
			DefaultMethodRetryHandler defaultMethodRetryHandler = new DefaultMethodRetryHandler();
			defaultMethodRetryHandler.setRetryCount(0);
			getMethod.setMethodRetryHandler(defaultMethodRetryHandler);
			int responseCode = http.executeMethod(getMethod);
			if (responseCode == 200) {
				return NETWORK_SUCCESS;
			} else {
				return NETWORK_FAIL;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return NETWORK_FAIL;
		}
	}
	/**
	 * set proxy
	 * @param http
	 * @param hostName
	 * @param port
	 * @param userName
	 * @param password
	 */
	public static void setProxy(HttpClient http,String hostName,int port,String userName,String password){
		if(userName!=null&&!userName.trim().equals("")&&password!=null&&!password.trim().equals("")){
			http.getState().setProxyCredentials(
	                new AuthScope(hostName, port),
	                new UsernamePasswordCredentials(userName, password));
		}else{
			http.getHostConfiguration().setProxy(hostName, port);
		}
	}
	 
	/**
	 * set proxy by the browser's proxy setting
	 * @param http
	 */
	public static void setProxyByBrowser(HttpClient http){
		List<Proxy> proxy = SystemProxy.getSystemProxy();
     	  if(proxy!=null&&proxy.size()>0){
     		Proxy p = proxy.get(0);
     		InetSocketAddress inetSocketAddress = (InetSocketAddress)p.address();
     		http.getHostConfiguration().setProxy(inetSocketAddress.getHostName(),inetSocketAddress.getPort());
     	  }
	}
	
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
    	
    	try {
			HttpClient http = new HttpClient();
			GetMethod getMethod = new GetMethod("http://globaldirectory.citigroup.net/globaldir_new/GDIR_Result_Detail.aspx?webGEID=0000450357");
			http.getParams().setContentCharset("GBK");
			getMethod.addRequestHeader("Accept", "*/*");
			http.getHttpConnectionManager().getParams().setSoTimeout(2000);
			getMethod.getParams().setSoTimeout(2000);
			DefaultMethodRetryHandler defaultMethodRetryHandler = new DefaultMethodRetryHandler();
			defaultMethodRetryHandler.setRetryCount(0);
			getMethod.setMethodRetryHandler(defaultMethodRetryHandler);
			int responseCode = http.executeMethod(getMethod);
			System.err.println(responseCode);
			if (responseCode == 200) {
			} else {
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println(NetUtil.getPageContentByURL("https://www.baidu.com/"));
//    	for(int i=100;i<255;i++){
//    		final int num = i;
//    		 new Thread(new Runnable() {
//
//    	            public void run() {
//    	            	boolean is = accessable("192.168.1."+num);
//    	            	if(is){
//    	            		LogUtil.info(num+" true!");   
//    	            	}
//    	            	
//    	            }
//    		 }).start();
//    	}
//        new Thread(new Runnable() {
//
//            public void run() {
//                while (true) {
//                    try {
//                    	LogUtil.info(getNetFlux());
//                        int preReceived = getNetFlux().get("Received");
//                        int preSent = getNetFlux().get("Sent");
//                        Thread.sleep(1000);
//                        int nextReceived = getNetFlux().get("Received");
//                        int nextSent = getNetFlux().get("Sent");
//                        LogUtil.info("the download speed:" + changeFluxUnit(nextReceived - preReceived, "kb") + "KB" + " �ϴ�:" + changeFluxUnit(nextSent - preSent, "kb") + "KB");
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        }).start();

    /**test UIManager lookAndFeel**/
//        LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
//        for (LookAndFeelInfo laf : lafs) {
//            LogUtil.info(laf);
//        }
//        Collection<Object> o = UIManager.getDefaults().values();
//        LogUtil.info(o.size());
//        for (Object oo : o) {
//            LogUtil.info(oo);
//        }
    /**test readObject writeObject**/
//        LogUtil.info(getMACAddress());
//        User user = new User("lgh3292", "liuguohualgh");
//        File file = new File("c://readwiretObject");
//        try {
//            file.createNewFile();
//            Object object = readObject(file);
//
//            LogUtil.info(User.getDeEncrypt((User) object));
////                writeObject(user.getEncrypt(user), file);
//        } catch (IOException ex) {
//            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        for (int i = 0; i < 1000; i++) {
//            BIT = i;
//            String temp = "abcdefghjklmnopqrstuvwxyz`zxc.,vmcxz.,v;lkjADL12349872134098798M.,m'askkj[   [poik1234567890-=,./';[]\\|!@#$%^&*()?<>:{}|+_)(*&^%$#@!~";
//            String encode = encrypt(temp);
//            LogUtil.info(i + "  " + encode + "  " + deEncrypt(encode).equals(temp) + "  " + deEncrypt(encode));
//            LogUtil.info();
//        }
//        showOnJFrame(new MailRightPanel());
//         showOnJFrame(new JTextPane());
//        showOnJFrame(new GroupSendMailPanel());
    /**���Properties ����**/
//        LogUtil.info(System.getProperties());
//        Properties p = new Properties();
//        loadProperties(new File("c://lgh.properties"), p);
//        LogUtil.info(p.get("lgh"));
//        p.put("cxh", "��С��");
//        saveProperties(new File("c://lgh.properties"), p);
    /**����**/
    //        LogUtil.info(Util.getDate(new SimpleDateFormat("yy��MM��dd��"), new Date()));
//        LogUtil.info(getMACAddress());
    }
}
