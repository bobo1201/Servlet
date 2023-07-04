package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable){

        QBoard board = QBoard.board; // Q도메인 객체

        JPQLQuery<Board> query = from(board); // select.. from board

        query.where(board.title.contains("1")); // where title like ...

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch(); // 쿼리 실행

        // count 실행
        long count = query.fetchCount();

        return null;
    }
    
    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable){
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);
        
        // 검색 조건과 키워드가 있다면
        if((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for (String type: types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            } // end for
            query.where(booleanBuilder);
        } // end if

        // bno > 0
        query.where(board.bno.gt(0L));

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();


        // 아래의 쿼리문과 같음
        // select * from board
        // where (title like '%1%' or content like '%1%' or writer like '%1%')
        // and bno > 0
        // order by bno desc limit 10;
        

        // return에 페이징 처리 결과 반환
        // List<T> : 실제 목록 데이터
        // Pageable : 페이지 관련 정보를 가진 객체
        // long : 전체 개수

        return new PageImpl<>(list, pageable, count); 
    }



}
