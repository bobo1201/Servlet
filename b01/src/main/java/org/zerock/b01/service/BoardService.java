package org.zerock.b01.service;

import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    // 댓글의 숫자까지 처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);


    // 게시글 이미지와 댓글의 숫자까지 처리(검색과 페이징 둘 다 적용됨)
    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);
    
    // BoardService 인터페이스가 DTO와 엔티티를 모두 처리하는 경우가 많아 
    // BoardService 인터페이스의 default 메소드 이용해서 처리
    // 인터페이스 내에서 default를 사용할 수 있고 메소드의 기본 구현을 제공할 수 있음
    // 기본 구현을 제공하면 클래스에서 이 메소드를 오버라이드하지 않아도 됨
    default Board dtoToEntity(BoardDTO boardDTO){
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();

        if(boardDTO.getFileNames() != null){
            boardDTO.getFileNames().forEach(fileName ->{
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }
        return board;
    }

    default BoardDTO entityToDTO(Board board){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        List<String> fileNames =
                board.getImageSet().stream().sorted().map(boardImage ->
                        boardImage.getUuid()+"_"+boardImage.getFileName()).collect(Collectors.toList());

        boardDTO.setFileNames(fileNames);

        return boardDTO;
    }
}
