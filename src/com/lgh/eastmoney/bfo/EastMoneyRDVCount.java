package com.lgh.eastmoney.bfo;

/**
 * 每张表历史每天的成功导入的数据量
 * @author liuguohu
 *
 */
public class EastMoneyRDVCount {
	private long date;
	private long risedropCount;
	private long risedropHisCount;
	private long volumeCount;
	
	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(long date) {
		this.date = date;
	}
	/**
	 * @return the risedropCount
	 */
	public long getRisedropCount() {
		return risedropCount;
	}
	/**
	 * @param risedropCount the risedropCount to set
	 */
	public void setRisedropCount(long risedropCount) {
		this.risedropCount = risedropCount;
	}
	/**
	 * @return the risedropHisCount
	 */
	public long getRisedropHisCount() {
		return risedropHisCount;
	}
	/**
	 * @param risedropHisCount the risedropHisCount to set
	 */
	public void setRisedropHisCount(long risedropHisCount) {
		this.risedropHisCount = risedropHisCount;
	}
	/**
	 * @return the volumeCount
	 */
	public long getVolumeCount() {
		return volumeCount;
	}
	/**
	 * @param volumeCount the volumeCount to set
	 */
	public void setVolumeCount(long volumeCount) {
		this.volumeCount = volumeCount;
	}
	
	 
	
}
