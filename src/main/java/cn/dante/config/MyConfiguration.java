package cn.dante.config;

import cn.dante.entity.Stu;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class MyConfiguration {
   @Bean
   public static Stu  getStuIns(){
       return new Stu(1,"xm");
   }

}
