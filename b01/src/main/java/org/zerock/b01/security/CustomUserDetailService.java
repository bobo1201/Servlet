package org.zerock.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
//@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    // PasswordEncoder를 잠깐 테스트하는 용도록 사용,BCryptPasswordEncoder 생성해 임시로 동작
    public CustomUserDetailService(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // 프로젝트 실행 후 '/login' 경로 호출 후 아무 내용으로 로그인 처리하면 null이 추가된 loadUserByUsername() 실행 됨
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername: " + username);

        // 사용자 인증과 관련된 정보들을 저장하는 역할
        // 패스워드 인증아 안되서 실패함
        UserDetails userDetails = User.builder()
                .username("user1")
//                .password("1111")
                .password(passwordEncoder.encode("1111"))
                .authorities("ROLE_USER")
                .build();

        return userDetails;
    }
    
    // 로그인 진행하면 '/' 경로로 이동함
}
