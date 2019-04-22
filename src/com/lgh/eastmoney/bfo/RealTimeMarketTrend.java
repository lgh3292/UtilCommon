package com.lgh.eastmoney.bfo;

/**
 * 大盘的实时行情
 * 上证,上证５０,Ａ股指数,Ｂ股指数,深证成指,深证综指,成份Ａ指,成份Ｂ指,沪深300,深证成指,深证综指,创业板指   的 实时涨跌情况
 * @author liuguohu
 *
 */
public class RealTimeMarketTrend {
	
	/**url:
	 *    http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/cache.aspx?Type=c1&Reference=xml&0.6440368893826206
	 * content:
	 *    "0000011,上证指数,2708.78,9108571,82868276,-14.71,-0.54%,2723.49,-1","....."   
	 *
	 *
	 *url:
	 *    http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication//Index.aspx?Type=z&ids=0000011,0000161,0000021,0000031,3990012,3991062,3990022,3990032,0003001,3990012,3991062,3990062&jsname=js_dp&Reference=xml&hick=Reference=xml&0.8453940291112758
	 *content:
	 *   "0000011,000001,上证指数,2684.04,2620.28,2625.54,2644.58,2605.14,8170032,81359481,-58.50,-2.18%,1004.19,1.47%,0.00%,0,40756,81318183,41298,-1,-1,0.06%,1.21,2.62%,1399.99,,0.00,0.00,2011-08-05 14:48:41,7,3105358672,0","0000161,000016,上证５０,1895.43,1849.91,1849.00,1863.63,1841.01,1902524,16896165,-46.43,-2.45%,1126.01,1.19%,0.00%,0,8474,16896165,0,-1,1,0.02%,1.34,2.82%,-7244.34,,0.00,0.00,2011-08-05 14:48:41,7,599910010,0","0000021,000002,Ａ股指数,2810.75,2744.02,2749.59,2769.55,2728.19,8024942,71758953,-61.16,-2.18%,1118.32,1.47%,0.00%,0,39532,71758953,0,-1,-1,0.06%,1.15,0.74%,6272.55,,0.00,0.00,2011-08-05 14:48:41,7,9660889972,0","0000031,000003,Ｂ股指数,283.55,276.00,275.24,278.73,273.47,40737,438998,-8.32,-2.93%,927.96,1.85%,0.00%,0,48,438998,0,-1,-1,-0.07%,1.74,0.00%,0.00,,0.00,0.00,2011-08-05 14:48:41,7,0,0","3990012,399001,深证成指,11934.41,11585.94,11696.91,11769.68,11547.15,7465537,64089830,-237.50,-1.99%,11.65,1.86%,0.00%,0,33821,64089830,0,-1,-1,0.06%,1.25,0.00%,0.00,,0.00,0.00,2011-08-05 14:48:40,7,0,0","3991062,399106,深证综指,1187.90,1151.70,1164.24,1172.96,1145.53,7465537,64089830,-23.67,-1.99%,11.65,2.31%,0.00%,0,33821,64089830,0,-1,1,0.03%,1.25,0.00%,0.00,,0.00,0.00,2011-08-05 14:48:40,7,0,0","3990022,399002,成份Ａ指,13078.15,12696.28,12817.88,12897.63,12653.77,7310787,48978014,-260.26,-1.99%,14.93,1.86%,0.00%,0,30192,48978014,0,-1,-1,0.06%,1.14,0.00%,0.00,,0.00,0.00,2011-08-05 14:48:40,7,0,0","3990032,399003,成份Ｂ指,5389.83,5238.90,5218.37,5273.27,5184.95,18537,343424,-171.46,-3.18%,5.40,1.64%,0.00%,0,217,343424,0,-1,-1,-0.10%,1.25,0.00%,0.00,,0.00,0.00,2011-08-05 14:48:40,7,0,0","0003001,000300,沪深300,2960.31,2881.74,2895.87,2916.41,2868.16,5602541,45927714,-64.44,-2.18%,1219.86,1.63%,0.00%,0,17826,45927714,0,-1,1,0.02%,1.26,0.00%,0.00,,0.00,0.00,2011-08-05 14:48:41,7,0,0","3990012,399001,深证成指,11934.41,11585.94,11696.91,11769.68,11547.15,7465537,64089830,-237.50,-1.99%,11.65,1.86%,0.00%,0,33821,64089830,0,-1,-1,0.06%,1.25,0.00%,0.00,,0.00,0.00,2011-08-05 14:48:40,7,0,0","3991062,399106,深证综指,1187.90,1151.70,1164.24,1172.96,1145.53,7465537,64089830,-23.67,-1.99%,11.65,2.31%,0.00%,0,33821,64089830,0,-1,1,0.03%,1.25,0.00%,0.00,,0.00,0.00,2011-08-05 14:48:40,7,0,0","3990062,399006,创业板指,937.48,905.16,917.90,926.62,899.32,403784,1873744,-19.57,-2.09%,21.55,2.91%,0.00%,0,1232,1873744,0,-1,-1,-0.07%,0.88,0.00%,0.00,,0.00,0.00,2011-08-05 14:48:40,7,0,0"
	 */
	 
	  
	private String emStockId;//代码 0000011,
	private String emStockName;//名称 上证指数,
	private double yesterdayPoint;//昨收
	private double todayOpenPoint;//今开
	private double currentPoint;//当前点数
	private double highestPoint;//最高点数
	private double lowestPoint;//最低
	private double transMoney;//成交额(万) 12200,  
	private double transVolume;//成交量(手) 153282,
	private double riseDropPoint;//涨跌点数 0.00,  
	private String riseDropScope;//涨跌幅 0.00%,
	
