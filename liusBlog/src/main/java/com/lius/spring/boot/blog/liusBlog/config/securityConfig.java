package com.lius.spring.boot.blog.liusBlog.config;

import com.lius.spring.boot.blog.liusBlog.service.UserServiceInter;
import com.mysql.cj.mysqlx.protobuf.MysqlxSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.Authenticator;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/3/2 11:04
 * Description: 安全配置类
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用安全方法配置
public class securityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "lius.ac.com";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 使用 Bcrypt 加密
    }

    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);// 设置密码加密方式
        return authenticationProvider;
    }

    /**
     * 自定义配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()//都可以访问
                .antMatchers("/h2-console/**").permitAll()//都可以访问
                .antMatchers("/admins/**").hasRole("ADMIN")//需要相应的角色才能访问
                .and()
                .formLogin()//基于form表单登录验证
                .loginPage("/login").failureUrl("/login-error")
                .and().rememberMe().key(KEY)// 启用 remember me
                .and().exceptionHandling().accessDeniedPage("/403");//处理异常，拒绝访问就重定向到403页面
        http.csrf().ignoringAntMatchers("/h2-console/**");// 禁用H2控制台的CSRF保护
        http.headers().frameOptions().sameOrigin();//允许来自同一来源的H2控制台请求
    }

    /**
     * 认证信息管理
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()// 认证信息存储在内存中
//                .withUser("lius").password("123456").roles("ADMIN");
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }
}