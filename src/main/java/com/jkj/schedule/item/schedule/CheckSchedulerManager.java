package com.jkj.schedule.item.schedule;

import java.util.ArrayList;
import java.util.List;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkj.schedule.item.job.CustomJob;
import com.jkj.schedule.item.job.JobCheckItemWatch;
import com.jkj.schedule.item.job.JobLoginItemWatch;
import com.jkj.schedule.main.ProcessManager;

public class CheckSchedulerManager implements ProcessManager{
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static CheckSchedulerManager instance = null;

	public SchedulerFactory schedulerFactory = new StdSchedulerFactory();

	private List<CustomJob> customJobList = null;

	public static CheckSchedulerManager getInstance() {
		if (instance == null) {
			instance = new CheckSchedulerManager();
		}

		return instance;
	}

	public CheckSchedulerManager() {
		super();
		
		setCustomJob();
	}

	private void setCustomJob() {
		customJobList = new ArrayList<CustomJob>();

		customJobList.add(new JobCheckItemWatch());
		customJobList.add(new JobLoginItemWatch());
	}

	@Override
	public void start() {
		try {
			for (CustomJob customJob : customJobList) {
				CheckSchedulerHandler handler = new CheckSchedulerHandler(customJob, customJob.getJobName(),
						customJob.getJobCron());
				handler.start();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public void close() {
	}

	@Override
	public void address(Object object) {
	}
}
