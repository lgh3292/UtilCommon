package com.lgh.eastmoney.ctl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lgh.eastmoney.bfo.EastMoneyURLMsg;
import com.lgh.util.logging.LogUtil;


public abstract class AbstractStockWorker  implements Runnable{

	
	protected String charsetName = "utf-8";
	protected int pageSize = 20;
	/**
	 * 单线程 获得所有的URL上 的信息
	 * @param urlMsg
	 * @param pageSizeName
	 * @param pageNumName
	 * @return
	 */
	protected List<?> getAllMessageBySingleThread(EastMoneyURLMsg urlMsg,String pageSizeName,String pageNumName){
 		List<Object> allMessages = new ArrayList<Object>();
 		LogUtil.info("...getting..."+urlMsg.getType()+"...."+urlMsg.getParameters());
 		String uri = urlMsg.getURL();
 		try {
 			int pageSize = getPageSize(uri);
 			for(int i=1;i<pageSize+1;i++){
 				urlMsg.setParameter(pageNumName,i+"");
 				String url = urlMsg.getURL();
 				List<?> eastMoneyRiseDrops = getMessage(url);
 				allMessages.addAll(eastMoneyRiseDrops);
 			}
 		} catch (Exception e) {
 			e.printStackTrace();LogUtil.error("error", e);
 		}
 		return allMessages;
	}
	
	
	/**
	 * 多线程 获得所有的URL上 的信息
	 * @param urlMsg
	 * @param pageSizeName
	 * @param pageNumName
	 * @return
	 */
	public Set<?> getAllMessageByMulThread(EastMoneyURLMsg urlMsg){
		/**mul thread to get stock message**/
		final Set<Object> allMessages = Collections.synchronizedSet(new HashSet<Object>());
		allMessages.clear();
		setPageSize(urlMsg);
		String uri = urlMsg.getURL();
		if(uri!=null&&!uri.trim().equals("")){
			Thread[] threads = null;
			try {
				int pageSize = getPageSize(uri);
				threads = new Thread[pageSize];
				for(int i=0;i<pageSize;i++){
					setPageNum(urlMsg,i);
					final String url = urlMsg.getURL();
					threads[i]=new Thread(new Runnable(){
						public void run(){
							List<?> eastMoneyRiseDrops = getMessage(url);
							allMessages.addAll(eastMoneyRiseDrops);
						}
					});
					threads[i].start();
				}
				//wait the thread,if time exceed 5000,stop the thread and return
				for(Thread thread:threads){
					try {
						thread.join(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();LogUtil.error("error", e);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();LogUtil.error("error", e);
			}
		}
		return allMessages;
	}
	//分页
	public abstract void setPageSize(EastMoneyURLMsg urlMsg);
	public abstract void setPageNum(EastMoneyURLMsg urlMsg,int pageNum);
	
	protected abstract List<?> getMessage(String url);
	protected abstract int getPageSize(String uri) throws Exception ;
}
