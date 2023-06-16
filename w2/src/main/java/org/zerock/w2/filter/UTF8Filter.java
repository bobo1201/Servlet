package org.zerock.w2.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 필터 적용하기 위해 필요한 어노테이션
@WebFilter(urlPatterns = {"/*"})
@Log4j2
public class UTF8Filter implements Filter {
    
    @Override
    // Request에 대해 doFilter를 실행하는 구조
    // POST 방식으로 값을 전달했을 때 UTF-8을 적용하면 한글 깨짐 현상 확인
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            ServletException, IOException {
        log.info("UTF8 filter....");

        HttpServletRequest req = (HttpServletRequest)request;

        req.setCharacterEncoding("UTF-8");

        // 다음 필터나 목적지(서블릿, JSP)로 갈 수 있도록 FilterChain의 doFilter 실행
        // 문제 생겨서 더 이상 진행할 수 없다면 리다이렉트 처리할 수 있음
        chain.doFilter(request, response);
    }
}

// 187 페이지