package com.lgh.eastmoney.bo;

import java.text.DecimalFormat;

public class EastMoneyInOutFlow extends EastMoneyStock{
	private long transDate;
	private int marketType;
	private String industryCode;
	private String induNames;
	private int days;
	private double ZJNETIN;
	private double ZJNETINPCT;
	private String MTIME;
	private DecimalFormat decimalFormat;
	/**
	 * input:
	 * "TRADEDATE":20110907,"STOCKCODE":"600470","STOCKNAME":"六国化工","MARKETTYPE":1,"INDUSTRYCODE":"C4","INDUNAMES":"石油化工",
	 * "DAYS":1,"ZJNETIN":30730.465794,"ZJNETINPCT":47.7893,"MTIME":"2011-09-07T12:14:20+08:00"
	 * @param input
	 * @throws Exception 
	 */
	public EastMoneyInOutFlow(String input) throws Exception {
		String[] arrays = input.split(",");
		if(arrays.length<10){
			throw new Exception("unexpected arrays length:"+arrays.length);
		}
		transDate = (long) getDoubleData(arrays[0]);
		emStockId = getStringData(arrays[1]);
		emStockName = getStringData(arrays[2]);
		marketType = (int) getDoubleData(arrays[3]);
		industryCode = getStringData(arrays[4]);
		induNames = getStringData(arrays[5]);
		days = (int) getDoubleData(arrays[6]);
		ZJNETIN = getDoubleData(arrays[7]);
		ZJNETINPCT = getDoubleData(arrays[8]);
		MTIME = getStringData(arrays[9]);
		decimalFormat = new DecimalFormat("0.00");
	}
	
	public double getDoubleData(String data){
		return Double.valueOf(data.substring(data.indexOf(":")+1, data.length()));
	}
	
	public String getStringData(String data){
		return data.substring(data.indexOf(":")+2, data.length()-1);
	}
	
	/**
	 * @return the transDate
	 */
	public long getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(long transDate) {
		this.transDate = transDate;
	}

	 

	 

	/**
	 * @return the marketType
	 */
	public int getMarketType() {
		return marketType;
	}

	/**
	 * @param marketType the marketType to set
	 */
	public void setMarketType(int marketType) {
		this.marketType = marketType;
	}

	/**
	 * @return the industryCode
	 */
	public String getIndustryCode() {
		return industryCode;
	}

	/**
	 * @param industryCode the industryCode to set
	 */
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	/**
	 * @return the induNames
	 */
	public String getInduNames() {
		return induNames;
	}

	/**
	 * @param induNames the induNames to set
	 */
	public void setInduNames(String induNames) {
		this.induNames = induNames;
	}

	/**
	 * @return the days
	 */
	public int getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(int days) {
		this.days = days;
	}

	/**
	 * @return the zJNETIN
	 */
	public double getZJNETIN() {
		return Double.valueOf(decimalFormat.format(ZJNETIN));
	}

	/**
	 * @param zJNETIN the zJNETIN to set
	 */
	public void setZJNETIN(double zJNETIN) {
		ZJNETIN = zJNETIN;
	}

	/**
	 * @return the zJNETINPCT
	 */
	public double getZJNETINPCT() {
		return Double.valueOf(decimalFormat.format(ZJNETINPCT));
	}

	/**
	 * @param zJNETINPCT the zJNETINPCT to set
	 */
	public void setZJNETINPCT(double zJNETINPCT) {
		ZJNETINPCT = zJNETINPCT;
	}

	/**
	 * @return the mTIME
	 */
	public String getMTIME() {
		return MTIME;
	}

	/**
	 * @param mTIME the mTIME to set
	 */
	public void setMTIME(String mTIME) {
		MTIME = mTIME;
	}
}
