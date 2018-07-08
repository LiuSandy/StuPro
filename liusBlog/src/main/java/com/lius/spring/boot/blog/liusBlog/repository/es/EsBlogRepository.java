package com.lius.spring.boot.blog.liusBlog.repository.es;

import com.lius.spring.boot.blog.liusBlog.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/2/23 20:44
 * Description: Blog 资源库文档接口
 */
public interface EsBlogRepository extends ElasticsearchCrudRepository<EsBlog, String> {

    /**
     * 分页查询博客（去重）
     *
     * @param title
     * @param summary
     * @param content
     * @param pageable
     * @return
     */
    Page<EsBlog> findDistinctFirstByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary, String content, Pageable pageable);
}
