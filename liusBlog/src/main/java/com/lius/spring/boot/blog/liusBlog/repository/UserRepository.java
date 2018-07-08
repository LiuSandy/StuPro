package com.lius.spring.boot.blog.liusBlog.repository;

import com.lius.spring.boot.blog.liusBlog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/2/18 21:22
 * Description: 用户接口
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户姓名分页查询用户列表
     *
     * @param name
     * @param pageable
     * @return
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * 根据用户账号查询用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}

//    /**
//     * 创建或者修改用户
//     *
//     * @param user
//     * @return
//     */
//    User saveOrUpdateUser(User user);
//
//    /**
//     * 删除用户
//     *
//     * @param id
//     */
//    void deleteUser(Long id);
//
//    /**
//     * 根据ID查询用户
//     *
//     * @param id
//     * @return
//     */
//    User getUserById(Long id);
//
//    /**
//     * 获取用户列表
//     *
//     * @return
//     */
//    List<User> listUser();