package com.lgh.eastmoney.ctl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lgh.eastmoney.bo.EastMoneyVolume;
import com.lgh.eastmoney.bo.EastMoneyVolumeDetail;
import com.lgh.eastmoney.ui.EastMoneyUtil;
import com.lgh.eastmoney.ui.PropertiesConfig;
import com.lgh.util.DateUtil;
import com.lgh.util.FileUtil;
import com.lgh.util.StreamUtil;
import com.lgh.util.ZhengZe;
import com.lgh.util.logging.LogUtil;


/**
 * 从http://fund.eastmoney.com/获得某支股票的成交量
 * @author liuguohu
 *
 */
public class StockVolume implements Runnable{
	//002142要从http://quote.eastmoney.com/f1.aspx?code=002142&market=2 取得
	//600000要从http://quote.eastmoney.com/f1.aspx?code=600000&market=1 取得
	private int market = 1;//从哪个market取得的数据,默认是1
	private String emStockId;
	private String emStockName;
	private String charsetName = "GBK";
	
	private static final Object innerlock  = new Object();
	private String url = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("volume_url");
	
	public StockVolume(String emStockId){
		this.emStockId = emStockId;
	}
	
	public String getEmStockName(){
		return emStockName;
	}
	public String getEmStockId(){
		return emStockId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		 if(obj instanceof StockVolume){
			 StockVolume volume = (StockVolume)obj;
			 return this.emStockId.equals(volume.getEmStockId());
		 }else if(obj instanceof String){
			 return ((String)obj).equals(this.emStockId);
		 }
		 return false;
	}

	public StockVolume(String emStockId,int market){
		this(emStockId);
		this.market = market;
	}
	public StockVolume(String emStockId,String emStockName){
		this(emStockId);
		this.emStockName = emStockName;
	}
	/**
	 * @return the market
	 */
	public int getMarket() {
		return market;
	}

