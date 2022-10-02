package com.pool.config.exception;

import java.sql.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pool.model.exception.CustomErrorResponse;
import com.pool.model.exception.UserProfileException;
import com.pool.util.InfinityConstants;


@RestControllerAdvice
public class InfinityGlobleExceptionHandler {

	@ExceptionHandler(value = UserProfileException.class)
	public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(UserProfileException error) {
		String errorCode = HttpStatus.NOT_FOUND.toString();
		Integer status = HttpStatus.NOT_FOUND.value();
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		String message=error.getMessage();
		
		if(message.equals(InfinityConstants.EMPTY_USER_PROFILE)) {
			errorCode = HttpStatus.NOT_FOUND.toString();
			status = HttpStatus.NOT_FOUND.value();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		
		
		return commonException(message,errorCode,status,httpStatus);
	}

	public ResponseEntity<CustomErrorResponse> commonException(String error, String errorCode,
															   Integer status, HttpStatus httpStatus) {
		return new ResponseEntity<CustomErrorResponse>(CustomErrorResponse.builder()
																		  .errorCode(errorCode)
																		  .errorMsg(error)
																		  .status(status)
																		  .timestamp(new Date(System.currentTimeMillis()))
																		  .build(),httpStatus);
	}
}
