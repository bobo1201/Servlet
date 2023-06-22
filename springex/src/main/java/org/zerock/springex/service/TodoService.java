package org.zerock.springex.service;

import org.zerock.springex.dto.TodoDTO;

import java.util.List;

// TodoMapper와 TodoController 사이에 서비스 계층을 설계해서 적용
// TodoService 인터페이스를 먼저 추가하고, TodoServiceImpl을 스프링 빈으로 처리
public interface TodoService {
    // 여러 개의 파라미터 대신 TodoDTO로 묶어서 전달 받음
    void register(TodoDTO todoDTO);

    List<TodoDTO> getAll();
}

// 308p까지 완료
