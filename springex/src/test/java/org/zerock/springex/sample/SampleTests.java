package org.zerock.springex.sample;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;

@Log4j2
// JUnit5 버전에서 'spring-test'를 이용하기 위한 설정
// JUnit4 버전에서는 @Runwith 사용
@ExtendWith(SpringExtension.class)
// 스프링의 설정 정보를 로딩하기 위해 사용
// xml로 설정되어 있으므로 @ContextConfiguration의 locations 속성 이용, 자바 설정의 경우 classes 이용
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class SampleTests {

    // 스프링에서 사용하는 의존성 주입 관련 어노테이션으로 만일 해당 타입의 빈이 존재하면 여기에 주입해 주길 원한다는 어노테이션
    @Autowired
    private SampleService sampleService;

    // root-context.xml에 선언된 HikariCP를 주입 받기 위해 DataSource 타입 변수 선언 및 객체 주입
    @Autowired
    private DataSource dataSource;

    @Test
    public void testService1(){
        log.info(sampleService);
        Assertions.assertNotNull(sampleService);
    }

    // 테스트 성공시
    // 03:47:46  INFO [org.springframework.test.context.support.DefaultTestContextBootstrapper] Loaded default TestExecutionListener
    // class names from location [META-INF/spring.factories]: [org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.event.ApplicationEventsTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
    // 03:47:46  INFO [org.springframework.test.context.support.DefaultTestContextBootstrapper] Using TestExecutionListeners: [org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@41a6d121, org.springframework.test.context.event.ApplicationEventsTestExecutionListener@4f449e8f, org.springframework.test.context.support.DependencyInjectionTestExecutionListener@411291e5, org.springframework.test.context.support.DirtiesContextTestExecutionListener@6e28bb87, org.springframework.test.context.event.EventPublishingTestExecutionListener@19f040ba]
    // 03:47:46  INFO [org.zerock.springex.sample.SampleTests] org.zerock.springex.sample.SampleService@2125ad3
    // BUILD SUCCESSFUL in 1s

    // 테스트 실패시 (Service를 root에 주입하지 않았을 시)
    // SampleService 타입의 객체를 주입하려고 하지만 해당 타입의 객체가 스프링 내에 등록된 것이 없다는 메시지가 출력됨
    // org.springframework.beans.factory.UnsatisfiedDependencyException : Error creating bean with name 'org.zerock.springex.sample.SampleService
    
    // INFO [org.zerock.springex.sample.SampleTests] SampleService(sampleDAO=org.zerock.springex.sample.SampleDAO@7a5b769b)
    // SampleService에 SampleDAO 객체가 주입됨

    
    // HikariDataSource는 javax.sql의 DataSource 인터페이스 구현체이므로 test 코드를 통해 설정 확인 
    @Test
    public void testConnection() throws Exception{
        Connection connection = dataSource.getConnection();
        log.info(connection);
        Assertions.assertNotNull(connection);

        connection.close();
    }

    // 테스트 성공 결과
    // 01:25:36  INFO [org.zerock.springex.sample.SampleTests] HikariProxyConnection@855277727 wrapping com.mysql.cj.jdbc.ConnectionImpl@54067fdc
    // 스프링은 필요한 객체를 스프링에서 주입해 개별적으로 클래스를 작성해 빈으로 등록해두면됨(원하는 곳에서 쉽게 다른 객체 사용 가능)


}
