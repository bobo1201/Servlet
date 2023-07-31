package org.zerock.api01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CustomServletConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // sample.html을 호출하면 정상적으로 처리되지 못한 에러 화면을 봄
        // Thymeleaf 가 없는 환경에서 스프링 MVC의 모든 경로를 스프링에서 처리하려고 시도하기 때문
        // 중간에 경로를 걸어주면 '/files/sample.html'을 호출했을 때 정상적인 화면이 나타남
        // 'Sample Client'
        registry.addResourceHandler("/files/**")
                .addResourceLocations("classpath:/static/");
    }
}