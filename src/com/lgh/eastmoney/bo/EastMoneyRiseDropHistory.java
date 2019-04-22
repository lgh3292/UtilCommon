package com.lgh.eastmoney.bo;

import java.text.DecimalFormat;
import java.util.Date;

import com.lgh.util.DateUtil;
import com.lgh.util.logging.LogUtil;
/**
 * 涨股涨跌历史记录
 * @author liuguohu
 *
 */
public class EastMoneyRiseDropHistory{
	private String type;
	private String emStockId;
	private Long transDate;//20110101
	private Double kaipan;
	private Double higestPrice;
	private Double lowestPrice;
	private Double shoupan;
	private Double transVolume;
	private Double transMoney;
	
	
	private Date transStartDate;
	private Date transEndDate; 
	

	//相对上一天涨跌幅及涨跌额
	private Double riseDropScope;
	private Double riseDropMoney;
	//昨天的收盘
	private Double yesShoupan;

	public EastMoneyRiseDropHistory(){
		
	}
	/**
	 * check whether kanpan,higestPrice,lowestPrice,shoupan is normal is one of these data is 0 will return false
	 * @return
	 */
	public boolean isHealthy(){
		if(kaipan!=null&&kaipan!=0){
			if(higestPrice!=null&&higestPrice!=0){
				if(lowestPrice!=null&&lowestPrice!=0){
					if(shoupan!=null&&shoupan!=0){
						return true;
					}	
				}	
			}
		}
		return false;
	}

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("0.00");
		Double d = 0.00;
		LogUtil.info(df.format(d));
		LogUtil.info(df.format(0.019));

		EastMoneyRiseDropHistory e = new EastMoneyRiseDropHistory();
		e.setKaipan(0.01);
		e.setLowestPrice(0.1);
		e.setHigestPrice(0.0);
		e.setShoupan(0.10);
		LogUtil.info(e.isHealthy());

	}
	/**
	 * @return the yesShoupan
	 */
	public Double getYesShoupan() {
		return yesShoupan;
	}

	/**
	 * @param yesShoupan the yesShoupan to set
	 */
	public void setYesShoupan(Double yesShoupan) {
		this.yesShoupan = yesShoupan;
	}
	/**
	 * @return the transStartDate
	 */
	public Date getTransStartDate() {
		return transStartDate;
	}

	public int getTransStartDateToInt(){
		return Integer.valueOf(DateUtil.getDateStr(transStartDate, "yyyyMMdd"));
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
	public int getTransEndDateToInt() {
		return Integer.valueOf(DateUtil.getDateStr(transEndDate, "yyyyMMdd"));
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

	public String getRiseDropScopeStr(){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(riseDropScope*100)+"%";
	}
	/**
	 * @param riseDropScope the riseDropScope to set
	 */
	public void setRiseDropScope(Double riseDropScope) {
		this.riseDropScope = riseDropScope;
	}

	/**
	 * @return the riseDropMoney
	 */
	public Double getRiseDropMoney() {
		return riseDropMoney;
	}

	/**
	 * @param riseDropMoney the riseDropMoney to set
	 */
	public void setRiseDropMoney(Double riseDropMoney) {
		this.riseDropMoney = riseDropMoney;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmStockId() {
		return this.emStockId;
	}

	public void setEmStockId(String emStockId) {
		this.emStockId = emStockId;
	}

	public Long getTransDate() {
		return this.transDate;
	}

	public void setTransDate(Long transDate) {
		this.transDate = transDate;
	}


	public Double getKaipan() {
		return this.kaipan;
	}

	public void setKaipan(Double kaipan) {
		this.kaipan = kaipan;
	}

	public Double getHigestPrice() {
		return this.higestPrice;
	}

	public void setHigestPrice(Double higestPrice) {
		this.higestPrice = higestPrice;
	}

	public Double getLowestPrice() {
		return this.lowestPrice;
	}

	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public Double getShoupan() {
		return this.shoupan;
	}

	public void setShoupan(Double shoupan) {
		this.shoupan = shoupan;
	}

	public Double getTransVolume() {
		return this.transVolume;
	}

	public String getTransVolumeStr(){
		return this.transVolume.intValue()/10000+"万";
	}
	public void setTransVolume(Double transVolume) {
		this.transVolume = transVolume;
	}

	public Double getTransMoney() {
		return this.transMoney;
	}
	public String getTransMoneyStr() {
		return this.transMoney.intValue()/10000+"万";
	}
	public void setTransMoney(Double transMoney) {
		this.transMoney = transMoney;
	}
}