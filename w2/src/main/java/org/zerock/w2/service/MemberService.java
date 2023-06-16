package org.zerock.w2.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.zerock.w2.dao.MemberDAO;
import org.zerock.w2.dao.TodoDAO;
import org.zerock.w2.domain.MemberVO;
import org.zerock.w2.domain.TodoVO;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.dto.TodoDTO;
import org.zerock.w2.util.MapperUtil;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
// HikariCP의 로그 역시 다르게 출력됨
// HikariCP가 내부적으로 slf4j 라이브러리 이용하고 있음
// log4j-slf4j-impl 라이브러리가 Log4j2를 이용할 수 있도록 설정됨
public enum MemberService {
    INSTANCE;

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService(){
        dao = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    public MemberDTO login(String mid, String mpw) throws Exception{
        MemberVO vo = dao.getWithPassword(mid, mpw);

        MemberDTO memberDTO = modelMapper.map(vo, MemberDTO.class);

        return memberDTO;
    }

    public void updateUuid(String mid, String uuid) throws Exception{
        dao.updateUuid(mid, uuid);
    }

    // 209 페이지 추가
    // uuid 값으로 사용자 찾을 수 있도록 함
    public MemberDTO getByUUID(String uuid) throws Exception{
        MemberVO vo = dao.selectUUID(uuid);

        MemberDTO memberDTO = modelMapper.map(vo, MemberDTO.class);

        return memberDTO;
    }
}
