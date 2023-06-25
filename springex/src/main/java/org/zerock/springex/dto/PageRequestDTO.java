package org.zerock.springex.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;


// 파라미터로 처리하기 위해 TodoMapper 인터페이스에 selectList()를 추가
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    @Min(value = 1)
    @Positive
    private int page = 1;


    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size = 10;

    // list.jsp의 Todo의 링크 처리 부분 수정하기 위해 링크 변수 생성
    private String link;

    // 변수는 따로 만들지 않았지만 todoMapper에 skip이라는 변수 사용가능(쿼리문)
    public int getSkip(){
        return (page - 1) * 10;
    }
    

    // get 방식으로 페이지 이동에 필요한 링크들을 생성
    public String getLink(){
        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);
            link = builder.toString();
        }
        return link;
    }

}
