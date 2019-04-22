/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.xml;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.lgh.util.logging.LogUtil;

/**
 *
 * @author lgh
 */
public class XmlReaderDom {
    /**
     * 第一种采用dom4j读取xml
     * @param args
     */
    public static void readyByDom(){
          try {
            File f = new File("java.xml");
            LogUtil.info(f.getAbsolutePath());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList nl = doc.getElementsByTagName("VALUE");
            for (int i = 0; i < nl.getLength(); i++) {
                System.out.print("车牌号码:" + doc.getElementsByTagName("NO").item(i).getFirstChild().getNodeValue());
                LogUtil.info("车主地址:" + doc.getElementsByTagName("ADDR").item(i).getFirstChild().getNodeValue());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
   
    public static void main(String[] args) {
      
      readyByDom();
    }
}
