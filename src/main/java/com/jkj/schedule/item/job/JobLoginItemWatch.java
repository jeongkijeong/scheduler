package com.jkj.schedule.item.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkj.schedule.common.Constant;
import com.jkj.schedule.common.Utils;
import com.jkj.schedule.item.watch.CheckItemWatchManager;

public class JobLoginItemWatch implements CustomJob {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String jobName = LOGIN_ITEM;
	private String jobCron = "5 * * * * ?";
	
	public JobLoginItemWatch() {
		super();

		String cron = Utils.getProperty(Constant.CRON_LOGIN_ITEM);
		if (cron != null) {
			this.jobCron = cron;
		}
	}

	@Override
	public String getJobName() {
		return this.jobName;
	}

	@Override
	public String getJobCron() {
		return this.jobCron;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("execute {} / {}", jobName, jobCron);
		
		CheckItemWatchManager.getInstance().address(jobName);
	}
}
