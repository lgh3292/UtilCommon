package com.lgh.citi.citiglobaldirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lgh.citi.citiglobaldirectory.bo.DirectReportOfEmp;
import com.lgh.citi.citiglobaldirectory.bo.Employee;
import com.lgh.citi.citiglobaldirectory.bo.EmployeeBasicInformation;
import com.lgh.citi.citiglobaldirectory.bo.IDInformation;
import com.lgh.citi.citiglobaldirectory.bo.ManagersAndReportInformation;
import com.lgh.citi.citiglobaldirectory.bo.MatrixReportofEmp;
import com.lgh.citi.citiglobaldirectory.bo.NonEmployeesofEmp;
import com.lgh.citi.citiglobaldirectory.bo.OneDownEmployee;
import com.lgh.util.FileUtil;
import com.lgh.util.logging.LogUtil;

public class CitiDirectoryGrabProcess {
	private File htmlPath;
	private File imagePath;
	private int local_disk_employees=0;
	private int directory_employees=0; 
	
	
	public CitiDirectoryGrabProcess(File htmlPath, File imagePath) {
		super();
		this.htmlPath = htmlPath;
		this.imagePath = imagePath;
	}


	/**
	 * check HTML Context from local Folder,
	 * if HTML Context file already exist, readContext and return
	 * otherwise get from citi directory site and return
	 * @param sb
	 * @param webGEIDa
	 * @return
	 * @throws Exception
	 */
	public StringBuffer checkAndReturnHtmlcontext(String webGEID) throws Exception{
		File htmlFile = new File(htmlPath,webGEID+".html");
		StringBuffer sb = new StringBuffer();
		if(htmlFile.exists()){
			sb = FileUtil.readContect(htmlFile);
			local_disk_employees++;
		}else{
			sb = CitiDirectory.get_Citi_Global_Directoryhtml_byGEID(webGEID);	
			CitiDirectory.save_Citi_Global_Directory_HtmlInfo(htmlFile, sb);
			CitiDirectory.save_Citi_Global_Directory_TitleImage_byGEID(webGEID, imagePath);
			Thread.sleep(500);
			directory_employees++;
		} 
		

		return sb;
	}
	
