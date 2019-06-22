package cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;

//@SpringBootApplication
//@MapperScan({"cn.dante.mapper"})
//@EnableFeignClients

//可替代@SpringBootApplication
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan

@EnableEurekaServer
@SpringBootApplication
public class Wp005Application {

	public static void main(String[] args) {

		SpringApplication.run(Wp005Application.class, args);

	}

}
