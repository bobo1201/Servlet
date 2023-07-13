package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

        // 원래 있던 첨부파일 3개 모두 삭제 후 1개 새로 생성
        // 첨부파일을 하나 추가
        boardDTO.setFileNames(Arrays.asList(UUID.randomUUID()+"_zzz.jpg"));

        boardService.modify(boardDTO);
    }

    @Test
    public void testList(){
        // 제목 혹은 내용 혹은 작성자가 '1'이라는 문자열을 가진 데이터를 검색하고 페이징 처리함
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);
    }

    @Test
    public void testRegisterWithImages(){
        log.info(boardService.getClass().getName());

        // 하나의 게시물에 3개의 이미지 파일 추가
        BoardDTO boardDTO = BoardDTO.builder()
                .title("file...Sample Title...")
                .content("Sample Content...")
                .writer("user00")
                .build();

        boardDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID()+"_aaa.jpg",
                        UUID.randomUUID()+"_bbb.jpg",
                        UUID.randomUUID()+"_ccc.jpg"
                ));

        Long bno = boardService.register(boardDTO);

        log.info("bno : " + bno);
    }

    @Test

    public void testReadAll(){
        Long bno = 101L;

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        for(String fileName : boardDTO.getFileNames()){
            log.info(fileName);
        } // end for
    }

    // board_image 테이블에서 3번의 delete가 실행되고 board 테이블의 게시글이 삭제 됨
    @Test
    public void testRemoveAll(){

        Long bno = 1L;

        boardService.remove(bno);
    }

    @Test
    public void testListWithAll(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardListAllDTO> responseDTO = boardService.listWithAll(pageRequestDTO);

        List<BoardListAllDTO> dtoList = responseDTO.getDtoList();

        dtoList.forEach(boardListAllDTO -> {
            log.info(boardListAllDTO.getBno()+":"+boardListAllDTO.getTitle());

            if(boardListAllDTO.getBoardImages() != null){
                for(BoardImageDTO boardImage : boardListAllDTO.getBoardImages()){
                    log.info(boardImage);
                }
            }

            log.info("----------------------------------");
        });


    }


}
