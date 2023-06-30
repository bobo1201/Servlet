package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IDENTITY : 데이터베이스에 위임(MySQL/MariaDB) - auto_increment
    // SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용(ORACLE) - @SequenceGenerator 필요
    // TABLE : 키 생성용 테이블 사용, 모든 DB에서 사용 - @TableGenerator 필요
    // AUTO : 방언에 따라 자동 지정, 기본 값
    private Long bno;

    @Column(length = 500, nullable = false) // 컬럼의 길이와 null 허용 여부
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}


