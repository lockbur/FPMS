package com.forms.platform.core.logger.log4j;

import java.io.IOException;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;

public class CustomRollingFileAppender extends RollingFileAppender{


	public CustomRollingFileAppender() {
		super();
	}

	public CustomRollingFileAppender(Layout layout, String filename, boolean append) throws IOException {
		super(layout, filename, append);
	}

	public CustomRollingFileAppender(Layout layout, String filename) throws IOException {
		super(layout, filename);
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
