package cn.dante.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//		(exclude=
//		{DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
//@EnableEurekaClient

public class Wp010Application {

	public static void main(String[] args) {
		SpringApplication.run(Wp010Application.class, args);
	}

}
