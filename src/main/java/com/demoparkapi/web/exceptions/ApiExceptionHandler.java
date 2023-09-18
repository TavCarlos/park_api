package com.demoparkapi.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.demoparkapi.exceptions.UsernameUniqueViolationException;
import com.demoparkapi.exceptions.CpfUniqueViolationException;
import com.demoparkapi.exceptions.EntityNotFoundException;
import com.demoparkapi.exceptions.PasswordInvalidException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage>  MethodArgumentNotValidException(HttpServletRequest request, BindingResult result) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s)", result));
	}
	
	@ExceptionHandler({UsernameUniqueViolationException.class, CpfUniqueViolationException.class})
	public ResponseEntity<ErrorMessage> UsernameUniqueViolationException(RuntimeException ex, HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> EntityNotFoundException(RuntimeException ex, HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
	@ExceptionHandler(PasswordInvalidException.class)
	public ResponseEntity<ErrorMessage> PasswordInvalidException(RuntimeException ex, HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
	}
}
