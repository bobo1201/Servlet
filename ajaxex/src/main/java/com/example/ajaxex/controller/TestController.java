package com.example.ajaxex.controller;

import com.example.ajaxex.vo.TestVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    @GetMapping("/test/ajax")
    public void testGet(){
    }

    @PostMapping("/test/ajax")
    @ResponseBody
    public Map<String, Object> testAjax(TestVo testVo){
        Map<String, Object> result = new HashMap<String, Object>();

        // 홍길동
        System.out.println(testVo.getName());

        // 남자
        System.out.println(testVo.getSex());

        // 20
        System.out.println(testVo.getAge());

        // 010-0000-0000
        System.out.println(testVo.getTellPh());

        // 응답 데이터 셋팅
        result.put("code", "0000");
        result.put("name", testVo.getName());
        result.put("sex", testVo.getSex());
        result.put("age", testVo.getAge());
        result.put("tellPh", testVo.getTellPh());

        return result;
    }

}
