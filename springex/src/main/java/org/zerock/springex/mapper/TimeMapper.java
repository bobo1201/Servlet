package org.zerock.springex.mapper;

import org.apache.ibatis.annotations.Select;

// MyBatis는 SQL 파일을 별도 처리 가능하지만 인터페이스와 어노테이션만으로도 처리 가능
// TimeMapper는 데이터베이스의 현재 시각을 문자열로 처리하도록 구성
// 작성된 인터페이스는 매퍼 인터페이스인데 마지막으로 어떤 매퍼 인터페이스를 설정했는지 root-context.xml에 등록해야 함
public interface TimeMapper {
    @Select("select now()")
    String getTime();
}
