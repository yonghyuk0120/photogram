package com.practice.photogram.handler;

import com.practice.photogram.handler.ex.CustomApiException;
import com.practice.photogram.handler.ex.CustomException;
import com.practice.photogram.handler.ex.CustomValidationApiException;
import com.practice.photogram.handler.ex.CustomValidationException;

import com.practice.photogram.util.Script;
import com.practice.photogram.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHanlder {
 
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		// CMRespDto, Script 비교
		// 1. 클라이언트에게 응답할 때는 Script 좋음.
		// 2. Ajax통신 - CMRespDto
		// 3. Android 통신 - CMRespDto
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());
		}
		
	}
	// 이건 rest로 페이지를 반환하는 게 아니라 값을 반환하는 것
	// 그런데 그 값이 script코드여서 자동실행되는것
	
	@ExceptionHandler(CustomException.class)
	public String exception(CustomException e) {
		return Script.back(e.getMessage());
	}
 
	
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
}
