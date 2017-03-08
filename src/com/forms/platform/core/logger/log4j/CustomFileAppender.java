package com.forms.platform.core.logger.log4j;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;

public class CustomFileAppender extends FileAppender{


	public CustomFileAppender() {
		super();
	}

	public CustomFileAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		super(layout, filename, append, bufferedIO, bufferSize);
	}

	public CustomFileAppender(Layout layout, String filename, boolean append) throws IOException {
		super(layout, filename, append);
	}

	public CustomFileAppender(Layout layout, String filename) throws IOException {
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
