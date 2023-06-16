package org.zerock.jdbcex.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.zerock.jdbcex.dao.TodoDAO;
import org.zerock.jdbcex.domain.TodoVO;
import org.zerock.jdbcex.dto.TodoDTO;
import org.zerock.jdbcex.util.MapperUtil;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
// HikariCP의 로그 역시 다르게 출력됨
// HikariCP가 내부적으로 slf4j 라이브러리 이용하고 있음
// log4j-slf4j-impl 라이브러리가 Log4j2를 이용할 수 있도록 설정됨
public enum TodoService {
    INSTANCE;
    
    private TodoDAO dao;
    private ModelMapper modelMapper;
    
    TodoService(){
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }
    
    public void register(TodoDTO todoDTO) throws Exception{
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);

        log.info(todoVO);
//        System.out.println("todoVO: " + todoVO);

        dao.insert(todoVO); // int를 반환하므로 이를 이용해 예외처리 가능
    }

    public List<TodoDTO> listAll() throws Exception{
        List<TodoVO> voList = dao.selectAll();

        log.info("voList................");

        log.info(voList);

        List<TodoDTO> dtoList = voList.stream()
                .map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());

        return dtoList;
    }

    public TodoDTO get(Long tno) throws Exception{
        log.info("tno: " + tno);
        TodoVO todoVO = dao.selectOne(tno);
        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);
        return todoDTO;
    }

    public void remove(Long tno) throws Exception{
        log.info("tno : " + tno);
        dao.deleteOne(tno);
    }

    public void modify(TodoDTO todoDTO) throws Exception{
        log.info("todoDTO : " + todoDTO);

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        
        dao.updateOne(todoVO);
    }
}
