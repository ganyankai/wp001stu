package cn.dante.controller;

import cn.dante.entity.Stu;
import cn.dante.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stu")
public class StuController {
    @Autowired
    private StuService stuService;

    @RequestMapping("/list")
    public List<Stu> list(){
//        int i = 1 / 0;
        return stuService.getList();
    }

}
