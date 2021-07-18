package com.wkassignment.search.numberservice.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wkassignment.search.numberservice.responsemodel.ErrorMessage;

@ControllerAdvice
public class NumserviceExceptionsHandler {
	/**
	 * this method will catch all the custom NumberServiceException.
	 * 
	 * @param numServiceException
	 * @return
	 * @author ankitarambole
	 */

	@ExceptionHandler(value = { NumberServiceException.class })
	public ResponseEntity<Object> handleNumberServiceException(NumberServiceException numServiceException) {

		ErrorMessage errorMessage = new ErrorMessage(numServiceException.getMessage(), new Date());

		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * this method will catch all the exceptions except NumberServiceException
	 * @param exception
	 * @return
	 * @author ankitarambole
	 */
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleGenericException(Exception exception) {

		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), new Date());

		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
