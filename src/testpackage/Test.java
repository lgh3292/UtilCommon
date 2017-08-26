package testpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

import com.lgh.util.db.DBConnectionPoolManager;

public class Test {
	public static  <A extends Comparable>A findLarger(A x, A y) {
		if (x.compareTo(y) > 0) {
			return x;
		} else {
			return y;
		}
	}


	public static void  readFile(File file){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String readLine = null;
			while((readLine=br.readLine())!=null){
				System.out.println(new String(readLine.getBytes(),"utf-16"));
				Thread.sleep(10);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		
		new Thread(new Runnable(){
			public void run(){
				Connection con = DBConnectionPoolManager.getInstance().getConnection();
				while(true){
					String sql = "select test_seq.nextval from dual";
				//	Statement m = con.createStatement();
					
				}
			}
			
		}).start();
	}
}