package com.zerock.w1;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "sampleServlet", urlPatterns = "/sample")
public class SampleServlet extends HttpServlet {


    // http://localhost:8080/sample 경로 접속시 실행 됨
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
                                                    ServletException, IOException{

        System.out.println("doGet...." + this);
    }

    // 톰캣 종료시 실행 됨(한번만)
    @Override
    public void destroy(){
        System.out.println("destroy.........................");
    }

    // 해당 경로 접속 시 실행 됨(한번만)
    @Override
    public void init(ServletConfig config) throws ServletException{
        System.out.println("init(ServletConfig)..........");
    }
}
