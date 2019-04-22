/*
 * MD5.java
 *
 * Created on 2007年11月17日, 下午1:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.lgh.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.lgh.util.logging.LogUtil;

/**
 * reading directly from file,get the MD5 String
 * @author tewang
 */
public class MD5 {

    private static final char[] hexChar = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8',
        '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    public final String[] hashTypes = {
        "MD2", "MD5", "SHA1", "SHA-256", "SHA-384", "SHA-512"
    };

    /** Creates a new instance of MD5 */
    public MD5() {
    }

    /**
     * encrypt by the assigned file and encrypt type
     */
    public static String getHashCalc(File file, String type) throws Exception {
        if (!file.exists() || file.isDirectory()) {
            return null;
        } else {
            return MD5.getResult(file, type);
        }
    }

    /**
     * get the MD5 String by the assigned file 
     */
    public static String getMD5(File file) throws Exception {
        if (!file.exists() || file.isDirectory()) {
            return null;
        } else {
            return MD5.getResult(file, "MD5");
        }
    }

    private static String getResult(File file, String type) throws Exception {
        FileInputStream fins = null;
        try {
            MessageDigest mds = MessageDigest.getInstance(type);
            fins = new FileInputStream(file);
        byte[] buf = new byte[1024];
            int readInt = 0;
            while ((readInt = fins.read(buf)) > 0) {
                mds.update(buf, 0, readInt);
            }
            byte[] buf2 = mds.digest();
            StringBuilder sb = new StringBuilder(buf2.length * 2);
            for (int i = 0; i < buf2.length; i++) {
                sb.append(hexChar[(buf2[i] & 0xf0) >>> 4]);
                sb.append(hexChar[buf2[i] & 0x0f]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } finally {
            if (fins != null) {
                fins.close();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {LogUtil.info(10<<20);
            List list = new ArrayList();
            for (int i = 0; i < 10; i++) {
                long time = System.currentTimeMillis();
//                LogUtil.info(MD5.getMD5(new File("E://Downloads//Windows_7_Offical_theme.rar")));
                
                long now = System.currentTimeMillis() - time;
                LogUtil.info(now);
                list.add(now);
            }
            long sum = 0;
            for(int i =0;i<list.size();i++)
                sum+=(Long)list.get(i);
            LogUtil.info(sum/list.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
