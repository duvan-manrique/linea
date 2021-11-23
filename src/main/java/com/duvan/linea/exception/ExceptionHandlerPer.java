package com.duvan.linea.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandlerPer extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ModelNotFoundException.class)
	public final ResponseEntity<ExceptionWrapper> manejadorModelNotFoundException(ModelNotFoundException e,
			WebRequest request){
		e.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), 
					e.getMessage(), request.getDescription(false));
		return new ResponseEntity<ExceptionWrapper>(ew, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ArithmeticException.class)
	public final ResponseEntity<ExceptionWrapper> manejadorModelArithmeticException(ArithmeticException e,
			WebRequest request){
		e.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
					"Ha ocurrido un error", request.getDescription(false));
		return new ResponseEntity<ExceptionWrapper>(ew, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ConflictException.class)
	public final ResponseEntity<ExceptionWrapper> manejadorConflictException(ConflictException e,
			WebRequest request){
		e.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.toString(), 
					e.getMessage(), request.getDescription(false));
		return new ResponseEntity<ExceptionWrapper>(ew, HttpStatus.CONFLICT);
	}		
	
	@ExceptionHandler(ArgumentRequiredException.class)
	public final ResponseEntity<ExceptionWrapper> manejadorArgumentRequiredException(ArgumentRequiredException e,
			WebRequest request){
		e.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), 
					e.getMessage(), request.getDescription(false));
		return new ResponseEntity<ExceptionWrapper>(ew, HttpStatus.BAD_REQUEST);
	}		
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionWrapper> manejadorModelException(Exception e,
			WebRequest request){
		e.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
					"Ha ocurrido un error", request.getDescription(false));
		return new ResponseEntity<ExceptionWrapper>(ew, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString(), 
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("Entro 2");
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.toString(), 
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), 
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.BAD_REQUEST);	
	}

	//Argumentos no validos -- Por averfiguar
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		ex.printStackTrace();
		
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
		
		
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), 
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.BAD_REQUEST);
	}

	//Por averfiguar
	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		System.out.println("Entro 3");
		// TODO Auto-generated method stub
		return super.handleMissingPathVariable(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ExceptionWrapper ew = new ExceptionWrapper(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), 
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(ew, HttpStatus.NOT_FOUND);
	}		
	
	
	
	
	
}
