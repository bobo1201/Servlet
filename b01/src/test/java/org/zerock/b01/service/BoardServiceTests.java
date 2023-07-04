package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.BoardDTO;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        // o.zerock.b01.service.BoardServiceTests   : org.zerock.b01.service.BoardServiceImpl$$EnhancerBySpringCGLIB$$80e22977
        // 실제 boardService 변수가 가르키는 객체의 클래스명 출력
        // BoardServiceImpl이 나오지 않고 스프링에서 BoardServiceImpl을 감싸서 만든 정보가 출력됨
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user00")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno : " + bno);
    }

    @Test
    public void testModify(){

        // 변경에 필요한 데이터만
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("Update....101")
                .content("Update content 101...")
                .build();

        boardService.modify(boardDTO);
    }
}
