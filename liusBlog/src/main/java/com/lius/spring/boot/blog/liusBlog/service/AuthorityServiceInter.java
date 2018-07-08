package com.lius.spring.boot.blog.liusBlog.service;

import com.lius.spring.boot.blog.liusBlog.domain.Authority;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/2/18 21:19
 * Description: 角色服务接口
 */
public interface AuthorityServiceInter {
	 
	
	/**
	 * 根据id获取 Authority
	 * @param id
	 * @return
	 */
	Authority getAuthorityById(Long id);
}
