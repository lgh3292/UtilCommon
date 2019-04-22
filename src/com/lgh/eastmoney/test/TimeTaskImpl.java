package com.lgh.eastmoney.test;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.lgh.util.DateUtil;
import com.lgh.util.logging.LogUtil;

public class TimeTaskImpl extends TimerTask{
	Timer timer = new Timer();
	
	public void start(){
		Calendar c =  Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 17);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND,01);
		Date d = new Date();
		d.setTime(c.getTimeInMillis());
//				timer.schedule(new TimeTaskImpl(), d,1000);
//		timer.schedule(new TimeTaskImpl(),1000);
		timer.scheduleAtFixedRate(new TimeTaskImpl(), d,10);
	}
	public void next(){
		Calendar c =  Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 17);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND,01);
		Date d = new Date();
		d.setTime(c.getTimeInMillis());
//				timer.schedule(new TimeTaskImpl(), d,1000);
		timer.schedule(new TimeTaskImpl(),1000);
		timer.scheduleAtFixedRate(new TimeTaskImpl(), d,10);
	}
	@Override
	public void run() {
		LogUtil.info("   "+DateUtil.getCurrentDate("yyyyMMdd HH:mm:ss"));
		//next();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	new TimeTaskImpl().start();
	}

}
