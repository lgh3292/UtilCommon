package com.lgh.citi.citiglobaldirectory.bo;

public class IDInformation {
	private String GEID;
	private String SOEID;
	private String RITS_ID;
	private String Source_System_ID;
	private String Primary_Login_ID;
	private String FCID;
	
	
	
	public IDInformation(String GEID) {
		this.GEID = GEID;
	}
	public IDInformation() {
	}
	public String getGEID() {
		return GEID;
	}
	public void setGEID(String gEID) {
		GEID = gEID;
	}
	public String getSOEID() {
		return SOEID;
	}
	public void setSOEID(String sOEID) {
		SOEID = sOEID;
	}
	public String getRITS_ID() {
		return RITS_ID;
	}
	public void setRITS_ID(String rITS_ID) {
		RITS_ID = rITS_ID;
	}
	public String getSource_System_ID() {
		return Source_System_ID;
	}
	public void setSource_System_ID(String source_System_ID) {
		Source_System_ID = source_System_ID;
	}
	public String getPrimary_Login_ID() {
		return Primary_Login_ID;
	}
	public void setPrimary_Login_ID(String primary_Login_ID) {
		Primary_Login_ID = primary_Login_ID;
	}
	public String getFCID() {
		return FCID;
	}
	public void setFCID(String fCID) {
		FCID = fCID;
	}

}
