package com.lgh.eastmoney.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.lgh.util.logging.LogUtil;

public class Test2 {
	public static void main(String[] args) {
		try {
			File file = new File("C:\\Documents and Settings\\liuguohu\\Desktop\\新建文件夹\\ES1010_g5u0301c_itg_es1.log.2011-08-15.1");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String readLine = "";
			boolean find = false;
			while((readLine=br.readLine())!=null){
				if(find){
					LogUtil.info(readLine);
					find = false;
				}
				if(readLine.contains("Failover mode will continue. New failover start time: ")){
					find = true;
				}
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
