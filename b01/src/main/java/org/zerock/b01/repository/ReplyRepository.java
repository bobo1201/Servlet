package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.board.bno = :bno")
    // 책에는 java 8버전이라 @Param을 안넣어도 되지만 이 프로젝트는 java11(java8+)이므로 @Param(bno)를 지정해야함
    // security 넣었을 때만 @Param 넣어주면됨 나머지는 상관없음
    Page<Reply> listOfBoard(@Param("bno") Long bno, Pageable pageable);
    
    // 댓글 삭제 메소드
    void deleteByBoard_Bno(Long bno);
}
