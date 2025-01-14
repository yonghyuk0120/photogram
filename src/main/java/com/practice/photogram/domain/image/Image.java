package com.practice.photogram.domain.image;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.photogram.domain.Likes.Likes;
import com.practice.photogram.domain.comment.Comment;
import com.practice.photogram.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image { // N,   1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caption; // 오늘 나 너무 피곤해!!
    private String postImageUrl; // 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 - DB에 그 저장된 경로를 insert


//    @JsonIgnoreProperties({"image"})
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1,  1

//     이미지 좋아요
	// 연관관계의 주인은 like다
	@OneToMany(mappedBy = "image")
    // like 객체 안의 image에 의해 수동적으로 결정되는 필드(mapped by)
	private List<Likes> likes;


    // 댓글

//	@JsonIgnoreProperties({"image"})
    @OrderBy("id DESC")
    // JPA 리포지토리에서 가져올 때 이렇게 order by 이용할 수 있다.
    // save는 실제 구현을 우리가 안 하니까, 이렇게 사용해야 한다.
	@OneToMany(mappedBy = "image")
	private List<Comment> comments;

    @Transient // DB에 칼럼이 만들어지지 않는다.
//     나같은 경우에는 dto 에서 이 부분을 연산해서 만들어서 보내므로 필요없다.
    private boolean likeState;

    @Transient
    private int likeCount;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
