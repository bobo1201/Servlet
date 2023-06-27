package org.zerock.springex.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Arrays;


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
    
    // 필터링 및 검색을 위한 변수 추가
    private String[] types;

    private String keyword;

    private boolean finished;

    private LocalDate from;

    private LocalDate to;


    // 변수는 따로 만들지 않았지만 todoMapper에 skip이라는 변수 사용가능(쿼리문)
    public int getSkip(){
        return (page - 1) * 10;
    }
    

    // get 방식으로 페이지 이동에 필요한 링크들을 생성
    // 검색 조건들 추가
    // 검색 기능이 추가되면 조회나 수정 화면에 있는 'List' 버튼이 문제됨.
    // 검색 조건들을 그대로 유지해야함
    // getLink가 수정되면서 화면에서 '/todo/readd?tno=xxx'와 같은 링크가 자동으로 모든
    // 검색조건을 반영하게 수정된 것을 확인할 수 있음
    public String getLink(){
//        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);
//            link = builder.toString();
//        }

//        return link;

        if(finished){
            builder.append("&finished=on");
        }

        if(types != null && types.length > 0){
            for(int i=0; i<types.length; i++){
                builder.append("&types=" + types[i]);
            }
        }

        if(keyword != null){
            try{
                // keyword부분은 URLEncoder를 이용해 링크로 처리할 수 있어야함
                builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }

        if(from != null){
            builder.append("&from=" + from.toString());
        }

        if(to != null){
            builder.append("&to=" + to.toString());
        }

        return builder.toString();
    }
    
    public boolean checkType(String type){
        // 배열 안에 아무 것도 없을 때의 조건 확인
        if(types == null || types.length == 0){
            return false;
        }

        // stream 요소 중 type과 동일한 값이 있는지 확인하는 조건
        // type::equals는 람다 표현식, 각 스트림 요소를 type과 비교해 equals 메소드로 동등성 검사
        // boolean으로 값 반환
        return Arrays.stream(types).anyMatch(type::equals);
    }
}
