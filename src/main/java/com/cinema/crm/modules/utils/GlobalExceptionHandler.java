package com.cinema.crm.modules.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
//public class GlobalExceptionHandler{
//
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Map<String,Object>> handelValidationExecption(MethodArgumentNotValidException ex){
//		Map<String,Object> response = new HashMap<>();
//		response.put("result", "error");
//		response.put("responseCode", 400);
//		response.put("msg", ex.getBindingResult().getFieldError().getDefaultMessage());
//		response.put("output", ex.getBindingResult().getFieldError().getDefaultMessage());
//		
//		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
//		
//	}
//	
//}
