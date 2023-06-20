package org.zerock.springex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@Controller
@Log4j2
public class SampleController {
    @GetMapping("/hello")
    public void hello(){
        // 메소드명과 같은 jsp가 있으면 리턴하지 않아도 해당 jsp를 호출할 수 있음
        log.info("hello");
    }

    @GetMapping("ex1")
    // http://localhost:8080/ex1?name=AAA&age=16 // name : AAA, age : 16이 나타남
    public void ex1(String name, int age){
        log.info("ex1............");
        log.info("name: " + name);
        log.info("age : " + age);
    }

    @GetMapping("ex2")
    // http://localhost:8080/ex2 // name : AAA, age : 16이 나타남
    public void ex2(@RequestParam(name="name", defaultValue = "AAA") String name,
                    @RequestParam(name="age", defaultValue = "20") int age){
        log.info("ex2............");
        log.info("name: " + name);
        log.info("age : " + age);
    }

    @GetMapping("ex3")
    // http://localhost:8080/ex2 // name : AAA, age : 16이 나타남
    public void ex3(LocalDate dueDate){
        log.info("ex3............");
        log.info("dueDate: " + dueDate);
    }
}
