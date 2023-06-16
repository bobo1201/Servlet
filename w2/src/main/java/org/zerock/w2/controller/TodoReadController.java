package org.zerock.w2.controller;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.TodoDTO;
import org.zerock.w2.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoReadController", value = "/todo/read")
@Log4j2
public class TodoReadController extends HttpServlet {
    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
                                                    ServletException, IOException{

        try{
            Long tno = Long.parseLong(req.getParameter("tno"));

            TodoDTO todoDTO = todoService.get(tno);

            //데이터 담기, 모델 담기
            req.setAttribute("dto", todoDTO);

            // 쿠키 찾기
            // findCookie 메소드를 직접 생성해서 req의 쿠키를 가져옴, viewTodos라는 이름으로 가져옴
            // todoListStr에 쿠키가 있다면 쿠키 값을 활용하고 없다면 새로운 문자열 생성
            // 개발자가 쿠키의 경로와 값을 만듦
            // 쿠키 이름 지정
            Cookie viewTodoCookie = findCookie(req.getCookies(), "viewTodos");
            // 문자열 내에 현재 Todo의 번호를 문자열로 연결
            String todoListStr = viewTodoCookie.getValue();
            boolean exist = false;

            // 2-3-4- 와 같은 형태로 연결하고 이미 조회한 번호는 추가하지 않음
            if (todoListStr != null && todoListStr.indexOf(tno+"-") >= 0){
                exist = true;
            }

            log.info("exist: " + exist);

            if(!exist){
                todoListStr += tno+"-";
                viewTodoCookie.setValue(todoListStr);
                
                // 쿠키의 유효기간은 24시간으로 지정하고 쿠키를 담아서 전송
                viewTodoCookie.setMaxAge(60*60*24);
                viewTodoCookie.setPath("/");
                resp.addCookie(viewTodoCookie);
            }

            req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req, resp);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ServletException("read error");
        }
    }

    // viewTodos(조회 목록 크키)를 찾아내는 메소드
    private Cookie findCookie(Cookie[] cookies, String cookieName){
        Cookie targetCookie = null;

        if(cookies != null && cookies.length > 0){
            for(Cookie ck:cookies){
                if(ck.getName().equals(cookieName)){
                    targetCookie = ck;
                    break;
                }
            }
        }
        
        if(targetCookie == null){
            targetCookie = new Cookie(cookieName, "");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(60*60*24);
        }

        return targetCookie;
    }
}
