package cn.dante.book.controller;

import cn.dante.book.entity.Book;
import cn.dante.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/list")
    public List<Book> list(){
        return bookService.getList();
    }

}
