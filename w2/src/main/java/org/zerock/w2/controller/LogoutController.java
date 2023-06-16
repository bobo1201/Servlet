package org.zerock.w2.controller;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// Post 방식으로 파라미터를 수집하고, HttpSession에 'loginInfo' 이름을 이용해서 간단한 문자열 저장
@WebServlet("/logout")
@Log4j2
public class LogoutController extends HttpServlet {

    // 중요한 처리 작업이므로 POST 방식인 경우에만 동작하도록 doPost() 로 설계
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
                                                    ServletException, IOException{
        log.info("log out..............");

        HttpSession session = req.getSession();

        // removeAttribute 메서드로 loginInfo에 해당하는 세션 정보 삭제 가능
        session.removeAttribute("loginInfo");
        
        // session에 있는 모든 세션 정보 한 번에 삭제 가능
        session.invalidate();

        resp.sendRedirect("/");
    }
}
