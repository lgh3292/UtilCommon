/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Authenticator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import com.lgh.eastmoney.ctl.StockVolume;

/**
 *
 * @author Administrator
 */
public class Util {
   
	public static String native2ascii(String str) {  
	    String tmp;  
	    StringBuffer sb = new StringBuffer(1000);  
	    char c;  
	    int i, j;  
	    sb.setLength(0);  
	    for (i = 0; i < str.length(); i++) {  
	        c = str.charAt(i);  
	        if (c > 255) {  
	            sb.append("\\u");  
	            j = (c >>> 8);  
	            tmp = Integer.toHexString(j);  
	            if (tmp.length() == 1)  
	                sb.append("0");  
	            sb.append(tmp);  
	            j = (c & 0xFF);  
	            tmp = Integer.toHexString(j);  
	            if (tmp.length() == 1)  
	                sb.append("0");  
	            sb.append(tmp);  
	        } else {  
	            sb.append(c);  
	        }  
	  
	    }  
	    return (new String(sb));  
	}  



	public static void main(String[] args) {
//       fileChooserDir(new JButton(),null);
    }

    /**
     * popup the dialog frame
     * @param com
     */
    public static File fileChooserDir(Component com,File showFile){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(showFile!=null){
            chooser.setCurrentDirectory(showFile);
        }
        int i = chooser.showDialog(com, "open");
        if(i==JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            return file;
        }
        return null;
    }
   
    /**
     * encrypt to the Stirng with appoint digit
     * @param string
     * @param bit
     * @return
     */
    public static String encryt(String string,int bit){
        char[] buffer = string.toCharArray();
        for(int i = 0;i<buffer.length;i++){
            int k = new Integer(buffer[i]);
            k = k - bit;
            buffer[i] = (char)k;
        }
        return new String(buffer);
    }
    
    /**
     * unencrypt with the String
     * @param string
     * @param bit
     * @return
     */
    public static String deEncryt(String string,int bit){
        char[] buffer = string.toCharArray();
        for(int i=0;i<buffer.length;i++){
            int k = new Integer(buffer[i]);
            k = k+bit;
            buffer[i] = (char)k;
        }
        return new String(buffer);
    }
    
    /**
     * load properties from XML
     * @param file
     */
    public static void loadPropertiesFromXML(File file){
        InputStream reader = null;
        try {
            Properties properties = new Properties();
            reader = new FileInputStream(file);
            properties.loadFromXML(reader);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Class.class.getResourceAsStream("eastmoney.properties")£ºfind the file in the directory where the Class is
     * Class.class.getResourceAsStream("/eastmoney.properties"): find the file in the bin directory
     * load the properties from folder
     * @param file
     */
    public static Properties loadProperties(InputStream in){
        InputStreamReader reader = null;
        try {
            Properties properties = new Properties();
            reader = new InputStreamReader(in);
            properties.load(reader);
            return properties;
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * load properties from file.
     * @param file
     * @return
     */
    public static Properties loadPropertiesFromFile(File file){
    	FileUtil.createFile(file);
        InputStreamReader reader = null;
        try {
            Properties properties = new Properties();
            reader = new InputStreamReader(new FileInputStream(file));
            properties.load(reader);
            return properties;
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    /**
     * save te properties
     * @param properties
     */
    public static void saveProperties(File file, Properties properties) {
        FileOutputStream fous = null;
        try {
        	FileUtil.createFile(file);
            fous = new FileOutputStream(file);
            properties.store(fous, "Mail_smtp_pop3");
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fous.close();
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
