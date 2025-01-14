package com.practice.photogram.web;

import com.practice.photogram.config.auth.PrincipalDetails;
import com.practice.photogram.domain.image.Image;
import com.practice.photogram.handler.ex.CustomValidationException;
import com.practice.photogram.service.ImageService;
import com.practice.photogram.web.dto.image.ImageStoryDto;
import com.practice.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }


    // API 구현한다면 - 이유 - (브라우저에서 요청하는게 아니라, 안드로이드,iOS 요청)
    @GetMapping("/image/popular")
    public String popular(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<ImageStoryDto> images = imageService.인기사진(principalDetails.getUser().getId());

        model.addAttribute("images",images);

        return "image/popular";
    }

    @GetMapping("/image/upload")
    public String upload(){
        return "image/upload";
    }




    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // 깍둑이, 이미지가 첨부되지 않았습니다.
        if(imageUploadDto.getFile().isEmpty()) {
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }

        imageService.사진업로드(imageUploadDto, principalDetails);
        return "redirect:/user/"+principalDetails.getUser().getId();
    }
}

