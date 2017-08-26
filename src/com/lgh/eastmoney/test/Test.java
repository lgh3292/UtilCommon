package com.lgh.eastmoney.test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;

import com.lgh.util.logging.LogUtil;
import com.lgh.util.net.NetUtil;

public class Test {
	private static Test test;
	private static final Object object = new Object();
	
	public Test(){
		
	} 
	public void a(){
		synchronized (object) {
			LogUtil.info(Thread.currentThread().getId()+" in");
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			LogUtil.info(Thread.currentThread().getId()+"out");
		}
	}
	public static Test getInstance(){
		synchronized (object) {
			LogUtil.info("in");
			if(test==null){
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				test = new Test();
			}
		}
		return test;
	}
	public  class Person{
		private int age;
		public int getAge(){
			return age;
		}
		public Person(int age){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.age = age;
		}
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			Person p = (Person)obj;
			return p.getAge()==age;
		}
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return age;
		}


	}
	public  void test(){
		final Set<Person> set = new HashSet<Person>();
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < 100; i++) {
					set.add(new Person(1));
					set.add(new Person(1));
				}
				LogUtil.info(set.size());
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < 100; i++) {
					set.add(new Person(1));
					set.add(new Person(1));
				}
				LogUtil.info(set.size());
			}
		}).start();
	}
	public static void main(String[] args) {
//		for(int i=0;i<3;i++)
//			new Thread(new Runnable(){
//				public void run(){
//					new Test().a();
//				}
//			}).start();
	
		new Test().test();
		 Set<Integer> set = new HashSet<Integer>();
		 set.add(new Integer(50));
		 set.add(new Integer(50));
		 set.add(new Integer(50));
		 LogUtil.info(set.size());
	}
	private static String getURLContent(String url) throws IOException {
		HttpClient http = new HttpClient();
		http.getParams().setContentCharset("GBK");
		GetMethod get = new GetMethod();
		URI uri = new URI(url, false, "GBK");
		get.setURI(uri);
		http.executeMethod(get);
		LogUtil.info(get.getResponseCharSet());
		Header[] hs = get.getResponseHeaders();
		for (Header h : hs) {
			System.out.print(h);
		}
		return NetUtil.getStringByRead(get.getResponseBodyAsStream(),"gbk");

	}
	 
	public static void testByBaidu() {
		try {
			HttpClient http = new HttpClient();
			http.getParams().setContentCharset("GBK");
	    	  
	          GetMethod get = new GetMethod("http://www.baidu.com/s?wd=" + URLEncoder.encode("filetype:lrc " + "°®ÎÒ¾Ã¾Ã", "GBK"));
	          get.addRequestHeader("Host", "www.baidu.com");
	          get.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11");
	          get.addRequestHeader("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
	          get.addRequestHeader("Accept-Language", "zh-cn,zh;q=0.5");
	          get.addRequestHeader("Keep-Alive", "300");
	          get.addRequestHeader("Referer", "http://www.baidu.com/");
	          get.addRequestHeader("Connection", "keep-alive");
	          int i = http.executeMethod(get);
	          String temp = NetUtil.getStringByRead(get.getResponseBodyAsStream(),"gbk");
	          get.releaseConnection();
	          Matcher m = Pattern.compile("(?<=LRC/Lyric - <a href=\").*?(?=\" target=\"_blank\">HTML°æ</a>)").matcher(temp);
	          String content = null;
	          if (m.find()) {
	              String str = m.group();
	              content =getURLContent(str);
	              m = Pattern.compile("(?<=<body>).*?(?=</body>)").matcher(content);
	              if (m.find()) {
	                  content = m.group();
	              }
	          }
	          LogUtil.info(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
}
