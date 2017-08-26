package com.lgh.eastmoney.bfo;

import java.text.DecimalFormat;

import com.lgh.util.logging.LogUtil;

/**
 * 个人所买的股票
 * @author liuguohu
 *
 */
public class EastMoneyPersonBuy {
	private String emStockId;
	private double amount;//持有份额
	private double buyMoney;//买入价
	
	public EastMoneyPersonBuy(String data) throws Exception{
		if(data==null||data.trim().equals("")){
			throw new Exception("EastMoneyPersonBuy data is null!");
		}
		String[] datas = data.split(",");
		if(datas.length<3||datas.length>3){
			throw new Exception("EastMoneyPersonBuy data length "+datas.length+"is invalidate,should be 3!");
		}
		emStockId = datas[0];
		amount = Double.valueOf(datas[1]);
		buyMoney = Double.valueOf(datas[2]);
	}
	
	/**
	 * @return the emStockId
	 */
	public String getEmStockId() {
		return emStockId;
	}
	/**
	 * @param emStockId the emStockId to set
	 */
	public void setEmStockId(String emStockId) {
		this.emStockId = emStockId;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * 获得当天盈亏
	 * @param riseDropMoney
	 * @return
	 */
	public double getCurrentDayProfitOrLoss(double riseDropMoney){
		return riseDropMoney*getAmount(); 
	}
	
	public String getFormatCurrentDayProfitOrLoss(double riseDropMoney){
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(riseDropMoney*getAmount()); 
	}
	/**
	 * 总盈亏
	 * @param newestPrice
	 * @return
	 */
	public double getAllProfitOrLoss(double newestPrice){
		return (newestPrice-getBuyMoney())*getAmount();
	}
	public String getFormatAllProfitOrLoss(double newestPrice){
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format((newestPrice-getBuyMoney())*getAmount()); 
	}
	/**
	 * 盈亏比率
	 * @param newestPrice
	 * @return
	 */
	public double getProfitOrLossPercent(double newestPrice){
		return (newestPrice-getBuyMoney())*100/getBuyMoney();
	}
	public String getFormatProfitOrLossPercent(double newestPrice){
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format((newestPrice-getBuyMoney())*100/getBuyMoney())+"%"; 
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the buyMoney
	 */
	public double getBuyMoney() {
		return buyMoney;
	}
	/**
	 * @param buyMoney the buyMoney to set
	 */
	public void setBuyMoney(double buyMoney) {
		this.buyMoney = buyMoney;
	}
	public static void main(String[] args) {
		LogUtil.info(3<3);
	}
}
