package com.lius.spring.boot.blog.liusBlog.service.impl;

import com.lius.spring.boot.blog.liusBlog.domain.Blog;
import com.lius.spring.boot.blog.liusBlog.domain.User;
import com.lius.spring.boot.blog.liusBlog.repository.BlogRepository;
import com.lius.spring.boot.blog.liusBlog.service.BlogServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/4/1 13:05
 * Description: 接口实现方法
 */
@Service
public class BlogServiceImpl implements BlogServiceInter {
    @Autowired
    private BlogRepository blogRepository;


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public void removeBlog(Long id) {
        blogRepository.delete(id);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
        title = "%" + title + "%";
        String tags = title;
        Page<Blog> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, tags, pageable);
        return blogs;

    }

    @Override
    public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user,title,pageable);
        return blogs;
    }

    @Override
    public void readingIncrease(Long id) {
        Blog blog = blogRepository.findOne(id);
        blog.setReading(blog.getReading() + 1);
        this.saveBlog(blog);
    }
}
