package org.zerock.api01.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

    @Value("${org.zerock.jwt.secret}")
    private String key;

    // jwt 생성시 유효 기간을 days라는 파라미터로 처리했지만 실제로는 plusMinutes()를 이용했으므로 분 단위로 처리됨
    // 짧은 유효 기간이 테스트 시에 유용하기 때문인데 개발이 완료되면 plusDays()로 변경해 줄 필요가 있음
    public String generateToken(Map<String, Object> valueMap, int days){
        log.info("generateKey..." + key);

        // 헤더 부분
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);

        // 테스트 시에는 짧은 유효 기간 (1)이면 1분, (60*24) 하면 유효 기간이 일(day) 단위로 변경됨
        int time = (60*24) * days; // 테스트는 분단위로 나중에 60 * 24 (일) 단위변경

        // builder를 이용해 Header, Payload 부분을 지정하고 발행 시간과 서명을 이용해 compact()를 수행하면 JWT 문자열 생성됨
        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();

        return jwtStr;
    }

    public Map<String, Object> validateToken(String token) throws JwtException{

        Map<String, Object> claim = null;

        claim = Jwts.parser()
                .setSigningKey(key.getBytes()) // Set Key
                .parseClaimsJws(token)  // 파싱 및 검증, 실패 시 에러
                .getBody();

        return claim;
    }


}
