package com.lius.spring.boot.blog.liusBlog.controller;

import com.lius.spring.boot.blog.liusBlog.domain.Blog;
import com.lius.spring.boot.blog.liusBlog.domain.User;
import com.lius.spring.boot.blog.liusBlog.service.BlogServiceInter;
import com.lius.spring.boot.blog.liusBlog.service.UserServiceInter;
import com.lius.spring.boot.blog.liusBlog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/3/4 10:09
 * Description: 用户主页控制器
 */
@Controller
@RequestMapping(value = "/u")
public class UserspaceController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserServiceInter userServiceInter;

    @Autowired
    private BlogServiceInter blogServiceInter;

    /**
     * 用户主页
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/{username}")
    public String userSpace(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        System.out.println("username" + username);
        return "redirect:/u/" + username + "blogs";
    }

    /**
     * 用户博客展示页面
     *
     * @param username
     * @param order
     * @param catalogId
     * @param keyword
     * @param async
     * @return
     */
    @GetMapping(value = "/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "catalogId", required = false) Long catalogId,
                                   @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                   @RequestParam(value = "async", required = false) String async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        Page<Blog> blogs = null;
        if (catalogId != null && catalogId > 0) {
            // TODO
        } else if (order.equals("hot")) { // 最热
            Sort sort = new Sort(Sort.Direction.DESC, "reading", "comment", "vote");
            Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
            blogs = blogServiceInter.listBlogsByTitleVote(user, keyword, pageable);
        } else if (order.equals("new")) { // 最新
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            blogs = blogServiceInter.listBlogsByTitleVote(user, keyword, pageable);
        }
        List<Blog> blogList = blogs.getContent();

        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("catalogId", catalogId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", blogs);
        model.addAttribute("blogList", blogList);

        return (async = true ? "/userspace/u :: #mainContainerRepleace" : "/userspace/u");
    }

    /**
     * 根据博客ID返回指定博客
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{username}/blogs/{id}")
    public String getBlogById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        // 每次阅读，可以认为阅读量增加一次
        blogServiceInter.readingIncrease(id);

        // 判断操作用户是否是博客的所有者
        boolean isBlogOwner = false;
        // 判断操作用户是否是博客的所有者
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && username.equals(principal.getUsername())) {
                isBlogOwner = true;
            }
        }

        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("blogModel", blogServiceInter.getBlogById(id));

        return "/userspace/blog";
    }

    /**
     * 编辑博客
     *
     * @return
     */
    @GetMapping(value = "/{username}/blogs/edit")
    public ModelAndView editBlog(@PathVariable("username") String username, Model model) {

        model.addAttribute("blog", new Blog(null, null, null));
//        model.addAttribute("fileServerUrl",fileServerUrl);
        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    /**
     * 获取编辑博客的界面
     *
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        model.addAttribute("blog", blogServiceInter.getBlogById(id));
        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    /**
     * 保存博客
     *
     * @param username
     * @param blog
     * @return
     */
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        blog.setUser(user);
        try {
            blogServiceInter.saveBlog(blog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }

    /**
     * 删除博客
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {

        try {
            blogServiceInter.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs";
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }

    /**
     * 获取个人设置页面
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping(value = "/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("filerServerUrl", "fileServerUrl");
        return new ModelAndView("/userspace/profile", "userModel", model);
    }

    /**
     * 保存用户的配置
     *
     * @param username
     * @param user
     * @return
     */
    @PostMapping(value = "/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {
        User originalUser = userServiceInter.getUserById(user.getId());
        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());

        // 判断密码是否做了变更
        String rawPassword = originalUser.getPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoderPassword = passwordEncoder.encode(user.getPassword());
        boolean isMatch = passwordEncoder.matches(rawPassword, encoderPassword);

        if (!isMatch) {
            originalUser.setEncodePassword(user.getPassword());
        }

        userServiceInter.saveUser(originalUser);
        return "redirct:/u/" + username + "/profile";
    }

    /**
     * 获取编辑头像页面
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/avatar", "userModel", model);
    }

    /**
     * 保存用户头像
     *
     * @param username
     * @param user
     * @return
     */
    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();
        User originalUser = userServiceInter.getUserById(user.getId());
        originalUser.setAvatar(avatarUrl);
        userServiceInter.saveUser(originalUser);
        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }
}
