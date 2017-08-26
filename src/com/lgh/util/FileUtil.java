/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

import com.lgh.util.logging.Level;
import com.lgh.util.logging.LogUtil;

/**
 *
 * @author lgh
 */
public class FileUtil {

    /**
     * create a new file
     * @param file
     */
    public static boolean createFile(File file) {
        if (file.getParentFile()!=null&&!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                return true;
            } catch (IOException ex) {
            	ex.printStackTrace();
            }
        }
        return false;
    }

    public static void deleteFile(File file){
    	if(!file.exists()){
    		return;
    	}
    	if(file.isFile()){
    		boolean success = file.delete();
    		LogUtil.log("..........delete file:"+file.getAbsolutePath()+ " "+(success==true?"success":"failed"), Level.INFO);
    	}else{
    		File[] files = file.listFiles();
    		for(File f:files){
    			deleteFile(f);
    		}
    		boolean success = file.delete();
    		LogUtil.log("..........delete folder:"+file.getAbsolutePath()+ " "+(success==true?"success":"failed"), Level.INFO);
    	}
    }
    
    /**
     * TODO get the 'web' path
     * @return
     * @author <a href="mailto:pheh.lin@gmail.com">PHeH</a><br>
     * Created On 2007-5-10 15:16:21
     */
    public static String getRootPath() {
        //因为类名为"Application"，因此" Application.class"一定能找到
        String result = FileUtil.class.getResource("FileUtil.class").toString();
        result.substring(0, result.length());
        int index = result.indexOf("WEB-INF");
        if (index == -1) {
            index = result.indexOf("bin");
        }
        result = result.substring(0, index);
        if (result.startsWith("jar")) {
        	//when class file is in the jar file,return "jar:file:/F:/ ..."
            result = result.substring(10);
        } else if (result.startsWith("file")) {
        	//when the class file is in class folder,return "file:/F:/ ..."
            result = result.substring(6);
        }
        if (result.endsWith("/")) {
        	//no including the last "/"
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * read file content
     * @param file
     * @return
     * @throws IOException
     */
    public static StringBuffer readContect(File file) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader br = null;
        try {
            if (file == null) {
                throw new IOException("the file is null!");
            }
            if (!file.exists()) {
                throw new IOException("file is not exist!");
            }
            if(!file.isFile()){
                  throw new IOException("the file is a path！");
            }
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String readLine = null;
            while((readLine = br.readLine())!=null){
                buffer.append(readLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            if(br!=null){
                br.close();
            }
        }
       return buffer;
    }
    /**
     * save the object
     * @param object
     * @param file
     */
    public static void saveObject(Object object, File file) {
        try {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                }
            }
            ObjectOutputStream oos = null;
            if (null != object && object instanceof Serializable) {
                oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeUnshared(object);
                oos.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    /**
     * read the object
     * @param file
     * @return
     */
    public static Object readObject(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            Object object = ois.readObject();
            return object;
        }finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
    
    /**
     * read the last line of file
     * @param file
     * @param charset
     * @return
     * @throws IOException
     */
	public static String readLastLine(File file, String charset)
			throws IOException {
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			return null;
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
			long len = raf.length();
			if (len == 0L) {
				return "";
			} else {
				long pos = len - 1;
				while (pos > 0) {
					raf.seek(pos);
					if (raf.readByte() == '\n') {
						break;
					}
					pos--;
				}
				if (pos == 0) {
					raf.seek(0);
				}else{
					pos++;
				}
				byte[] bytes = new byte[(int) (len - pos)];
				raf.read(bytes);
				if (charset == null) {
					return new String(bytes);
				} else {
					return new String(bytes, charset);
				}
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e2) {
				}
			}
		}
		return null;
	}
    
	/**
	 * add a line to the file end
	 * @param file
	 * @param line
	 */
	public static void writeToLastLine(File file,String line){
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			return;
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "rw");
			long len = raf.length();
			if(len>0){
				raf.seek(len-1);
				if(raf.readByte()!='\n'){
					raf.writeBytes("\r\n");
				}
			}
			raf.write(line.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void leftMove(byte[] buf){
		byte[] newbyte =new byte[buf.length];
		for(byte b:buf){
			
		}
		
	}
	public void leftMove(FileInputStream fins, FileOutputStream fous){
		
		
		
        
	}
	
	
	
    public static void main(String[] args) {
    	deleteFile(new File("C:\\Documents and Settings\\liuguohu\\Desktop\\Allway Sync"));
    	try {
//			String lastLine = readLastLine(new File("c://laba.log.0"), "utf-8");
//			LogUtil.info("lastLine:"+lastLine);
			//writeToLastLine(new File("c://laba.log.0"),"你好，国华3");
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
