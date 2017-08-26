package com.lgh.eastmoney.ctl;

import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.lgh.eastmoney.bo.EastMoneyStock;
import com.lgh.eastmoney.bo.EastMoneyVolume;
import com.lgh.eastmoney.ui.PropertiesConfig;
import com.lgh.util.DateUtil;
import com.lgh.util.FileUtil;
import com.lgh.util.coms.UISystemTray;
import com.lgh.util.logging.LogUtil;

/**
 * use the stack's way to run the StockVolume,first will put all the stocks to the Stacks,
 * and the stocks will be load in the construct method from the stocks.data
 * then run by the threadMount(if the thread number in memory are too many,the web(eastmoney.com) may
 * forbid people to visit again,so you couldn't make too much thread in the memory)
 * pop the StockVolume from the stack and run.
 * @author liuguohu
 *
 */
public class StockWorker extends Thread{
	private Stack<Runnable> stack = new Stack<Runnable>();
	private File stockDataFile = new File("conf/stocks.data");
	private List<StockVolume> stocks = new ArrayList<StockVolume>();
	public StockWorker(){
	}
	
	@Override
	public void run(){
		LogUtil.info("get stock volume and risedrop message........");
		StockWorker worker = new StockWorker();
		//1.add default stocks
		worker.addDefaultStocks();
//		worker.addStocks(600000, 600020);
		
		//2.add stock volume worker
		worker.addStockVolumeWorker();
		
		//3.add stock riseDrop worker
		worker.addStockRiseDropWorker();
		worker.startWorker(10);
		PropertiesConfig.getWritableEastmoneyProperties().put(DateUtil.getCurrentDate("yyyyMMdd"), "ok");
		PropertiesConfig.updateWritableEastmoney();
		LogUtil.info("end get stock volume and risedrop message!");
	}
	/**
	 * load the stocks in the mareket  from the stocks.data
	 */
	public void addDefaultStocks(){
		//StockWorker.class.getResourceAsStream("stocks.data")
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(stockDataFile)));
			String readLine =null;
			while((readLine=br.readLine())!=null){
				String[] datas = readLine.split(",");
				StockVolume sv = new StockVolume(datas[0], Integer.valueOf(datas[1]));
				//if(sv.getMarket()<5){//will not add the data if the market exceed 5
				stocks.add(sv);
			}
		} catch (IOException e) {
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

		List<EastMoneyStock> list = (List<EastMoneyStock>) new EastMoneyService().findData(null, EastMoneyStock.class);
		List<StockVolume> temp = new ArrayList<StockVolume>();
		for(EastMoneyStock eastMoneyStock:list){
			temp.add(toStockVolume(eastMoneyStock));
		}
		LogUtil.info("There has "+stocks.size()+" volume stock before add stock from db!");
		for(StockVolume sv:temp){
			if(!stocks.contains(sv)){
				stocks.add(sv);
			}
		}
		LogUtil.info("There has "+stocks.size()+" volume stock after add stock from db!");
	}
	
	public StockVolume toStockVolume(EastMoneyStock eastMoneyStock){
		StockVolume sv = new StockVolume(eastMoneyStock.getEmStockId(),eastMoneyStock.getEmStockName());
		return sv;
	}
	/**
	 * add stocks if you want to put it to stack from begin to the end 
	 * @param start
	 * @param end
	 */
	public void addStocks(int start,int end){
		if(stack==null){
			stack = new Stack<Runnable>();
		}
		for(int i=start;i<end;i++){
			StockVolume s = new StockVolume(String.valueOf(i));
			stack.add(s);
		}
	}
 
	/**
	 * create stockVolume
	 */
	public void addStockVolumeWorker(){
		/**save volume detail to TABLE:east_money_volume_detail if properties set flag to true
		 * will not add to the stack if the job has runned.
		 * **/
		LogUtil.info("Add stock to volume Worker,Get the current day's runned task from db,will add the not runned task!");
		String saveVolumeEnable = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("save_volume_enable");
		List<EastMoneyVolume> runnedVolumeStock = null;
		try {
			//查找当天已经跑过的任务
			runnedVolumeStock = new EastMoneyService().
			findRunnedEastMoneyVolume(Long.valueOf(DateUtil.getCurrentDate("yyyyMMdd")).longValue());
		} catch (NumberFormatException e) {
			e.printStackTrace();LogUtil.error("error", e);
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
		if(Boolean.valueOf(saveVolumeEnable)){
			if(stocks!=null){
				for(StockVolume sv:stocks){
					boolean find = false;
					//如果当天已经跑过，则不加个栈里
					for(EastMoneyVolume ev:runnedVolumeStock){
						if(ev.getEmStockId().equals(sv.getEmStockId())){
							find = true;
							break;
						}
					}
					if(!find){
						stack.add(sv);
					}
				}
			}
			LogUtil.info("Find "+runnedVolumeStock.size()+" volume task has runned,has "+stack.size()+" not runned stack,add to worker and waiting for execute the getting volume data");
		}
	}
	
	/**
	 * add riseDropWorker
	 */
	public void addStockRiseDropWorker(){
		/**this flag is use to judge whether open ths save risedrop_function**/
		String risedropFunctionEnable = PropertiesConfig.getReadOnlyEastmoneyProperties().getProperty("risedrop_function_enable");
		if(Boolean.valueOf(risedropFunctionEnable)){
			StockRiseDrop  srd = new StockRiseDrop();
			stack.add(srd);
		}
	}
	/**
	 * start Fetch date with the threadMount
	 */
	public void startWorker(int threadMount) {
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < threadMount; i++) {
		
			Thread t = new Thread(new Runnable() {

				public void run() {
					boolean flag = true;
					while(flag){
						if(stack.isEmpty()){
							flag = false;
							break;
						}
						Runnable s = stack.pop();
						LogUtil.info("...rest task count:"+stack.size());
						Thread thread = new Thread(s);
						thread.start();
						//thread will sheep until the StockVolume finish the request and save the data
						try {
							thread.join(60000);
						} catch (Exception e) {
							e.printStackTrace();LogUtil.error("error", e);
						}
					}
				}
			});
			t.start();
			threads.add(t);
		}
		for(Thread t:threads){
			try {
				t.join(6000000);
			} catch (InterruptedException e) {
				e.printStackTrace();LogUtil.error("error", e);
			}
		}
		//任务跑完 1.判断文件是否存在，如果存在先删除  2.将最新的股票数据加入到文件里
		if(stockDataFile.exists()){
			LogUtil.info("Finish to get "+stocks.size()+" volume data,delete stocks.data and reCreate the file. delete status:"+stockDataFile.delete());
		}
		FileUtil.createFile(stockDataFile);
		for(StockVolume sv:stocks){
			FileUtil.writeToLastLine(stockDataFile, sv.getEmStockId()+","+sv.getMarket());
			LogUtil.info(sv.getEmStockId()+","+sv.getMarket());
		}
		if(UISystemTray.getInstance()!=null){
			UISystemTray.getInstance().getTrayIcon().displayMessage("volume and risedrop", "finish to get "+stocks.size()+" volumn data!", MessageType.INFO);	
		}
	}
	
 
	public static void main(String[] args) {
		new StockWorker().start();
	}
}
