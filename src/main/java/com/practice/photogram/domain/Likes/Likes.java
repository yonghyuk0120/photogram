package com.practice.photogram.domain.Likes;

import com.practice.photogram.domain.image.Image;
import com.practice.photogram.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"imageId", "userId"}
                )
        }
        // 같은 유저가 같은 이미지에는 좋아요를 못하게 묶어서 유니크 제약
)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "imageId")
    //DB의 foreignKey 를 imageId라는 이름으로 만들어 줘
    @ManyToOne(fetch = FetchType.LAZY)
    private Image image; // 1
    // N:1, 하나의 이미지가 여러개의 좋아요를 받을 수 있다.

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1
    // N:1 여러 유저가 좋아요를 만들 수 있다.
    private LocalDateTime createDate;


    // 연관관계 편의 메서드
    // set으로 덮어 써도 되지만, java 관례가 아닌 무언가 다른 변경이 있다는 것을 알리기 위해 change를 쓴다.
    // 하지만 여기선 필요없다.
    public void changeImage(Image img){
        this.image = img;
        image.getLikes().add(this);
    }

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
