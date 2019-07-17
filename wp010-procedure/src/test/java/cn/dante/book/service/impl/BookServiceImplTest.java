package cn.dante.book.service.impl;

import cn.dante.book.Wp010Application;
import cn.dante.book.service.BookService;
import cn.dante.book.util.PageData;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Wp010Application.class)
public class BookServiceImplTest {
    @Autowired
    private BookService bookService;

    @Test
    public void page() {
        PageData page = bookService.page();
        System.out.println(page);
    }

}