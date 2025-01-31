//package com.practice.photogram.config.oauth;
//
//import com.practice.photogram.config.auth.PrincipalDetails;
//import com.practice.photogram.domain.user.User;
//import com.practice.photogram.domain.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Service
//public class OAuth2DetailsService extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
////    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//// 시큐리티config에서 bean으로 등록되는 bCryptPasswordEncoder보다
//// Oauth서비스가 먼저 호출되기때문에 IoC(스프링 컨테이너) 안에 아직 encoder가 들어오지 못 한다.
//
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        Map<String,Object> userInfo = oAuth2User.getAttributes();
//        String username = "facebook_" + (String) userInfo.get("id");
////        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());
//        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
//        // SercurityConfig 가 Oauth서비스보다 늦게 뜨기 때문에 여기서는 직접 생성해서 써야한다.
//        String email = (String) userInfo.get("email");
//        String name = (String) userInfo.get("name");
//
//        User userEntity = userRepository.findByUsername(username);
//
//        if(userEntity ==null ){ // 최초 로그인
//
//            User user = User.builder().username(username).password(password).email(email).name(name).role("ROLE_USER").build();
//            return  new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes());
//
//        }else{ // 이미 페이스북으로 회원가입 했음.
//
//            return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
//        }
//
//
//
//
//    }
//}
