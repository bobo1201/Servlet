package org.zerock.springex.sample;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}
