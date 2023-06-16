package org.zerock.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.w2.dao.TodoDAO;
import org.zerock.w2.domain.TodoVO;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {

    private TodoDAO todoDAO;

    // 각각의 테스트 메서드 실행 전에 수행되어야 할 설정 또는 초기화 작업
    @BeforeEach
    public void ready(){
        todoDAO = new TodoDAO();
    }


    // getTime()이 정상 동작하는지 확인
    @Test
    public void testTime() throws Exception{

        System.out.println(todoDAO.getTime());
    }

    @Test
    public void testTime2() throws Exception{

        System.out.println(todoDAO.getTime2());
    }

    @Test
    public void testInsert() throws Exception{

        // 빌더 패턴은 생성자와 달리 필요한 만큼 데이터 세팅 가능
        // finish 속성은 false로 기본 지정되어 있음
        // 0 : false
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title...")
                .dueDate(LocalDate.of(2021,12,31))
                .build();

        todoDAO.insert(todoVO);
    }

    @Test
    public void testList() throws Exception{
        List<TodoVO> list = todoDAO.selectAll();

        list.forEach(vo -> System.out.println(vo));

        // 위의 forEach문과 동일함
        // for (TodoVO vo : list) {
        //     System.out.println(vo);
        // }
    }

    @Test
    public void testSelectOne() throws Exception{

        // 반드시 존재하는 번호 사용
        Long tno = 1L;

        TodoVO vo = todoDAO.selectOne(tno);

        System.out.println(vo);
    }

    @Test
    public void testUpdateOne() throws Exception{
        TodoVO todoVO = TodoVO.builder()
                .tno(1L)
                .title("Sample Title...")
                .dueDate(LocalDate.of(2021,12,31))
                .finished(true)
                .build();

        todoDAO.updateOne(todoVO);
    }

    @Test
    public void testDeleteOne() throws Exception{
        Long tno = 6L;

        todoDAO.deleteOne(tno);
    }
}
