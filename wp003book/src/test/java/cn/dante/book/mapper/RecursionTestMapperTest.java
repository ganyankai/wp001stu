package cn.dante.book.mapper;

import cn.dante.book.Wp003bookApplication;
import cn.dante.book.entity.RecursionTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Wp003bookApplication.class)
public class RecursionTestMapperTest {
    @Autowired
    private RecursionTestMapper recursionTestMapper;

    public Integer getAllParentCount(Integer id){
        Integer count = 0;
//        Integer pid = -1;
        while (id!=0){
            System.out.println(id);
            id = recursionTestMapper.getParentId(id);
//            id = pid;
            if (id==0){
                break;
            }
            count+=1;
        }
        return count;
    }
    @Test
    public void test5() {
       Integer id = recursionTestMapper.getParentId(5);
        System.out.println(id);
    }

    @Test
    public void test4() {
        Integer count = getAllParentCount(5);
        System.out.println(count);
    }



    @Test
    public void test3() {
        Integer count = recursionTestMapper.getSonCount(4);
        System.out.println(count);
    }


    @Test
    public void test2() {
        Integer count = recursionTestMapper.getParentCount(4);
        System.out.println(count);
    }

    @Test
    public void list() {
        List<RecursionTest> list = recursionTestMapper.list();
        System.out.println(list.size());
    }


}