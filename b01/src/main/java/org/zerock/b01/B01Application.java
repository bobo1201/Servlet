package org.zerock.b01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication를 실행하면 자동으로 웹 서버가 실행(Apache Tomcat)
@SpringBootApplication
public class B01Application {

    public static void main(String[] args) {
        SpringApplication.run(B01Application.class, args);
    }

}
