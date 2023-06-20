package org.zerock.springex.sample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

// SampleService에 필요한 SampleDAO 타입의 빈이 두 개가 되므로 스프링은 어떤 것을 주입해야할지 모름
// SampleDAO 타입의 객체가 하나이길 기대했지만 2개가 발견되는 오류 발생
@Repository
@Qualifier("event")
// 두 클래스 중 하나를 @Primary로 지정하면 에러 해결 가능, 먼저 사용하고 싶은 것을 Primary로 지정함
public class EventSampleDAOImpl implements SampleDAO {
}
