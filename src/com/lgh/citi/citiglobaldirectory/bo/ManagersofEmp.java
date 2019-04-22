package com.lgh.citi.citiglobaldirectory.bo;

import java.util.List;

public class ManagersofEmp {

	private List<Managers> managers;
	
	
	public List<Managers> getManagers() {
		return managers;
	}


	
	public void setManagers(List<Managers> managers) {
		this.managers = managers;
	}


	protected class Managers{
		String name;
		String role;
		String GOCDescription;
		String phone;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public String getGOCDescription() {
			return GOCDescription;
		}
		public void setGOCDescription(String gOCDescription) {
			GOCDescription = gOCDescription;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		
	}
}
