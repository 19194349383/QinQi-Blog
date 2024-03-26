package com.qinqi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qinqi.domain.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {

    //通过文章类型id 获取文章信息
    public List<Article> getByTypeId(Long id);



}
