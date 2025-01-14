package com.practice.photogram.web.dto.image;


import com.practice.photogram.domain.image.Image;
import com.practice.photogram.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {
	
	private MultipartFile file;
	private String caption;
	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl)
				// uuid가 붙은 경로가 필요하므로 인자로 받아서  entity로 만든다.
				.user(user)
				// 또한 이미지 객체를 만드려면 user 정보가 필요하다.
				.build();
	}
}
