package com.lgh.eastmoney.ctl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.lgh.eastmoney.bfo.EastMoneyURLMsg;
import com.lgh.eastmoney.bfo.RealTimeMarketTrend;
import com.lgh.eastmoney.bo.EastMoneyRiseDrop;
import com.lgh.eastmoney.ui.EastMoneyUtil;
import com.lgh.util.ZhengZe;
import com.lgh.util.logging.LogUtil;

/**
 * record the (http://quote.eastmoney.com/center/list.html#10_0_0_u?sortType=C&sortRule=-1)
 * the stocks' rise or down(大盘的实时行情和各股票的实时行情)
 * @author liuguohua
 *
 */
public class StockRiseDrop extends AbstractStockWorker{
	
	public void run() {
		try {
			long start = System.currentTimeMillis();
			getMessageAndSave();
			LogUtil.info("....StockRiseDrop waste:"+(System.currentTimeMillis()-start)+" to getStockMessageAndSaveToDB!");
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
	}
	
	
	/**
	 * 获得所有的涨跌情况并，保存数据
	 */
	public void getMessageAndSave(){
		List<EastMoneyURLMsg> dropStockMsgs = EastMoneyUtil.getRiseDropStockMsgs("risedrop_url_all");
		for(EastMoneyURLMsg dropStockMsg:dropStockMsgs){
			boolean success = false;
			int reTry = 0;
			while(!success){
				try {
					String uri = dropStockMsg.getURL();
					int pageSize = getPageSize(uri);
					LogUtil.info(dropStockMsg.getType()+"  "+pageSize);
					for (int i = 1; i < pageSize + 1; i++) {
						dropStockMsg.setParameter("page",i+"");
						final String url = dropStockMsg.getURL();
						Thread.sleep(100);
						List<EastMoneyRiseDrop> eastMoneyRiseDrops = (List<EastMoneyRiseDrop>) getMessage(url);
						new EastMoneyService().saveToDB(eastMoneyRiseDrops, EastMoneyRiseDrop.class);
					}
					success = true;
				} catch (Exception e) {
					e.printStackTrace();LogUtil.error("catch Exception while getMessage Content: "+dropStockMsg.getURL(), e);
					LogUtil.info("system will retry after:"+reTry+" second!");
					reTry++;
					try {
						Thread.sleep(1000*reTry);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		}
	}
	
	
	/**
	 * 获取大盘实时涨跌
	 * @param url
	 * @return
	 */
	public List<RealTimeMarketTrend> getRealtimeMarketTrend(String url){
		List<RealTimeMarketTrend> eastMoneyRiseDrops = new ArrayList<RealTimeMarketTrend>();
		try {
			//Thread.sleep(100);
			String content = getContent(url);
			List<String> list= ZhengZe.getResult(content, "[^\\\"^,]{1}[^\\\"]*[^\\\"]{1}", "");
			for(String str:list){
				//0000011,上证指数,2708.78,9108571,82868276,-14.71,-0.54%,2723.49,-1
				//3990012,深证成指,12085.92,7958467,62793568,-30.94,-0.26%,12116.86,-1
				//257,29,609
				//93,19,787
				String[] strs = str.split(",");
				if(strs.length>6){
					RealTimeMarketTrend riseDrop = new RealTimeMarketTrend(strs);
					eastMoneyRiseDrops.add(riseDrop);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
		return eastMoneyRiseDrops;
	}
	
	/**
	 * get the real time message of all stock rise and drop
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws InterruptedException 
	 */
	public Set<EastMoneyRiseDrop> getAllRealTimeStockMessage(EastMoneyURLMsg dropStockMsg) throws UnsupportedEncodingException{
		return (Set<EastMoneyRiseDrop>) getAllMessageByMulThread(dropStockMsg);
	}
	
	
	@Override
	public void setPageSize(EastMoneyURLMsg urlMsg){
		urlMsg.setParameter("pageSize", pageSize+"");
	}
	@Override
	public void setPageNum(EastMoneyURLMsg urlMsg,int pageNum){
		urlMsg.setParameter("page",pageNum+"");
	}
	
	/**
	 * get the stock riseDrop content
	 * 1.get page size
	 * 2.get the Stock RiseDrop content by foreach page size
	 * @return
	 */
	@Override
	public List<?> getMessage(String url){
		List<EastMoneyRiseDrop> eastMoneyRiseDrops = new ArrayList<EastMoneyRiseDrop>();
		try {
			String content = getContent(url);
			List<String> list= ZhengZe.getResult(content, "[^\\\"^,]{1}[^\\\"]*[^\\\"]{1}", "");
			for(String str:list){
				EastMoneyRiseDrop riseDrop = new EastMoneyRiseDrop(str.split(","));
				eastMoneyRiseDrops.add(riseDrop);
			}
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
		return eastMoneyRiseDrops;
	}
	/**
	 * get the Rise and Drop Content with the uri
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public String getContent(String url) throws Exception{
		String temp = EastMoneyUtil.getContent(url, charsetName);
		//temp:
		/**
	    input:  var C1Cache={quotation:["0000011,上证指数,2681.27,5561377,48367366,2.79,0.10%,2678.49,1","3990012,
		  	  	深证成指,11935.64,5572005,41800132,13.27,0.11%,11922.37,-1"]
				,record:["511,51,333","663,38,602"]}  
				
		pattern: [\\\"]{1}[^\\[]{1,}[\\\"]{1}	
			
		expert result:
		
		"0000011,上证指数,2680.29,5610067,48812009,1.81,0.07%,2678.49,0",
		"3990012,深证成指,11934.92,5622288,42145564,12.55,0.11%,11922.37,1", 
		"515,46,334",
		"655,56,592"
		*
		*/
		
		List<String> list = ZhengZe.getResult(temp, "[\\\"]{1}[^\\[]{1,}[\\\"]{1}", "");
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
	 * return the pagesize by the uri]
	 */ 
	@Override
	public int getPageSize(String url) throws Exception {
		String input = EastMoneyUtil.getContent(url,charsetName);
		String pattern = "pages:[0-9]{1,10}";
		return EastMoneyUtil.getNum(input, pattern,"pages:");
	}
	

	
	
	public static void main(String[] args) {
		StockRiseDrop  srd = new StockRiseDrop();
		try {
		//	new Thread(srd).start();
//			String content = srd.getContent("http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/cache.aspx?Type=c1&Reference=xml&0.6440368893826206");
//			LogUtil.info(content);
			
			
			//get stock real time mesage
			/****/
			List<EastMoneyURLMsg> dropStockMsgs = EastMoneyUtil.getRiseDropStockMsgs("risedrop_url_all");
			for(EastMoneyURLMsg dropStockMsg:dropStockMsgs){
//				LogUtil.info(srd.getAllRealTimeStockMessage(dropStockMsg).size()+"");
				srd.getMessage(dropStockMsg.getURL());
			}
			  
			
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
	}
}
