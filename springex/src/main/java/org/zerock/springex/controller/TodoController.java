package org.zerock.springex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zerock.springex.dto.TodoDTO;

@Controller
@RequestMapping("/todo")
@Log4j2
public class TodoController {

    @RequestMapping("/list")
    public void list(){
        log.info("todo list.......");
    }

//    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @GetMapping("/register")
    // Mapping 경로와 같은 jsp가 있으면 리턴하지 않아도 해당 jsp를 호출할 수 있음
    public void registerGet(){
        log.info("GET todo register.....");
    }

    @PostMapping("/register")
    public void registerPost(TodoDTO todoDTO){
        log.info("POST todo register.....");

        log.info(todoDTO);
    }
}
