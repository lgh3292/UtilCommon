/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.xml;

import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lgh.util.logging.LogUtil;

/**
 *
 * @author lgh
 */
public class XmlReaderDom4j {

    public static void readByDom4j() {
        long lasting = System.currentTimeMillis();
        try {
            InputStream is = XmlReaderDom4j.class.getResourceAsStream("java.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            Element foo;
            for (Iterator i = root.elementIterator("VALUE"); i.hasNext();) {
                foo = (Element) i.next();
                System.out.print("车牌号码:" + foo.elementText("NO"));
                LogUtil.info("车主地址:" + foo.elementText("ADDR"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readByDom4j();
    }
}
