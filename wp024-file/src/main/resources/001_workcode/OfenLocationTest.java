package com.zrytech.mytest;

import com.zrytech.framework.BaseApplication;
import com.zrytech.framework.app.entity.OfenLocation;
import com.zrytech.framework.app.repository.OfenLocationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest(classes= BaseApplication.class)
public class OfenLocationTest {
    @Autowired
    private OfenLocationRepository ofenLocationRepository;

    @Test
    public void test1(){
        List<OfenLocation> list = ofenLocationRepository.findAll();
        System.out.println(list.get(0).getName());
    }

}
