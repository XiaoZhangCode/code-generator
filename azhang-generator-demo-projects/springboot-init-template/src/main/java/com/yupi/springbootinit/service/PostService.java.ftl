package ${basePackage}.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${basePackage}.springbootinit.model.dto.post.PostQueryRequest;
import ${basePackage}.springbootinit.model.entity.Post;

/**
 * 帖子服务
 */
public interface PostService extends IService<Post> {

    /**
     * 获取查询条件
     *
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

<#if needEs>
     /**
     * 从 ES 查询
     *
     * @param postQueryRequest
     * @return
     */
    Page<Post> searchFromEs(PostQueryRequest postQueryRequest); 
</#if>

}
