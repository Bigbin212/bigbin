package com.bigbincome.bigbin.common.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;

public class ServerRestException extends RestException {
	public ServerRestException() {
	}

	public ServerRestException(String message) {
		super(message);
	}

	public ServerRestException(ObjectNode details) {
		super.details = details;
	}

	public ServerRestException(String message, ObjectNode details) {
		super(message);
		super.details = details;
	}

	@Override
	public HttpStatus status() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
