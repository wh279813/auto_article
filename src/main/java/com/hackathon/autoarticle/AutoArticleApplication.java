package com.hackathon.autoarticle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan("com.autoarticle.*.dao")
@Configuration

public class AutoArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoArticleApplication.class, args);
    }

}
