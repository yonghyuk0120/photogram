package com.practice.photogram.handler.aop;


import com.practice.photogram.handler.ex.CustomValidationApiException;
import com.practice.photogram.handler.ex.CustomValidationException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@Aspect
@RequiredArgsConstructor
public class ValidationAdvice {

    private final MessageSource messageSource;


    @Around("execution(* com.practice.photogram.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // ProceedingJoinPoint : 실행되는 함수의 모든 곳에 접근할 수 있는 변수.
        // 이 부분이 목표 함수보다 먼저 실행되는 부분이다.

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
//                System.out.println("유효성 검사 해당 함수");
                BindingResult bindingResult = (BindingResult) arg;

                /////// 유효성 검사 함수(메세지 소스 없는 방법은 아래쪽에 있다.) start--------
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), messageSource.getMessage(error, Locale.getDefault()));
                    }
                    // 메세지 소스에서 해당 필드에 해당하는 에러메시지 직접 꺼내오는 방법.
                    // userApi의 update는 무조건 기본 메세지고, 위의 방법은 메세지 소스를 이용 할 수도 있는 방법. 파고 보면 결국 하나하나 JSON 응답에 넣어 주는 행위임.
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }
                /////// 유효성 검사 함수 end--------

            }
        }


        //
        return proceedingJoinPoint.proceed();
        // 이제 해당 함수가 실행된다.
    }



//    유효성 검사 함수 메세지 소스 없이 사용.
//            if (bindingResult.hasErrors()) {
//        Map<String, String> errorMap = new HashMap<>();
//        for (FieldError error : bindingResult.getFieldErrors()) {
//            errorMap.put(error.getField(), error.getDefaultMessage());
//        }
//        throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
//    }



// 웹쪽 컨트롤러에 대한 어드바이스 - 나는 사용 x
//    @Around("execution(* com.practice.photogram.web.*Controller.*(..))")
//    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Object[] args = proceedingJoinPoint.getArgs();
//        for (Object arg : args) {
//            if (arg instanceof BindingResult) {
////                System.out.println("유효성 검사 해당 함수");
//                BindingResult bindingResult = (BindingResult) arg;
//
//                /////// 유효성 검사 함수(메세지 소스 없는 방법은 아래쪽에 있다.) start--------
//                if (bindingResult.hasErrors()) {
//                    Map<String, String> errorMap = new HashMap<>();
//
//                    for (FieldError error : bindingResult.getFieldErrors()) {
//                        errorMap.put(error.getField(), error.getDefaultMessage());
//                    }
//
//
//                    throw new CustomValidationException("유효성 검사 ", errorMap);
//                }
//                /////// 유효성 검사 함수 end--------
//
//            }
//        }
//
//        return proceedingJoinPoint.proceed();
//    }

}
