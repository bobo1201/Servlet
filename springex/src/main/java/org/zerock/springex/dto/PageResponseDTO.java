package org.zerock.springex.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

// TodoMapper에서 TodoVO의 목록과 전체 데이터 수를 가져온다면 이를 서비스 계층에서
// 한 번에 담아 처리하도록 DTO 구성
// PageResponseDTO라는 이름으로 생성하고 아래와 같은 데이터 기능 가지도록 구성
// TodoDTO 목록, 전체 데이터 수, 번호 처리 위한 데이터(시작 번호, 끝 번호)
// PageResponseDTO 제네릭을 이용해서 설계
// 게시판이나 회원 정보 등도 페이징 처리가 필요할 수 있으며 공통적으로 처리하기 위해 제네릭으로 구성
@Getter
@ToString
public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    // 시작 페이지 번호
    private int start;
    // 끝 페이지 번호
    private int end;

    // 이전 페이지 존재 여부
    private boolean prev;
    // 다음 페이지 존재 여부
    private boolean next;
    
    private List<E> dtoList;

    // 여러 정보를 생성자를 이용해 받아서 처리하는 것이 안전
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page/10.0)) * 10;

        this.start = end - 9;

        int last = (int)(Math.ceil(total/(double)size));

        this.end = end > last ? last : end;

        this.prev = this.start > 1;

        this.next = total > this.end * this.size;
    }
}
