package com.zerock.w1.todo;

import com.zerock.w1.todo.dto.TodoDTO;
import com.zerock.w1.todo.service.TodoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TodoListController", urlPatterns = "/todo/list")
public class TodoListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
                                                        ServletException, IOException{

        System.out.println("/todo/list");

        // list라는 이름으로 List<TodoDTO> 객체를 보관함
        List<TodoDTO> dtoList = TodoService.INSTANCE.getList();
        
        // 보관된 데이터는 JSP에서 EL로 간단하게 확인 가능
        req.setAttribute("list", dtoList);

        req.getRequestDispatcher("/WEB-INF/todo/list.jsp").forward(req, resp);

    }
}
