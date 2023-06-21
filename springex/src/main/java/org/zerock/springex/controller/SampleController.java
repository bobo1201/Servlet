package org.zerock.springex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.TodoDTO;

import java.time.LocalDate;


@Controller
@Log4j2
public class SampleController {
    @GetMapping("/hello")
    public void hello(){
        // Mapping 경로와 같은 jsp가 있으면 리턴하지 않아도 해당 jsp를 호출할 수 있음
        log.info("hello");
    }

    @GetMapping("/ex1")
    // http://localhost:8080/ex1?name=AAA&age=16 // name : AAA, age : 16이 나타남
    public void ex1(String name, int age){
        log.info("ex1............");
        log.info("name: " + name);
        log.info("age : " + age);
    }

    @GetMapping("/ex2")
    // http://localhost:8080/ex2 // name : AAA, age : 16이 나타남
    public void ex2(@RequestParam(name="name", defaultValue = "AAA") String name,
                    @RequestParam(name="age", defaultValue = "20") int age){
        log.info("ex2............");
        log.info("name: " + name);
        log.info("age : " + age);
    }

    @GetMapping("/ex3")
    // http://localhost:8080/ex3?dueDate=2020-10-10 // dueDate : 2020-10-10이 나타남
    public void ex3(LocalDate dueDate){
        log.info("ex3............");
        log.info("dueDate: " + dueDate);
    }

    @GetMapping("/ex4")
    public void ex4(Model model){
        log.info("-----------------------");

        model.addAttribute("message", "Hello World");
    }

    // JSP에서 별도의 처리 없이 ${todoDTO}를 이용할 수 있음
    // 자동으로 생성된 변수명 todoDTO라는 이름 외에 다른 이름을 사용하고 싶다면 명시적으로
    // @ModelAttribute()를 지정할 수 있음
    @GetMapping("/ex4_1")
    // ${dto}와 같은 이름의 변수로 처리할 수 있음
    public void ex4Extra(@ModelAttribute("dto") TodoDTO todoDTO, Model model){
        log.info("todoDTO");
    }

    @GetMapping("/ex5")
    public String ex5(RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("name", "ABC");
        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/ex6";
    }

    @GetMapping("/ex6")
    public void ex6(){
    }

    @GetMapping("/ex7")
    // http://localhost:8080/ex7?p1=AAA&p2=BBB 실행시 BBB는 문자열이므로
    // 01:02:59  WARN [org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver] Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: "BBB"]
    // 경고 메시지가 뜨면서 400에러가 나타남
    public void ex7(String p1, int p2){
        log.info("p1............" + p1);
        log.info("p2............" + p2);
    }
}
