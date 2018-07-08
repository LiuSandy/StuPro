package com.lius.spring.boot.blog.liusBlog.repository;

import com.lius.spring.boot.blog.liusBlog.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/2/18 21:19
 * Description: 角色接口
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
}
