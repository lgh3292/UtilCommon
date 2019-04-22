package com.lgh.eastmoney.bo;
/**
 * data is get from EastMoneyVolumeDetail
 * @author liuguohu
 *
 */
public class EastMoneyVolume {

	private String emStockId;//股票ID
	private double transInMoney;//买入成交额
	private double transOutMoney;//卖出成交额
	private double transInVolume;//买入成交量
	private double transOutVolume;//卖出成交量
	
	private int emDateId;//当天时间 20110613
	
	public EastMoneyVolume() {
		super();
	}
	
	
	 
	/**
	 * @return the emDateId
	 */
	public int getEmDateId() {
		return emDateId;
	}
	/**
	 * @param emDateId the emDateId to set
	 */
	public void setEmDateId(int emDateId) {
		this.emDateId = emDateId;
	}
	public EastMoneyVolume(String emStockId, double transInMoney,
			double transOutMoney, double transInVolume, double transOutVolume,int emDateId) {
		super();
		this.emStockId = emStockId;
		this.transInMoney = transInMoney;
		this.transOutMoney = transOutMoney;
		this.transInVolume = transInVolume;
		this.transOutVolume = transOutVolume;
		this.emDateId = emDateId;
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
	 * @return the transInMoney
	 */
	public double getTransInMoney() {
		return transInMoney;
	}
	/**
	 * @param transInMoney the transInMoney to set
	 */
	public void setTransInMoney(double transInMoney) {
		this.transInMoney = transInMoney;
	}
	/**
	 * @return the transOutMoney
	 */
	public double getTransOutMoney() {
		return transOutMoney;
	}
	/**
	 * @param transOutMoney the transOutMoney to set
	 */
	public void setTransOutMoney(double transOutMoney) {
		this.transOutMoney = transOutMoney;
	}
	/**
	 * @return the transInVolume
	 */
	public double getTransInVolume() {
		return transInVolume;
	}
	/**
	 * @param transInVolume the transInVolume to set
	 */
	public void setTransInVolume(double transInVolume) {
		this.transInVolume = transInVolume;
	}
	/**
	 * @return the transOutVolume
	 */
	public double getTransOutVolume() {
		return transOutVolume;
	}
	/**
	 * @param transOutVolume the transOutVolume to set
	 */
	public void setTransOutVolume(double transOutVolume) {
		this.transOutVolume = transOutVolume;
	}

	
}
