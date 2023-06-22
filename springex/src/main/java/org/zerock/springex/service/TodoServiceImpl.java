package org.zerock.springex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.springex.domain.TodoVO;
import org.zerock.springex.dto.TodoDTO;
import org.zerock.springex.mapper.TodoMapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    // final로 고정하고 @RequiredArgsConstructor를 통해 생성자 생성
    private final TodoMapper todoMapper;

    private final ModelMapper modelMapper;

    // 주입된 ModelMapper를 이용해 TodoDTO를 TodoVO로 변환하고
    // TodoMapper를 통해 insert 처리함
    // service 패키지를 root-context.xml, component-scan 패키지로 추가
    @Override
    public void register(TodoDTO todoDTO){
        log.info(modelMapper);

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);

        log.info(todoVO);
        
        todoMapper.insert(todoVO);
    }

    @Override
    public List<TodoDTO> getAll(){
        
        
        // stream을 이용해 TodoVO는 map()을 통해 TodoDTO로 바꾸고 collect()를 이용해 List<TodoDTO>로 묶어줌
        List<TodoDTO> dtoList = todoMapper.selectAll().stream()
                .map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());

        return dtoList;

    }
}
