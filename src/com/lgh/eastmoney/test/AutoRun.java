package com.lgh.eastmoney.test;

import java.util.ArrayList;
import java.util.List;

public class AutoRun implements Runnable{
	public List<IAutoRunEvent> events = new ArrayList<IAutoRunEvent>();
	
	public AutoRun(){
		loadIAutoRunEvents();
	}
	
	private void loadIAutoRunEvents(){
		
	}
	
	public void run() {
		while(true){
			for(final IAutoRunEvent event:events){
				if(event.isSchedule()){
					new Thread(new Runnable(){
						public void run(){
							event.executeJob();
						}
					}).start();
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
