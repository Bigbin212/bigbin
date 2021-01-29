package com.bigbincome.bigbin.common.exception;

import org.springframework.http.HttpStatus;

public class Exceptions {
	public static final String ERROR_CODE_KEY = "errorCode";
	public static final String ERROR_MESSAGE_KEY = "errorMessage";
	
	public static class Code {
		public static final String IO_ERROR = "IO_ERROR";
		public static final String E0501 = "E0501";
	}

	public static class Message {
		public static final String DE0310 = "failed to parse data to json";
		public static final String E0501 = "failed to process new task";
	}

	public static ServiceException of(int statusCode) {
		return of(HttpStatus.valueOf(statusCode));
	}

	public static ServiceException of(HttpStatus status) {
		return new ServiceException("" + status.value(), status.getReasonPhrase());
	}

	public static final ServiceException NOT_FOUND = of(HttpStatus.NOT_FOUND);
}
