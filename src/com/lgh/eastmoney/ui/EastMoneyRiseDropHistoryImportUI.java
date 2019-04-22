package com.lgh.eastmoney.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lgh.eastmoney.bo.EastMoneyRiseDropHistory;
import com.lgh.eastmoney.bo.EastMoneyStock;
import com.lgh.eastmoney.ctl.EastMoneyService;
import com.lgh.util.DateUtil;
import com.lgh.util.logging.LogUtil;

public class EastMoneyRiseDropHistoryImportUI{
	public  Date date;//需要导入 的数据的起始时间
	private List<EastMoneyStock> eastMoneyStocks = null;

	public EastMoneyRiseDropHistoryImportUI(){
		date = EastMoneyUtil.getTheNewestImportDate();
	}
	
	public static void main(String[] args) throws ParseException {
//		EastMoneyStock stock = new EastMoneyStock();
//		stock.setType("SH");
//		stock.setEmStockId("600000");
//		stock.setEmStockName("浦发银行");
//		List<EastMoneyStock> list  =new ArrayList<EastMoneyStock>();
//		list.add(stock);
//		List<EastMoneyStock> list = (List<EastMoneyStock>)new EastMoneyRiseDropHistoryImport().findData(null);
//		LogUtil.info(list.size()+"  "+list.contains(stock));
		 
		new EastMoneyRiseDropHistoryImportUI().importData(new File("C:\\Documents and Settings\\liuguohu\\Desktop\\data1"));
	}

	 
	
	public void importData(File file){
		eastMoneyStocks = (List<EastMoneyStock>)new EastMoneyService().findData(null,EastMoneyStock.class);
		File[] files = file.listFiles();
		int count = 0;
		//insert to east_money_risedrop_history count
		int sum_risedrop_history =0; 
		//insert to east_money_stock
		int sum_new_stock = 0;
		long start = System.currentTimeMillis();
		StringBuffer newstockBuffer = new StringBuffer();
		for(File f:files){
			LogUtil.info("analyse file:"+ f.getName() +"  "+count+"/"+files.length);
			String fileName = f.getName();
			String type = fileName.substring(0,2);
			
			BufferedReader br = null;
	    	try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String readLine = null;
				int index = 0;
				List<EastMoneyRiseDropHistory> list = new ArrayList<EastMoneyRiseDropHistory>();
				String emStockId = null;
				String emStockName = null;
				EastMoneyStock stock = new EastMoneyStock();
				while((readLine=br.readLine())!=null){
					if(index==0){
						String[] strs = readLine.split(" ");
						if(strs.length>2){
							emStockId = strs[0];
							emStockName = strs[1];
							stock.setType(type);
							stock.setEmStockId(emStockId);
							stock.setEmStockName(emStockName);
						}
					}else if(index>1){
						String[] strs  = readLine.split("	");
						if(strs.length==7){
							EastMoneyRiseDropHistory history = new EastMoneyRiseDropHistory();
							history.setType(type);
							history.setEmStockId(emStockId);
							history.setTransDate(new Long(getDate(strs[0])));
							history.setKaipan(Double.valueOf(strs[1]));
							history.setHigestPrice(Double.valueOf(strs[2]));
							history.setLowestPrice(Double.valueOf(strs[3]));
							history.setShoupan(Double.valueOf(strs[4]));
							history.setTransVolume(Double.valueOf(strs[5]));
							history.setTransMoney(Double.valueOf(strs[6]));
							if(history.getTransDate()>Long.valueOf(DateUtil.getDateStr(date, "yyyyMMdd"))){
								list.add(history);	
							}
						}else{
							throw new Exception(" the length is not the 7!");
						}
					}
					index++;
				}
				new EastMoneyService().saveToDB(list, EastMoneyRiseDropHistory.class);
				if(!eastMoneyStocks.contains(stock)){
					//this is a new stock
					newstockBuffer.append("\n");
					newstockBuffer.append(stock.getType()+stock.getEmStockId()+"   "+new String(stock.getEmStockName().getBytes(),"GBK"));
					sum_new_stock++;
					new EastMoneyService().saveToDB(stock, EastMoneyStock.class);
				}
				sum_risedrop_history+=list.size();
			} catch (Exception e) {
				e.printStackTrace();LogUtil.error("error", e);
			}finally{
				if(br!=null){
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();LogUtil.error("error", e);
					}
				}
			}
			count++;
		}
		
		
		LogUtil.info("finish to save to db:"+sum_risedrop_history+"  all waste time:"+(System.currentTimeMillis()-start)+"  date:"+DateUtil.getCurrentDate("yyyyMMdd"));
		StringBuffer messageBuffer = new StringBuffer();
		messageBuffer.append("\n#ImportDate:"+DateUtil.getCurrentDate("yyyyMMdd HH:mm:ss")+" insert "+sum_risedrop_history+" to east_money_risedrop_history and "+sum_new_stock+" new stock to east_money_stock !");
		if(newstockBuffer.length()>0){
			messageBuffer.append("\n ----------the new stocks start----------");
			messageBuffer.append(newstockBuffer);
			messageBuffer.append("\n -------------     end    ---------------");
		}
		
		messageBuffer.append("\n"+DateUtil.parseDateToStr(new Date(), "yyyyMMdd HH:mm:ss"));
		EastMoneyUtil.writeToLastLine(messageBuffer.toString());
	}
	/**
	 * format date from 04/03/1991 to 19910403
	 * @param date
	 * @return
	 */
	public static int getDate(String date){
		String[] strs = date.split("/");
		return Integer.valueOf(strs[2]+strs[0]+strs[1]);
	}

	public void run() {
		
	}
	


}
