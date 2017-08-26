/*
 * DateChooser.java
 *
 * Created on 2007年6月2日, 下午1:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.lgh.eastmoney.test;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.lgh.util.UIUtil;
import com.lgh.util.logging.LogUtil;

/**
 *
 * @author tewang
 */
public class DateChooser extends JPanel{
    private JComboBox yearJComboBox = new JComboBox();
    private JComboBox monthJComboBox = new JComboBox();
    private JComboBox dayJComboBox = new JComboBox();
    private Calendar calendar;
//    private static DateChooser dateChooser;
    /** Creates a new instance of DateChooser */
    public DateChooser() {
        
    }
    public DateChooser(Calendar c){
        this.calendar = c;
        initDateChooser();
    }
    public DateChooser(Calendar start ,Calendar end){
        
    }
    private void initDateChooser(){
        initJComboBox();
        initJPanel();
    }
    private void initJComboBox(){
        for(int i=2000;i<2050;i++){//初始化年
            yearJComboBox.addItem(i);
        }
        for(int i=1;i<=12;i++){//初始化月
            monthJComboBox.addItem(i);
        }
        for(int i=1;i<=29;i++){//初始化日
            dayJComboBox.addItem(i);
        }
        yearJComboBox.addPopupMenuListener(new PopupMenuListener(){
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                Calendar caldar=Calendar.getInstance();
                int y=caldar.get(Calendar.YEAR);
                yearJComboBox.removeAllItems();
                for(int i=2000;i<=y;i++){//初始化年
                    yearJComboBox.addItem(i);
                }
                yearJComboBox.setSelectedItem(y);
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            	LogUtil.info("2");
            }
            
            public void popupMenuCanceled(PopupMenuEvent e) {
            	LogUtil.info("3");
            }
        });
        monthJComboBox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {

            }
        
        });
        monthJComboBox.addPopupMenuListener(new PopupMenuListener(){//添加月监听,当有点击月时,日的时间变为1
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                int month = (Integer)monthJComboBox.getSelectedItem();
                Calendar caldar=Calendar.getInstance();
                int y=caldar.get(Calendar.YEAR);
                int m=caldar.get(Calendar.MONTH)+1;
                if((Integer)yearJComboBox.getSelectedItem()==y){
                    monthJComboBox.removeAllItems();
                    for(int i=1;i<=m;i++){//初始化日
                        monthJComboBox.addItem(i);
                    }
                }else {
                    monthJComboBox.removeAllItems();
                    for(int i=1;i<=12;i++){//初始化日
                        monthJComboBox.addItem(i);
                    }
                }
                monthJComboBox.setSelectedItem(month);

            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                dayJComboBox.setSelectedItem(1);
            }
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
        dayJComboBox.addPopupMenuListener(new PopupMenuListener(){//添加日监听,当有点击日,时要根据选择的月进行判断,是不是平年,或者润年
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                Calendar caldar=Calendar.getInstance();
                int y=caldar.get(Calendar.YEAR);
                int m=caldar.get(Calendar.MONTH)+1;
                int d=caldar.get(Calendar.DATE);
                if(((Integer)yearJComboBox.getSelectedItem() == y)&&((Integer)monthJComboBox.getSelectedItem()==m)){
                    dayJComboBox.removeAllItems();
                    for(int i=1;i<=d;i++){
                        dayJComboBox.addItem(i);
                    }
                }else {
                    
                    // dayJComboBox.setSize(20,100);
                    //dayJComboBox.repaint()
                    int day =(Integer)dayJComboBox.getSelectedItem();
                    dayJComboBox.removeAllItems();
                    int year  =(Integer)yearJComboBox.getSelectedItem();
                    int month = (Integer)monthJComboBox.getSelectedItem();
                    if(month==2){
                        if((year%4==0&&year%100!=0)||(year%400==0)){
                            for(int i=1;i<=29;i++){
                                dayJComboBox.addItem(i);
                            }
                        } else {
                            for(int i=1;i<=28;i++){
                                dayJComboBox.addItem(i);
                            }
                        }
                    }else if(month%2==1){
                        for(int i=1;i<=31;i++){
                            dayJComboBox.addItem(i);
                        }
                    }else if(month%2==0&&month!=2){
                        for(int i=1;i<=30;i++){
                            dayJComboBox.addItem(i);
                        }
                    }
                    dayJComboBox.setSize(20,100);
                    dayJComboBox.repaint();
                    dayJComboBox.setSelectedItem(day);
                }
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
        /**
         * 设置初始化的年月日为当前日期
         */
        Calendar cal=Calendar.getInstance();
        int y,m,d;
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH)+1;
        d=cal.get(Calendar.DATE);
        yearJComboBox.setSelectedItem(y);
        monthJComboBox.setSelectedItem(m);
        dayJComboBox.setSelectedItem(d);
        
    }
    private void initJPanel(){
        this.add(yearJComboBox);
        this.add(monthJComboBox);
        this.add(dayJComboBox);
        
    }
    public  Calendar getCalendar(){
        Calendar c = Calendar.getInstance() ;
        int year = (Integer)yearJComboBox.getSelectedItem();
        int month = (Integer)monthJComboBox.getSelectedItem();
        int day = (Integer)dayJComboBox.getSelectedItem();
        c.set(year,month,day);
        return c;
    }
//    public static JPanel getInstance(Calendar c){
//        if(dateChooser==null){
//            dateChooser = new DateChooser(c);
//        }
//        return dateChooser;
//    }
    public static void main(String[] args) {
		UIUtil.showOnJFrame(new DateChooser(Calendar.getInstance()));
	}
}
