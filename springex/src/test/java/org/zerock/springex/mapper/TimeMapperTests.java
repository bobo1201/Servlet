package org.zerock.springex.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class TimeMapperTests {

    //  required 속성 지정, 해당 객체를 주입 받지 못하더라도 예외가 발생하지 않음
    @Autowired(required = false)
    private TimeMapper timeMapper;

    // MyBatis와 스프링을 연동하고 매퍼 인터페이스를 활용하는 방식은 개발자가 실제 동작하는 클래스와 객체를 생성하지 않고
    // 스프링에서 자동으로 생성되는 방식을 이용하게 됨.
    // 자동으로 생성된 객체를 이용하므로 개발자가 직접 코드를 수정할 수 없지만 인터페이스만으로도 개발 완료할 수 있음
    @Test
    public void testGetTime(){
        log.info(timeMapper.getTime());
    }

    @Autowired(required = false)
    private TimeMapper2 timeMapper2;

    @Test
    public void testNow(){
        log.info(timeMapper2.getNow());
    }
    // 과거에는 MyBatis를 단독으로 이용하는 경우 간혹 있었지만 최근 프로젝트에서는 스프링과 MyBatis의 연동은
    // 기본 설정처럼 사용되고 있으므로 반드시 문제가 없는지 확인해야함

    // 스프링과 MyBatis 연동을 통해 DB 처리 가능하다면 Web 영역을 스프링으로 처리하는 방법 보기
}
