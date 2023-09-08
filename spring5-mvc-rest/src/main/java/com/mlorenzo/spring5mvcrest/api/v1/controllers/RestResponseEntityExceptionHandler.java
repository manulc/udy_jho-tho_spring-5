package com.mlorenzo.spring5mvcrest.api.v1.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.mlorenzo.spring5mvcrest.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleNotFountException(ResourceNotFoundException exception,WebRequest request){
		return new ResponseEntity<Object>("Resource Not Found", new HttpHeaders(),HttpStatus.NOT_FOUND);
	}

}
