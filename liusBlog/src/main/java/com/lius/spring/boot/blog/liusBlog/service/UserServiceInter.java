package com.lius.spring.boot.blog.liusBlog.service;

import com.lius.spring.boot.blog.liusBlog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/3/5 9:18
 * Description: 用户服务接口
 */
public interface UserServiceInter {

    /**
     * 保存用户
     * @param user
     * @return
     */
    User saveUser(User user);

    /**
     * 删除用户
     * @param
     * @return
     */
    void removeUser(Long id);

    /**
     * 删除列表里面的用户
     * @param
     * @return
     */
    void removeUsersInBatch(List<User> users);

    /**
     * 更新用户
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 根据id获取用户
     * @param
     * @return
     */
    User getUserById(Long id);

    /**
     * 获取用户列表
     * @param
     * @return
     */
    List<User> listUsers();

    /**
     * 根据用户名进行分页模糊查询
     * @param
     * @return
     */
    Page<User> listUsersByNameLike(String name, Pageable pageable);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
