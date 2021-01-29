package com.bigbincome.bigbin.common.exception;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 486200447173026794L;
	
	private String errorCode;

	public BaseException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseException(String errorCode, Throwable e) {
		this(errorCode, e.getMessage(), e);
	}

	public BaseException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
