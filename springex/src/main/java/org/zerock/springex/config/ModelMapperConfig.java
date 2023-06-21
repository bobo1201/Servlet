package org.zerock.springex.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// DTO, VO를 서로 변환, ModelMapper를 스프링의 빈으로 등록해서 처리
// ModelMapperConfig는 MapperUtil 클래스를 스프링 버전으로 변경한 버전으로
// @Configuration를 이용, 해당 클래스가 스프링 빈에 대한 설정을 하는 클래스임
@Configuration
public class ModelMapperConfig {

    // 해당 메소드의 실행 결과로 반환된 객체를 스프링의 빈으로 등록시키는 역할을 함
    // XML 설정 이외에 Java 설정 이용하는 경우가 늘고 있음 @Configuration, @Bean
    @Bean
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
        
        // 해당 클래스를 Bean으로 인식하도록 root-context.xml에 config 패키지 추가
    }
}
