package com.practice.photogram.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{
	
	// 객체를 구분할 때!! 사용되는 번호,
	// 내부에서 사용하는 것이므로 우리에게 중요한 것이 아니다.
	private static final long serialVersionUID = 1L;

	private Map<String, String> errorMap;
	
	public CustomValidationException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}
	
	public Map<String, String> getErrorMap(){
		return errorMap;
	}
}