	public RealTimeMarketTrend(String[] data) throws Exception{
		if(data==null||data.length<9){
			throw new Exception("the data length be should not less than 30,please check it!");
		}
		emStockId = data[1];
		emStockName = data[2];
		yesterdayPoint = Double.valueOf(data[3]);
		todayOpenPoint = Double.valueOf(data[4]);
		currentPoint = Double.valueOf(data[5]);
		highestPoint = Double.valueOf(data[6]);
		lowestPoint = Double.valueOf(data[7]);
		transMoney = Double.valueOf(data[8]);
		transVolume = Double.valueOf(data[9]);
		riseDropPoint = Double.valueOf(data[10]);
		riseDropScope = data[11];
	}
	
	/**
	 * @return the yesterdayPoint
	 */
	public double getYesterdayPoint() {
		return yesterdayPoint;
	}
	/**
	 * @param yesterdayPoint the yesterdayPoint to set
	 */
	public void setYesterdayPoint(double yesterdayPoint) {
		this.yesterdayPoint = yesterdayPoint;
	}
	/**
	 * @return the todayOpenPoint
	 */
	public double getTodayOpenPoint() {
		return todayOpenPoint;
	}
	/**
	 * @param todayOpenPoint the todayOpenPoint to set
	 */
	public void setTodayOpenPoint(double todayOpenPoint) {
		this.todayOpenPoint = todayOpenPoint;
	}
	/**
	 * @return the highestPoint
	 */
	public double getHighestPoint() {
		return highestPoint;
	}
	/**
	 * @param highestPoint the highestPoint to set
	 */
	public void setHighestPoint(double highestPoint) {
		this.highestPoint = highestPoint;
	}
	/**
	 * @return the lowestPoint
	 */
	public double getLowestPoint() {
		return lowestPoint;
	}
	/**
	 * @param lowestPoint the lowestPoint to set
	 */
	public void setLowestPoint(double lowestPoint) {
		this.lowestPoint = lowestPoint;
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
	 * @return the emStockName
	 */
	public String getEmStockName() {
		return emStockName;
	}
	/**
	 * @param emStockName the emStockName to set
	 */
	public void setEmStockName(String emStockName) {
		this.emStockName = emStockName;
	}
	/**
	 * @return the currentPoint
	 */
	public double getCurrentPoint() {
		return currentPoint;
	}
	/**
	 * @param currentPoint the currentPoint to set
	 */
	public void setCurrentPoint(double currentPoint) {
		this.currentPoint = currentPoint;
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
	 * @return the riseDropPoint
	 */
	public double getRiseDropPoint() {
		return riseDropPoint;
	}
	/**
	 * @param riseDropPoint the riseDropPoint to set
	 */
	public void setRiseDropPoint(double riseDropPoint) {
		this.riseDropPoint = riseDropPoint;
	}
	/**
	 * @return the riseDropScope
	 */
	public String getRiseDropScope() {
		return riseDropScope;
	}
	/**
	 * @param riseDropScope the riseDropScope to set
	 */
	public void setRiseDropScope(String riseDropScope) {
		this.riseDropScope = riseDropScope;
	}

	
}
