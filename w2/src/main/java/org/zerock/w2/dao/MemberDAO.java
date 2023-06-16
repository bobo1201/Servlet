package org.zerock.w2.dao;

import lombok.Cleanup;
import org.zerock.w2.domain.MemberVO;
import org.zerock.w2.domain.TodoVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public MemberVO getWithPassword(String mid, String mpw) throws Exception{
        String query = "select mid, mpw, mname from tbl_member where mid = ? and mpw = ?";

        MemberVO memberVO = null;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, mid);
        preparedStatement.setString(2, mpw);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        // resultSet에 저장된 값들을 하나씩 불러옴
        resultSet.next();

        memberVO = MemberVO.builder()
                .mid(resultSet.getString(1))
                .mpw(resultSet.getString(2))
                .mname(resultSet.getString(3))
                .build();

        return memberVO;
    }

    public void updateUuid(String mid, String uuid) throws Exception{
        String sql = "update tbl_member set uuid =? where mid = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, uuid);
        preparedStatement.setString(2, mid);

        preparedStatement.executeUpdate();
    }

     // 208 페이지 추가  UUID 찾기
    public MemberVO selectUUID(String uuid) throws Exception{
        String query = "selcet mid, mpw, mname, uuid from tbl_member where uuid = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, uuid);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        MemberVO memberVO = MemberVO.builder()
                .mid(resultSet.getString(1))
                .mpw(resultSet.getString(2))
                .mname(resultSet.getString(3))
                .uuid(resultSet.getString(4))
                .build();

        return memberVO;

    }
}

// try() 내에 선언된 변수들이 자동으로 close() 될 수 있는 구조로 작성