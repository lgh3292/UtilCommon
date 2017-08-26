package com.lgh.eastmoney.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lgh.eastmoney.bo.EastMoneyRiseDrop;
import com.lgh.eastmoney.bo.EastMoneyRiseDropHistory;
import com.lgh.eastmoney.bo.EastMoneyStock;
import com.lgh.eastmoney.ctl.EastMoneyService;
import com.lgh.util.DateUtil;
import com.lgh.util.logging.LogUtil;
/**
 * analyse the stocks which adapt to the input condition
 * @author liuguohu
 *
 */
public class EastMoneyStockAnalyser {

	private List<EastMoneyStock> eastMoneyStocks = null;
	private Date import_date;
	//analyse condition
	private List<AnalyseCondition> analyseConditions;
	//the analyse result 
	private List<EastMoneyRiseDrop> analyseResultDrops;
	public EastMoneyStockAnalyser(List<AnalyseCondition> analyseConditions){
		this.analyseConditions = analyseConditions;
		initData();
	}
	
	private void initData(){
		import_date = EastMoneyUtil.getTheNewestImportDate();
		analyseResultDrops = new ArrayList<EastMoneyRiseDrop>();
		eastMoneyStocks = (List<EastMoneyStock>)new EastMoneyService().findData(null,EastMoneyStock.class);
	}
	
	/**
	 * analyse the stocks
	 * for each stock need to get data from below two steps
	 * 1.get history data between old days(1990) and import_date from east_money_risedrop_history
	 * 2.get lastest data between import_date and currentday from east_money_risedrop 
	 */
	private void doAnalyse(){
		List<EastMoneyStock> stocks = new ArrayList<EastMoneyStock>();
		EastMoneyHistoryUI ui = null;
		for(EastMoneyStock stock:eastMoneyStocks){
			int stockid = Integer.valueOf(stock.getEmStockId());
			if(stockid>=300000){
				List<EastMoneyRiseDropHistory> list = getEastMoneyRiseDropHistorys(stock);
				double[] higestMoney = new double[analyseConditions.size()];
				double[] lowestMoney = new double[analyseConditions.size()];
				
				for(int i=0;i<list.size();i++){
					
					EastMoneyRiseDropHistory curHis = list.get(i);
					for(int j=0;j<analyseConditions.size();j++){
						//if it's the first data,we set the riseDropScope and riseDropMoney as default value.
						Date startD = analyseConditions.get(j).getTransStartDate();
						Date endD = analyseConditions.get(j).getTransEndDate();
						Date transDate= DateUtil.parseDate(String.valueOf(curHis.getTransDate()), "yyyyMMdd");
						if((transDate.getTime()-startD.getTime())>=0&&(endD.getTime()-transDate.getTime())>=0){
							if(higestMoney[j]==0||lowestMoney[j]==0){
								higestMoney[j] = curHis.getShoupan();
								lowestMoney[j] = curHis.getShoupan();
								curHis.setRiseDropScope(new Double(0));
								curHis.setRiseDropMoney(new Double(0));
								curHis.setYesShoupan(0.0);
							}else{
								EastMoneyRiseDropHistory preHis = list.get(i-1);
								if(curHis.getShoupan()>higestMoney[j]){
									higestMoney[j] = curHis.getShoupan();
								}else if(curHis.getShoupan()<lowestMoney[j]){
									lowestMoney[j] = curHis.getShoupan();
								}
								curHis.setYesShoupan(preHis.getShoupan());
								curHis.setRiseDropMoney(curHis.getShoupan()-curHis.getKaipan());
								curHis.setRiseDropScope((curHis.getShoupan()-preHis.getShoupan())/preHis.getShoupan());
							}
						}
					}
					
				}
				
				boolean[] conditions = new boolean[analyseConditions.size()];
				for(int j=0;j<analyseConditions.size();j++){
					if((higestMoney[j]-lowestMoney[j])/higestMoney[j]>analyseConditions.get(j).getRiseDropScope()){
//						LogUtil.info(higestMoney+"  "+lowestMoney+"  "+(higestMoney-lowestMoney)/higestMoney+"  "+stock.getEmStockName()+"  "+stock.getEmStockId());
						conditions[j] = true;
					}
				}
				boolean addToList = true;
				for(boolean condition:conditions){
					if(!condition){
						addToList = false;
						break;
					}
				}
				if(addToList){
					stocks.add(stock);
					if(ui==null){
					ui = new  EastMoneyHistoryUI(stocks);
					}
				}

			}
		}
		
		for(EastMoneyStock stock:stocks){
			LogUtil.info("  "+stock.getEmStockName()+"  "+stock.getEmStockId());
		}
	}
	
