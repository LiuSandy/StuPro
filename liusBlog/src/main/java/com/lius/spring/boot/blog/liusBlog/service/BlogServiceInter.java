package com.lius.spring.boot.blog.liusBlog.service;

import com.lius.spring.boot.blog.liusBlog.domain.Blog;
import com.lius.spring.boot.blog.liusBlog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/4/1 12:54
 * Description: Blog 服务接口
 */

public interface BlogServiceInter {
    /**
     * 保存Blog
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除Blog
     *
     * @param id
     */
    void removeBlog(Long id);

    /**
     * 根据ID 获取Blog
     *
     * @param id
     * @return
     */
    Blog getBlogById(Long id);

    /**
     * 根据用户进行博客名称分页模糊查询 （最新）
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable);

    /**
     * 根据用户进行博客名称分页模糊查询 （最热）
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable);

    /**
     * 阅读量递增
     * @param id
     */
    void readingIncrease(Long id);
}
