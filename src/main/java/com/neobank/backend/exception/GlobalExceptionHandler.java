package com.neobank.backend.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// Our business exceptions
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception){
		
		return ResponseEntity
				.status(exception.getStatus())
				.body(ErrorResponse.builder()
						.status(exception.getStatus().value())
						.message(exception.getMessage())
						.timeStamp(LocalDateTime.now())
						.build());
	}
	
	// @Validation exceptions
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
		
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponse.builder()
						.status(HttpStatus.BAD_REQUEST.value())
						.message("Validation error")
						.errors(errors)
						.timeStamp(LocalDateTime.now())
						.build()
						);
				
		
		
	}
	
	//incorrect password / user not found
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
		
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(ErrorResponse.builder()
						.status(HttpStatus.UNAUTHORIZED.value())
						.message("Email or password incorrect")
						.timeStamp(LocalDateTime.now())
						.build());
		
		
	}
	
	
	// Unexpected general errors
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex){
		
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ErrorResponse.builder()
						.status(HttpStatus.INTERNAL_SERVER_ERROR.value())	
						.message("Unexpected general errors")
						.timeStamp(LocalDateTime.now())
						.build());
						
		
	}
}
