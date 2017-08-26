package testpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Test1 {
	private int a;
	protected int b;
	public Test1(int a){
		this.a = a;
		b = 3;
	}
	public void tes(){
		
	}
public static void main(String[] args) {
	if(args.length<3){
		System.err.println("参数少于三个");
		return ;
	}
	boolean isproxy = Boolean.valueOf(args[0]).booleanValue();
	if(isproxy){
		System.setProperty("http.proxySet", "true");
	    System.setProperty("http.proxyHost", "128.64.191.238");
	    System.setProperty("http.proxyPort", "8080");
	}
    try {
    	List list = new ArrayList();
    	
    	String temp = "http://lgh3292.javaeye.com/blog/";
    	int[] array = new int[]{600905,600628,
    	600394,
    	600270,
    	600132,
    	599901,
    	599869,
    	592648,
    	592619,
    	591977,
    	591440,
    	589126,
    	588930,
    	586322,
    	585204,
    	585133,
    	585037,
    	584884};
    	for(int i = 0;i<array.length;i++){
    		list.add(new URL(temp+array[i]));
    	}
    	int number = Integer.valueOf(args[1]).intValue();
    	int sleep = Integer.valueOf(args[2]).intValue();
    	System.out.println("您当前输入的循环次数为:"+number+" 每条记录sleep:"+sleep);
    	for(int j = 0;j<number;j++)
    	for(int i = 0;i<list.size();i++){
    		URL url = (URL)list.get(i);
        	java.net.HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        	connection.setRequestProperty("User-Agent", "Internet Explorer");
        	BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        	System.out.println("跑完了："+i+"  url:"+"http://lgh3292.javaeye.com"+url.getPath());
        	Thread.sleep(sleep);
    	}
    	String browse = getBrowse(new URL("http://lgh3292.javaeye.com/"));
    	System.out.println(browse);
	} catch (Exception e) {
		// TODO: handle exception
	}
    
}

	public static String getBrowse(URL url) throws IOException{
		java.net.HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    	connection.setRequestProperty("User-Agent", "Internet Explorer");
    	BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
    	String readLine = null;
    	while((readLine = br.readLine())!=null){
    		if(readLine.indexOf("浏览:")>=0){
    			return readLine;
    		}
    	}
    	return null;
	}
}
