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
import org.zerock.springex.dto.PageRequestDTO;
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
    public void list(@Valid PageRequestDTO pageRequestDTO,
            BindingResult bindingResult, Model model){

        log.info(pageRequestDTO);

        // @Valid를 이용해 잘못된 파라미터 값들이 들어오면 page:1, size:10으로 고정된 값 처리
        if(bindingResult.hasErrors()){
            pageRequestDTO = PageRequestDTO.builder().build();
        }

        model.addAttribute("responseDTO", todoService.getList(pageRequestDTO));

//        log.info("todo list.......");
//        model.addAttribute("dtoList", todoService.getAll());
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

    @GetMapping({"/read", "/modify"})
    public void selectOne(Model model, Long tno){

        TodoDTO todoDTO = todoService.getOne(tno);

        log.info(todoDTO);

        model.addAttribute("dto", todoDTO);
    }

    @PostMapping("/remove")
    public String remove(Long tno, RedirectAttributes redirectAttributes){

        log.info("---------------remove----------------");
        log.info("tno : " + tno);

        todoService.remove(tno);

        return "redirect:/todo/list";
    }

    // @Valid를 이용해 필요한 내용들을 검증하고 문제가 있는 경우 다시 현재 페이지로
    // 다시 현재 페이지로 이동하면 tno 파라미터가 필요해 redirectAttributes를 이용해
    // addAttribute를 받아 tno의 값을 받음, errors라는 이름으로 BindingResult 모든 에러 전달
    @PostMapping("/modify")
    public String modify(@Valid TodoDTO todoDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            log.info("has errors..........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("tno", todoDTO.getTno());
            return "redirect:/todo/modify";
        }

        log.info(todoDTO);

        todoService.modify(todoDTO);

        return "redirect:/todo/list";
    }
}
