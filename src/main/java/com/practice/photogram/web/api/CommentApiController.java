package com.practice.photogram.web.api;

import com.practice.photogram.config.auth.PrincipalDetails;
import com.practice.photogram.domain.comment.Comment;
import com.practice.photogram.handler.ex.CustomValidationApiException;
import com.practice.photogram.service.CommentService;
import com.practice.photogram.web.dto.CMRespDto;
import com.practice.photogram.web.dto.comment.CommentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;


    @PostMapping("/api/comment")
    // story.js에서 값을 보낸다.
//    public ResponseEntity<?> commentSave(CommentDto commentDto){
    // 이런식으로 받으면 못 받는다.
    // 위처럼 받는 것은 x-www-form-urlencoded 형식의 키밸류 형태
    // 아래처럼 requestbody를 해야 json을 받는다. contentType: "application/json; charset=utf-8",
    public ResponseEntity<?> commentSave(@RequestBody @Valid CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails){
//        System.out.println(commentDto);

// ---------
// bindingresult 유효성 검사 코드 aop로 이동시켰음.
// ---------



        CommentDto comment = commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDto<>(1,"댓글쓰기성공",comment), HttpStatus.CREATED);


    }


    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id){


        commentService.댓글삭제(id);
        return new ResponseEntity<>(new CMRespDto<>(1,"댓글삭제성공",null), HttpStatus.OK);
    }

    //만약

}
