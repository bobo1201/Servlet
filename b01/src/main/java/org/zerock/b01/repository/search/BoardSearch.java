package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.Board;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);
    
    // 검색 조건과 키워드를 types, keyword로 칭함
    // types는 여러 조건의 조합이 가능하도록 처
    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

}
