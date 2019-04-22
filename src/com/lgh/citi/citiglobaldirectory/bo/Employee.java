package com.lgh.citi.citiglobaldirectory.bo;

import com.lgh.util.Util;
import com.lgh.util.logging.LogUtil;


public class Employee {
	private Employee superVisorEmployee;
	private EmployeeBasicInformation employeeBasicInformation;
	private IDInformation idInformation;
	private WorkInformation workInformation;
	private ContractInformation contractInformation;
	private LocationInformation locationInformation;
	private ManagersAndReportInformation managersAndReportInformation;
	private PeopleBased peopleBased;
	private ManagedSegment managedSegment;
	private ManagedGeography managedGeography;
	public EmployeeBasicInformation getEmployeeBasicInformation() {
		return employeeBasicInformation;
	}
	
	public void printEmployee_and_SuperVisor(Employee superVisorEmployee, StringBuffer sb){
		if(sb==null){
			sb = new StringBuffer();
		}
		if(superVisorEmployee!=null){
			String superVisorGEID = superVisorEmployee.getIdInformation().getGEID();
			sb.append("->"+superVisorGEID);
			if(superVisorEmployee.getSuperVisorEmployee()!=null){
				printEmployee_and_SuperVisor(superVisorEmployee.getSuperVisorEmployee(),sb);
			}else{
				LogUtil.log(sb.toString());
			}
		}
	}
	
	public Employee getSuperVisorEmployee() {
		return superVisorEmployee;
	}



	public void setSuperVisorEmployee(Employee superVisorEmployee) {
		this.superVisorEmployee = superVisorEmployee;
	}



	public void printEmployee_and_Parnt(){
		StringBuffer sb = new StringBuffer();
		sb.append("");
	}
	 

//	public Employee(String GEID){
//		idInformation = new IDInformation(GEID);
//	}
	
	
	public Employee(String GEID,Employee superVisorEmployee){
		idInformation = new IDInformation(GEID);
		this.superVisorEmployee = superVisorEmployee;
	}
	public void setEmployeeBasicInformation(
			EmployeeBasicInformation employeeBasicInformation) {
		this.employeeBasicInformation = employeeBasicInformation;
	}
	public IDInformation getIdInformation() {
		return idInformation;
	}
	public void setIdInformation(IDInformation idInformation) {
		this.idInformation = idInformation;
	}
	public WorkInformation getWorkInformation() {
		return workInformation;
	}
	public void setWorkInformation(WorkInformation workInformation) {
		this.workInformation = workInformation;
	}
	public ContractInformation getContractInformation() {
		return contractInformation;
	}
	public void setContractInformation(ContractInformation contractInformation) {
		this.contractInformation = contractInformation;
	}
	public LocationInformation getLocationInformation() {
		return locationInformation;
	}
	public void setLocationInformation(LocationInformation locationInformation) {
		this.locationInformation = locationInformation;
	}
	public ManagersAndReportInformation getManagersAndReportInformation() {
		return managersAndReportInformation;
	}
	public void setManagersAndReportInformation(
			ManagersAndReportInformation managersAndReportInformation) {
		this.managersAndReportInformation = managersAndReportInformation;
	}
	public PeopleBased getPeopleBased() {
		return peopleBased;
	}
	public void setPeopleBased(PeopleBased peopleBased) {
		this.peopleBased = peopleBased;
	}
	public ManagedSegment getManagedSegment() {
		return managedSegment;
	}
	public void setManagedSegment(ManagedSegment managedSegment) {
		this.managedSegment = managedSegment;
	}
	public ManagedGeography getManagedGeography() {
		return managedGeography;
	}
	public void setManagedGeography(ManagedGeography managedGeography) {
		this.managedGeography = managedGeography;
	}
	
	
}
