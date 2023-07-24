package org.zerock.b01.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.b01.security.CustomUserDetailService;

import javax.sql.DataSource;

@Log4j2
// 설정이 완료된 후 프로젝트 실행하면 알수 없는 password가 생성되어 출력됨
// 값은 매번 다르게 생성되고 기본적으로 생성되는 사용자 아이디는 user
// 로그인 창이 생김
// Using generated security password: 74b854ce-6695-437a-b0d9-8c2b6f59b873
// url 경로 : http://localhost:8082/login
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
// 어노테이션으로 권한 설정 + prePostEnabled 속성은 원하는 곳에
// @PreAuthorize or @PostAuthorize 어노테이션 이용해서 사전, 사후 권한 체크 가능
public class CustomSecurityConfig {

    // 주입 필요
    private final DataSource dataSource;
    private final CustomUserDetailService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 해당 설정 때문에 바로 board/list 경로로 접근할 수 있음
    // 메소드 설정으로 모든 사용자가 모든 경로에 접근할 수 있음
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        log.info("--------------------configure--------------------");

        // 커스텀 로그인 페이지
        // username이라는 사용자의 아이디 인증을 코드로 구현
        // 로그인 화면에서 로그인을 진행한다는 설정 추가
        // .loginPage()를 추가해 로그인 페이지 경로 지정
        http.formLogin().loginPage("/member/login");
        
        // csrf 비활성화
        http.csrf().disable();
        
        // 쿠키 값을 인코딩 하기 위한 key값과 필요한 정보 저장하는 tokenRepository 지정
        http.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailService)
                .tokenValiditySeconds(60*60*24*30);

        return http.build();
    }

    // 정적 자원(css, js 파일 등에 필터가 적용됨)
    // 해당 설정 해제
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info("--------------------web configure--------------------");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
}
