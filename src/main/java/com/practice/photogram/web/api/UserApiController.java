package com.practice.photogram.web.api;


import com.practice.photogram.config.auth.PrincipalDetails;
import com.practice.photogram.domain.user.User;
import com.practice.photogram.handler.ex.CustomValidationApiException;
import com.practice.photogram.service.SubscribeService;
import com.practice.photogram.service.UserService;
import com.practice.photogram.web.dto.CMRespDto;
import com.practice.photogram.web.dto.subscribe.SubscribeDto;
import com.practice.photogram.web.dto.user.UserUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{id}")
    // api validation 유효성 검사, 예외 처리 방법!!
    public ResponseEntity<?> update(@PathVariable int id, @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {


    // 유효성 검사 코드 aop로 옮겨감.



        User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity); // 세션 정보 변경
        // 수정 후 세션 정보 변경해주기.

        return new ResponseEntity<>(new CMRespDto<>(1, "회원수정완료", null), HttpStatus.OK);


    }


    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 가져오기 성공", subscribeDto), HttpStatus.OK);
    }


    @PutMapping("/api/user/{principallId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principallId, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //받는 변수 이름은 profile.jsp의 폼태그의 name값으로 정확히 잡아야 한다.
        User userEntity = userService.회원프로필사진변경(principallId, profileImageFile);
        principalDetails.setUser(userEntity);
// 이 과정으로 세션 유저가 알아서 변경된다.
        return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 성공", null), HttpStatus.OK);


    }


}
