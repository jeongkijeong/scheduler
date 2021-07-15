package com.jkj.schedule.main;

import com.jkj.schedule.common.CommonString;

public interface ProcessManager extends CommonString {
	public void start();

	public void close();

	public void address(Object object);
}