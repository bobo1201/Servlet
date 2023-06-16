package org.zerock.w2.controller;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.dto.TodoDTO;
import org.zerock.w2.service.MemberService;
import org.zerock.w2.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

// Post 방식으로 파라미터를 수집하고, HttpSession에 'loginInfo' 이름을 이용해서 간단한 문자열 저장
@WebServlet("/login")
@Log
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException{
        log.info("login get...............");

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req,resp);
    }
    
    
    // mid, mpw 수집한 후 이를 이용해 문자열 구성
    // 나중에는 DTO 변경, 구성된 문자열을 HttpSession은 이용하는 공간에 저장함
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException{
        log.info("login post..............");
        
        
        // 폼을 통해 전송한 데이터나 URL 쿼리 문자열에서 파라미터 값을 추출 할 수 있음
        // request 클라이언트 요청을 나타내는 객체로 이 객체를 통해 파라미터 값 추출 가능
        // getParameter("parameterName") : parameterName 은 추출하려는 파라미터 이름
        // 폼의 input 요소의 name 속성이나 URL 쿼리 문자열에 사용된 이름과 일치해야 합니다.
        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");

        String auto = req.getParameter("auto");

        boolean rememberMe = auto != null && auto.equals("on");

        try{
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid, mpw);

            // 206 수정 try catch문에 uuid 내용 추가
            // 로그인시 체크박스를 체크하면 데이터 베이스에 임의의 값이 생성됨
            // UUID를 이용해 임의의 번호 생성
            // MemberDAO,  MemberService, LoginController 수정
            if(rememberMe){
                // 컬럼에 uuid 값 추가(업데이트)
                String uuid = UUID.randomUUID().toString();
                
                MemberService.INSTANCE.updateUuid(mid, uuid);
                memberDTO.setUuid(uuid);
                
                // remember-me 이름의 쿠키 생성해서 전송
                Cookie rememberCookie = new Cookie("remember-me", uuid);
                rememberCookie.setMaxAge(60*60*24*7);  // 쿠키 유효기간은 1주일
                // 쿠키는 해당 경로 및 하위 경로에서만 유효함
                // 웹 애플리케이션의 최상위 경로
                rememberCookie.setPath("/"); // 쿠키의 경로를 루트로 설정

                resp.addCookie(rememberCookie);
            }


            // HttpSession을 이용해 setAttribute()를 사용자 공간에 'loginInfo'라는 이름으로 문자열 보관
            HttpSession session = req.getSession();
            // 정상적으로 로그인 된 경우 HttpSession을 이용해 'loginInfo' 이름으로 객체 저장
            // 예외가 발생하는 경우 '/login'으로 이동 'result'라는 파라미터를 전달해 문제가 발생했다는 사실 전달
            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/todo/list");
        } catch (Exception e){
            resp.sendRedirect("/login?result=error");
        }
    }
}
