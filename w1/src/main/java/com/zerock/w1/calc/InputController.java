package com.zerock.w1.calc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "inputController", urlPatterns = "/calc/input")
public class InputController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
                                                        ServletException, IOException{

        System.out.println("InputController..doGet...");

        // RequestDispatcher라는 존재는 말 그대로 서블릿에 전달된 요청 (Request)을 다른 쪽으로 전달 혹은 배포 하는 역할을 하는 객체입니다.
        // RequestDispatcher를 이용하면 InputController는 마치 버스 정류장처렴 ‘/WEB-INF/calc/input.jsp’라는 목적지로 가는 증간 경유지가 됨
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/calc/input.jsp");

        dispatcher.forward(req, resp);
    }
}
