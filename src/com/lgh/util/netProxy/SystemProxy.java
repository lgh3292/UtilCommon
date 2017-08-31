package com.lgh.util.netProxy;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.lgh.util.logging.LogUtil;
/**
 * ������Ҫ������
 * 1.���ϵͳ����
 *   see the method [getSysteProxy]
 *   
 * 2.ϵͳ����������������,���ж��ַ�������ʹ�ã�
 * 
 * a.the first way to set proxy is add the params in the java run
 *   set a proxy
 *    java  -Dhttp.proxyHost=myproxyserver.com  -Dhttp.proxyPort=80 MyJavaApp
 *   bypass a proxy:
 *    java -Dhttp.nonProxyHosts="*.mycompany.com|*.mycompany.local|localhost MyJavaApp 
 *      
 * b.The second way we can add the params by the System.setProperty.There
 * also has two details of set SysteProxy if we need set userName and password
 * see the details from the method [setSystemProxy]
 * @author liuguohu
 *
 */
public class SystemProxy {
	public static void main(String[] args) {
		
//		dump("http://www.yahoo.com");
		LogUtil.info("**************");

		try {
			//if we don't use the proxy
			URL url = new URL("http://internal.server.local/");
			URLConnection conn = url.openConnection(Proxy.NO_PROXY);  
			getSystemProxy();
		} catch (Exception e) {
			e.printStackTrace();
		}   
		
	}
	
	
	public static void dump(String URLName){
	    try {
	      DataInputStream di = null;
	      FileOutputStream fo = null;
	      byte[] b = new byte[1];

	      // PROXY
	      System.setProperty("http.proxyHost","proxy.tay.cpqcorp.net") ;
	      System.setProperty("http.proxyPort", "8080") ;

	      URL u = new URL(URLName);
	      HttpURLConnection con = (HttpURLConnection) u.openConnection();
	      
	      
	      //
	      // it's not the greatest idea to use a sun.misc.* class
	      // Sun strongly advises not to use them since they can
	      // change or go away in a future release so beware.
	      //
	      sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	      String encodedUserPwd = encoder.encode("username:password".getBytes());
	      con.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);

	      //since jdk1.2 we can use Authenticator.setDefault to set the username and password 
	      //Authenticator.setDefault(new AuthenticatorImpl(userName,password));
	      
	      di = new DataInputStream(con.getInputStream());
	      while(-1 != di.read(b,0,1)) {
	         System.out.print(new String(b));
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}


	/**
	 * get the systemproxy 
	 * @return
	 */
	public static List<Proxy> getSystemProxy(){
		   System.setProperty("java.net.useSystemProxies", "true"); 
		   System.getProperties().remove("java.net.useSystemProxies");
		   List<Proxy> list = null;
		   try {    
			   list = ProxySelector.getDefault().select(new URI("http://www.yahoo.com"));  
			   
		   }catch (URISyntaxException e) { 
			   e.printStackTrace();   
		   }
		   
		   if (list != null) {    
			    for(Proxy proxy:list){
			    	LogUtil.info("proxy type : " + proxy.type());  
					   InetSocketAddress addr = (InetSocketAddress) proxy.address();     
					   if (addr == null) {          
						   LogUtil.info("No Proxy");         
					   }   
					   else {        
						   LogUtil.info("proxy hostname : " + addr.getHostName());   
						   LogUtil.info("proxy port : " + addr.getPort());       
					   }   
			    }
		   }
		  
		   return list;
	}
	/**
	 * set the sysProxy for the URLConnection 
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 */
    public static void setSystemProxy(String host,String port,final String userName,final String password){
    	System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", "proxy.tay.cpqcorp.net");
        System.setProperty("http.proxyPort", "8080");
        //first way to set the userName and password
        if(userName!=null&&!userName.trim().equals("")&&password!=null&&!password.trim().equals("")){
        	 Authenticator.setDefault(new Authenticator(){

				/* (non-Javadoc)
				 * @see java.net.Authenticator#getPasswordAuthentication()
				 */
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// TODO Auto-generated method stub
					return new PasswordAuthentication(userName,password.toCharArray());
				}
        		 
        	 });
        }
        //second way to set the userName and password
        
   }
   
   /**
    * setSystemProxy
    * @param host
    * @param port
    * @param url
    * @return
    * @throws IOException
    */
	private static HttpURLConnection setSystemProxy(String host, String port,URL url) throws IOException {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("123.0.0.1", 8080));
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
		return httpURLConnection;
	}

   /**
    * read content from HttpURLConnection
    * @param httpURLConnection
    * @return
    * @throws IOException
    */
   private String readContent(HttpURLConnection httpURLConnection) throws IOException{
	   httpURLConnection.connect();
	   String line = null;
	   StringBuffer buffer = new StringBuffer();
	   BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
	   while((line=br.readLine())!=null){
		   buffer.append(line);
	   }
	   return buffer.toString();
   }
   
}