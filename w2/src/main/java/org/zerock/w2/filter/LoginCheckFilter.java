package org.zerock.w2.filter;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;


// Filter 인터페이스 사용해야하는데 import 해줘야함
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

// 필터 적용하기 위해 필요한 어노테이션
// /todo/로 시작하는 모든 경로에 대해 필터링
@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {
    
    @Override
    // Request에 대해 doFilter를 실행하는 구조
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            ServletException, IOException {
        log.info("Login check filter....");

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        HttpSession session = req.getSession();
        
        // settion의 loginInfo의 변수에 값이 없다면 login 경로로 이동해라
        if(session.getAttribute("loginInfo") == null){
            resp.sendRedirect("/login");
            return;
        }
        
        // 211 page 추가
        // session에 loginInfo 값이 없다면
        // 쿠키를 체크
        // HttpServletRequest를 이용해 모든 쿠키 중 'remember-me' 이름의 쿠키 검색
        Cookie cookie = findCookie(req.getCookies(), "remember-me");
        
        // 세션에도 없고 쿠키도 없다면 그냥 로그인으로
        if(cookie == null){
            resp.sendRedirect("/login");
            return;
        }
        
        // 쿠키가 존재하는 상황이라면 
        log.info("cookie는 존재하는 상황");
        // uuid 값
        String uuid = cookie.getValue();

        try{
            // 데이터베이스 확인
            // 해당 쿠키의 value를 이용해 MemberService를 통해 MemberDTO를 구성
            MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

            log.info("쿠키의 값으로 조회한 사용자 정보 : " + memberDTO);
            if (memberDTO == null){
                throw  new Exception("Cookie value is not valid");
            }

            // 회원 정보를 세션에 추가
            // HttpSession을 이용해 'loginInfo'라는 이름으로 MemberDTO를 setAttribute()
            session.setAttribute("loginInfo", memberDTO);
            // 다음 필터나 목적지(서블릿, JSP)로 갈 수 있도록 FilterChain의 doFilter 실행
            // 문제 생겨서 더 이상 진행할 수 없다면 리다이렉트 처리할 수 있음
            // 정상적으로 FilterChain의 doFilter() 수행
            chain.doFilter(request, response);
        } catch (Exception e){
            e.printStackTrace();
            resp.sendRedirect("/login");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String name){
        if (cookies == null || cookies.length == 0){
            return null;
        }

        Optional<Cookie> result = Arrays.stream(cookies)
                .filter(ck -> ck.getName().equals(name))
                .findFirst();

        return result.isPresent()?result.get():null;
    }
}
