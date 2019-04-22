package com.lgh.citi.citiglobaldirectory.bo;

public class TwoDownEmplyee{
	private String webGEID;
	private String name;
	private String jobTitle;
	private String phone;
	public String getWebGEID() {
		return webGEID;
	}

	

	@Override
	public String toString() {
		return "TwoDownEmplyee [webGEID=" + webGEID + ", name=" + name
				+ ", jobTitle=" + jobTitle + ", phone=" + phone + "]";
	}



	public void setWebGEID(String webGEID) {
		this.webGEID = webGEID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}