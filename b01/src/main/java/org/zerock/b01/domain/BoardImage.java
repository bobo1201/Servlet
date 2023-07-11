package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 엔티티 클래스 작성시에 연관 관계를 적용할 때는 항상 exclude를 이용
@ToString(exclude = "board")
// Comparable 인터페이스를 적용하는데 @OneToMany 처리에서 순번에 맞게 정렬하기 위함
public class BoardImage implements Comparable<BoardImage> {

    @Id
    private String uuid;

    private String fileName;
    
    private int ord;
    
    @ManyToOne
    private Board board;

    @Override
    public int compareTo(BoardImage other){
        return this.ord - other.ord;
    }


    // Board 객체를 나중에 지정할 수 있게 하는데 이것은 나중에 Board 엔티티 삭제시
    // BoardImage 객체의 참조도 변경하기 위해 사용
    public void changeBoard(Board board){
        this.board = board;
    }
}


