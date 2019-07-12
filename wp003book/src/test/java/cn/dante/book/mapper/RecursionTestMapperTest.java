package cn.dante.book.mapper;

import cn.dante.book.Wp003bookApplication;
import cn.dante.book.entity.RecursionTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Wp003bookApplication.class)
public class RecursionTestMapperTest {
    @Autowired
    private RecursionTestMapper recursionTestMapper;

//    private Integer count = 0;
    //获得所有的子孙节点数量
    public Integer getAllSonCount(Integer id){
        List<Map<String, Object>> mapList = recursionTestMapper.getSonIdandCount(id);
        Integer count = 0;
        if (mapList!=null && mapList.size()>0){
//            count= mapList.size();
            for (Map map:
                    mapList) {
                count++;
                System.out.println("count:"+count);
                System.out.println(map.get("id")+":"+map.get("count"));
//                recursionTestMapper.getSonIdandCount((Integer) map.get("id"));
//                count = getAllSonCount((Integer)map.get("id"));
                //巧妙利用返回值来保存变量,递归的关键在于搞清楚主要方法的参数和返回值,可以先把主要方法结构
                //补充完整
                count += getAllSonCount((Integer)map.get("id"));
            }
//            return count;
        }
//        else{
//            return count;
//        }

        return count;
    }

    @Test
    public void test8(){
        System.out.println("total:"+getAllSonCount(1));

    }

    @Test
    public void test7(){
        System.out.println("total:");
        System.out.println("total:"+getAllSonCount(1));

    }

    @Test
    public void test6() {
//        Integer id = recursionTestMapper.getParentId(5);
//        System.out.println(id);
        List<Map<String, Object>> mapList = recursionTestMapper.getSonIdandCount(4);
        if (mapList!=null && mapList.size()>0){
            for (Map map:
                    mapList) {
                System.out.println(map.get("id")+":"+map.get("count"));
                recursionTestMapper.getSonIdandCount((Integer) map.get("id"));
            }
        }
//        Map<Integer, Integer> map = recursionTestMapper.getSonIdandCount(4);

        System.out.println(mapList);
    }

    //获得所有的父和爷爷节点
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