package com.jkj.schedule.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkj.schedule.common.CommonString;
import com.jkj.schedule.common.Constant;
import com.jkj.schedule.common.Utils;
import com.jkj.schedule.context.ContextManager;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class ProcessMain implements SignalHandler, CommonString {
	private static final Logger logger = LoggerFactory.getLogger(ProcessMain.class);

	public static void main(String[] args) throws Exception {
		int retv = -1;
		
		String cfgFilePath = Constant.DEFAULT_CFG_FILE_PATH;
		String logFilePath = Constant.DEFAULT_LOG_FILE_PATH;

		if (args != null && args.length >= 2) {
			cfgFilePath = args[0];
			logFilePath = args[1];
		}

		retv = Utils.loadProperties(cfgFilePath);
		if (retv < 0) {
			return;
		}

		retv = Utils.loadLogConfigs(logFilePath);
		if (retv < 0) {
			return;
		}

		ProcessMain processMain = new ProcessMain();
		processMain.startProcess();

		delegateHandler("TERM", processMain);
		while (Constant.RUN) {
			Thread.sleep(3000);
		}
	}

	/**
	 * start main process.
	 * */
	private void startProcess() {
		logger.debug("startProcess");
		
		ContextManager contextManager = ContextManager.getInstance();
		contextManager.startManager();
	}

	/**
	 * close main process.
	 * */
	private void closeProcess() {
		logger.debug("closeProcess");
		
		ContextManager contextManager = ContextManager.getInstance();
		contextManager.closeManager();

		System.exit(0);
	}

	@Override
	public void handle(Signal signal) {
		logger.info("Received SIG name[" + signal.getName() + "] / number[" + signal.getNumber() + "]");

		String SIGName = signal.getName();
		if (SIGName == null || SIGName.length() == 0) {
			return;
		}

		// End of process
		if (SIGName.equals("TERM")) {
			logger.info("close " + getClass().getName());
			closeProcess();
		}
	}

	public static void delegateHandler(String SIGName, SignalHandler SIGHandler) {
		Signal SIG = null;

		try {
			SIG = new Signal(SIGName);
			SIGHandler = Signal.handle(SIG, SIGHandler);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
