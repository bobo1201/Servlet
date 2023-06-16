package org.zerock.jdbcex.dao;

import lombok.Cleanup;
import org.zerock.jdbcex.domain.TodoVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    public String getTime(){
        String now = null;

        // getConnection : 데이터베이스 연결을 제공하는 객체를 반환함
        try(Connection connection = ConnectionUtil.INSTANCE.getConnection();
            // SQL 문장을 준비
            PreparedStatement preparedStatement = connection.prepareStatement("select now()");
            ResultSet resultSet = preparedStatement.executeQuery();
        ){
            // 결과 집합의 첫 번째 행으로 이동
            resultSet.next();

            // 현재 행의 첫 번째 열의 값을 문자열로 가져옴, now() 함수에 의해 반환된 현재 시간을 가져오는 것
            now = resultSet.getString(1);
        } catch (Exception e){
            e.printStackTrace();
        }
        return now;
    }

    // 위의 코드에서 간결해진 것을 볼 수 있음
    // 최소한의 코드로 close()가 보장되는 코드 작성할 수 있음
    public String getTime2() throws Exception{

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("select now()");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        String now = resultSet.getString(1);
        
        return now;
    }

    // insert()는 파라미터로 입력된 TodoVO 객체의 정보를 이용해 DML(insert/update/delete) 실행
    // executeUpdate() 실행, PreparedStatement는 ?를 이용해 나중에 전달할 데이터를 지정
    // 인덱스 번호가 1부터 시작, set을 3개 지정해야함
    // set은 다양한 타입에 맞춰 값 지정가능하지만 LocalDate 타입을 지원하지 않음(java.sql.Date 타입을 이용해 변환)
    public void insert(TodoVO vo) throws Exception{
        String sql = "insert into tbl_todo (title, dueDate, finished) values (?, ?, ?)";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, vo.getTitle());
        preparedStatement.setDate(2, Date.valueOf(vo.getDueDate()));
        preparedStatement.setBoolean(3, vo.isFinished());

        preparedStatement.executeUpdate();
    }
    
    // TodoDAO를 이용해 tbl_todo 내의 모든 데이터를 가져오는 기능 구현
    public List<TodoVO> selectAll() throws Exception{
        String sql = "select * from tbl_todo";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<TodoVO> list = new ArrayList<>();

        while(resultSet.next()){
            TodoVO vo = TodoVO.builder()
                    .tno(resultSet.getLong("tno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished"))
                    .build();

            // 각 행을 하나씩 가져온 후 list로 저장
            list.add(vo);
        }
        return list;
    }

    // 쿼리문을 실행하기 때문에 ResultSet이 필요
    public TodoVO selectOne(Long tno) throws Exception{
        String sql = "select * from tbl_todo where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1, tno);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        TodoVO vo = TodoVO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished"))
                .build();

        return vo;
    }

    public void deleteOne(Long tno) throws Exception{
        String sql = "delete from tbl_todo where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1, tno);

        preparedStatement.executeUpdate();
    }

    public void updateOne(TodoVO todoVO) throws Exception{
        String sql = "update tbl_todo set title=?, dueDate=?, finished=? where tno=?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);


        preparedStatement.setString(1, todoVO.getTitle());
        preparedStatement.setDate(2, Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3, todoVO.isFinished());
        preparedStatement.setLong(4, todoVO.getTno());

        preparedStatement.executeUpdate();
    }
}

// try() 내에 선언된 변수들이 자동으로 close() 될 수 있는 구조로 작성