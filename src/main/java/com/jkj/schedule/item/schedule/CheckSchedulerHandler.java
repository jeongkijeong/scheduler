package com.jkj.schedule.item.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckSchedulerHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private Job job = null;
	
	private String jobName = "";
	private String jobCron = "";

	public CheckSchedulerHandler(Job job, String jobName, String jobCron) {
		super();
		this.job = job;
		
		this.jobName = jobName;
		this.jobCron = jobCron;
	}

	public void start() {
		try {
			Scheduler scheduler = CheckSchedulerManager.getInstance().schedulerFactory.getScheduler();

			JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(this.jobName + "_job", Scheduler.DEFAULT_GROUP).build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(this.jobName + "_trigger", Scheduler.DEFAULT_GROUP)
					.withSchedule(CronScheduleBuilder.cronSchedule(jobCron)).build();

			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();

			logger.info("jobName : {} / jobCron : {}", jobName, jobCron);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
}
