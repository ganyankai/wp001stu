package com.zrytech.mytest;

import com.github.pagehelper.PageInfo;
import com.zrytech.framework.BaseApplication;
import com.zrytech.framework.app.dao.FamiliarCarDao;
import com.zrytech.framework.app.entity.FamiliarCar;
import com.zrytech.framework.app.entity.OfenLocation;
import com.zrytech.framework.app.mapper.FamiliarCarMapper;
import com.zrytech.framework.app.repository.FamiliarCarRepository;
import com.zrytech.framework.app.repository.OfenLocationRepository;
import com.zrytech.framework.base.entity.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= BaseApplication.class)
public class FamiliarCarTest {
    @Autowired
    private FamiliarCarRepository familiarCarRepository;

    @Autowired
    private FamiliarCarMapper familiarCarMapper;

    @Autowired
    private FamiliarCarDao familiarCarDao;

    //ok
    @Test
    public void test1(){
        List<FamiliarCar> list = familiarCarRepository.findAll();
        System.out.println(list.size());
    }

    //ok
    @Test
    public void test2(){
        PageInfo<FamiliarCar> pageSystemfo = familiarCarMapper.familiarCarPage(new FamiliarCar(), new Page());
        System.out.println(pageSystemfo.getList().get(0).getCargoOwnnerId());
    }

    //rel
    @Test
    public void test3(){
        FamiliarCar familiarCar = new FamiliarCar();
        familiarCar.setCargoOwnnerId(3);
        PageInfo<FamiliarCar> pageSystemfo = familiarCarMapper.familiarCarRelPage(familiarCar, new Page());
//        System.out.println(pageSystemfo.getList().get(0).getCarCargoOwnner().getName());
        List<FamiliarCar> list = pageSystemfo.getList();
        for (FamiliarCar fc:
                list) {
            System.out.println(fc.getCarCargoOwnner().getName());
        }

    }

    //jrel
    @Test
    public void test4(){
        FamiliarCar familiarCar = new FamiliarCar();
        familiarCar.setCargoOwnnerId(4);
        PageInfo<FamiliarCar> pageSystemfo = familiarCarDao.familiarJRelCarPage(familiarCar,new Page());
        List<FamiliarCar> list = pageSystemfo.getList();
        for (FamiliarCar fc:
                list) {
            System.out.println(fc.getCarCargoOwnner().getName()+":"+fc.getCarCargoOwnner().getType());
            System.out.println("fc.toString():");
            System.out.println(fc.toString());
        }
    }
    //ok
    //单条记录删除
    @Test
    public void test5(){
        int i = familiarCarMapper.deleteSingle(3, 22);
        System.out.println(i);
    }

    //ok
    //单条记录删除对象
    @Test
    public void test6(){
        FamiliarCar familiarCar = new FamiliarCar();
        familiarCar.setCargoOwnnerId(3);
        familiarCar.setCarOwnnerId(22);
        int i = familiarCarMapper.deleteSingleObj(familiarCar);
        System.out.println(i);
    }

    //ok
    //批量关注
    @Test
    public void test7(){
        List<FamiliarCar> list = new ArrayList<>();

        FamiliarCar familiarCar = new FamiliarCar();
        familiarCar.setCargoOwnnerId(3);
        familiarCar.setCarOwnnerId(22);

        FamiliarCar familiarCar2 = new FamiliarCar();
        familiarCar2.setCargoOwnnerId(3);
        familiarCar2.setCarOwnnerId(6);

        list.add(familiarCar2);
        list.add(familiarCar);
        System.out.println(list);
        
        familiarCarMapper.fcBatchSave(list,new Date());
    }
}
