package com.lius.spring.boot.blog.liusBlog.controller;

import com.lius.spring.boot.blog.liusBlog.domain.es.EsBlog;
import com.lius.spring.boot.blog.liusBlog.repository.es.EsBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/2/28 17:38
 * Description:Blog控制器
 */
@RestController
@RequestMapping(value = "/blogs")
public class BlogController {
    @Autowired
    private EsBlogRepository esBlogRepository;

    /**
     * 博客全文搜索
     *
     * @param title
     * @param summary
     * @param content
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/search")
    public List<EsBlog> list(@RequestParam(value = "title") String title,
                             @RequestParam(value = "summary") String summary,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageRequest pageable = new PageRequest(pageIndex, pageSize);
        Page<EsBlog> page = esBlogRepository.findDistinctFirstByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);
        return page.getContent();
    }

    /**
     * 博客搜索
     *
     * @return
     */
    @GetMapping
    public String listBlog(@RequestParam(value = "order", required = false, defaultValue = "new") String order, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        System.out.println("order:" + order + ";keyword" + keyword);
        return "redirect:/index?order=" + order + "&keyword=" + keyword;
    }
}
