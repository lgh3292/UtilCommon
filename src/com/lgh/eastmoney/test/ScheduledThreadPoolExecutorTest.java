package com.lgh.eastmoney.test;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lgh.util.logging.LogUtil;

/**
 * 定时调度
 * @author liuguohu
 *
 */
public class ScheduledThreadPoolExecutorTest {
	ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(11);
	public static void main(String[] args) {
		new ScheduledThreadPoolExecutorTest().start();
	}
	public void start(){
//		s.s
		s.scheduleWithFixedDelay(new Test(),1, 1, TimeUnit.SECONDS);
	}
	private class Test implements Runnable{
		public void run(){
//			while(true){
			LogUtil.info(Thread.currentThread().getName());
				try {
					Thread.sleep(1000); 
//					s.schedule(this,0,TimeUnit.SECONDS);
				} catch (Exception e) {
					e.printStackTrace();
//				}
			}
		}
	}
}
