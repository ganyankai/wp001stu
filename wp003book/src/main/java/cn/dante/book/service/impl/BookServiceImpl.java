package cn.dante.book.service.impl;

import cn.dante.book.entity.Book;
import cn.dante.book.mapper.BookMapper;
import cn.dante.book.repository.BookRepository;
import cn.dante.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository stuRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<Book> getList() {

//        return stuRepository.findAll();
        
        return bookMapper.list();
    }

//    @Override
//    public String getStuList() {
//        return null;
//    }


}
