package org.zerock.springex.mapper;

import org.apache.ibatis.annotations.Select;

// XML로 SQL 분리하기
// MyBatis 이용시 SQL은 @Select 같은 어노테이션을 사용하지만 대부분 SQL을 별도의 파일로 분리하는 것 권장
// XML을 사용하는 이유는 SQL이 길어지면 이를 어노테이션으로 처리하기가 복잡해지기 떄문이기도 하고 
// 어노테이션이 나중에 변경되면 프로젝트 전체를 다시 빌드하는 작업이 필요함, 단순 파일로 사용하는 것이 편리
// 매퍼 인터페이스 정의하고 메소드 선언, XML 파일 작성(파일명과 인터페이스 이름 같게), <select> 같은 태그를 이용해서 SQL 작성
// <select>, <insert> 등의 태그에 id 속성 값을 매퍼 인터페이스 메소드 이름과 같게 작성
public interface TimeMapper2 {

    // resource → mapper 디렉토리 생성 및 xml 생성
    String getNow();
}
