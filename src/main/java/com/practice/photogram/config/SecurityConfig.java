package com.practice.photogram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// TODO : 시큐리티 최신버전
@Configuration // IoC
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {


        http.csrf(c -> c.disable());
        // 기본적으로 달리는 csrf 비활성화

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**")
                // 이거는 인증이 필요하고(막아야 할 주소)
                .authenticated().anyRequest().permitAll());
                // 그게 아닌 모든 요청은 허용하겠다.

        http.formLogin(form -> form
                .loginPage("/auth/signin") // get
                .loginProcessingUrl("/auth/signin")
                // post 위의 경우 시큐리티가 로그인 프로세스를 진행
                .defaultSuccessUrl("/"));

        return http.build();
    }

}
