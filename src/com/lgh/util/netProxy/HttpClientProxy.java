package com.lgh.util.netProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import org.apache.commons.httpclient.DefaultMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

@SuppressWarnings("deprecation")
public class HttpClientProxy { 
	public static void main(String[] args) {
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
	}
	 
	public enum ConectStatus{
		SUCCESS(1,"success"),FAIL(2,"fail");
		private int key;
		private String value;
		ConectStatus(int key,String value){
			this.key = key;
			this.value = value;
		}
		@Override
		public String toString(){
			return value;
		}
	}
	
	 
	/**
	 *  ping net work
	 */
	public static HttpClientProxy.ConectStatus pingNetWork(){
		BufferedReader br = null;
		try {
			 Process pp = 	Runtime.getRuntime().exec("ping www.baidu.com");
			 br = new BufferedReader(new InputStreamReader(pp.getInputStream()));
			 String readLine = null;
			 while((readLine=br.readLine())!=null){
				 if(readLine.contains("Reply from")){
					 return ConectStatus.SUCCESS; 
				 }else if(readLine.contains("could not found")){
					 return ConectStatus.FAIL;
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
		return ConectStatus.FAIL;
	}
	
	public static HttpClientProxy.ConectStatus testNetWork(final HttpClient http){
		ConectStatus returnCode;
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
				returnCode = ConectStatus.SUCCESS;
			} else {
				returnCode = ConectStatus.FAIL;
			}
			
		}catch (Exception e) {
			returnCode = ConectStatus.FAIL;
		}
		return returnCode;
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
}
