package com.neobank.backend.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

	private int status;
	private String message;
	private LocalDateTime timeStamp;
	private List<String> errors; //More than one errors in validation errors
	
}
