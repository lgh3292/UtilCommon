/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.xml;

import java.util.*;
import org.jdom.*;
import org.jdom.input.*;

import com.lgh.util.logging.LogUtil;

public class XmlReaderJDOM {

    public static void main(String arge[]) {
        long lasting = System.currentTimeMillis();
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(XmlReaderJDOM.class.getResourceAsStream("java.xml"));
            Element foo = doc.getRootElement();
            List allChildren = foo.getChildren();
            for (int i = 0; i < allChildren.size(); i++) {
                System.out.print("车牌号码:" + ((Element) allChildren.get(i)).getChild("NO").getText());
                LogUtil.info("车主地址:" + ((Element) allChildren.get(i)).getChild("ADDR").getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
