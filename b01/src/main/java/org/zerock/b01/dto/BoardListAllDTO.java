package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// boardImage, 댓글 개수 모두 처리하는 DTO
public class BoardListAllDTO {
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private Long replyCount;

    private List<BoardImageDTO> boardImages;
}