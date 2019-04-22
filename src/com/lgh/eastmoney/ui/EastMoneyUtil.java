package com.lgh.eastmoney.ui;

import java.awt.Color;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.lgh.eastmoney.bfo.EastMoneyURLMsg;
import com.lgh.eastmoney.ctl.NetworkManager;
import com.lgh.util.DateUtil;
import com.lgh.util.FileUtil;
import com.lgh.util.ZhengZe;
import com.lgh.util.logging.LogUtil;
import com.lgh.util.net.NetUtil;

public class EastMoneyUtil {
	public static Color GREEN_COLOR = new Color(0,139,0);
	public static Color RED_COLOR = new Color(255,0,0);

	
	/**
	 * whether current date is trans Time
	 * the time is from 9:00 AM-11:35 AM and 13:00 PM-15:10PM and it should be Monday to Friday
	 * except Saturday and Sunday
	 * @return
	 */
	public boolean isTransTime(){
		Calendar c = Calendar.getInstance();
		if(c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			return false;
		}
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		int field_a_start = 9*60;
		int field_a_end = 11*60+35;
		
		int field_b_start = 13*60;
		int field_b_end = 15*60+10;
		if(hour>9&&hour<12){
			
		}
		return true;
	}
	/**
	 * get 列表中指定的数据
	 * @param list
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static List<? extends Object> getObjectList(List<? extends Object> list,int pageNumber,int pageSize){
		List<Object> objects = new ArrayList<Object>();
		for(int i=(pageNumber-1)*pageSize;i<pageNumber*pageSize;i++){
			if(list.size()>i)
			  objects.add(list.get(i));
		}
		return objects;
	}
	
	/**
	 * get all riseDrop stocks messge
	 * @throws UnsupportedEncodingException
	 */
	public static List<EastMoneyURLMsg> getRiseDropStockMsgs(String keySet){
		List<EastMoneyURLMsg> dropStockMsgs = new ArrayList<EastMoneyURLMsg>();
		String risedrop_url_all = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty(keySet);
		String[] risedrop_url_alls = risedrop_url_all.split(",");
		for(int i=0;i<risedrop_url_alls.length;i++){
			EastMoneyURLMsg rdum = getRiseDropURLMsg(risedrop_url_alls[i]);
			dropStockMsgs.add(rdum);		
		}
		return dropStockMsgs;
	}
	
	/**
	 * get RiseDropURLMsg by the key
	 * @param key
	 * @return
	 */
	public static EastMoneyURLMsg getRiseDropURLMsg(String key){
		String host = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty(key+"_host");
		String listener = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty(key+"_listener");
		String parameters = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty(key+"_parameters");
		EastMoneyURLMsg rdum = new EastMoneyURLMsg(key,host,listener,parameters);
		return rdum;

	}
	
	/**
	 * THE import date history file record every time we get import the data's date
	 */
	private static File IMPORT_DATE_HISTORY_FILE = new File("conf/importdateHistory.txt");
	
