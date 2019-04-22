package com.lgh.citi.citiglobaldirectory.bo;

public class ManagersAndReportInformation {
	private ManagersofEmp managersofEmp;
	private DirectReportOfEmp directReprtOfEmp;
	private MatrixReportofEmp matrixReportofEmp;
	private NonEmployeesofEmp nonEmployeesofEmp;
	private OrganizationsManagedbyEmp managedbyEmp;

	public ManagersAndReportInformation() {
		managersofEmp = new ManagersofEmp();
		directReprtOfEmp = new DirectReportOfEmp();
		matrixReportofEmp = new MatrixReportofEmp();
		nonEmployeesofEmp = new NonEmployeesofEmp();
		managedbyEmp = new OrganizationsManagedbyEmp();
	}

	
	
	public NonEmployeesofEmp getNonEmployeesofEmp() {
		return nonEmployeesofEmp;
	}



	public void setNonEmployeesofEmp(NonEmployeesofEmp nonEmployeesofEmp) {
		this.nonEmployeesofEmp = nonEmployeesofEmp;
	}



	public ManagersofEmp getManagersofEmp() {
		return managersofEmp;
	}

	public void setManagersofEmp(ManagersofEmp managersofEmp) {
		this.managersofEmp = managersofEmp;
	}

	public DirectReportOfEmp getDirectReprtOfEmp() {
		return directReprtOfEmp;
	}

	public void setDirectReprtOfEmp(DirectReportOfEmp directReprtOfEmp) {
		this.directReprtOfEmp = directReprtOfEmp;
	}

	public MatrixReportofEmp getMatrixReportofEmp() {
		return matrixReportofEmp;
	}

	public void setMatrixReportofEmp(MatrixReportofEmp matrixReportofEmp) {
		this.matrixReportofEmp = matrixReportofEmp;
	}


	public OrganizationsManagedbyEmp getManagedbyEmp() {
		return managedbyEmp;
	}

	public void setManagedbyEmp(OrganizationsManagedbyEmp managedbyEmp) {
		this.managedbyEmp = managedbyEmp;
	}

}
