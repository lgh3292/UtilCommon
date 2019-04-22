package com.lgh.citi.citiglobaldirectory.junit;
import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import com.lgh.citi.citiglobaldirectory.CitiDirectoryGrabProcess;
import com.lgh.citi.citiglobaldirectory.bo.Employee;
import com.lgh.util.FileUtil;
public class CitiDirectoryGrabProcessJunit {
	private File htmlPath ;
	private File imagePath;
	private Employee employee;
	private int local_disk_employees=0;
	private int directory_employees=0; 
	@Before
	public void init(){
		htmlPath = new File("test\\html");
		imagePath = new File("test\\Images");
		employee = new Employee("0000430188",null);
	}
	
	
	@Test
	public void testsave_Citi_Global_Directory_EmployeeAndMembers_HtmlIfo_and_TitleImages_byGEID() throws IOException{
		

		CitiDirectoryGrabProcess citiDirectoryGrabProcess = new CitiDirectoryGrabProcess(htmlPath, imagePath);
		
		
		String webGEID = employee.getIdInformation().getGEID();
		/*for test*/
		StringBuffer sb = new StringBuffer();
		sb= FileUtil.readContect(new File(htmlPath,webGEID+".html"));

		Document document = Jsoup.parse(sb.toString());
		citiDirectoryGrabProcess.get_and_Parse_xml(document, employee);
		
	}
}
