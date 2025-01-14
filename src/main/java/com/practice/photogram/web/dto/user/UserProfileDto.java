package com.practice.photogram.web.dto.user;


import com.practice.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	private boolean pageOwnerState;
	// isPageOwner 쓰지말 것. JSP에서  is가 붙은건 파싱이 잘 안될때가 있다.
	// 대신 state 사용.
	private int imageCount;
	private boolean subscribeState;
	private long subscribeCount;
	private User user;
	// 유저정보를 다 줄게 아니라 유저 dto로 보내야한다.
}
