package com.lius.spring.boot.blog.liusBlog.controller;

import com.lius.spring.boot.blog.liusBlog.domain.Authority;
import com.lius.spring.boot.blog.liusBlog.domain.User;
import com.lius.spring.boot.blog.liusBlog.service.AuthorityServiceInter;
import com.lius.spring.boot.blog.liusBlog.service.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/3/2 11:22
 * Description: 主页控制器
 */
@Controller
public class MainController {

    private static final Long ROLE_USER_AUTHORITY_ID = 2L;

    @Autowired
    private UserServiceInter userService;

    @Autowired
    private AuthorityServiceInter authorityService;
    /**
     * 主页面
     *
     * @return
     */
    @GetMapping(value = "/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }

    /**
     * 登陆页面
     *
     * @return
     */
    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    /**
     * 登陆错误页面
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/login-error")
    public String loginError(Model model) {

        model.addAttribute("loginError", true);
        model.addAttribute("errMsg", "登录失败， 用户名或密码错误");
        return "login";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String registerUser(User user) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorities);
        userService.saveUser(user);
        return "redirect:/login";
    }
}
