package cn.dante.book.service;

import cn.dante.book.entity.Book;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
//@FeignClient(name = "stu")
public interface BookService {
    public List<Book> getList();

//    @GetMapping("/list")  //
//    String getStuList();

}
