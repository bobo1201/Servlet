package org.zerock.api01.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 일반 웹과 달리 Access Key를 발급받을 때 자신의 mid와 mpw를 이용하므로 다른 정보들 없이 구성
// 스프링 시큐리티에서 사용하는 권한으로 예제에서 'ROLE_USER'를 ㅊ추가해서 사용할 것
public class APIUser {

    @Id
    private String mid;

    private String mpw;

    public void changePw(String mpw){
        this.mpw = mpw;
    }
}
