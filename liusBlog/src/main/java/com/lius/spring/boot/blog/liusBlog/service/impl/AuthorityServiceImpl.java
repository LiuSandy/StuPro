/**
 * 
 */
package com.lius.spring.boot.blog.liusBlog.service.impl;

import com.lius.spring.boot.blog.liusBlog.domain.Authority;
import com.lius.spring.boot.blog.liusBlog.repository.AuthorityRepository;
import com.lius.spring.boot.blog.liusBlog.service.AuthorityServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/2/18 21:19
 * Description: 角色服务接口实现
 */
@Service
public class AuthorityServiceImpl  implements AuthorityServiceInter {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	public Authority getAuthorityById(Long id) {
		return authorityRepository.findOne(id);
	}

}
