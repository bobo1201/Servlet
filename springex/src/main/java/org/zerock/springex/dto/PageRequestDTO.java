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

    // 변수는 따로 만들지 않았지만 todoMapper에 skip이라는 변수 사용가능(쿼리문)
    public int getSkip(){
        return (page - 1) * 10;
    }

}
