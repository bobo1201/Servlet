package org.zerock.b01.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
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

    
    // Board에서 BoardImage에 대한 참조를 가지는 방식으로 구조 작성
    // 양방향 : bidirectiona
    // Board 엔티티 모든 상태 변화에 BoardImage 객체들 역시 같이 변경됨
    // Board에서 BoardImage 객체들을 모두 관리
    @OneToMany(mappedBy = "board",
                cascade = {CascadeType.ALL},
                fetch = FetchType.LAZY,
                // 아래의 코드가 있어야 실제 삭제가 이루어짐
                orphanRemoval = true) // BoardImage의 board 변수
    @Builder.Default
    // 해당 사이즈만큼 한 번에 in 조건으로 사용
    @BatchSize(size = 20)
    private Set<BoardImage> imageSet = new HashSet<>();

    public void addImage(String uuid, String fileName){

        // 양방향이므로 참조 관계가 서로 일치하도록 작성해야 함
        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();

        imageSet.add(boardImage);
    }

    // 첨부파일 모두 삭제
    public void clearImages(){
        
        // Board 참조를 null로 변경하게 함
        // 항상 상위 엔티티의 상태와 하위 엔티티의 상태를 맞추는 것이 좋음
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));

        this.imageSet.clear();
    }

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}


