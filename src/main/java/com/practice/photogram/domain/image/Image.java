package com.practice.photogram.domain.image;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.photogram.domain.user.User;
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
@Entity
public class Image { // N,   1
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String caption; // 오늘 나 너무 피곤해!!
	private String postImageUrl; // 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 - DB에 그 저장된 경로를 insert
	

	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user; // 1,  1
	
	// 이미지 좋아요
//	@JsonIgnoreProperties({"image"})
//	@OneToMany(mappedBy = "image")
//	private List<Likes> likes;
	
	// 댓글
//	@OrderBy("id DESC")
//	@JsonIgnoreProperties({"image"})
//	@OneToMany(mappedBy = "image")
//	private List<Comment> comments;
	
	@Transient // DB에 칼럼이 만들어지지 않는다.
	private boolean likeState;
	
	@Transient
	private int likeCount;
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
