package org.zerock.b01.service;

import org.zerock.b01.dto.MemberJoinDTO;

public interface MemberService {
    
    static class MidExistException extends Exception{
        
    }
    
    // MidExistException이라는 예외를 static 클래스로 선언해서 필요한 곳에서 사용함
    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
}
