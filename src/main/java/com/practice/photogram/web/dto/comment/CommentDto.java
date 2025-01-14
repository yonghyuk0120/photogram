package com.practice.photogram.web.dto.comment;

import com.practice.photogram.domain.comment.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 숫자쓸때, Integer사용하고, NotNull 사용하기
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private int id;
    @NotBlank
    private String content;
    @NotNull
    private Integer imageId;
    private CommentUserDto user;

    public CommentDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user = new CommentUserDto(comment.getUser().getId(), comment.getUser().getName(), comment.getUser().getUsername());
        // 순서 주의

    }



    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CommentUserDto {
        //    public static class StoryUserDto{
        // 외부에서 build하면서 쓸 수 도 있어서 public해놨지만
        // 람다식 사용할 때는 private으로 해도 된다.
        // 아니다. 결국 jsp 에서 get으로 이것들에 대해 접근하므로 public으로 해야한다.
        private int id;
        private String name;
        private String username;

    }

}
