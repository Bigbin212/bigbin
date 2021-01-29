package com.bigbincome.bigbin.common.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;

public class ClientRestException extends RestException {
	public ClientRestException() {
	}

	public ClientRestException(String message) {
		super(message);
	}

	public ClientRestException(ObjectNode details) {
		super.details = details;
	}

	public ClientRestException(String message, ObjectNode details) {
		super(message);
		super.details = details;
	}

	@Override
	public HttpStatus status() {
		return HttpStatus.BAD_REQUEST;
	}
}
