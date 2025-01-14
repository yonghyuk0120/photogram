package com.practice.photogram.config.auth;


import com.practice.photogram.domain.user.User;
import com.practice.photogram.domain.user.UserRepository;
import com.practice.photogram.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service // IoC
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	 
	// 1. 패스워드는 알아서 체킹하니까 신경쓸 필요 없다.
	// 2. 리턴이 잘되면 자동으로 UserDetails 타입을 세션으로 만든다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findByUsername(username);

		if(userEntity == null) {
			return null;
		}else {
			return new PrincipalDetails(userEntity);
		}
	}

}
