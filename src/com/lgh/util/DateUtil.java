/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author lgh
 */
public class DateUtil {
	/**
	 * return pre days
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getPreMonths(Date date,int months){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		Date d = new Date();
		d.setTime(calendar.getTimeInMillis());
		return d;
	}
	
	/**
	 * parse the date by assigned String date example:  
	 * date: 20110808
	 * pattern: yyyyMMdd
	 * @param date
	 * @param parrern
	 * @return will return current date if error happen
	 */
	public static Date parseDate(String date,String parrern){
		SimpleDateFormat sdf = new SimpleDateFormat(parrern);
		try {
			Date d = sdf.parse(date);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	/**
	 * return pre days
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getPreDays(Date date,int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		Date d = new Date();
		d.setTime(calendar.getTimeInMillis());
		return d;
	}
	
	public static String getTimestampToString(String pattern,Timestamp timestamp){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(timestamp.getTime());
	}
	
	public static Timestamp getCurrentTimestamp(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return new Timestamp(sdf.parse(sdf.format(new Date())).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * parse the date and get long
	 * @param date
	 * @return
	 */
	public static long parseDateToLong(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d = sdf.parse(date);
			return d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static String parseDateToStr(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static String parseDateToStr(long l, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = new Date(l);
		return sdf.format(date);
	}
	/**
	 * return the current date's String type by the date pattern
	 * @param pattern)
	 * @return
	 */
	public static String getCurrentDate(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	
	/**
	 * return the current date's String type by the date pattern
	 * @param pattern)
	 * @return
	 */
	public static String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	/**
	 * return the current date's String type by the date pattern
	 * @param pattern)
	 * @return
	 */
	public static String getDateStr(long date,String pattern){
		Date d = new Date(date);
		return getDateStr(d,pattern);
	}
	
	/**
	 * return the current date's String type by the date pattern
	 * @param pattern)
	 * @return
	 */
	public static String getDateStr(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	/**
	 * detect how many working days does it delay by day and assigned date(except Saturday and Sunday)
	 */
	public static int OverTimeDays(Calendar day1, Calendar day2) {
		Calendar temp1 = Calendar.getInstance();
		Calendar temp2 = Calendar.getInstance();
		temp1.setTime(day1.getTime());
		temp2.setTime(day2.getTime());
		int days = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		while (!dateFormat.format(temp1.getTime()).equals(
				dateFormat.format(temp2.getTime()))) {
			if (temp2.after(temp1)) {
				temp1.add(Calendar.DATE, 1);
				if (temp1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| temp1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					continue;
				}
				days++;
			} else {
				temp1.add(Calendar.DATE, -1);
				if (temp1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| temp1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					continue;
				}
				days--;
			}
		}
		return days;
	}
	/**
	 * every day from Mondy to Friday and between 9AM to 6PM
	 * @param date
	 */
	public static boolean isWoringTime(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minOfHour = calendar.get(Calendar.MINUTE);
		if(dayOfWeek>=Calendar.MONDAY&&dayOfWeek<=Calendar.FRIDAY){
			if(hourOfDay>=9&&hourOfDay<18){
				return true;
			}
		}
		return false;
	}
	/**
	 * every day from Mondy to Friday and between 9:20AM to 11:35AM and 1:00PM to 3:05PM
	 * @param date
	 */
	public static boolean isTransactionTime(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minOfHour = calendar.get(Calendar.MINUTE);
		if(dayOfWeek>=Calendar.MONDAY&&dayOfWeek<=Calendar.FRIDAY){
			if(hourOfDay>=9&&hourOfDay<=11){
				if(hourOfDay==9){
					if(minOfHour>=20){
						return true;
					}
				}else if(hourOfDay==11){
					if(minOfHour<=35){
						return true;
					}
				}else{
					return true;
				}
			}
			if(hourOfDay>=13&&hourOfDay<=15){
				if(hourOfDay==15){
					if(minOfHour<=5){
						return true;
					}
				}else{
					return true;
				}
			}
		}
		return false;
	}
}
