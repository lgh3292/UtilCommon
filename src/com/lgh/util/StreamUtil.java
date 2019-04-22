package com.lgh.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import com.lgh.util.logging.LogUtil;

import testpackage.Customer;

public class StreamUtil {
	/**
	 * get byteArray from inputStream
	 * @param is
	 * @return
	 */
	public static byte[] getByteArrayByInputStream(InputStream is){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int len = 0;
		byte[] bytes = new byte[128];
		try {
			while ((len = is.read(bytes)) > 0) {
				bout.write(bytes, 0, len);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bout.toByteArray();
	}
	/**
	 * read Unshared from byte arrays
	 * @param bytesead
	 * @return
	 */
	public static Object readFromStream(byte[] bytes){;
		ByteArrayInputStream bins = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bins);
			Object object = ois.readUnshared();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * get the objet's byte array
	 * @param object
	 * @return
	 */
	public static byte[] getByteArrayByObject(Object object){
		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(bous);
			oos.writeUnshared(object);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(oos!=null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bous.toByteArray();
	}
	
	public static void main(String[] args) {
		Customer cust = new Customer("272401534", new Date(), new ArrayList());
		byte[] custArray = getByteArrayByObject("hello");
		
		LogUtil.info(custArray.length+"  "+new String(custArray));
		LogUtil.info(readFromStream(custArray));
	}
}
