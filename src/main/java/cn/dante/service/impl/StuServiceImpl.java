package cn.dante.service.impl;

import cn.dante.entity.Stu;
import cn.dante.mapper.StuMapper;
import cn.dante.repository.StuRepository;
import cn.dante.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuServiceImpl implements StuService{
    @Autowired
    private StuRepository stuRepository;

    @Autowired
    private StuMapper stuMapper;

    @Override
    public List<Stu> getList() {

//        return stuRepository.findAll();
        int i = 1 / 0;
        return stuMapper.list();
    }


}
