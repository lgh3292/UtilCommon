package com.lgh.util.net;

import org.apache.commons.httpclient.HttpClient;

import com.lgh.eastmoney.ui.PropertiesConfig;
import com.lgh.util.logging.LogUtil;
/**
 * ͨ�����ַ�ʽ��֤�����Ƿ���ã������������һ�־ʹ����������
 * ��һ����cmd:  ping www.baidu.com
 * �ڶ�����ֱ��ͨ��httpClient access www.baidu.com,�������reponseCode=200�����ɹ�
 * @author DNAF01069
 *
 */
public class NetworkManager { 
	private static NetworkManager INSTANCE = null;
	
	public static NetworkManager getInstance(){
		synchronized (NetworkManager.class) {
			if(INSTANCE==null){
				INSTANCE = new NetworkManager();
			}
		}
		return INSTANCE;
	}
	private String proxy_auto_detect;
	private String proxy_set;
	private String proxy_host;
	private String proxy_port;
	private String proxy_userName;
	private String proxy_password;
	
	private static int INIT_STATE = 0;
	private static int PROXY = 1;
	private static int UN_PROXY = 1<<2;
	private static int PROXY_STATUS = INIT_STATE;//whether to use the config proxy
	
	
	private static boolean disableNet = false;
	
	private NetworkManager(){
		proxy_auto_detect = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("proxy_auto_detect");
		proxy_set = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("proxy_set");
		proxy_host= PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("proxy_host");
		proxy_port= PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("proxy_port");
		proxy_userName= PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("proxy_userName");
		proxy_password= PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("proxy_password");
		LogUtil.log("proxy_auto_detect:"+proxy_auto_detect+"   use proxy?:"+proxy_set+"  info: current proxy settting:"+proxy_host+":"+proxy_port+":"+proxy_userName+":"+proxy_password);
		if(Boolean.valueOf(proxy_auto_detect)){
			detectNetWork();
		}
	}
	
	public synchronized void disableNet(){
		disableNet = true;
	}
	
	public synchronized void enableNet(){
		disableNet = false;
	}
	/**
	 * ���ص�ǰ���������Ƿ����
	 * @return
	 */
	public boolean isNetEnabled(){
		return ((PROXY_STATUS^INIT_STATE)==0?false:true)&&!disableNet;
	}
	/**
	 * set the proxy by the system config
	 * @param http
	 */
	public void setProxy(HttpClient http){
		if(Boolean.valueOf(proxy_auto_detect)){
			if((PROXY_STATUS^PROXY)==INIT_STATE){
				HttpClientProxy.setProxy(http, proxy_host, Integer.valueOf(proxy_port), proxy_userName, proxy_password);
			}
		}else if((PROXY_STATUS^UN_PROXY)==INIT_STATE){
			if(Boolean.valueOf(proxy_set)){
				HttpClientProxy.setProxy(http, proxy_host, Integer.valueOf(proxy_port), proxy_userName, proxy_password);
			}
		}else{
			LogUtil.info(" unknow error...........");
		}
	}
	
	/**
	 * ̽�������Ƿ���ã������ô������ֱ������
	 * 1.�����ǰֱ�����ӿ����ã�������̲߳�ȥ���ӣ�ֱ�����粻�����ˣ������̲߳Ż᳢�������Ƿ����
	 * 2.����������ӿ����ã���ֱ�����Ӳ�ȥ���ӣ�ֱ���������Ӳ������ˣ�ֱ�����ӲŻ᳢�������Ƿ����
	 * 3.�����Ǵ������ӻ�����ֱ�����Ӷ���ÿ��30�볢������һ�Ρ�
	 */
	public void detectNetWork(){
		long start = System.currentTimeMillis();
		final Object lock = new Object();
		
		Thread unproxyThread = new Thread(new Runnable(){
		public void run(){
			long nextRunTime = System.currentTimeMillis();
			while(true){
				if(System.currentTimeMillis()>=nextRunTime&&!disableNet){
					if((PROXY_STATUS^PROXY)!=INIT_STATE){
						HttpClientProxy.ConectStatus returnCode = HttpClientProxy.pingNetWork();
						LogUtil.info("ping network,directly...."+returnCode+"  PROXY_STATUS:"+PROXY_STATUS);
						synchronized (lock) {
							if (returnCode==HttpClientProxy.ConectStatus.SUCCESS) {
								PROXY_STATUS |= UN_PROXY;
							} else {
								// remove UN_PROXY
								if((PROXY_STATUS^UN_PROXY)==INIT_STATE){
									PROXY_STATUS ^= UN_PROXY;
								}
							}
						}	
						//every 30 second to check the connection
						nextRunTime=System.currentTimeMillis()+10000;
					}
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();LogUtil.error("error", e);
				}
			}
		}
		});
		
		
		Thread proxyThread = new Thread(new Runnable(){
			public void run(){
				long nextRunTime = System.currentTimeMillis();
				while(true){
					if(System.currentTimeMillis()>=nextRunTime&&!disableNet){
						if((PROXY_STATUS^UN_PROXY)!=INIT_STATE){
							HttpClient httpClient =new HttpClient();
							HttpClientProxy.setProxy(httpClient, proxy_host, Integer.valueOf(proxy_port), proxy_userName, proxy_password);
							HttpClientProxy.ConectStatus returnCode = HttpClientProxy.testNetWork(httpClient);
							LogUtil.info("ping testNetWork...."+returnCode+"  PROXY_STATUS:"+PROXY_STATUS);
							synchronized (lock) {
								if (returnCode==HttpClientProxy.ConectStatus.SUCCESS) {
									PROXY_STATUS |= PROXY;
								} else {
									if((PROXY_STATUS^PROXY)==INIT_STATE){
										PROXY_STATUS ^= PROXY;
									}
								}
							}
							//every 30 second to check the connection
							nextRunTime=System.currentTimeMillis()+10000;
						}
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();LogUtil.error("error", e);
					}
				}
				
			}
		});
		
		try {
			unproxyThread.start();
			proxyThread.start();
			long breakTime = System.currentTimeMillis()+5000;
			while(true){
				Thread.sleep(20);
				if((PROXY_STATUS^INIT_STATE)!=0){
					break;
				}
				if(System.currentTimeMillis()>breakTime){
					break;
				}
//				if(!unproxyThread.isAlive()){
//					break;
//				}
//				if(!proxyThread.isAlive()){
//					break;
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
		LogUtil.info("..............waste to detectNetWork:"+(System.currentTimeMillis()-start));
	}
	public static void main(String[] args) {
		final NetworkManager p = NetworkManager.getInstance();
		new Thread(new Runnable(){
			public void run(){
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();LogUtil.error("error", e);
					}
					LogUtil.info(p.isNetEnabled());
				}
			}
		}).start();
	}
}
