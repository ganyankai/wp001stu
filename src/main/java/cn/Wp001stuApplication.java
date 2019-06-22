package cn;

import cn.dante.config.MyConfiguration;
import cn.dante.entity.Stu;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;

//@SpringBootApplication
//@MapperScan({"cn.dante.mapper"})
//@EnableFeignClients

//可替代@SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class Wp001stuApplication {

	public static void main(String[] args) {


		SpringApplication.run(Wp001stuApplication.class, args);

//		Stu stu = MyConfiguration.getStuIns();
//		System.out.println("stu");
//		System.out.println(stu);


	}

}
