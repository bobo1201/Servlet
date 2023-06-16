package org.zerock.w2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
// getter, setter, toString, equals, hashCode 모두 컴파일 가능
// VO는 주로 읽기 위주의 작업만 함
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String mid;
    private String mpw;
    private String mname;
    private String uuid;
}

// DTO → VO, VO → DTO 변환은 ModelMapper 라이브러리 이용
