package com.practice.photogram.config;

//import com.practice.photogram.config.oauth.OAuth2DetailsService;     // oauth2 설정 - 필요시 주석 풀기
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration // IoC
// 강의랑 다르고 내가 찾아서 설정한 내용이다. 시큐리티 버전이 바뀌어서 이렇게 람다식으로 해야한다.
public class SecurityConfig {

    // oauth2 설정 - 필요시 주석 풀기
//    private final OAuth2DetailsService oAuth2DetailsService;

    /* 로그인 실패 핸들러 의존성 주입 */
    private final AuthenticationFailureHandler customAuthenticationFailureHandler;


    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 기본적으로 달리는 csrf 비활성화
        http.csrf(c -> c.disable());



        // 권한 조정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**")
                // 이거는 인증이 필요하고(막아야 할 주소)
                .authenticated().anyRequest().permitAll());
                // 그게 아닌 모든 요청은 허용하겠다.

        // 폼 로그인 지정
        http.formLogin(form -> form // form 로그인 추가
                .loginPage("/auth/signin") // get
                .loginProcessingUrl("/auth/signin")
                // post 위의 경우 시큐리티가 로그인 프로세스를 진행
                .failureHandler(customAuthenticationFailureHandler)
                .defaultSuccessUrl("/"));

        // oauth2 설정 - 필요시 주석 풀기
//        http.oauth2Login(oauth-> oauth // oauth2 로그인 추가
//                .userInfoEndpoint(c -> c. // 최종응답을 userInfo로 바로 받음.
//                        userService(oAuth2DetailsService)));


        return http.build();
    }

}
