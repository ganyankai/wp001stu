package cn.dante.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HelloController {
//    @RequestMapping("/hello")
    @GetMapping("/hello")
    public String hello() {
//        int i = 2/0;
        return "hello dante";
    }

}
