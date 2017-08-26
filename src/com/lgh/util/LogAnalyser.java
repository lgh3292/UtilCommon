package com.lgh.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LogAnalyser {

	/**
	 * analyse the from file by analyString,execute the file 
	 * @param preFile
	 * @param executeFile
	 * @param analyString
	 * @param remove 
	 */
	public static void remove(File fromFile,File executeFile,String analyString,boolean remove){
		BufferedReader br = null;
		BufferedWriter bw_tmp = null;
		BufferedWriter bw_out = null;
		try {
			String absolutePath = fromFile.getAbsolutePath();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fromFile)));
			File tempFile  = new File(fromFile.getParent(),"tempfile"+System.currentTimeMillis());
			bw_tmp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
			bw_out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(executeFile)));
			String readLine = null;
			try {
				boolean serialException = false;
				while((readLine = br.readLine())!=null){
					if(serialException){
						if(readLine.trim().equals("")||(readLine.indexOf("at"))>=0){
							bw_out.append(readLine);
							bw_out.newLine();
						}else{
							serialException = false;
						}
					}
					if((readLine.indexOf(analyString))>=0){
						serialException = true;
						bw_out.append(readLine);
						bw_out.newLine();
					}else{
						if(!serialException){
							bw_tmp.append(readLine);
							bw_tmp.newLine();
						}
					}
				}
				br.close();
				bw_tmp.close();
				bw_out.close();
				fromFile.delete();
				tempFile.renameTo(new File(absolutePath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bw_tmp!=null){
				try {
					bw_tmp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bw_out!=null){
				try {
					bw_tmp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		//split the exception and remove this exception from file,excute new exception file log by it's name
		
		//ES
//		String[] exceptions= new String[]{"com.hp.es.service.util.exception.EsException","com.hp.es.service.util.xml.MarshalHelper.unmarshalUsingEIAMappingFile",
//				"com.hp.es.service.wsInterface.EsHttpListener.verifyAccess","com.hp.es.xml.service.ContractSummaryRequest",
//				"com.hp.es.xml.service.InstalledBaseUnitRequestChoice","SifException"};
		
		String[] exceptions = new String[]{"getHighestPriorityException"};
		//TM
//		String[] exceptions= new String[]{"com.hp.es.tm.dao.TmDao.insertTmTransaction","com.hp.es.tm.ejb.TransactionReceiveBean.onMessage"
//				,"com.hp.es.tm.service.TmService.calculateNumberOfWarranties","com.hp.es.tm.util.TmMarshalHelper.unmarshalUsingEIAMappingFile",
//				"com.hp.es.xml.service.ContractSummaryRequest","com.hp.es.xml.service.InstalledBaseUnitRequestChoice",
//				"org.exolab.castor.types.DateDescriptor$DateFieldHandler","org.exolab.castor.xml.Unmarshaller.convertSAXExceptionToMarshalException"};
		String[] fileNames = new String[]{
				//"TM1015_g3u802_es4.log.2011-03-25"
				"ES1010_g3u802_es4.log.2011-03-28"
		};
		for(String fileName:fileNames){
			for(String exception:exceptions)
			  LogAnalyser.remove(new File("C:\\dev\\802Dev\\"+fileName), new File("C:\\dev\\802Dev\\"+fileName+"("+exception+").log"), exception, false);
		}
		
	}
}
