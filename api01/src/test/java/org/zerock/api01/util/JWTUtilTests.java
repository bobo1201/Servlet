package org.zerock.api01.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Log4j2
public class JWTUtilTests {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerate(){
        Map<String, Object> claimMap = Map.of("mid", "ABCDE");

        String jwtStr = jwtUtil.generateToken(claimMap, 1);

        log.info(jwtStr);
    }

    @Test
    public void testValidate(){

        // 유효 시간이 지난 토큰
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTEzNjkyMjUsIm1pZCI6IkFCQ0RFIiwiaWF0IjoxNjkxMzY5MTY1fQ.sLTGC86q_cM3dwhWYu_OaY4ZLS4xon_WPw1MYENFa4s";

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info(claim);

    }

    // JWT 문자열을 생성해서 이를 검증하는 작업을 같이 수행하는 테스트 메소드 작성
    // mid와 email을 이용해 JWT 문자열을 생성하고 validateToken()을 실행함
    // validateToken()의 리턴값에는 mid와 email이 그대로 들어있는 것을 확인할 수 있음
    @Test
    public void testAll(){
        String jwtStr = jwtUtil.generateToken(Map.of("mid", "AAAA", "email", "aaaa@bbb.com"), 1);

        log.info(jwtStr);

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info("MID: " + claim.get("mid"));

        log.info("EMAIL: " + claim.get("email"));
    }
}
