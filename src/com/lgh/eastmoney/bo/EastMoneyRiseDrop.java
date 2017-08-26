package com.lgh.eastmoney.bo;

import java.text.DecimalFormat;
import java.util.Date;

import com.lgh.eastmoney.bfo.EastMoneyPersonBuy;
import com.lgh.util.DateUtil;
import com.lgh.util.logging.LogUtil;


/**
 * 
java code				sql column
code;                   code                        6000281,                                                                                                     
emStockId;              em_stock_id                 代码 600028,                                                                                                 
emStockName;            em_stock_name               名称 中国石化,                                                                                               
yesterdayStopv;         yesterday_stop              昨收 7.96,                                                                                                   
todayStart;             today_start                 今开 7.99,                                                                                                   
newestPrice;            newest_price                最新价 7.96,                                                                                                 
higestPrice;            higest_price                最高  8.00,                                                                                                  
lowestPrice;            lowest_price                最低 7.92,                                                                                                   
transMoney;             trans_money                 成交额(万) 12200,                                                                                            
transVolume;            trans_volume                成交量(手) 153282,                                                                                           
riseDropMoney;          rise_drop_money             涨跌额 0.00,                                                                                                 
riseDropScope;          rise_drop_scope             涨跌幅 0.00%,                                                                                                
averagePrice;           average_price               均价 7.96,                                                                                                   
scope;                  scope                       振幅 1.01%,                                                                                                  
weibi;                  weibi                       委比 -42.88%,                                                                                                
weicha;                 weicha                      委差 -7191,                                                                                                  
xianshou;               xianshou                    现手 ,35,                                                                                                    
neiPan;                 neiPan                      内盘 62253,                                                                                                  
waiPan;                 waiPan                      外盘 91029,                                                                                                  
xianshouFlag;           xianshou_flag               -1,                                                                                                          
unknow1;                unknow1;                    0,                                                                                                           
fiveMinRiseDrop;        fiveMinRise_drop            五分钟涨跌 0.00%,                                                                                            
liangbi;                liangbi                     量比 0.59,                                                                                                   
changeRate;             change_rate                 换手率 0.02%,                                                                                                
cityEarnings;           city_earnings               市盈利 8.41,                                                                                                 
unknow2;                unknow2                     001150|002464|003499|003500|003505|003510|003522|003528|003536|003550|003552|003570|003575|003596|5009|50012,
buy1;                   buy1                        买一 7.96,                                                                                                   
sell1;                  sell1                       卖一 7.97,                                                                                                   
transDate;              trans_date                  时间 2011-06-10 15:03:02,                                                                                    
unknow3;                unknow3                     0,                                                                                                           
unknow4;                unknow4                     69922039774",                                                                                                 


 * @author liuguohua
 *
 */
public class EastMoneyRiseDrop extends EastMoneyStock{
	
	private String code;//6000281,
//	private String emStockId;//代码 600028,
//	private String emStockName;//名称 中国石化,
	private double yesterdayStop;//昨收 7.96,
	private double todayStart;//今开 7.99,
	private double newestPrice;//最新价 7.96,
	private double higestPrice;//最高  8.00,
	private double lowestPrice;//最低 7.92,
	private double transMoney;//成交额(万) 12200,  
	private double transVolume;//成交量(手) 153282,
	private double riseDropMoney;//涨跌额 0.00,  
	private String riseDropScope;//涨跌幅 0.00%,
	private double averagePrice;//均价 7.96,
	private String scope;//振幅 1.01%,
	private String weibi;//委比 -42.88%,
	private double weicha;//委差 -7191,
	private double xianshou;//现手 35,
	private double neiPan;//内盘 62253,
	private double waiPan;//外盘 91029,
	private int xianshouFlag;//-1,  -1：现手为绿色，1：现手为红色  0:现手为黑色
	private int unknow1;//0,
	private String fiveMinRiseDrop;//五分钟涨跌 0.00%,
	private double liangbi;//量比 0.59,
	private String changeRate;//换手率 0.02%,
	private double cityEarnings;//市盈利 8.41,
	private String unknow2;//001150|002464|003499|003500|003505|003510|003522|003528|003536|003550|003552|003570|003575|003596|5009|50012,
	private double buy1;//买一 7.96,
	private double sell1;//卖一 7.97,
	private Long transDate;//时间 2011-06-10 15:03:02,
	private int unknow3;//0,
	private String unknow4;//69922039774",
	
	//for Trans_Date
	private Date transStartDate;
	private Date transEndDate;

