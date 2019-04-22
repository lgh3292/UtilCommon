package com.lgh.citi.citiglobaldirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.lgh.util.FileUtil;
import com.lgh.util.logging.LogUtil;
import com.lgh.util.net.NetUtil;

public class CitiDirectory {
	
	
	public static boolean save_Citi_Global_Directory_HtmlInfo(File htmlFile, StringBuffer sb) throws Exception  {
		if(htmlFile.exists()){
			LogUtil.info("Html File "+htmlFile.getAbsolutePath()+" already exist! ");
			return false;
		}else{
			FileUtil.writeToLastLine(htmlFile ,sb.toString());
			LogUtil.info("successfully save html File: "+ htmlFile.getAbsolutePath());
			return true;
			
		}
	}
	
	 /**
	  * 
	  * 
     * @throws Exception 
     * @date 20181018 lgh
     */
	 public static boolean save_Citi_Global_Directory_TitleImage_byGEID(String webGEID,File imagePath)  {
		 try {
			 String imageName = webGEID+".jpg";
			 HttpClient http = new HttpClient();
		    	StringBuffer sb = new StringBuffer();
				String url="http://globaldirectory.citigroup.net/ExzWR3359/"+imageName;
				GetMethod getMethod = new GetMethod(url);
				http.getParams().setContentCharset("UTF-8");
				getMethod.addRequestHeader("Accept", "*/*");
				int responseCode = http.executeMethod(getMethod);
				if(responseCode==200){
					byte[] bytes = NetUtil.getArraysByRead(getMethod.getResponseBodyAsStream(), Charset.forName("utf-8").toString());
					File imageFile = new File(imagePath, imageName);
					if(imageFile.exists()){
						LogUtil.info("Image File "+imageFile.getAbsolutePath()+" already exist! ");
						return false;
					}
					FileUtil.writeByteArray(bytes, imageFile);
					LogUtil.info("successfully save Image File:"+imageFile.getAbsolutePath());
				}else{
					LogUtil.error(webGEID+" image not found!");
					return false;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return true;
		 	
	 }
	
	 /**
     * @throws Exception 
     * @date 20181018 lgh
     * to get the TITLE by GEID
     */
    
    public static StringBuffer get_Citi_Global_Directoryhtml_byGEID(String webGEID) throws Exception  {
    	StringBuffer sb =new StringBuffer();
    	HttpClient http = new HttpClient();
		String url="http://globaldirectory.citigroup.net/globaldir_new/GDIR_Result_Detail.aspx?webGEID="+webGEID;
		GetMethod getMethod = new GetMethod(url);
		http.getParams().setContentCharset("UTF-8");
		getMethod.addRequestHeader("Accept", "*/*");
		int responseCode = http.executeMethod(getMethod);
		if (responseCode == 200) {
			sb = NetUtil.getStringByReadLine(getMethod.getResponseBodyAsStream(),Charset.forName("utf-8"));
			getMethod.releaseConnection();
		} else {
			throw new Exception(webGEID+" html info not found!"+ responseCode);
		}
		
		return sb;
    }
    
    
    public static StringBuffer get_Citi_Global_DirectoryTitleName_byGEID(StringBuffer globalDirectoryhtml,String webGEID) throws Exception{
    	StringBuffer sb = new StringBuffer();
    	Document doc = Jsoup.parse(globalDirectoryhtml.toString());
		Element content = doc.getElementById("ContentPlaceHolderBody_DivBusTextLeft2");
		Elements e= content.getAllElements();
		 
		 if(e!=null&&e.size()>0){
			 List<Node> es = e.get(0).childNodes();
			 Node name = es.get(3).childNode(1).childNode(0);
			 Node title1 = es.get(4);
			 Node title2 = es.get(6);	
			 System.out.println("name: "+name+ "  e4:"+title1+"   e6:"+title2);
			 sb.append(webGEID+"	"+ name);
			 if(title2.toString().trim().equals("")){
				 sb.append("	"+title1.toString().trim());
			 }else{
				 sb.append("	"+title2.toString().trim());
			 }
			 
		 }
		 return sb;
    }
	/**
	 * getNameTitleDetail by the input file and generate the result to output file
	 * 
	 * the input file should be GEID list, 
	 * such as 
	 * 101235620
	 * 101003452
	 * 
	 * the output file will be GEID, TITLE, NAME
	 * 0003712638	ZHANG, SAN (Laurie)	DIRECTOR
	 * 0004814135	LI, DI 	DIRECTOR
	 * 
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void getNameTitleDetail(File in, File out) throws Exception{
		if(!in.exists()){
			throw new FileNotFoundException("in file not found");
		}
		
    	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(in)));
    	String webGEID = null;
    	if(!out.exists()){
    		out.createNewFile();
    	}
    	while((webGEID=br.readLine())!=null){
    		try {
    			StringBuffer htmlinfo = get_Citi_Global_Directoryhtml_byGEID(webGEID);
    			FileUtil.writeToLastLine(out,get_Citi_Global_DirectoryTitleName_byGEID(htmlinfo, webGEID).toString());
			} catch (Exception e) {
				System.err.println("could not find: "+webGEID);
				FileUtil.writeToLastLine(out, webGEID+" could not find!");
			}
    	}
	}
	
	
	 public static void main(String args[]) throws Exception {
		    
		 
		 /**getNameTitleDetail by the input file and generate the result to output file
		 
	    	File file =new File("c:\\temp\\webGEID.txt");
	    	File outFile = new File("c:\\temp\\webGEID_Result.txt");
	    	getNameTitleDetail(file, outFile);**/
		 
		 
		 
		 
		 /** get Image file to the path**/
		 File imagePath = new File("CitiGlobalDirectory\\Images");
		 if(!imagePath.exists()){
			 imagePath.mkdirs();
		 }
		 
		 /** get html info  to the path*/
		 
		 File htmlPath = new File("CitiGlobalDirectory\\html");
		 if(!htmlPath.exists()){
			 htmlPath.mkdirs();
		 }
		 
		 
	
		 
	 }
}
