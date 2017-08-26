package com.lgh.eastmoney.test;

public interface IAutoRunEvent {
	//return whether the time is come ,if yes do executeJob();
	public boolean isSchedule();
	//do executeJob
	public void executeJob();
}
