package org.zerock.jdbcex.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

// HikariDataSource를 이용하므로 처리를 쉽게 사용할 수 있도록 enum으로 구성
// 변수를 하나만 사용하게 하기 위해서 enum을 사용해서 변수를 하나만 사용할 수 있도록 했습니다.
// enum으로 싱글톤 패턴을 이용할 수 있도록 만듭니다!
public enum ConnectionUtil {
    INSTANCE;

    private HikariDataSource ds;

    ConnectionUtil(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/webdb");
        config.setUsername("root");
        config.setPassword("1234");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws Exception{
        return ds.getConnection();
    }
}
