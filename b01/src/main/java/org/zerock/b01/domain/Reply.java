package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 참조하는 객체를 사용하지 않도록 exclude
@ToString
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    // @ManyToOne과 같이 연관 관계를 나타낼 때는 반드시 fetch 속성은 Lazy로 지정
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;

    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }
}
