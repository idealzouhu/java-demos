package com.zouhu.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class SpringAopApplication {
	public static void main(String[] args) {
		// ApplicationContext context = SpringApplication.run(SpringAopApplication.class, args);
		// TestService testService = context.getBean(TestService.class);
		// System.out.println("Spring AOP 示例");
		// System.out.println("======================================================================================");
		// System.out.println("测试结果：");
		// testService.doSomething();
		// System.out.println("======================================================================================");
		SpringApplication.run(SpringAopApplication.class, args);
	}

}
