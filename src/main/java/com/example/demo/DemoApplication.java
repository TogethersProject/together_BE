package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(
        basePackages = {"com.together.board.*"
                ,"com.together.user_calendar.*"
                ,"com.together.common.utils"//공통 유틸리티: 암호화, 파일 처리 등
                ,"spring.conf"}
)
@EntityScan({"com.together.board.bean"
        ,"com.together.user_calendar.bean"})
@EnableJpaRepositories({"com.together.board.DAO"
        ,"com.together.user_calendar.DAO"})
@SpringBootApplication
@EnableScheduling   //일정 시간이 지나면 임시 이미지 저장 폴더 자동 삭제를 위함
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
