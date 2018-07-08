package com.lius.spring.boot.blog.liusBlog.repository.es;

import com.lius.spring.boot.blog.liusBlog.domain.es.EsBlog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.awt.print.Pageable;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/2/23 20:50
 * Description: 测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBlogRepositoryTest {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Before
    public void initRepositoryData() {
        System.out.println("*****************************");
        // 清除所有数据
        esBlogRepository.deleteAll();
        esBlogRepository.save(new EsBlog("相思", "老卫跟你谈谈安装 Elasticsearch",
                "关于如何来安装 Elasticsearch，这个请看我的博客 http://www.lius.ac.cn/"));
        esBlogRepository.save(new EsBlog("登鹳雀楼", "老卫跟你谈谈安装 Elasticsearch",
                "关于如何来安装 Elasticsearch，这个请看我的博客 http://www.lius.ac.cn/"));
        esBlogRepository.save(new EsBlog("静夜思", "老卫跟你谈谈安装 Elasticsearch",
                "关于如何来安装 Elasticsearch，这个请看我的博客 http://www.lius.ac.cn/"));
    }

    @Test
    public void testFindDistinctFirstByTitleContainingOrSummaryContainingOrContentContaining() {
        PageRequest pageable = new PageRequest(0, 20);
        String title = "思";
        String summery = "安装";
        String content = "安装";

        Page<EsBlog> esBlogs = esBlogRepository.findDistinctFirstByTitleContainingOrSummaryContainingOrContentContaining(title, summery, content, pageable);
        System.out.println("------------------------------------START");
//        assertThat(esBlogs.getTotalElements()).isEqualTo(2);
        for (EsBlog blog : esBlogs.getContent()){
            System.out.println(blog.toString());
        }
        System.out.println("------------------------------------END");
    }
}