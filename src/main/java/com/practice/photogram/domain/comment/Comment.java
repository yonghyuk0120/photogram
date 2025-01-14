package com.practice.photogram.domain.comment;

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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length=100, nullable = false)
    private String content;

    @JoinColumn(name="userId")
    @ManyToOne(fetch =FetchType.LAZY)
    private User user;

    @JoinColumn(name="imageId")
    @ManyToOne(fetch =FetchType.LAZY)
    private Image image;




    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }


}
