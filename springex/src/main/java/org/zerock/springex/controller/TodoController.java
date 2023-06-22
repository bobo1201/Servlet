package org.zerock.springex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.TodoDTO;
import org.zerock.springex.service.TodoService;

import javax.validation.Valid;

@Controller
@RequestMapping("/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @RequestMapping("/list")
    public void list(Model model){
        log.info("todo list.......");

        model.addAttribute("dtoList", todoService.getAll());
    }

//    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @GetMapping("/register")
    // Mapping 경로와 같은 jsp가 있으면 리턴하지 않아도 해당 jsp를 호출할 수 있음
    public void registerGet(){
        log.info("GET todo register.....");
    }

    //RedirectAttributes가 없어도 실행됨
    @PostMapping("/register")
    // TodoDTO에 @Valid를 적용하고 BindingResult 타입을 파라미터로 새롭게 추가
    public String registerPost(@Valid TodoDTO todoDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        log.info("POST todo register.....");

        // 검증에 문제가 있다면 다시 입력 화면으로 리다이렉트되도록 처리
        // 잘못된 결과는 RedirectAttributes의 addFlashAttribute를 통해 전달
        if(bindingResult.hasErrors()){
            log.info("has errors.............");

//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            // 값이 유효하지 않다면 해당 하면으로 다시 돌아감
            return "redirect:/todo/register";
        }

        log.info(todoDTO);

        todoService.register(todoDTO);

        return "redirect:/todo/list";
    }
}
