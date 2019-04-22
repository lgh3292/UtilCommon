package com.lgh.eastmoney.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import com.lgh.util.Util;
import com.lgh.util.logging.LogUtil;

public class PropertiesConfig {
	private static PropertiesConfig PROPERTIES_CONFIG = new PropertiesConfig();
	private static Properties readOnlyEastmoney;
	private static Properties writableEastmoney;
	

	private static File WRITABLE_EM_FILE = new File("eastmoney_writable.properties");
	static{
		
		//InputStream inputStream = PropertiesConfig.class.getResourceAsStream("/eastmoney.properties");
//		LogUtil.info(PropertiesConfig.class.getResource(""));
		//file:/C:/projects/myproject/J2SE/UtilCommon/bin/com/lgh/eastmoney/
		try {
			readOnlyEastmoney = Util.loadProperties(new FileInputStream("conf/eastmoney_readonly.properties"));
			writableEastmoney = Util.loadPropertiesFromFile(WRITABLE_EM_FILE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();LogUtil.error("error", e);
		}
	}
	
	/**
	 * @return the readOnlyEastmoney
	 */
	public static Properties getReadOnlyEastmoneyProperties() {
		return readOnlyEastmoney;
	}

	/**
	 * @return the eastmoney
	 */
	public static Properties getWritableEastmoneyProperties() {
		return writableEastmoney;
	}
	
	/**
	 * get PropertiesConfig instance
	 * @return
	 */
	public static PropertiesConfig getInstance(){
		synchronized (PropertiesConfig.class) {
			if(PROPERTIES_CONFIG==null){
				PROPERTIES_CONFIG = new PropertiesConfig();
			}
		}
		return PROPERTIES_CONFIG;
	}
	
	public static void updateWritableEastmoney(){
		Util.saveProperties(WRITABLE_EM_FILE, writableEastmoney);
	}
	
	public static void main(String[] args) {
		
	}
	
}
