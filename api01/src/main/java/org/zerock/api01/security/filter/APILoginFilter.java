package org.zerock.api01.security.filter;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;


// 스프링 시큐리티의 AbstractAuthenticationProcessingFilter를 이용해 jwt 문자열을 발행하는 기능 완전한 분리
@Log4j2
public class APILoginFilter extends AbstractAuthenticationProcessingFilter {
    public APILoginFilter(String defaultFilterProcessesUrl){
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException,
                                                IOException, ServletException{
        log.info("APILoginFilter---------------------------------");


        if(request.getMethod().equalsIgnoreCase("GET")){
        // POST 방식으로 요청이 들어올 때 JSON 문자열을 처리하도록 GET은 제한함
            log.info("GET METHOD NOT SUPPORT");
            return null;
        }

        Map<String, String> jsonData = parseRequestJSON(request);
        
        log.info(jsonData);

        // Map으로 처리된 mid, mpw를 이용해 로그인 처리하는 부분에 토큰 인증 정보 만듬(//789p)
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                jsonData.get("mid"),
                jsonData.get("mpw"));

        return getAuthenticationManager().authenticate(authenticationToken);
    }


    private Map<String, String> parseRequestJSON(HttpServletRequest request){
        
        // JSON 데이터를 분석해서 mid, mpw 전달 값을 Map으로 처리
        try(Reader reader = new InputStreamReader(request.getInputStream())){
            Gson gson = new Gson();
            
            return gson.fromJson(reader, Map.class);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
