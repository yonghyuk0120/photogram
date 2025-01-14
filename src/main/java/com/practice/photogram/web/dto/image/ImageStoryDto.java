package com.practice.photogram.web.dto.image;

import com.practice.photogram.domain.Likes.Likes;
import com.practice.photogram.domain.comment.Comment;
import com.practice.photogram.domain.image.Image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageStoryDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caption;
    private String postImageUrl;
    private StoryUserDto user; // 1,  1
    private LocalDateTime createDate;


    // like 관련 추가 필드
    private List<LikesDto> likes;
    private boolean likeState;
    private int likeCount;


    // comment 관련 추가 필드
    private List<StoryCommentDto> comments;



    // Dto 변환용 생성자
    public ImageStoryDto(Image img, int principalId) {
        this.id = img.getId();
        this.caption = img.getCaption();
        this.postImageUrl = img.getPostImageUrl();
        this.user = new StoryUserDto(img.getUser().getId(), img.getUser().getName(),  img.getUser().getProfileImageUrl(), img.getUser().getUsername());
        // 생성자 순서 틀려서 하루종일 고생했다. url과 username의 위치를 다르게적었었음
//         img.getUser().getUsername(), img.getUser().getProfileImageUrl());
        this.createDate = img.getCreateDate();


        // like 로직
        this.likeCount = img.getLikes().size();
        this.likes = img.getLikes().stream().map(LikesDto::new).toList();
//        this.likes = img.getLikes().stream().map(like -> new LikesDto(like)).toList();


        for (Likes like : img.getLikes()) {
            if (like.getUser().getId() == principalId) {
                // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로긴한 사람이 좋아요 한것인지 비교
                this.setLikeState(true);
                break;
            }
        }
//        img.getLikes().forEach((like) -> {
//            if (like.getUser().getId() == principalId) {
//                // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로긴한 사람이 좋아요 한것인지 비교
//                this.setLikeState(true);
//            }
//        });

        // comment 로직

        this.comments = img.getComments().stream().map(StoryCommentDto::new).toList();





    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class StoryUserDto {
        //    public static class StoryUserDto{
        // 외부에서 build하면서 쓸 수 도 있어서 public해놨지만
        // 람다식 사용할 때는 private으로 해도 된다.
        // 아니다. 결국 jsp 에서 get으로 이것들에 대해 접근하므로 public으로 해야한다.
        private int id;
        private String name;
        private String profileImageUrl;
        private String username;

    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class LikesDto {
        private int id;
        private StoryUserDto user;
        private LocalDateTime createDate;

        private LikesDto(Likes like) {
            this.id = like.getId();
            this.user = new StoryUserDto(like.getUser().getId(), like.getUser().getName(),  like.getUser().getProfileImageUrl(), like.getUser().getUsername());
            this.createDate = like.getCreateDate();

        }

    }



    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class StoryCommentDto {
        private int id;
        private StoryUserDto user;
        private LocalDateTime createDate;
        private String content;

        private StoryCommentDto(Comment comment) {

            this.id = comment.getId();
            this.user = new StoryUserDto(comment.getUser().getId(), comment.getUser().getName(),  comment.getUser().getProfileImageUrl(), comment.getUser().getUsername());
            this.createDate = comment.getCreateDate();
            this.content = comment.getContent();
        }

    }


}


