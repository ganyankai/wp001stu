package cn.dante.book.service.impl;

import cn.dante.book.entity.Book;
import cn.dante.book.mapper.BookMapper;
import cn.dante.book.repository.BookRepository;
import cn.dante.book.service.BookService;
import cn.dante.book.util.PageData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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


    public PageData page() {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(1, 1);
        List<Book> list = bookMapper.list();
        PageData<Book> pageData = new PageData<>(page.getPageSize(), page.getPageNum(), page.getTotal(), list);
        return pageData;
    }

}
