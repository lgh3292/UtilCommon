package com.lgh.eastmoney.ctl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.lgh.eastmoney.bfo.EastMoneyURLMsg;
import com.lgh.eastmoney.bo.EastMoneyInOutFlow;
import com.lgh.eastmoney.bo.EastMoneyRiseDrop;
import com.lgh.eastmoney.ui.EastMoneyUtil;
import com.lgh.util.ZhengZe;
import com.lgh.util.logging.LogUtil;

public class StockMoneyInOutFlow extends AbstractStockWorker {
	
	
	public StockMoneyInOutFlow(){
		this.charsetName = "gb2312";
		pageSize = 30;
	}
	
	
	public void run() {
		
	}
	
	/**
	 * 获得所有的资金流入流出情况并，保存数据
	 */
	public void getMessageAndSave(){
		List<EastMoneyURLMsg> dropStockMsgs = EastMoneyUtil.getRiseDropStockMsgs("in_out_flow_stock");
		for(EastMoneyURLMsg dropStockMsg:dropStockMsgs){
			try {
			    String uri = dropStockMsg.getURL();
				int pageSize = getPageSize(uri);
				LogUtil.info(dropStockMsg.getType()+"  "+pageSize);
				for (int i = 1; i < pageSize + 1; i++) {
					dropStockMsg.setParameter("page",i+"");
					final String url = dropStockMsg.getURL();
					Thread.sleep(100);
					List<EastMoneyRiseDrop> eastMoneyRiseDrops = (List<EastMoneyRiseDrop>) getMessage(url);
					//new EastMoneyService().saveToDB(eastMoneyRiseDrops, EastMoneyRiseDrop.class);
				}
			} catch (Exception e) {
				e.printStackTrace();LogUtil.error("error", e);
			}
		}
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
		urlMsg.setParameter("limit", pageSize+"");
	}
	@Override
	public void setPageNum(EastMoneyURLMsg urlMsg,int pageNum){
		urlMsg.setParameter("skip",pageNum*30+"");
	}
	
	
	/**
	 * input:
	 * {"TRADEDATE":20110907,"STOCKCODE":"600078","STOCKNAME":"澄星股份","MARKETTYPE":1,"INDUSTRYCODE":"C4","INDUNAMES":"石油化工",
	 * "DAYS":1,"ZJNETIN":36690.280929,"ZJNETINPCT":30.6425,"MTIME":"2011-09-07T12:14:20+08:00"},
	 * {"TRADEDATE":20110907,"STOCKCODE":"600470","STOCKNAME":"六国化工","MARKETTYPE":1,"INDUSTRYCODE":"C4","INDUNAMES":"石油化工",
	 * "DAYS":1,"ZJNETIN":30730.465794,"ZJNETINPCT":47.7893,"MTIME":"2011-09-07T12:14:20+08:00"}
	 * 
	 * pattern:[^\\{^\\}^,]{1}[^\\{^\\}]*[^\\{^\\}]{1}
	 * 
	 * expect result:
	 * "TRADEDATE":20110907,"STOCKCODE":"600078","STOCKNAME":"澄星股份","MARKETTYPE":1,"INDUSTRYCODE":"C4","INDUNAMES":"石油化工",
	 * "DAYS":1,"ZJNETIN":36690.280929,"ZJNETINPCT":30.6425,"MTIME":"2011-09-07T12:14:20+08:00"
	 * 
	 * "TRADEDATE":20110907,"STOCKCODE":"600470","STOCKNAME":"六国化工","MARKETTYPE":1,"INDUSTRYCODE":"C4","INDUNAMES":"石油化工",
	 * "DAYS":1,"ZJNETIN":30730.465794,"ZJNETINPCT":47.7893,"MTIME":"2011-09-07T12:14:20+08:00"
	 */
	@Override
	protected List<?> getMessage(String url) {
		List<EastMoneyInOutFlow> eastMoneyInOutFlow = new ArrayList<EastMoneyInOutFlow>();
		try {
			String content = getContent(url);
			List<String> list= ZhengZe.getResult(content, "[^\\{^\\}^,]{1}[^\\{^\\}]*[^\\{^\\}]{1}", "");
			for(String str:list){
				EastMoneyInOutFlow riseDrop = new EastMoneyInOutFlow(str);
				eastMoneyInOutFlow.add(riseDrop);
			}
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
		return eastMoneyInOutFlow;
	}

	/**
	 * get the input's content with the pattern
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String getContent(String url) throws Exception {
		String temp = EastMoneyUtil.getContent(url, charsetName);
		//temp:
		/**
	    input:  V_BS_QUICKWIN2_ZJNETIN={"offset":0,"rows":[{"TRADEDATE":20110825,"STOCKCODE":"601901","STOCKNAME":"方正证券",
	    		"MARKETTYPE":1,"INDUSTRYCODE":"I","INDUNAMES":"金融保险","DAYS":1,"ZJNETIN":89311.998859,"ZJNETINPCT":46.5731,"MTIME":
	    		"2011-08-25T15:15:54+08:00"}],"total_rows":2155,"query":[],"millis":5};  
				
		pattern: [\\\{]{1}[^\\{^\\}^\\[^\\]]{1,}[\\\}]{1}	
			
		expert result:
		
		"TRADEDATE":20110825,"STOCKCODE":"601901","STOCKNAME":"方正证券","MARKETTYPE":1,"INDUSTRYCODE":"I","INDUNAMES":"金融保险","DAYS":1,"ZJNETIN":89311.998859,"ZJNETINPCT":46.5731,"MTIME":"2011-08-25T15:15:54+08:00"
		*
		*/
		
		List<String> list = ZhengZe.getResult(temp, "[\\{]{1}[^\\{^\\}^\\[^\\]]{1,}[\\}]{1}", "");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.size();i++){
			sb.append(list.get(i));
			if(i!=list.size()-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}

	@Override
	protected int getPageSize(String uri) throws Exception {
		String input = EastMoneyUtil.getContent(uri,charsetName);
		String pattern = "\"total_rows\":[0-9]{1,10}";
		int total_rows = EastMoneyUtil.getNum(input, pattern,"\"total_rows\":");
		int a = total_rows/pageSize;
		int b = total_rows%pageSize;
		if(b>0){
			a+=1;
		}
		return a;
	}

	
	public static void main(String[] args) {
		try {
			
			//String url = "http://hq2qt.eastmoney.com/EM_Quote2010NumericApplication/index.aspx?type=s&sortType=C&sortRule=-1&pageSize=50&page=1&jsName=quote_123&style=42&_g=0.507468256896243";
			String url = "http://f10.stockstar.com/f10/V_BS_QUICKWIN2_ZJNETIN/skip=0&limit=30&sort=ZJNETIN%20desc&filter-DAYS-int=1&full=1&d=121021";
			//http://f10.stockstar.com/f10/V_BS_QUICKWIN2_ZJNETIN/skip=30&limit=30&sort=ZJNETIN%20desc&filter-DAYS-int=1&full=1&d=121547
//			LogUtil.info(new StockMoneyInOutFlow().getMessage(url));
			StockMoneyInOutFlow flow = new StockMoneyInOutFlow();
			List<EastMoneyURLMsg> dropStockMsgs = EastMoneyUtil.getRiseDropStockMsgs("in_out_flow_stock");
			Set set = flow.getAllMessageByMulThread(dropStockMsgs.get(0));
			System.out.println(set.size());
			Object o = set;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
