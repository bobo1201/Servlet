package com.zerock.w1.calc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "calcController", urlPatterns = "/calc/makeResult")
public class CalcController extends HttpServlet {

    @Override
    // Post 방식으로 호출하는 경우만 가능
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
                                                        ServletException, IOException{

        // JSP의 ${param.num1}과 같음
        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");

        System.out.printf(" num1: %s", num1);
        System.out.printf(" num2: %s", num2);
        
        // 처리가 끝난 직후 다른 경로로 이동
        resp.sendRedirect("/index");
    }
}