	/**
	 * get the last line's String
	 * @return
	 */
	public static Date getTheNewestImportDate(){
		try {
			String lastLine = FileUtil.readLastLine(IMPORT_DATE_HISTORY_FILE, "utf-8");
			Date date = DateUtil.parseDate(lastLine, "yyyyMMdd HH:mm:ss");
			return date;
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
		return null;
	}

	/**
	 * write the date to the lastline
	 * @param date
	 */
	public static void writeToLastLine(String date){
		FileUtil.writeToLastLine(IMPORT_DATE_HISTORY_FILE, date);
	}
	
	
	/**
	 * get the HttpClient 
	 * 1.If the System set the proxy true ,it will the set proxy
	 * 2.else connect directly
	 * @return
	 */
	public static HttpClient getHttpClient() throws Exception{
		HttpClient http = new HttpClient();
		if(NetworkManager.getInstance().isNetEnabled()){
			NetworkManager.getInstance().setProxy(http);
		}else{
			throw new Exception("net exception,could not connect to network!");
		}
		return http;
	}
	/**
	 * 
	 * @param uri
	 * @return
	 * @throws Exception  
	 * @throws Exception
	 */
	public static String getContent(String uri,String charsetName) throws Exception{
		HttpClient http = getHttpClient();
		GetMethod getMethod = new GetMethod(uri);
		http.getParams().setContentCharset("GBK");
//		http.getHostConfiguration().setHost("hqdigi2.eastmoney.com", 80,"http");
		getMethod.addRequestHeader("Accept", "*/*");
		// get.addRequestHeader("Referer","http://quote.eastmoney.com/f1.aspx?code=600000&market=1");
		// get.addRequestHeader("Accept-Language", "zh-cn");
		// get.addRequestHeader("UA-CPU","x86");
		// get.addRequestHeader("Accept-Encoding","gzip, deflate");
		// get.addRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Hewlett-Packard; EmbeddedWB 14.52 from: http://www.bsalsa.com/ EmbeddedWB 14.52; .NET CLR 1.1.4322; .NET CLR 1.0.3705; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; Maxthon 2.0)");
		// get.addRequestHeader("Host", "hqdigi2.eastmoney.com");
		// get.addRequestHeader("Connection", "Keep-Alive");
		// get.addRequestHeader("Cache-Control", "no-cache");
		// get.addRequestHeader("Cookie",
		// "emstat_bc_emcount=19395079052358515452; emstat_ss_emcount=62_1307482200_3463750106; HAList=a-sh-600005-%u6B66%u94A2%u80A1%u4EFD%2Ca-sh-601899-%u7D2B%u91D1%u77FF%u4E1A%2Ca-sh-600119-%u957F%u6C5F%u6295%u8D44%2Ca-sh-601166-%u5174%u4E1A%u94F6%u884C%2Ca-sh-600036-%u62DB%u5546%u94F6%u884C%2Ca-sh-601398-%u5DE5%u5546%u94F6%u884C%2Ca-sh-600022-%u6D4E%u5357%u94A2%u94C1%2Ca-sh-600115-%u4E1C%u65B9%u822A%u7A7A%2Ch-0-00670-%u4E2D%u56FD%u4E1C%u65B9%u822A%u7A7A%u80A1%u4EFD%2Ca-sh-600897-%u53A6%u95E8%u7A7A%u6E2F%2Ca-sz-000429-%u7CA4%u9AD8%u901F%uFF21%2Ca-sh-601111-%u4E2D%u56FD%u56FD%u822A%2Ca-sh-600029-%u5357%u65B9%u822A%u7A7A%2Ca-sh-600513-%u8054%u73AF%u836F%u4E1A%2Ca-sh-600010-%u5305%u94A2%u80A1%u4EFD; emhq_stock=600734%2C600000%2C600187; _pub_isn=1302142491792; em_hq_fls=new");

		int responseCode = http.executeMethod(getMethod);
		if (responseCode == 200) {
			String temp = NetUtil.getStringByRead(getMethod.getResponseBodyAsStream(),charsetName);
//			LogUtil.info(temp);
			getMethod.releaseConnection();
			return temp;
		} else {
			throw new Exception("error happen while getPageSize: the response code:"+ responseCode);
		}
	}
	
	/**
	 * get array content from the url
	 */ 
	public static byte[] getArrayContent(String uri,String charsetName) throws Exception{
		HttpClient http = getHttpClient();
		GetMethod getMethod = new GetMethod(uri);
		http.getParams().setContentCharset("GBK");
		getMethod.addRequestHeader("Accept", "*/*");

		int responseCode = http.executeMethod(getMethod);
		if (responseCode == 200) {
			byte[] temp = NetUtil.getArraysByRead(getMethod.getResponseBodyAsStream(),charsetName);
			getMethod.releaseConnection();
			return temp;
		} else {
			throw new Exception("error happen while getPageSize: the response code:"+ responseCode);
		}
	}
	/*****************************************************************
	 ********************正则表达式*************************
	 ******************************************************************
	 */
	
	/**
	 * get the page size from input by the pattern
	 * @param input 
	 * @param pattern  
	 *   input:
	 *       	var quote_123={rank:["6007141,600714,金瑞矿业,19.60,19.80,21.56,21.56,19.69,21348,100715,1.96,10.00%,21.20,9.54%,
	 * 				100.00%,77859,5,67511,33204,-1,0,0.00%,4.01,6.67%,309.77,001163|002437|003519|003595,21.56,0.00,2011-08-04 14:06:42,0,150937500,0",
	 * 				"....“],pages:45}
	 *   pattern 
	 *       	pages:[0-9]{1,10}
	 *   
	 *   expect result: 
	 *          pages:45
	 *   
	 * @return
	 */
	public static int getNum(CharSequence input,String pattern,String removeable){
		List<String> list = ZhengZe.getResult(input, pattern, removeable);
		if(list.size()==1){
			return Integer.valueOf(list.get(0));
		}else{
			LogUtil.log("unvalide result while find pageSize:"+list.size());
		}
		return -1;
	}
	
	/**
	 * input:
	 *    "002142,500,12.020","600000,260,11.235"
	 * pattern:
	 *    [^\"][^\"]*[^\"]
	 * expect result:
	 *     002142,500,12.020
	 *	   600000,260,11.235   
	 */
	public static List<String> getPersonBuy(){
		String s = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("person_buy");
		List<String> list = ZhengZe.getResult(s, "[^\"][^\"]*[^\"]", "");
		return list;
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
//		 String url="http://globaldirectory.citigroup.net/globaldir_new/GDIR_Result_Detail.aspx?webGEID=0000450357";
//		 byte[] content = getArrayContent(url, "utf-8");
//		 System.out.println(content.length); 
//		 FileOutputStream fous = new FileOutputStream(new File("c://a.bmp"));
//		 fous.write(content);
//		 fous.flush();
//		 fous.close();
		
		
	}
	
}
