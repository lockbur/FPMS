package com.forms.platform.core.logger.log4j;

import java.io.IOException;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;

public class CustomDailyRollingFileAppender extends DailyRollingFileAppender{

	public CustomDailyRollingFileAppender() {
		super();
	}

	public CustomDailyRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException {
		super(layout, filename, datePattern);
	}

	@Override
	public void setFile(String file) {
		file = CustomLoggerAppenderHolder.resolverCustomFileName(file);
		super.setFile(file);
	}

	@Override
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		fileName = CustomLoggerAppenderHolder.resolverCustomFileName(fileName);
		super.setFile(fileName, append, bufferedIO, bufferSize);
	}
}
