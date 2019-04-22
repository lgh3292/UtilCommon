package com.lgh.citi.citiglobaldirectory.bo;

import java.util.List;

public class OneDownEmployee {
	private String webGEID;
	private String name;
	private String jobTitle;
	private String phone;
	
	private List<TwoDownEmplyee> twoDownEmplyees;
	
	
	
	
	@Override
	public String toString() {
		return "OneDownEmployee [webGEID=" + webGEID + ", name=" + name
				+ ", jobTitle=" + jobTitle + ", phone=" + phone
				+ ", twoDownEmplyees=" + twoDownEmplyees + "]";
	}


	public String getWebGEID() {
		return webGEID;
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


	public List<TwoDownEmplyee> getTwoDownEmplyees() {
		return twoDownEmplyees;
	}


	public void setTwoDownEmplyees(List<TwoDownEmplyee> twoDownEmplyees) {
		this.twoDownEmplyees = twoDownEmplyees;
	}


	
}
