package com.practice.photogram.domain.user;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.photogram.domain.image.Image;

import com.practice.photogram.domain.subscribe.Subscribe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비에 테이블을 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    @Column(length = 100,  unique = true) // OAuth2 로그인을 위해 칼럼 늘리기
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website; // 웹 사이트
    private String bio; // 자기 소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    // 나는 연관관계의 주인이 아니다. 그러므로 테이블에 칼럼을 만들지마.

    // Lazy = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages() 함수의 image들이 호출될 때 가져와!!
    // Eager = User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와!!
    @OneToMany(mappedBy = "user")
    private List<Image> images; // 양방향 매핑

//    @OneToMany(mappedBy = "fromUser")
//    private List<Subscribe> subscribes; // 양방향 매핑


    private String profileImageUrl; // 사진
    private String role; // 권한


    private LocalDateTime createDate;

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