	private String personBuyMsg;
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if(emStockId!=null){
			return Integer.valueOf(emStockId.trim());	
		}
		return 1;
	}

	public void update(EastMoneyRiseDrop drop){
		code = drop.getCode();
		emStockId = drop.getEmStockId();
		emStockName = drop.getEmStockName();
		yesterdayStop = drop.getYesterdayStop();
		todayStart = drop.getTodayStart();
		newestPrice = drop.getNewestPrice();
		higestPrice = drop.getHigestPrice();
		lowestPrice = drop.getLowestPrice();
		transMoney = drop.getTransMoney();
		transVolume = drop.getTransVolume();
		riseDropMoney = drop.getRiseDropMoney();
		riseDropScope = drop.getRiseDropScope();
		averagePrice = drop.getAveragePrice();
		scope = drop.getScope();
		weibi = drop.getWeibi();
		weicha = drop.getWeicha();
		xianshou = drop.getXianshou();
		neiPan = drop.getNeiPan();
		waiPan = drop.getWaiPan();
		xianshouFlag = drop.getXianshouFlag();
		unknow1 = drop.getUnknow1();
		fiveMinRiseDrop = drop.getFiveMinRiseDrop();
		liangbi = drop.getLiangbi();
		changeRate = drop.getChangeRate();
		cityEarnings = drop.getCityEarnings();
		unknow2 = drop.getUnknow2();
		buy1 = drop.getBuy1();
		sell1 = drop.getSell1();
		transDate = drop.getTransDate();
		unknow3 = drop.getUnknow3();
		unknow4 =  drop.getUnknow4();
		this.transStartDate = drop.getTransStartDate();
		this.transEndDate = drop.getTransEndDate();
		this.personBuyMsg = drop.getPersonBuyMsg();
	}
	
	public EastMoneyRiseDropHistory getEastMoneyRiseDropHistory() {
		EastMoneyRiseDropHistory history = new EastMoneyRiseDropHistory();
		history.setEmStockId(getEmStockId());
		history.setHigestPrice(getHigestPrice());
		history.setKaipan(getTodayStart());
		history.setLowestPrice(getLowestPrice());
		history.setRiseDropMoney(getRiseDropMoney());

		history.setRiseDropScope(getRiseDropScopeDouble() / 100);
		history.setShoupan(getNewestPrice());
		history.setTransDate(getTransDate());
		history.setTransMoney(getTransMoney());
		history.setTransVolume(getTransVolume());
		history.setYesShoupan(getYesterdayStop());
		return history;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EastMoneyRiseDrop){
			EastMoneyRiseDrop drop = (EastMoneyRiseDrop)obj;
			if(drop.getEmStockId()!=null&&emStockId!=null){
				return drop.getEmStockId().equals(emStockId);
			}
		}
		return false;
	}

	public EastMoneyRiseDrop(){
		
	}
	
	
	
	public EastMoneyRiseDrop(String[] data) throws Exception{
		if(data==null||data.length<30){
			throw new Exception("the data length be should not less than 30,please check it!");
		}
		code = data[0];
		emStockId = data[1];
		emStockName = data[2];
		yesterdayStop = Double.valueOf(data[3]);
		todayStart = Double.valueOf(data[4]);
		newestPrice = Double.valueOf(data[5]);
		higestPrice = Double.valueOf(data[6]);
		lowestPrice = Double.valueOf(data[7]);
		transMoney = Double.valueOf(data[8]);
		transVolume = Double.valueOf(data[9]);
		riseDropMoney = Double.valueOf(data[10]);
		riseDropScope = data[11];
		averagePrice = Double.valueOf(data[12]);
		scope = data[13];
		weibi = data[14];
		weicha = Double.valueOf(data[15]);
		xianshou = Double.valueOf(data[16]);
		neiPan = Double.valueOf(data[17]);
		waiPan = Double.valueOf(data[18]);
		xianshouFlag = Integer.valueOf(data[19]);
		unknow1 = Integer.valueOf(data[20]);
		fiveMinRiseDrop = data[21];
		liangbi = Double.valueOf(data[22]);
		changeRate = data[23];
		cityEarnings = Double.valueOf(data[24]);
		unknow2 = data[25];
		buy1 = Double.valueOf(data[26]);
		sell1 = Double.valueOf(data[27]);
		long l = DateUtil.parseDateToLong(data[28]);
		transDate = Long.valueOf(DateUtil.parseDateToStr(l, "yyyyMMdd"));
		unknow3 = Integer.valueOf(data[29]);
		unknow4 = data[30];
	}
	
	/**
	 * @return the personBuyMsg
	 */
	public String getPersonBuyMsg() {
		return personBuyMsg;
	}


	/**
	 * @param personBuyMsg the personBuyMsg to set
	 */
	public void setPersonBuyMsg(String personBuyMsg) {
		this.personBuyMsg = personBuyMsg;
	}
	
	public void setPersonBuyMsg(EastMoneyPersonBuy personBuy){
		StringBuffer sb = new StringBuffer();
		DecimalFormat format = new DecimalFormat("0.00");
		sb.append(personBuy.getFormatCurrentDayProfitOrLoss(riseDropMoney));
		sb.append(" "+personBuy.getFormatAllProfitOrLoss(newestPrice));
		sb.append(" "+personBuy.getFormatProfitOrLossPercent(newestPrice));
		this.personBuyMsg = sb.toString();
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
	 * @return the unknow2
	 */
	public String getUnknow2() {
		return unknow2;
	}

	/**
	 * @param unknow2 the unknow2 to set
	 */
	public void setUnknow2(String unknow2) {
		this.unknow2 = unknow2;
	}

	/**
	 * @return the unknow4
	 */
	public String getUnknow4() {
		return unknow4;
	}

	/**
	 * @param unknow4 the unknow4 to set
	 */
	public void setUnknow4(String unknow4) {
		this.unknow4 = unknow4;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the emStockId
	 */
	@Override
	public String getEmStockId() {
		return emStockId;
	}

	/**
	 * @param emStockId the emStockId to set
	 */
	@Override
	public void setEmStockId(String emStockId) {
		this.emStockId = emStockId;
	}

	/**
	 * @return the emStockName
	 */
	@Override
	public String getEmStockName() {
		return emStockName;
	}

	/**
	 * @param emStockName the emStockName to set
	 */
	@Override
	public void setEmStockName(String emStockName) {
		this.emStockName = emStockName;
	}

	/**
	 * @return the yesterdayStop
	 */
	public double getYesterdayStop() {
		return yesterdayStop;
	}

	/**
	 * @param yesterdayStop the yesterdayStop to set
	 */
	public void setYesterdayStop(double yesterdayStop) {
		this.yesterdayStop = yesterdayStop;
	}

	/**
	 * @return the todaySart
	 */
	public double getTodayStart() {
		return todayStart;
	}

	/**
	 * @param todaySart the todaySart to set
	 */
	public void setTodayStart(double todayStart) {
		this.todayStart = todayStart;
	}

	/**
	 * @return the newestPrice
	 */
	public double getNewestPrice() {
		return newestPrice;
	}

	/**
	 * @param newestPrice the newestPrice to set
	 */
	public void setNewestPrice(double newestPrice) {
		this.newestPrice = newestPrice;
	}

	/**
	 * @return the higestPrice
	 */
	public double getHigestPrice() {
		return higestPrice;
	}

	/**
	 * @param higestPrice the higestPrice to set
	 */
	public void setHigestPrice(double higestPrice) {
		this.higestPrice = higestPrice;
	}

	/**
	 * @return the lowestPrice
	 */
	public double getLowestPrice() {
		return lowestPrice;
	}

	/**
	 * @param lowestPrice the lowestPrice to set
	 */
	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	/**
	 * @return the transMoney
	 */
	public double getTransMoney() {
		return transMoney;
	}

	/**
	 * @param transMoney the transMoney to set
	 */
	public void setTransMoney(double transMoney) {
		this.transMoney = transMoney;
	}

	/**
	 * @return the transVolume
	 */
	public double getTransVolume() {
		return transVolume;
	}

	/**
	 * @param transVolume the transVolume to set
	 */
	public void setTransVolume(double transVolume) {
		this.transVolume = transVolume;
	}

	/**
	 * @return the riseDropMoney
	 */
	public double getRiseDropMoney() {
		return riseDropMoney;
	}

	/**
	 * @param riseDropMoney the riseDropMoney to set
	 */
	public void setRiseDropMoney(double riseDropMoney) {
		this.riseDropMoney = riseDropMoney;
	}

	/**
	 * @return the riseDropScope
	 */
	public String getRiseDropScope() {
		return riseDropScope;
	}
	
	
	public Double getRiseDropScopeDouble(){
		if(riseDropScope!=null){
			try {
				String scope = riseDropScope.substring(0,riseDropScope.length()-2);
				return Double.valueOf(scope);
			} catch (Exception e) {
				e.printStackTrace();LogUtil.error("error", e);
				return 0.0;
			}
		}
		return 0.0;
	}

	/**
	 * @param riseDropScope the riseDropScope to set
	 */
	public void setRiseDropScope(String riseDropScope) {
		this.riseDropScope = riseDropScope;
	}

	/**
	 * @return the averagePrice
	 */
	public double getAveragePrice() {
		return averagePrice;
	}

	/**
	 * @param averagePrice the averagePrice to set
	 */
	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the weibi
	 */
	public String getWeibi() {
		return weibi;
	}

	/**
	 * @param weibi the weibi to set
	 */
	public void setWeibi(String weibi) {
		this.weibi = weibi;
	}

	/**
	 * @return the weicha
	 */
	public double getWeicha() {
		return weicha;
	}

	/**
	 * @param weicha the weicha to set
	 */
	public void setWeicha(double weicha) {
		this.weicha = weicha;
	}

	/**
	 * @return the xianshou
	 */
	public double getXianshou() {
		return xianshou;
	}

	/**
	 * @param xianshou the xianshou to set
	 */
	public void setXianshou(double xianshou) {
		this.xianshou = xianshou;
	}

	/**
	 * @return the neiPan
	 */
	public double getNeiPan() {
		return neiPan;
	}

	/**
	 * @param neiPan the neiPan to set
	 */
	public void setNeiPan(double neiPan) {
		this.neiPan = neiPan;
	}

	/**
	 * @return the waiPan
	 */
	public double getWaiPan() {
		return waiPan;
	}

	/**
	 * @param waiPan the waiPan to set
	 */
	public void setWaiPan(double waiPan) {
		this.waiPan = waiPan;
	}

	/**
	 * @return the xianshouFlag
	 */
	public int getXianshouFlag() {
		return xianshouFlag;
	}

	/**
	 * @param xianshouFlag the xianshouFlag to set
	 */
	public void setXianshouFlag(int xianshouFlag) {
		this.xianshouFlag = xianshouFlag;
	}

	/**
	 * @return the unknow1
	 */
	public int getUnknow1() {
		return unknow1;
	}

	/**
	 * @param unknow1 the unknow1 to set
	 */
	public void setUnknow1(int unknow1) {
		this.unknow1 = unknow1;
	}

	/**
	 * @return the fiveMinRiseDrop
	 */
	public String getFiveMinRiseDrop() {
		return fiveMinRiseDrop;
	}

	/**
	 * @param fiveMinRiseDrop the fiveMinRiseDrop to set
	 */
	public void setFiveMinRiseDrop(String fiveMinRiseDrop) {
		this.fiveMinRiseDrop = fiveMinRiseDrop;
	}

	/**
	 * @return the liangbi
	 */
	public double getLiangbi() {
		return liangbi;
	}

	/**
	 * @param liangbi the liangbi to set
	 */
	public void setLiangbi(double liangbi) {
		this.liangbi = liangbi;
	}

	/**
	 * @return the changeRate
	 */
	public String getChangeRate() {
		return changeRate;
	}

	/**
	 * @param changeRate the changeRate to set
	 */
	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}

	/**
	 * @return the cityEarnings
	 */
	public double getCityEarnings() {
		return cityEarnings;
	}

	/**
	 * @param cityEarnings the cityEarnings to set
	 */
	public void setCityEarnings(double cityEarnings) {
		this.cityEarnings = cityEarnings;
	}

 

	/**
	 * @return the buy1
	 */
	public double getBuy1() {
		return buy1;
	}

	/**
	 * @param buy1 the buy1 to set
	 */
	public void setBuy1(double buy1) {
		this.buy1 = buy1;
	}

	/**
	 * @return the sell1
	 */
	public double getSell1() {
		return sell1;
	}

	/**
	 * @param sell1 the sell1 to set
	 */
	public void setSell1(double sell1) {
		this.sell1 = sell1;
	}

	/**
	 * @return the date
	 */
	public Long getTransDate() {
		return transDate;
	}

	/**
	 * @param date the date to set
	 */
	public void setTransDate(Long transDate) {
		this.transDate = transDate;
	}

 

	/**
	 * @return the unknow3
	 */
	public int getUnknow3() {
		return unknow3;
	}

	/**
	 * @param unknow3 the unknow3 to set
	 */
	public void setUnknow3(int unknow3) {
		this.unknow3 = unknow3;
	}

	
}
