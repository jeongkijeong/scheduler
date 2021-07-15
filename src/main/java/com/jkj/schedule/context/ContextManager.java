package com.jkj.schedule.context;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkj.schedule.common.CommonString;
import com.jkj.schedule.common.Constant;
import com.jkj.schedule.item.schedule.CheckSchedulerManager;
import com.jkj.schedule.item.watch.CheckItemWatchManager;
import com.jkj.schedule.main.ProcessManager;

public class ContextManager implements CommonString {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<ProcessManager> managerList = null;

	public static ContextManager instance = null;

	public static ContextManager getInstance() {
		if (instance == null) {
			instance = new ContextManager();
		}

		return instance;
	}

	public ContextManager() {
		super();

		managerList = new ArrayList<ProcessManager>();

		// 점검항목.
		managerList.add(CheckItemWatchManager.getInstance());

		// 스케쥴러.
		managerList.add(CheckSchedulerManager.getInstance());
/**/
	}

	/**
	 * 컨텍스트 매니저 시작.
	 */
	public int startManager() {
		logger.info(this.getClass().getSimpleName() + " start");

		Constant.RUN = true;
		try {
			for (ProcessManager manager : managerList) {
				manager.start();
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		logger.info(this.getClass().getSimpleName() + " start completed");
		
		return -1;
	}

	/**
	 * 컨텍스트 매니저 종료.
	 */
	public int closeManager() {
		logger.info(this.getClass().getSimpleName() + " close");

		Constant.RUN = false;
		
		try {
			for (ProcessManager manager : managerList) {
				manager.close();
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		logger.info(this.getClass().getSimpleName() + " close completed");
		System.exit(0);
		
		return -1;
	}

}
