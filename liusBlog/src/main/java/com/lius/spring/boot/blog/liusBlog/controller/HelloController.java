package com.lius.spring.boot.blog.liusBlog.controller;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/2/18 20:32
 * Description: Hello 控制器
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