	/**
	 * @param market the market to set
	 */
	public void setMarket(int market) {
		this.market = market;
	}
	public void run() {
		try {
			Thread.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
		
		EastMoneyVolumeDetail eastMoneyVolumeDetail = new EastMoneyVolumeDetail();
		eastMoneyVolumeDetail.setEmStockId(emStockId);
		eastMoneyVolumeDetail.setEmVolumeDate(new Timestamp(new Date().getTime()));
		eastMoneyVolumeDetail.setEmDateId(Integer.valueOf(DateUtil.getCurrentDate("yyyyMMdd")));
		
		boolean success = false;
		int reTry = 0;
		EastMoneyVolume eastMoneyVolume = null;
		while(!success){
			try {
				LogUtil.info("start getStock "+emStockId+" volume message!");
				StringBuffer volumeContent = getStockMessage();
				eastMoneyVolumeDetail.setEmVolumeDetail(StreamUtil.getByteArrayByObject(volumeContent));
				eastMoneyVolume = analyseToEastMoneyVolume(volumeContent.toString());
				success = true;
			} catch (Exception e) {
				reTry++;
				e.printStackTrace();
				LogUtil.error("error while get "+emStockId +" StockVolume,will try to get data after "+reTry+" second!", e);
				try {
					Thread.sleep(1000*reTry);
				} catch (InterruptedException e1) {
					e1.printStackTrace();LogUtil.error("error while get StockVolume", e);
				}
			}
		}
		LogUtil.info("success to getStock "+emStockId+" volume message!");
		/**save volume message to TABLE:east_money_volume**/
		new EastMoneyService().saveToDB(eastMoneyVolume, EastMoneyVolume.class);
		
		/**save volume detail to TABLE:east_money_volume_detail if properties set flag to true**/
		String saveVolumeDetailDbFlag = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("save_volume_detail_db_flag");
		if(Boolean.valueOf(saveVolumeDetailDbFlag)){
			new EastMoneyService().saveToDB(eastMoneyVolumeDetail, EastMoneyVolumeDetail.class);
		}
		
		/**analyse the content and save it to the file if the volume and the summoney is huge,
		 * the volume and the summoney is set in the properties file.
		 */
		String exportVolumeFlag = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("export_volume_flag");
		if(Boolean.valueOf(exportVolumeFlag)){
			exportFile(eastMoneyVolume);
		}
		
	}
	
	
	/**
	 * check the volume and the allMoney huge's stocks and save it to the file
	 * @param file
	 * @param allVolume
	 * @param allMoney
	 * @throws IOException 
	 * @throws Exception 
	 */
	public EastMoneyVolume analyseToEastMoneyVolume(String volumeContent) throws IOException{
		
		//use ZhengZe to split the data {"09:25:17,4.65,1183,-1","09:30:12,4.62,2110,-1"....}
		List<String> list= ZhengZe.getResult(volumeContent, "[^\\\"^,]{1}[^\\\"]*[^\\\"]{1}", "");
		List<VolumeDetail> volumeDetails =new ArrayList<VolumeDetail>();
		double transInVolume = 0;
		double transOutVolume = 0;
		double transInMoney = 0;
		double transOutMoney = 0;
		for(String s:list){
			try {
				VolumeDetail vd = new VolumeDetail(s);
				if(vd.getFlag().equals("1")){//IN
					transInVolume+=vd.getTransVolume();
					transInMoney+=(vd.getPrice()*vd.getTransVolume()*100);
				}else{//out volume
					transOutVolume+=vd.getTransVolume();
					transOutMoney+=(vd.getPrice()*vd.getTransVolume()*100);
				}
				volumeDetails.add(vd);
			} catch (Exception e) {
				e.printStackTrace();LogUtil.error("error", e);
			}
		}
		
		EastMoneyVolume eastMoneyVolume = new EastMoneyVolume();
		eastMoneyVolume.setEmStockId(emStockId);
		eastMoneyVolume.setEmDateId(Integer.valueOf(DateUtil.getCurrentDate("yyyyMMdd")));
		eastMoneyVolume.setTransInVolume(transInVolume);
		eastMoneyVolume.setTransOutVolume(transOutVolume);
		eastMoneyVolume.setTransInMoney(transInMoney/10000);
		eastMoneyVolume.setTransOutMoney(transOutMoney/10000);
		return eastMoneyVolume;
	}
 
	/**
	 * export the data to outputfile
	 * @param eastMoneyVolume
	 * @throws IOException 
	 */
	private void exportFile(EastMoneyVolume eastMoneyVolume){
		synchronized (innerlock) {
			try {
				/**record the save path**/
				String fileSavePath = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("file_save_path");
				String sumVolume =  PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("sum_volume");
				String sumVolumeMoney =  PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("sum_volume_money");
				File file = new File(fileSavePath,"hugeVolumes"+DateUtil.getCurrentDate("yyyyMMdd")+".csv");
				int allVolume = Integer.valueOf(sumVolume);
				int allMoney = Integer.valueOf(sumVolumeMoney);
				boolean flag = FileUtil.createFile(file);
				FileWriter writer;
				
					writer = new FileWriter(file,true);
				
				
				double inVolume = eastMoneyVolume.getTransInVolume();
				double outVolume = eastMoneyVolume.getTransOutVolume();
				double inMoney = eastMoneyVolume.getTransInMoney();
				double outMoney = eastMoneyVolume.getTransOutMoney();
				if(flag){
					//if the file is not exist,create the file and add the head column message to the file
					writer.write("stock_id,in_volume(shou),out_volume(shou),inMoney(wan),outMoney(wan),in-out,in+out");
					writer.write("\n");
				}
				if((inVolume+outVolume)>allVolume&&(inMoney+outMoney)/10000>allMoney){
					writer.write(this.emStockId+","+inVolume+","+outVolume+","+inMoney/10000+","+outMoney/10000+","+(inMoney-outMoney)/10000+","+(inMoney+outMoney)/10000);
//					writer.write("\r\n");//in txt
					writer.write("\n");//in csv
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();LogUtil.error("error", e);
			}
		}
	}
	
	/**
	 * get the stockVolume 
	 * 1.get page size
	 * 2.get the content by foreach the page size
	 * @return
	 * @throws Exception 
	 */
	public StringBuffer getStockMessage() throws Exception{
		int pageSize = getPageSize();
		StringBuffer sb = new StringBuffer();
		String uri = null;
		for(int i=1;i<pageSize+1;i++){
			//LogUtil.println("get stock:"+emStockId+" volume,current get page:"+i+" content!");
			uri = url+"type=OB&stk="+emStockId+market+"&reference=xml&limit=0&page="+i;
			String content = getContent(uri);
			sb.append(content);
			if(i!=pageSize){
				sb.append(",");
			}
		}
		return sb;
	}

	

	
	
	/**
	 * get volume content by the uri
	 */ 
	public String getContent(String uri) throws Exception {
		String temp = EastMoneyUtil.getContent(uri,charsetName);
		List<String> list = ZhengZe.getResult(temp, "data:[^\\]]*", "data:[");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.size();i++){
			sb.append(list.get(i));
			if(i!=list.size()-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * return the pagesize by the uri
	 * @throws Exception 
	 */ 
	public int getPageSize() throws Exception  {
		String uri = url+"type=OB&stk="+emStockId+market+"&reference=xml&limit=0&page=1";
		String input = EastMoneyUtil.getContent(uri,charsetName);
		String pattern = "pages:[0-9]{1,10}";
		int pageSize = EastMoneyUtil.getNum(input, pattern,"pages:");
		if(market>5){
			return 0;
		}
		if(pageSize==0){
			market++;
			return getPageSize();
		}
		return pageSize;
	}
	
	
	/**
	 * write the stock volume date to the txt file
	 */ 
	public void testWriteData(){
		File file = new File("C://test.txt");
		File file2 = new File("C://test_data.txt");
		try {
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			BufferedWriter br2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
			for(int i=600000;i<602000;i++){
					Connection con = EastMoneyDao.getInstance().getConnection();
					EastMoneyVolumeDetail detail = new EastMoneyVolumeDetail();
					detail.setEmStockId(String.valueOf(i));
					List<?> list = EastMoneyDao.getInstance().findEastMoneyVolumeDetail(detail, con);
					EastMoneyVolumeDetail e2 = (EastMoneyVolumeDetail)list.get(0);
					if(e2.getEmVolumeDetailStr().toString().trim().equals("")){
						br.write(e2.getEmStockId());
						br.newLine();
					}else{
						br2.write(e2.getEmStockId()+":"+e2.getEmVolumeDetailStr().toString());
						br2.newLine();
					}
			}
			br.close();
			br2.close();
		} catch (Exception e1) {
			e1.printStackTrace();LogUtil.error("error", e1);
		}
	}
	

	private class VolumeDetail{
		private String transTime;//trans date
		private double price;//trans price
		private int transVolume;//trans volume
		private String flag;//in or out the trans
		public VolumeDetail(String volumeStr) throws Exception{
			String[] strs = volumeStr.split(",");
			if(strs.length!=4){
				throw new Exception("the length is unexpect data,it should be 4,but not "+strs.length);
			}
			this.transTime = strs[0];
			this.price = Double.valueOf(strs[1].trim());
			this.transVolume = Integer.valueOf(strs[2].trim());
			this.flag = strs[3];
		}
		public String getTransTime() {
			return transTime;
		}
		public void setTransTime(String transTime) {
			this.transTime = transTime;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public int getTransVolume() {
			return transVolume;
		}
		public void setTransVolume(int transVolume) {
			this.transVolume = transVolume;
		}
		public String getFlag() {
			return flag;
		}
		public void setFlag(String flag) {
			this.flag = flag;
		}
	}
	
	public static void main(String[] args) throws Exception{ 
		
		//add the stocks and do analyse these stocks
		List<String> stocks = new ArrayList<String>();
//		BufferedReader br = new BufferedReader(new InputStreamReader(StockVolume.class.getResourceAsStream("stocks.data")));
//		String readLine =null;
//		try {
//			while((readLine=br.readLine())!=null){
//				stocks.add(readLine.trim());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();LogUtil.error("error", e);
//		}
		stocks.add("601111");
		stocks.add("601669");//中国水电
		stocks.add("601106");//一重
		stocks.add("002142");//宁波
		stocks.add("601899");//紫金
		stocks.add("600000");
		stocks.add("601299");//北车
		stocks.add("600116");//三峡
		stocks.add("600036");//招商
		
		stocks.add("600131");//岷江
		
		stocks.add("600022");//济南
		stocks.add("601288");//农行
		stocks.add("600115");
		
		
		stocks.add("601991");
		
		
//		List<EastMoneyStock> list = (List<EastMoneyStock>) EastMoneyDao.getInstance().findData(null, EastMoneyStock.class);
		for(String stock:stocks){
			//test one stock and get the stock volume
			try {
				StockVolume sv =new StockVolume(stock);
				System.out.println(stock+","+sv.getPageSize()+","+sv.getMarket());
				EastMoneyVolume emv = sv.analyseToEastMoneyVolume(sv.getStockMessage().toString());
				LogUtil.info(emv.getEmStockId()+"  inMoney:"+emv.getTransInMoney()+" outMoney:"+emv.getTransOutMoney()+"  inVolume:"+emv.getTransInVolume()+" outVolume:"+emv.getTransOutVolume() );
//				sv.exportFile(emv);
			} catch (Exception e) {
				e.printStackTrace();LogUtil.error("error", e);
			}
		}
		
	}


}

