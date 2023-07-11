package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.BoardImage;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;
    
    @Test
    public void testInsert(){
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i%10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        });
    }

    @Test
    public void testSelect(){
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.change("update..title 100", "update content 100");

        boardRepository.save(board);
    }

    @Test
    public void testDelete(){
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging(){
        // 1page order by bno desc
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count : " + result.getTotalElements());
        log.info("total page : " + result.getTotalPages());
        log.info("page number : " + result.getNumber());
        log.info("page size : " + result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info(board));
    }

    @Test
    public void testSearch1(){
        // 2 page order by bno desc
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll(){
        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
    }

    @Test
    public void testSearchAll2(){
        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        // total pages
        log.info(result.getTotalPages());

        // page size
        log.info(result.getSize());

        // pageNumber
        log.info(result.getNumber());

        // prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testSearchReplyCount(){
        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        // total pages
        log.info(result.getTotalPages());

        // page size
        log.info(result.getSize());

        // pageNumber
        log.info(result.getNumber());

        // prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    // 게시물 하나에 3개의 첨부파일 추가하는경우를 가정
    // 영속성 전이가 일어나므로 board 테이블에 1번, board_image 테이블에 3번 insert가 일어남
    @Test
    public void testInsertWithImages(){

        Board board = Board.builder()
                .title("Image Test")
                .content("첨부파일테스트")
                .writer("tester")
                .build();

        for(int i=0; i<3; i++){
            board.addImage(UUID.randomUUID().toString(), "file"+i+".jpg");
        } // end for

        boardRepository.save(board);
    }

    // select문이 실행된 후 에러가 발생
    // @Transactional을 이용하면 에러 해결 가능
    // 필요할 때마다 메소드 내에서 추가적인 쿼리를 여러 번 실행하는 것이 가능해짐
    @Test
    @Transactional  // 없다면 board_image를 조회하지 않음
    public void testReadWithImages(){

        // 반드시 존재하는 bno로 확인
        Optional<Board> result = boardRepository.findById(1L);

        Board board = result.orElseThrow();

        log.info(board);
        log.info("------------------");
        // db와 연결이 끝나서 no session이라는 에러가 나타남
        log.info(board.getImageSet());
    }

    @Test
    public void testReadWithImages1(){

        // board와 boardImage를 한 번에 처리할 수 있게 된 것을 확인할 수 있음
        // 반드시 존재하는 bno로 확인
        // OneToMany 구조를 사용하는 경우 얻을 수 있는 장점 중 하나가 하위 엔티티 처리임
        Optional<Board> result = boardRepository.findByIdWithImages(1L);

        Board board = result.orElseThrow();

        log.info(board);
        log.info("------------------");

        for (BoardImage boardImage : board.getImageSet()){
            log.info(boardImage);
        }
    }

    // 상위 엔티티의 상태 변화가 하위 엔티티까지 영향을 주지만 삭제는 안됨
    // 하위 엔티티의 참조가 더 이상 없는 상태가 되면 @OneToMany에 orphanRemoval 속성 값을 true로 지정해야 삭제됨
    // 이전 data는 board_bno가 삭제되고 남아있음
    @Transactional
    @Commit
    @Test
    public void testModifyImages(){
        Optional<Board> result = boardRepository.findByIdWithImages(1L);

        Board board = result.orElseThrow();

        //기존의 첨부파일들은 삭제
        board.clearImages();

        // 새로운 첨부파일들
        for(int i = 0; i<2; i++){
            board.addImage(UUID.randomUUID().toString(), "updatefile"+i+".jpg");
        }

        boardRepository.save(board);
    }
    
    // Reply 엔티티들을 삭제한 후 Board를 삭제함
    @Test
    @Transactional
    @Commit
    public void testRemoveAll(){
        Long bno = 1L;
        replyRepository.deleteByBoard_Bno(bno);
        boardRepository.deleteById(bno);
    }


    @Test
    public void testInsetAll(){
        for(int i = 1; i<=100; i++){
            Board board = Board.builder()
                    .title("Title.."+i)
                    .content("Content.."+i)
                    .writer("writer.."+i)
                    .build();

            for (int j=0; j<3; j++){
                if(i % 5 == 0){
                    continue;
                }
                board.addImage(UUID.randomUUID().toString(), i+"file"+j+".jpg");
            }
            boardRepository.save(board);
        } // end for
    }

    @Transactional
    @Test
    public void testSearchImageReplyCount(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        boardRepository.searchWithAll(null, null, pageable);
    }

}
