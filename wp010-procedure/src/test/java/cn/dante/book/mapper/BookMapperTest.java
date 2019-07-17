package cn.dante.book.mapper;

import cn.dante.book.Wp010Application;
import cn.dante.book.entity.Book;
import cn.dante.book.util.PageData;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Wp010Application.class)
public class BookMapperTest {
    @Autowired
    private BookMapper mapper;

    @Test
    public void getBook() {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(1, 1);
        List<Book> list = mapper.getBook(1);
        PageData<Book> pageData = new PageData<>(page.getPageSize(), page.getPageNum(), page.getTotal(), list);
        System.out.println(pageData);
    }
}