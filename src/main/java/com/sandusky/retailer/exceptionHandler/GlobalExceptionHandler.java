package com.sandusky.retailer.exceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.mongodb.MongoQueryException;
import com.sandusky.retailer.enums.RewardPointConstants;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	// Handle validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		log.error(RewardPointConstants.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getValue(), ex);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	// Handle MongoDB Duplicate Key exceptions
	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<String> handleDuplicateKeyException(DuplicateKeyException ex) {
		return new ResponseEntity<>(RewardPointConstants.DUPLICATE_KEY_ERROR.getValue() + ex.getMessage(),
				HttpStatus.CONFLICT);
	}

	// Handle MongoDB Query Execution exceptions
	@ExceptionHandler(MongoQueryException.class)
	public ResponseEntity<String> handleMongQueryException(MongoQueryException ex) {
		return new ResponseEntity<>(RewardPointConstants.MONGO_QUERY_EXCEPTION_MSG.getValue() + ex.getMessage(),
				HttpStatus.CONFLICT);
	}

	// Handle resource not found (custom exception)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(NoHandlerFoundException ex) {
		return new ResponseEntity<>(RewardPointConstants.ENDPOINT_NOT_FOUND_MSG.getValue().concat(ex.getRequestURL()),
				HttpStatus.NOT_FOUND);
	}

	// Handle general server errors
	@ExceptionHandler(Exception.class)
	public ResponseStatusException handleGenericException(Exception ex) {
		log.error(RewardPointConstants.EXCEPTION_DURING_PROCESSING_REQUEST.getValue(), ex);
		return new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
				ex.getLocalizedMessage(), ex);
	}
}