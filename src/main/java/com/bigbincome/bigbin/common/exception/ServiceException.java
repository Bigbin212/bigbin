package com.bigbincome.bigbin.common.exception;

public class ServiceException extends BaseException {
	private static final long serialVersionUID = -5495707875010775004L;

	public ServiceException(String errorCode, String message) {
		super(errorCode, message);
	}

	public ServiceException(String errorCode, Throwable e) {
		super(errorCode, e);
	}

	public ServiceException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
}
