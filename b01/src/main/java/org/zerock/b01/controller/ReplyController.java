package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.service.ReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor // 의존성 주입
public class ReplyController {

    private final ReplyService replyService;

    // @ApiOperation은 Swagger UI에서 해당 기능의 설명으로 출력됨
    @ApiOperation(value = "Replies POST", notes = "POST 방식으로 댓글 등록")
    // register() 파라미터에는 ReplyDTO를 통해 파라미터를 수집한다고 되어있음
    // consumes 사용 : 해당 메소드를 받아서 소비하는 데이터가 어떤 종류인지 명시할 수 있음
    // JSON 타입의 데이터 처리하는 메소드임을 명시
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    // @RequestBody는 JSON 문자열을 ReplyDTO로 변환하기 위해 표시
    public Map<String, Long> register(
            @Valid @RequestBody ReplyDTO replyDTO,
            BindingResult bindingResult) throws BindException {

        log.info(replyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        // Map key, value 초기화
        // Map<String, Long> resultMap = Map.of("rno", 111L);

        Map<String, Long> resultMap = new HashMap<>();

        Long rno = replyService.register(replyDTO);

        resultMap.put("rno", rno);

        return resultMap;

        // 수정된 부분
        // 1. ReplyDTO 수집할 때 @Vaild 적용
        // 2. BindingResult를 파라미터로 추가하고 문제 있을 때 BindException을 throw하도록 수정
        // 3. 메소드 선언부에 BindException을 throws하도록 수정
        // 4. 메소드 리턴값에 문제가 있다면 @RestControllerAdvice가 처리할 것이므로 정상적인 결과만 리턴
    }

    // 특정 게시물의 댓글 목록
    @ApiOperation(value = "Replies of Board", notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{bno}")
    // getList()에서 bno 값은 경로에 있는 값을 취해서 사용할 것이므로 @PathVariable 이용
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno,
                                             PageRequestDTO pageRequestDTO){

        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno, pageRequestDTO);

        return responseDTO;
    }

    // 특정 댓글 조회
    @ApiOperation(value = "Read Reply", notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping("/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno){

        ReplyDTO replyDTO = replyService.read(rno);

        return replyDTO;
    }

    @ApiOperation(value = "Delete Reply", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping("/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno){

        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }

    @ApiOperation(value = "Modify Reply", notes = "PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(@PathVariable("rno") Long rno,
                                    @RequestBody ReplyDTO replyDTO){

        replyDTO.setRno(rno);  // 번호를 일치시킴

        replyService.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }


}
