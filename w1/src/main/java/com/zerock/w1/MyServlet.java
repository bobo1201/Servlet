package com.zerock.w1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// HttpServlet이라는 경로 상속 및 @WebServlet 어노테이션으로 서블릿의 경로 지정
@WebServlet(name = "myServlet", urlPatterns = "/my")
public class MyServlet extends HttpServlet {

    @Override
    // URL에 GET 요청이 오면 HTML 페이지를 응답으로 보냄
    // doGet() : 브라우저의 주소를 직접 변경해서 접근하는 경우에 호출되는 메소드
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
                                                    ServletException, IOException{
        // 응답 콘텐츠 타입을 HTML로 설정
        resp.setContentType("text/html");

        // PrintWriter() 호출
        PrintWriter out = resp.getWriter();

        // System.out.println 대신 PrintWriter 객체 사용해 브라우저쪽으로 출력 처리
        out.println("<html><body>");
        out.println("<h1>MyServlet</h1>");
        out.println("</body></html>");
    }
}
