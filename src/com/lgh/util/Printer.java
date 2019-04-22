/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.lgh.util.logging.LogUtil;

/**
 * 
 * @author lgh
 */
public class Printer {

	/**
	 * print list
	 * 
	 * @param list
	 */
	public static void printList(List list) {
		for (int i = 0; i < list.size(); i++) {
			LogUtil.info(list.get(i));
		}
	}
	/**
	 * print the collection 
	 * @param collection
	 */
	public static void printCollection(Collection collection) {
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			LogUtil.info(iterator.next());
		}
	}
}
