package org.zerock.springex.sample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// SampleDAO는 실체가 없어 인터페이스를 구현한 클래스 선언
// @Repository를 이용해 해당 클래스의 객체를 스프링의 빈으로 처리되도록 구성
// SampleService의 입장에서 인터페이스만 보고 있어 SampleDAOImpl의 인스턴스인지는 알 수 없지만 코드 작성에 문제 없음
// 객체의 실체를 몰라도 객체와 객체의 의존 관계를 가능하게 하는 방식이 느슨한 결합(loose coupling)이라고 함
// SampleDAO 객체를 다른 객체로 변경해도 SampleService 타입을 이용하는 코드를 수정할 일이 없음
@Repository
@Qualifier("normal")
public class SampleDAOImpl implements SampleDAO{
}