	/**
	 * 

	 *	1. Initial a Employee with webGEID in IDInformation 
	 *	2. Start to loop
	 *	3. Call method Save html info and image of "People head"
	 *	4. Get "Direct Reports"xml of ""People head"
	 *	5. Parse Direct Reports xml to DirectReportOfEmp.OneDownEmployee, loop the list.
	 *	6. Get "Matrix Reports"xml of "People head"
	 *	7. Parse "Matrix Reports"xml to MatrixReportOfEmp.OneDownEmployee,loop the list
	 *	8. Get "Non-employees"xml  of "People head"
	 *	9. Parse "Non-employees"xml to "NonEmployeesofEmp.OneDownEmployee , loop the list
	 * 
	 * 
	 * 
	 * 
	 * @param employee
	 */
	public  void save_Citi_Global_Directory_EmployeeAndMembers_HtmlIfo_and_TitleImages_byGEID(Employee employee){
		String webGEID = employee.getIdInformation().getGEID();
		try {
			
			 /*	1. Initial a Employee with webGEID in IDInformation */
			LogUtil.info("save html&image info for webGEID:"+ webGEID+", find "+ local_disk_employees +" local employees! and "+ directory_employees+" directory employees!");
			
			/** 2. to check "People head" */
			/*	3. Call method Save html info and image of "People head"*/
			StringBuffer sb =  checkAndReturnHtmlcontext(webGEID);
			
			employee.printEmployee_and_SuperVisor(employee.getSuperVisorEmployee(), new StringBuffer());
			Document document = Jsoup.parse(sb.toString());
			get_and_Parse_xml(document, employee);

			
			/**after save current employee HTML info and image file
			 * we start to check if this employee have one-down employee which include direct report employees, 
			 * matrix report employees and non-employees. if they are not null, then we start to get the list and 
			 * loop them one by one.
			 */
			ManagersAndReportInformation managersAndReportInformation = employee.getManagersAndReportInformation();
			DirectReportOfEmp directReportOfEmp = managersAndReportInformation.getDirectReprtOfEmp();
			MatrixReportofEmp matrixReportofEmp = managersAndReportInformation.getMatrixReportofEmp();
			NonEmployeesofEmp nonEmployeesofEmp = managersAndReportInformation.getNonEmployeesofEmp();
			List<OneDownEmployee> onedown_directReportOfEmps = directReportOfEmp.getOneDownEmployees();
			List<OneDownEmployee> onedown_matrixReportofEmps = matrixReportofEmp.getOneDownEmployees();
			List<OneDownEmployee> onedown_nonEmployeesofEmps = nonEmployeesofEmp.getOneDownEmployees();
			
			for(OneDownEmployee oneDownEmployee:onedown_directReportOfEmps){
				save_Citi_Global_Directory_EmployeeAndMembers_HtmlIfo_and_TitleImages_byGEID(new Employee(oneDownEmployee.getWebGEID(),employee));
			}
			for(OneDownEmployee oneDownEmployee:onedown_matrixReportofEmps){
				save_Citi_Global_Directory_EmployeeAndMembers_HtmlIfo_and_TitleImages_byGEID(new Employee(oneDownEmployee.getWebGEID(),employee));
			}
			for(OneDownEmployee oneDownEmployee:onedown_nonEmployeesofEmps){
				save_Citi_Global_Directory_EmployeeAndMembers_HtmlIfo_and_TitleImages_byGEID(new Employee(oneDownEmployee.getWebGEID(),employee));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("failed to save"+ webGEID);
		}
		
	}


	
	public void get_and_Parse_xml(Document document,Employee employee){
		get_and_Parse_EmployBasicInformation(document.getElementById("ContentPlaceHolderBody_DivBusTextLeft2"),employee);
		get_and_Parse_IDInformationXML(document.getElementById("ContentPlaceHolderBody_tbIDInfo"),employee);
	 /*	4. Get "Direct Reports"xml of ""People head"*/
	 /*	5. Parse Direct Reports xml to DirectReportOfEmp.OneDownEmployee, loop the list.*/
		get_and_Parse_DirectReportsXML(document.getElementById("ContentPlaceHolderBody_pnlDirect_ReportContent"),employee);
	 /*	6. Get "Matrix Reports"xml of "People head"*/ 
	 /*	7. Parse "Matrix Reports"xml to MatrixReportOfEmp.OneDownEmployee,loop the list*/
		get_and_Parse_MatrixReportXML(document.getElementById("ContentPlaceHolderBody_pnlMatrix_ReportContent"),employee);
	 /*	8. Get "Non-employees"xml  of "People head"*/
	 /*	9. Parse "Non-employees"xml to "NonEmployeesofEmp.OneDownEmployee , loop the list*/
		get_and_Parse_NonEmployeesXML(document.getElementById("ContentPlaceHolderBody_pnlEmp_NonEmpContent"),employee);
	
	}



	public void get_and_Parse_EmployBasicInformation(Element content, Employee employee) {
		EmployeeBasicInformation employeeBasicInformation = employee.getEmployeeBasicInformation();
		if(employeeBasicInformation==null){
			employeeBasicInformation = new EmployeeBasicInformation();
			employee.setEmployeeBasicInformation(employeeBasicInformation);
		}
		employeeBasicInformation.setBasicInformation(content.text());
		LogUtil.info(content.text());
	}



	public void get_and_Parse_IDInformationXML(Element content,Employee employee) {
		IDInformation idInformation = employee.getIdInformation();
		if(idInformation==null){
			idInformation = new IDInformation();
			employee.setIdInformation(idInformation);
		}
		Elements trs = content.getElementsByTag("tr");
		for(int i= 0;i<trs.size();i++){
			 Elements tds= trs.get(i).getElementsByTag("td");
			 if(tds.size()!=3){
				 LogUtil.debug("error in handle this element." + tds.toString()+"\n");
				 break;
			 }
			 String title = tds.get(1).text();
			 String value = tds.get(2).text();
			 if(title.equals("GEID")){
				 LogUtil.debug("GEID: "+value);
				 idInformation.setGEID(value);
			 }else if(title.equals("SOEID")){
				 LogUtil.debug("SOEID: "+value);
				 idInformation.setSOEID(value);
			 }else if(title.equals("RITS ID")){
				 LogUtil.debug("RITS ID: "+value);
				 idInformation.setRITS_ID(value);
			 }else if(title.equals("Source System ID")){
				 LogUtil.debug("Source System ID: "+value);
				 idInformation.setSource_System_ID(value);
			 }else if(title.equals("Primary Login ID")){
				 LogUtil.debug("Primary Login ID: "+value);
				 idInformation.setPrimary_Login_ID(value);
			 }else if(title.equals("FCID")){
				 LogUtil.debug("FCID: "+value);
				 idInformation.setFCID(value);
			 }
		}
		
	}



	public void get_and_Parse_NonEmployeesXML(Element content,Employee employee) {
		ManagersAndReportInformation managersAndReportInformation = employee.getManagersAndReportInformation();
		if(managersAndReportInformation==null){
			managersAndReportInformation = new ManagersAndReportInformation();
			employee.setManagersAndReportInformation(managersAndReportInformation);
		}
		
		NonEmployeesofEmp nonEmployeesofEmp = managersAndReportInformation.getNonEmployeesofEmp();
		List<OneDownEmployee> oneDownEmployees = new ArrayList<OneDownEmployee>();
		Elements trs = content.getElementsByTag("tr");
		for(int i= 1;i<trs.size();i++){
			 OneDownEmployee oneDownEmployee = new OneDownEmployee();
			 Elements tds= trs.get(i).getElementsByTag("td");
			 if(tds.size()!=3){
				 LogUtil.debug("error in handle this element." + tds.toString()+"\n");
				 i+=tds.size()/3;
				 continue;
			 }
			 for(int j=0;j<tds.size();j++){
				 if(j==0){
					 Element nameElement = tds.get(j);
					 String href = nameElement.select("a[href]").attr("href");
					 String webGEID = href.substring(href.indexOf("=")+1);
					 oneDownEmployee.setWebGEID(webGEID);
					 oneDownEmployee.setName(nameElement.text());
				 }else if(j==1){
					 oneDownEmployee.setJobTitle(tds.get(j).text());
				 }else if(j==2){
					 oneDownEmployee.setPhone(tds.get(j).text());
				 }
			 }
			 oneDownEmployees.add(oneDownEmployee);
		}
		nonEmployeesofEmp.setOneDownEmployees(oneDownEmployees);
		LogUtil.info("nonEmployeesofEmp:"+ oneDownEmployees.size()+"  "+oneDownEmployees);
	}


	public void get_and_Parse_MatrixReportXML(Element content,Employee employee) {
		ManagersAndReportInformation managersAndReportInformation = employee.getManagersAndReportInformation();
		if(managersAndReportInformation==null){
			managersAndReportInformation = new ManagersAndReportInformation();
			employee.setManagersAndReportInformation(managersAndReportInformation);
		}
		
		MatrixReportofEmp matrixReportofEmp = managersAndReportInformation.getMatrixReportofEmp();
		List<OneDownEmployee> oneDownEmployees = new ArrayList<OneDownEmployee>();
		Elements trs = content.getElementsByTag("tr");
		for(int i= 1;i<trs.size();i++){
			 OneDownEmployee oneDownEmployee = new OneDownEmployee();
			 Elements tds= trs.get(i).getElementsByTag("td");
			 if(tds.size()!=3){
				 LogUtil.debug("error in handle this element." + tds.toString()+"\n");
				 continue;
			 }
			 for(int j=0;j<tds.size();j++){
				 if(j==0){
					 Element nameElement = tds.get(j);
					 String href = nameElement.select("a[href]").attr("href");
					 String webGEID = href.substring(href.indexOf("=")+1);
					 oneDownEmployee.setWebGEID(webGEID);
					 oneDownEmployee.setName(nameElement.text());
				 }else if(j==1){
					 oneDownEmployee.setJobTitle(tds.get(j).text());
				 }else if(j==2){
					 oneDownEmployee.setPhone(tds.get(j).text());
				 }
			 }
			 oneDownEmployees.add(oneDownEmployee);
		}
		matrixReportofEmp.setOneDownEmployees(oneDownEmployees);
		LogUtil.info("matrixReportofEmp:"+ oneDownEmployees.size()+"  "+oneDownEmployees);
	}


 


	public void get_and_Parse_DirectReportsXML(Element content,Employee employee) {
		ManagersAndReportInformation managersAndReportInformation = employee.getManagersAndReportInformation();
		if(managersAndReportInformation==null){
			managersAndReportInformation = new ManagersAndReportInformation();
			employee.setManagersAndReportInformation(managersAndReportInformation);
		}
		
		DirectReportOfEmp directReportOfEmp = managersAndReportInformation.getDirectReprtOfEmp();
		List<OneDownEmployee> oneDownEmployees = new ArrayList<OneDownEmployee>();
		Elements trs = content.getElementsByTag("tr");
		for(int i= 1;i<trs.size();i++){
			 OneDownEmployee oneDownEmployee = new OneDownEmployee();
			 Elements tds= trs.get(i).getElementsByTag("td");
			 if(tds.size()!=4){
				 LogUtil.debug("error in handle this element." + tds.toString()+"\n");
				 i+=tds.size()/4;
				 continue;
			 }
			 for(int j=1;j<tds.size();j++){
				 if(j==1){
					 Element nameElement = tds.get(j);
					 String href = nameElement.select("a[href]").attr("href");
					 String webGEID = href.substring(href.indexOf("=")+1);
					 oneDownEmployee.setWebGEID(webGEID);
					 oneDownEmployee.setName(nameElement.text());
				 }else if(j==2){
					 oneDownEmployee.setJobTitle(tds.get(j).text());
				 }else if(j==3){
					 oneDownEmployee.setPhone(tds.get(j).text());
				 }
			 }
			 oneDownEmployees.add(oneDownEmployee);
		}
		directReportOfEmp.setOneDownEmployees(oneDownEmployees);
		LogUtil.info("directReportOfEmp:"+ oneDownEmployees.size()+"  "+oneDownEmployees);
	}



	public File getHtmlPath() {
		return htmlPath;
	}


	public void setHtmlPath(File htmlPath) {
		this.htmlPath = htmlPath;
	}


	public File getImagePath() {
		return imagePath;
	}


	public void setImagePath(File imagePath) {
		this.imagePath = imagePath;
	}
	
	public static void main(String[] args) {
		File htmlPath = new File("CitiGlobalDirectory\\html");
		File imagePath = new File("CitiGlobalDirectory\\Images");
		Employee employee = new Employee("0000537302",null);
		CitiDirectoryGrabProcess citiDirectoryGrabProcess = new CitiDirectoryGrabProcess(htmlPath, imagePath);
		citiDirectoryGrabProcess.save_Citi_Global_Directory_EmployeeAndMembers_HtmlIfo_and_TitleImages_byGEID(employee);
		
	}
}
