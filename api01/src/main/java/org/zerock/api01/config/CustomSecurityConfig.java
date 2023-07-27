package org.zerock.api01.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// 1. CSRF 토큰의 비활성화, 2. 세션을 사용하지 않음
@Log4j2
// 설정이 완료된 후 프로젝트 실행하면 알수 없는 password가 생성되어 출력됨
// 값은 매번 다르게 생성되고 기본적으로 생성되는 사용자 아이디는 user
// 로그인 창이 생김
// Using generated security password: 74b854ce-6695-437a-b0d9-8c2b6f59b873
// url 경로 : http://localhost:8082/login
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
// 어노테이션으로 권한 설정 + prePostEnabled 속성은 원하는 곳에
// @PreAuthorize or @PostAuthorize 어노테이션 이용해서 사전, 사후 권한 체크 가능
public class CustomSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    // 정적 자원(css, js 파일 등에 필터가 적용됨)
    // 해당 설정 해제
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info("--------------------web configure--------------------");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 해당 설정 때문에 바로 board/list 경로로 접근할 수 있음
    // 메소드 설정으로 모든 사용자가 모든 경로에 접근할 수 있음
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        log.info("------------------configure------------------");

        // 1. csrf 비활성화
        http.csrf().disable();
        
        // 2. 세션을 사용하지 않음
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
    
}
