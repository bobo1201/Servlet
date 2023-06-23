package org.zerock.springex.controller.formatter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

// 체크박스에서 완료여부 처리, 브라우저가 체크박스 클릭 상태일 때 'on'이라는 값을 전달함
// TodoDTO로 데이터 수집할 때 문자열 'on'을 boolean 타입으로 처리할 수 있어야함
// servet-context도 수정해야함
public class CheckboxFormatter implements Formatter<Boolean> {

    @Override
    public Boolean parse(String text, Locale locale) throws ParseException{
        if(text == null){
            return false;
        }
        return text.equals("on");
    }

    @Override
    public String print(Boolean object, Locale locale){
        return object.toString();
    }
    
}
