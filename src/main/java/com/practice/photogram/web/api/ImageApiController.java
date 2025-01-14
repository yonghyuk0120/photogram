package com.practice.photogram.web.api;


import com.practice.photogram.config.auth.PrincipalDetails;
import com.practice.photogram.domain.image.Image;
import com.practice.photogram.service.ImageService;
import com.practice.photogram.service.LikesService;
import com.practice.photogram.web.dto.CMRespDto;
import com.practice.photogram.web.dto.image.ImageStoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageApiController {
    private final ImageService imageService;
    private final LikesService likesService;


// 페이징 처리 이전
//    @GetMapping("/api/image")
//    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//
//        List<ImageStoryDto> imageDtos = imageService.이미지스토리(principalDetails.getUser().getId());
//
//        return new ResponseEntity<>(new CMRespDto<>(1, "성공", imageDtos), HttpStatus.OK);
//    }


// 페이징 처리 이후
    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,@PageableDefault(size=3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
// @PageableDefault(size=3, sort = "id", direction = Sort.Direction.DESC)
// 대신에 보내는 요청 주소 에 적을 수 도 있음.
// http://localhost:8080/api/image?size=3 등등
// 혹은 yml에 적어주면 global 세팅도 가능하다.
        Page<ImageStoryDto> imageStoryDtos = imageService.이미지스토리(principalDetails.getUser().getId(), pageable);

        return new ResponseEntity<>(new CMRespDto<>(1, "성공", imageStoryDtos), HttpStatus.OK);
    }


    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.좋아요(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1,"좋아요 성공",null), HttpStatus.CREATED);

    }


    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unLikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.좋아요취소(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1,"좋아요 취소 성공",null), HttpStatus.CREATED);

    }

// 테스트용
//    @GetMapping("/image/popular")
//    public ResponseEntity<?> popular(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        List<ImageStoryDto> images = imageService.인기사진(principalDetails.getUser().getId());
//
//
//
//        return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
//    }

}
