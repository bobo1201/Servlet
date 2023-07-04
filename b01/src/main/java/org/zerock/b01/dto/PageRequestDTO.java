package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    // 문자열 하나로 처리해서 나중에 각 문자를 분리하도록 구성
    private String type; // 검색의 종류 t,c,w,tc,tw,twc

    private String keyword;
    
    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }
        // 타입이라는 문자열을 배열로 반환해 주는 기능
        return type.split("");
    }

    // props라는 배열에 저장, prop1, prop2 ... 등의 문자열이 props로 전달됨
    public Pageable getPageable(String...props){
        return PageRequest.of(this.page -1, this.size, Sort.by(props).descending());
    }

    private String link;

    // 검색 조건과 페이징 조건 등을 문자열로 구성하는 getLink() 추가
    public String getLink(){
        if(link == null){
            StringBuilder builder = new StringBuilder();

            builder.append("page=" + this.page);

            builder.append("&size=" + this.size);

            if(type != null && type.length() > 0){
                builder.append("&type=" + type);
            }

            if(keyword != null){
                try{
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e){
                }
            }
            // builder에 string 경로를 붙여줌
            link = builder().toString();
        }
        return link;
    }
    
}
