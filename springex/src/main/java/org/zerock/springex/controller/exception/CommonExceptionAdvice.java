package org.zerock.springex.controller.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;


@ControllerAdvice
// @ExceptionHandler라는 어노테이션을 사용할 수 있음
// @ControllerAdvice 동작을 확인하기 위해 고의로 예외 발생하는 코드를 SampleController에 추가
@Log4j2
public class CommonExceptionAdvice {

    // 만들어진 문자열을 그대로 브라우저에 전송
    // 문자열이나 JSON 데이터를 그대로 전송할 때 사용되는 어노테이션
    @ResponseBody
    // NumberFormatException 예외가 발생하면 'NUMBER FORMAT EXCEPTION' 해당 문자가 화면에 출력됨
    // @ExceptionHandler를 가진 모든 메소드는 해당 타입의 예외를 파라미터로 전달받을 수 있음
    @ExceptionHandler(NumberFormatException.class)
    public String exceptNumber(NumberFormatException numberFormatException){
        log.error("----------------------------------");
        log.error(numberFormatException.getMessage());
        
        return "NUMBER FORMAT EXCEPTION";
    }

    @ResponseBody
    // 어디서 문제가 발생했는지 자세한 메시지를 확인하고 싶을 때 Exception 타입을 처리하도록 구성
    @ExceptionHandler(Exception.class)
    public String exceptCommon(Exception exception){
        log.error("----------------------------------");
        log.error(exception.getMessage());

        StringBuffer buffer = new StringBuffer("<ul>");

        buffer.append("<li>" + exception.getMessage() + "</li>");

        Arrays.stream(exception.getStackTrace()).forEach(stackTraceElement -> {
            buffer.append("<li>" + stackTraceElement + "</li>");
        });
        buffer.append("</ul>");

        return buffer.toString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(){
        return "custom404";
    }
}

