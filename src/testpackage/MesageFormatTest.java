/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testpackage;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class MesageFormatTest {

    public static synchronized  String format(String pattern, Object[] arguments) {
        System.out.println("start");
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(MesageFormatTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        MessageFormat temp = new MessageFormat(pattern);
         System.out.println("end ");
        return temp.format(arguments);
    }
    public static void main(String[] args) {
        for(int i =0;i<10;i++){
            new Thread(new Runnable(){
                public void run(){
                    format("test{0}",new Object[]{"lgh"});
                }
            }).start();
        }
    }

}
