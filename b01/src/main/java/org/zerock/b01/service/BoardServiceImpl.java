package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.*;
import org.zerock.b01.repository.BoardRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
// 스프링은 해당 객체를 감싸는 별도의 클래스를 생성해 내는데 여러 번의 데이터 연결 방지
@Transactional
public class BoardServiceImpl implements BoardService {

    private final ModelMapper modelMapper;

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO boardDTO){

        // Board board = modelMapper.map(boardDTO, Board.class);

        // BoardService 인터페이스의 dtoToEntity 추가하면서 수정
        Board board = dtoToEntity(boardDTO);
        
        Long bno = boardRepository.save(board).getBno();
        
        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno){
        
        // Optional<Board> result = boardRepository.findById(bno);

        // board_image까지 조인 처리되는 findByWithImages()를 이용
        // EntityGraph를 이용
        Optional<Board> result = boardRepository.findByIdWithImages(bno);
        
        Board board = result.orElseThrow();

        // BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        // 이미지까지 매핑하기 위해서 BoardService에서 만들어준 entityToDTO 생성
        BoardDTO boardDTO = entityToDTO(board);
        
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO){
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow();

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        // 첨부파일의 처리
        board.clearImages();

        if(boardDTO.getFileNames() != null){
            for(String fileName : boardDTO.getFileNames()){
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            }
        }

        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno){
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());

        // null 이므로 정상 동작하지 않는 상태
        // return null;

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO){
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();

    }

    // Querydsl 처리가 끝난 후에 수정
    @Override
    public PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO){

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardListAllDTO> result = boardRepository.searchWithAll(types, keyword, pageable);

        return PageResponseDTO.<BoardListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }



}
