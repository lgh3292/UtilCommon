package testpackage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lgh.util.db.DBConnectionManager;
import com.lgh.util.db.DBMessage;
public class GetMessage {
	public  static String getID(String patternStr){
		int firstIndex = patternStr.indexOf(",");
		int secondIndex = patternStr.indexOf(",", firstIndex+1);
		return patternStr.substring(firstIndex+1,secondIndex);
	}
	
	public static void getPageURL(){
		  try {
//          DBConnection dbCon = new DBConnection("localhost",1521,"oracle","test","test",DBConnection.ORACLE);
		  for(int i=1;i<95;i++){
		       System.out.println("................inserting "+ i);
		       Thread.sleep(300);
		       String url = "http://bbs.longtop.com/list.asp?boardid=61&page="+i+"&selTimeLimit=&action=&topicmode=0";
//		       String url = "http://www.baidu.com";
		       HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		       con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727; MS-RTC LM 8)");
		       BufferedReader bis = new BufferedReader(new InputStreamReader(con.getInputStream()));
		       
		       String readLine = null;
		       StringBuffer sb = new StringBuffer();
		       while((readLine=bis.readLine())!=null){
		        sb.append(readLine);
		       }
		       bis.close();
//		       System.out.println(sb.toString());
//		       String temp  ="aaaaa document.write (dvbbs_topic_list(TempStr,27933','61','黄佳--到岗通知--综合软件业务线 </Script>";
		       
		       String regex = "document.write \\(dvbbs_topic_list\\(TempStr,[^>]*";
		       Pattern pattern  = Pattern.compile(regex);
		       Matcher match = pattern.matcher(sb.toString());
		       int j = 1;
		       while(match.find()){
		         String findStr = match.group();
		         findStr=findStr.replace("'", "");
		         findStr=findStr.replace("--", "-");
		         
		    	 String exesql = "insert into longtop_page_url values(LONGTOP_PAGE_URL_ID_SEQ.Nextval,"+"'"+i+"',"+j+",'"+findStr+"')";
		    	 System.out.println(exesql);
//		         dbCon.execute(exesql);
		         j++;
		       }
		      }
		     }catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
	}
	
	
	
   public static void getURLContent(){

       try {
    	    Connection dbCon = DBConnectionManager.getInstance(new DBMessage("localhost",1521,"oracle","test","test",DBMessage.ORACLE)).getConnection();
    	    for(int i=90;i<95;i++){
    	 	System.out.println("................inserting "+ i);
    	 	Thread.sleep(100);
    	 	ResultSet rs = dbCon.createStatement().executeQuery("select * from longtop_page_url where page_no='"+i+"'");
    	 	Map map  = new HashMap();
			while(rs.next()){
			 	String temp = rs.getString(4);
			 	String temp2 = getID(temp);
	  //     	System.out.println(temp2);
			 	String id = rs.getString(1);
			 	String url = "http://bbs.longtop.com/dispbbs.asp?boardID=61&ID="+temp2+"&page="+i;
			 	System.out.println(url);
			 	map.put(id,url);
			} 	
			Set set = map.keySet();
			Iterator iterator =set.iterator();
			while(iterator.hasNext()){
				String id = (String)iterator.next();
				String url = (String)map.get(id);
				try{
					HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
					con.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727; MS-RTC LM 8)");
					BufferedReader bis = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String readLine = null;
					StringBuffer sb = new StringBuffer();
					while ((readLine = bis.readLine()) != null) {
						sb.append(readLine);
					}
					bis.close();
					String regex = "document.write \\(dvbbs_show_topic\\([^S]*";
					Pattern pattern = Pattern.compile(regex);
					Matcher match = pattern.matcher(sb.toString());
					while (match.find()) {
						String findStr = match.group();
						System.out.println(findStr);
						findStr = findStr.replace("'", "");
						findStr = findStr.replace("--", "-");
						findStr = findStr.replace("&nbsp", "");
						
						String exesql = "update longtop_page_url set page_content='"+findStr+"'"+" where id='"+id+"'";
						System.out.println(exesql);
//						dbCon.execute(exesql);
//						j++;
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			
    	    } 
       	}catch (Exception e) {
			e.printStackTrace();
		}
       
   }
    public static void main(String[] args){
     
        

//      System.setProperty("http.proxySet","true");
//      System.setProperty("http.proxyHost","168.1.6.112");
//      System.setProperty("http.proxyPort","9999");
//      
//    
//      getPageURL();

    	String a = "aadocument.apiptt>dpf</Scpipt>aadocument.adf</Scpipt>aadocument.adf</Scpipt>";
    	String regex = "document.[^p-pi-ip-pt-t>]*Scpipt";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(a);
    	while(matcher.find()){
    		System.out.println(matcher.group());
    	}
//   con.setRequestMethod("GET");
//   con.addRequestProperty("Accept", "*/*");
//   con.addRequestProperty("X-VERSION", "1");
//   con.addRequestProperty("X-SRCUSERTYPE", "1");
//   con.addRequestProperty("X-SRCUIN", "409782976");
//   con.addRequestProperty("X-DSTUIN", "409782976");
//   con.addRequestProperty("X-DSTUSERTYPE", "1");
//   con.addRequestProperty("X-IMAGETYPE", "1");
//   con.addRequestProperty("Pragma", "no-cache");
//   con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
//   con.addRequestProperty("X-SIGNSTRING","310927CC8381D76064B66B513AE729B328EFC995D82659B9");
//   con.addRequestProperty("X-FILEHASH","AC4C0CD5E1FC9E232A579170E4112396");
    
//   con.addRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/x-shockwave-flash, */*");   
//   con.addRequestProperty("Accept-Language", "en-us,zh-cn;q=0.5");   
//   con.addRequestProperty("Accept-Encoding", "gzip, deflate");   
//   con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727; MS-RTC LM 8)");   
//
   
//   
//   BufferedReader bis = new BufferedReader(new InputStreamReader(con.getInputStream()));
//   String readLine = null;
//   while((readLine=bis.readLine())!=null){
//    System.out.println(readLine);
//   }
//   
//   

    }
}
