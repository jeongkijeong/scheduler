package com.jkj.schedule.item.watch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkj.schedule.context.DataHandler;
import com.jkj.schedule.item.job.CustomJob;

public class CheckItemWatchHandler extends DataHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public CheckItemWatchHandler() {
		super();
	}

	@Override
	public void handler(Object object) {
		try {
			String jobName = (String) object;
			logger.info("request for job [{}]", jobName);

			switch (jobName) {
			case CustomJob.CHECK_ITEM:
				// TODO for check item.

				break;
			case CustomJob.LOGIN_ITEM:
				// TODO for login item.

				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
