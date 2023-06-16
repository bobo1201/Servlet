package org.zerock.w2.domain;


import lombok.*;

import java.time.LocalDate;

@Getter  // 읽기 전용으로 많이 사용하므로 추가
@Builder // 객체 생성 시에 빌더 패턴을 이용하기 위함
// TodoVO.builder().build()와 같은 형태로 객체 생성 가능
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TodoVO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}
