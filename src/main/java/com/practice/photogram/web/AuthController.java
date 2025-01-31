package com.practice.photogram.web;

import com.practice.photogram.domain.user.User;
import com.practice.photogram.handler.ex.CustomValidationApiException;
import com.practice.photogram.handler.ex.CustomValidationException;
import com.practice.photogram.service.AuthService;
import com.practice.photogram.service.UserService;
import com.practice.photogram.web.dto.auth.SignupDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor // final 필드를 DI 할때 사용
@Controller // 1. IoC 2. 파일을 리턴하는 컨트롤러
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(@ModelAttribute SignupDto signupDto) {
        return "auth/signup";
    }


    @PostMapping("/auth/signup")
    // 동기통신에서 validation 에러처리 - 내가 만든 거
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key=value (x-www-form-urlencoded)


        // 이건 DB에 가봐야 하는 후처리 문제 - 글로벌 에러 아닌가 의문 - x jsp에서 해당 필드에 대해 에러 메세지를 표현해야 하므로 rejectValue가 맞다.
        // 강의에서는 자동으로 담기는 bindingresult 외에는 전부 익셉션핸들러로 처리했지만
        // 이렇게 해야 메세지 소스를 사용할 수 있다.
        if (userService.checkUsernameDuplicate(signupDto.getUsername()))
            bindingResult.rejectValue("username", "NotUnique", new Object[]{"username"}, null);

        // 이건 DB에 가봐야 하는 후처리 문제
        if (userService.checkEmailDuplicate(signupDto.getEmail()))
            bindingResult.rejectValue("email", "NotUnique", new Object[]{"email"}, null);

        // bindingResult에 담긴 오류는 자동으로 모델에 담겨 뷰로 넘어간다.
        if (bindingResult.hasErrors())
            return "auth/signup";


        User user = signupDto.toEntity();
        authService.회원가입(user);
        return "redirect:/auth/signin";

    }

    


// 동기처리 원래버전.
//    @PostMapping("/auth/signup")
//    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key=value (x-www-form-urlencoded)
//
//
//        if (bindingResult.hasErrors()) {
//            Map<String, String> errorMap = new HashMap<>();
//
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                errorMap.put(error.getField(), error.getDefaultMessage());
//            }
//
//
//            throw new CustomValidationException("유효성 검사 실패", errorMap);
//
//        } else {
//
//            User user = signupDto.toEntity();
//            authService.회원가입(user);
//            return "auth/signin";
//        }
//    }

}
