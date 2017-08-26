/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.xml;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import com.lgh.util.logging.LogUtil;

import javax.xml.parsers.*;

/**
 *
 * @author lgh
 */
public class XmlReaderSAX extends DefaultHandler {

    java.util.Stack tags = new java.util.Stack();

    public XmlReaderSAX() {
        super();
    }

    public static void main(String args[]) {
        long lasting = System.currentTimeMillis();
        try {
            SAXParserFactory sf = SAXParserFactory.newInstance();
            SAXParser sp = sf.newSAXParser();
            XmlReaderSAX reader = new XmlReaderSAX();
            sp.parse(XmlReaderSAX.class.getResourceAsStream("java.xml"), reader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtil.info("运行时间：" + (System.currentTimeMillis() - lasting) + "毫秒");
    }

    @Override
	public void characters(char ch[], int start, int length) throws SAXException {
        String tag = (String) tags.peek();
        if (tag.equals("NO")) {
            System.out.print("车牌号码：" + new String(ch, start, length));
        }
        if (tag.equals("ADDR")) {
        	LogUtil.info("地址:" + new String(ch, start, length));
        }
    }

    @Override
	public void startElement(String uri, String localName, String qName, Attributes attrs) {
        tags.push(qName);
    }
}
