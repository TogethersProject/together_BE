package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(
        basePackages = {"com.together.board.*"
                        ,"com.together.common.utils"//공통 유틸리티: 암호화, 파일 처리 등
                        ,"spring.conf"}
)
@EntityScan({"com.together.board.bean"})
@EnableJpaRepositories({"com.together.board.DAO"})
@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
