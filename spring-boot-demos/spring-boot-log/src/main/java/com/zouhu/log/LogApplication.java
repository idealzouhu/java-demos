package com.zouhu.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogApplication.class, args);

		Slf4jExample example = new Slf4jExample();
		example.example();

		LombokExample lombokExample = new LombokExample();
		lombokExample.example();
	}

}
