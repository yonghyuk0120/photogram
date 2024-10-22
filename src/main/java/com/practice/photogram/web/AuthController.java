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
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key=value (x-www-form-urlencoded)


        if (userService.checkUsernameDuplicate(signupDto.getUsername()))
            bindingResult.rejectValue("username", "NotUnique", new Object[]{"username"}, null);

        if (userService.checkEmailDuplicate(signupDto.getEmail()))
            bindingResult.rejectValue("email", "NotUnique", new Object[]{"email"}, null);


        if (bindingResult.hasErrors())
            return "auth/signup";


        User user = signupDto.toEntity();
        authService.회원가입(user);
        // 로그를 남기는 후처리!!
        return "redirect:/auth/signin";

    }


//     회원가입버튼 -> /auth/signup -> /auth/signin
//     회원가입버튼 X
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
//            throw new CustomValidationException("유효성 검사 ", errorMap);
//
//        } else {
//
//            User user = signupDto.toEntity();
//            authService.회원가입(user);
//            // 로그를 남기는 후처리!!
//            return "auth/signin";
//        }
//    }

}
