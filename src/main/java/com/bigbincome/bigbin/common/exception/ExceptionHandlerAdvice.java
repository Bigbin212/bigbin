package com.bigbincome.bigbin.common.exception;

import com.bigbincome.bigbin.common.util.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

import static com.bigbincome.bigbin.common.Constants.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {
	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<JsonNode> handleBaseException(BaseException ex) {
		log.error(ex.getMessage(), ex);

		ObjectNode result = JsonUtils.object();
		String notFound = String.valueOf(HttpStatus.NOT_FOUND.value());
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		result.put("errorCode", ex.getErrorCode());
		if (notFound.equals(result.get("errorCode").asText())) {
			status = HttpStatus.NOT_FOUND;
		}
		result.put("errorMessage", status.getReasonPhrase());
		return ResponseEntity.status(status).body(result);
	}

	@ExceptionHandler
	public @ResponseBody
    ResponseEntity<JsonNode> handleRestException(RestException ex) {
		log.error("error occurs from rest service - {}, {}, {}", ex.getClass().getName(), ex.getMessage(),
				ex.getDetails());

		ObjectNode result = JsonUtils.object().put(SUCCESS, false);
		ObjectNode error = JsonUtils.object().put(CODE, ex.status().value());
		result.set(ERROR, error);

		if (ex.getMessage() != null) {
			error.put(MESSAGE, ex.getMessage());
		}
		if (ex.getDetails() != null) {
			error.set(DETAILS, ex.getDetails());
		}

		return ResponseEntity.status(ex.status()).body(result);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody
    JsonNode handle(ConstraintViolationException ex) {
		log.error("validation failed - {}", ex.getMessage());

		ObjectNode result = JsonUtils.object().put(SUCCESS, false);
		ObjectNode error = JsonUtils.object().put(CODE, HttpStatus.BAD_REQUEST.value());
		ObjectNode details = JsonUtils.object();
		ex.getConstraintViolations().forEach(cv -> {
			if (cv != null) {
				details.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
		});
		error.set(DETAILS, details);
		result.set(ERROR, error);
		return result;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody
    JsonNode handleValidationExceptions(MethodArgumentNotValidException ex) {
		ObjectNode result = JsonUtils.object().put(SUCCESS, false);
		ObjectNode error = JsonUtils.object().put(CODE, HttpStatus.BAD_REQUEST.value());
		ObjectNode details = JsonUtils.object();
		ex.getBindingResult().getAllErrors().forEach(err -> {
			String fieldName = ((FieldError) err).getField();
			String errorMessage = err.getDefaultMessage();
			details.put(fieldName, errorMessage);
		});
		error.set(DETAILS, details);
		result.set(ERROR, error);
		return result;
	}
}
