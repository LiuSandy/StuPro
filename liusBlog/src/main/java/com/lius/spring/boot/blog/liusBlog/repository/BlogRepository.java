package com.lius.spring.boot.blog.liusBlog.repository;

import com.lius.spring.boot.blog.liusBlog.domain.Blog;
import com.lius.spring.boot.blog.liusBlog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Blog 仓库
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {
    /**
     * 根据用户名分页查询用户列表
     *
     * @return
     */
    Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user, String title, Pageable pageable);

    /**
     * 根据用户名分页查询用户列表
     *
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);
}
