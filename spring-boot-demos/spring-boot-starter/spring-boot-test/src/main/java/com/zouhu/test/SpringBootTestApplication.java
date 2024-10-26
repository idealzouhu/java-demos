package com.zouhu.test;

import com.zouhu.email.MailService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTestApplication {
	@Autowired
	private MailService mailService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestApplication.class, args);
	}

	@PostConstruct	// 表明该方法需要在完成字段的注入后由容器自动调用
	public void sendTestMail() {
		mailService.sendMail("to@example.com", "Test Subject", "This is a test email.");
	}
}
