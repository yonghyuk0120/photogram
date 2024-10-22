package com.practice.photogram.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	private Integer id; // TODO : int -> Integer
	private String username;
	private String profileImageUrl;
	private Boolean subscribeState;
	private Boolean equalUserState;
}
