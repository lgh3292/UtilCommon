package testpackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Drink {
	public static int key = 1;

	public static void main(String[] args) {
		File filepath = new File("c:\\temp\\test");
		File[] files = filepath.listFiles();
		for (File en : files) {
			try {
				System.out.println(en.getAbsolutePath());
				System.out.println(en.getPath() + en.getAbsolutePath()
						+ en.getCanonicalPath() + en.getParentFile()
						+ en.getParent());
				
				enFilebyByte(en);
//				deFilebyByte(en);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	
	
	public static void enFilebyByte(File in) throws Exception {

		if (in == null) {
			throw new IOException("the file is null!");
		}
		if (!in.exists()) {
			throw new IOException("file is not exist!");
		}
		if (!in.isFile()) {
			throw new IOException("the file is a path£¡");
		}
		String inFullFileName = in.getName();
		String prefix = inFullFileName.substring(inFullFileName
				.lastIndexOf("."));
		String filename = inFullFileName.substring(0,
				inFullFileName.lastIndexOf("."));
		File out = new File(in.getParent() + "\\" + filename + "_e" + prefix);
		FileInputStream bi = new FileInputStream(in);
		FileOutputStream fo =new FileOutputStream(out);
		try {
			
				int len = 0;
				byte[] bytes = new byte[1024];
				try {
					while ((len = bi.read(bytes)) > 0) {
						
						fo.write(enByteArry(bytes), 0, len);
						//fo.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (bi != null) {
				bi.close();
			}
			if(fo!=null)
				fo.close();
		}
	}
	
	
	public static void deFilebyByte(File in) throws Exception {

		if (in == null) {
			throw new IOException("the file is null!");
		}
		if (!in.exists()) {
			throw new IOException("file is not exist!");
		}
		if (!in.isFile()) {
			throw new IOException("the file is a path£¡");
		}
		String inFullFileName = in.getName();
		String prefix = inFullFileName.substring(inFullFileName
				.lastIndexOf("."));
		String filename = inFullFileName.substring(0,
				inFullFileName.lastIndexOf("."));
		File out = new File(in.getParent() + "\\" + filename + "_d" + prefix);
		FileInputStream bi = new FileInputStream(in);
		FileOutputStream fo =new FileOutputStream(out);
		try {
			
				int len = 0;
				byte[] bytes = new byte[1024];
				try {
					while ((len = bi.read(bytes)) > 0) {
						
						fo.write(deByteArry(bytes), 0, len);
						//fo.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (bi != null) {
				bi.close();
			}
			if(fo!=null)
				fo.close();
		}
	}

	public static void deFilebyStream(File in) throws Exception {

		if (in == null) {
			throw new IOException("the file is null!");
		}
		if (!in.exists()) {
			throw new IOException("file is not exist!");
		}
		if (!in.isFile()) {
			throw new IOException("the file is a path£¡");
		}
		String inFullFileName = in.getName();
		String prefix = inFullFileName.substring(inFullFileName
				.lastIndexOf("."));
		String filename = inFullFileName.substring(0,
				inFullFileName.lastIndexOf("."));
		File out = new File(in.getParent() + "\\" + filename + "_de" + prefix);

		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					in)));
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out)));

			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				System.out.println(readLine);
				String sb = getString(readLine, -key);
				System.out.println(sb);
				if (sb != null && sb.length() > 0) {
					bw.write(sb);
					bw.newLine();
					bw.flush();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	
	
	
	public static void enFileByStream(File in) throws Exception {

		if (in == null) {
			throw new IOException("the file is null!");
		}
		if (!in.exists()) {
			throw new IOException("file is not exist!");
		}
		if (!in.isFile()) {
			throw new IOException("the file is a path£¡");
		}
		String inFullFileName = in.getName();
		String prefix = inFullFileName.substring(inFullFileName
				.lastIndexOf("."));
		String filename = inFullFileName.substring(0,
				inFullFileName.lastIndexOf("."));
		File out = new File(in.getParent() + "\\" + filename + "_en" + prefix);

		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					in)));
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out)));

			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				System.out.println(readLine);
				String sb = getString(readLine, key);
				System.out.println(sb);
				if (sb != null && sb.length() > 0) {
					bw.write(sb);
					bw.newLine();
					bw.flush();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	
	public static byte[] enByteArry(byte[] ins){
		if(ins==null){
			return null;
		}
		byte[] ous = new byte[ins.length];
		for(int i=0;i<ins.length;i++){
			ins[i]^=key;
			ous[i] = ins[i];
		}
		return ous;
	}
	
	
	public static byte[] deByteArry(byte[] ins){
		if(ins==null){
			return null;
		}
		byte[] ous = new byte[ins.length];
		for(int i=0;i<ins.length;i++){
			ins[i]^=key;
			ous[i] = ins[i];
			
		}
		return ous;
	}
	public static String getString(String str, int key) {
		if (key == 0) {
			return str;
		}
		char[] chars = str.toCharArray();
		for (int i = chars.length - 1; i >= 0; i--) {
			chars[i] += key;
		}
		return new String(chars);
	}
}