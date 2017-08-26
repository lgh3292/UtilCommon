package testpackage;

import java.io.File;
import java.io.FileInputStream;

public class ClassB {
	public static void main(String[] args) throws Exception {
		File file = new File("c://snr_request.txt");
		FileInputStream fins = new FileInputStream(file);
		byte[] readBytes = new byte[1024];
		int len = 0;
		while((len=fins.read(readBytes))!=-1){
			System.out.println(len);
		}
		fins.close();
	}
}
