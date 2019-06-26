package cn.dante.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Wp003bookApplication {

	public static void main(String[] args) {
		SpringApplication.run(Wp003bookApplication.class, args);
	}

}
