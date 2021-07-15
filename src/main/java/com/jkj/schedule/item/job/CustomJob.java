package com.jkj.schedule.item.job;

import org.quartz.Job;

public interface CustomJob extends Job {
	public String LOGIN_ITEM = "loginItem";
	public String CHECK_ITEM = "checkItem";

	public String getJobName();

	public String getJobCron();
}
