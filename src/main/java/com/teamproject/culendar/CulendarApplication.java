package com.teamproject.culendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케줄러 사용
@EnableJpaAuditing
@SpringBootApplication
public class CulendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CulendarApplication.class, args);
	}

}
