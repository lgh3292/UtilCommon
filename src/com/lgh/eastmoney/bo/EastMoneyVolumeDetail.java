package com.lgh.eastmoney.bo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.lgh.util.StreamUtil;

public class EastMoneyVolumeDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int emVolumeId;
	private String emStockId;
	private byte[] emVolumeDetail;
	private int emDateId;
	private Timestamp emVolumeDate;
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
	/**
	 * @return the emVolumeId
	 */
	public int getEmVolumeId() {
		return emVolumeId;
	}
	/**
	 * @param emVolumeId the emVolumeId to set
	 */
	public void setEmVolumeId(int emVolumeId) {
		this.emVolumeId = emVolumeId;
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
	 * @return the emVolumeDetail
	 */
	public byte[] getEmVolumeDetail() {
		return emVolumeDetail;
	}
	
	public Object getEmVolumeDetailStr(){
		return StreamUtil.readFromStream(emVolumeDetail);
	}
	/**
	 * @param emVolumeDetail the emVolumeDetail to set
	 */
	public void setEmVolumeDetail(byte[] emVolumeDetail) {
		this.emVolumeDetail = emVolumeDetail;
	}
	/**
	 * @return the emVolumeDate
	 */
	public Timestamp getEmVolumeDate() {
		return emVolumeDate;
	}
	/**
	 * @param emVolumeDate the emVolumeDate to set
	 */
	public void setEmVolumeDate(Timestamp emVolumeDate) {
		this.emVolumeDate = emVolumeDate;
	}
	
	
}