	public List<EastMoneyRiseDropHistory> getEastMoneyRiseDropHistorys(EastMoneyStock stock){
		Date startDate = null;
		Date endDate =  null;
		for(AnalyseCondition analyseCondition:analyseConditions){
			if(startDate==null){
				startDate = analyseCondition.getTransStartDate();
			}
			if(endDate==null){
				endDate = analyseCondition.getTransEndDate();
			}
			if(analyseCondition.getTransStartDate().before(startDate)){
				startDate = analyseCondition.getTransStartDate();
			}
			if(analyseCondition.getTransEndDate().after(endDate)){
				endDate = analyseCondition.getTransEndDate();
			}
		}
		//1.get history data
		EastMoneyRiseDropHistory history = new EastMoneyRiseDropHistory();
		history.setEmStockId(stock.getEmStockId());
		history.setTransStartDate(startDate);
		if(import_date.before(endDate)){
			history.setTransEndDate(import_date);
		}else{
			history.setTransEndDate(endDate);
		}
		List<EastMoneyRiseDropHistory> list= (List<EastMoneyRiseDropHistory>)new EastMoneyService().findData(history,EastMoneyRiseDropHistory.class);
		//2.add east_money_risedrop data if the conditionEndDate is after then import_date
		if(import_date.before(endDate)){
			EastMoneyRiseDrop emrisedrop = new EastMoneyRiseDrop();
			emrisedrop.setEmStockId(stock.getEmStockId());
			emrisedrop.setTransStartDate(import_date);
			emrisedrop.setTransEndDate(new Date());
			List<EastMoneyRiseDrop> drops= (List<EastMoneyRiseDrop>) new EastMoneyService().findData(emrisedrop,EastMoneyRiseDrop.class);
			for(EastMoneyRiseDrop drop:drops){
				EastMoneyRiseDropHistory his = drop.getEastMoneyRiseDropHistory();
				list.add(his);
			}
			LogUtil.info(stock.getEmStockId()+"  "+stock.getEmStockName()+"   "+(list.size()-drops.size())+" row in risedropHis and "+drops.size()+" in riseDrops");
		}
		
		return list;
	}
 
	public static class AnalyseCondition{
		private Date transStartDate;
		private Date transEndDate;
		private Double riseDropScope;//the scope between the highest and the loweset price

		
		public AnalyseCondition(){
			
		}
		/**
		 * @return the transStartDate
		 */
		public Date getTransStartDate() {
			return transStartDate;
		}

		/**
		 * @param transStartDate the transStartDate to set
		 */
		public void setTransStartDate(Date transStartDate) {
			this.transStartDate = transStartDate;
		}

		/**
		 * @return the transEndDate
		 */
		public Date getTransEndDate() {
			return transEndDate;
		}

		/**
		 * @param transEndDate the transEndDate to set
		 */
		public void setTransEndDate(Date transEndDate) {
			this.transEndDate = transEndDate;
		}

		/**
		 * @return the riseDropScope
		 */
		public Double getRiseDropScope() {
			return riseDropScope;
		}

		/**
		 * @param riseDropScope the riseDropScope to set
		 */
		public void setRiseDropScope(Double riseDropScope) {
			this.riseDropScope = riseDropScope;
		}

	}
	
	public static void main(String[] args) throws ParseException {
		long start = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		//Condition1
		AnalyseCondition condition1 = new AnalyseCondition();
		condition1.setTransStartDate(sdf.parse("20111021"));
		condition1.setTransEndDate(sdf.parse("20111104"));
		condition1.setRiseDropScope(0.0);
		
		//Condition2
		AnalyseCondition condition2 = new AnalyseCondition();
		condition2.setTransStartDate(sdf.parse("20110216"));
		condition2.setTransEndDate(sdf.parse("20111021"));
		condition2.setRiseDropScope(0.4);
		
		List<AnalyseCondition> conditions = new ArrayList<AnalyseCondition>();
		conditions.add(condition1);
		conditions.add(condition2);
		EastMoneyStockAnalyser analyser = new EastMoneyStockAnalyser(conditions);
		analyser.doAnalyse();
		LogUtil.info("waste time:"+(System.currentTimeMillis()-start));
	}
}
