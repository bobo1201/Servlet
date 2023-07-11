package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    // 제목에 특정 키워드가 존재하는 게시글들을 bno의 역순으로 정렬해서 가져옴
    // 단순 쿼리를 작성할 때 많이 사용하고 실제 개발에서는 많이 사용되지 않음
//    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);
  
    // 위의 쿼리문과 똑같음
    // @Query("select b from Board b where b.title like concat('%', :keywore, '%')")
    // Page<Board> findKeyword(String keyword, Pageable pageable);
    
    // native 속성 지정하는 예제
//    @Query(value = "select now()", nativeQuery = true)
//    String getTime();

    // 지연 로딩이라도 한 번에 조인해서 select가 이루어지도록 하는 방법
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board b where b.bno = :bno")
    Optional<Board> findByIdWithImages(Long bno);

}
