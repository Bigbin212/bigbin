package com.bigbincome.bigbin.common.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public abstract class RestException extends RuntimeException {
	@Getter
	@Setter
	protected ObjectNode details;

	public RestException() {
	}

	public RestException(String message) {
		super(message);
	}

	public abstract HttpStatus status();
}
