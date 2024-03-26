package com.qinqi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinqi.dao.ArticleDao;
import com.qinqi.domain.Article;
import com.qinqi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {


    //根据 文章类型id 查询对应的文章信息
    public List<Article> getByTypeId(Long id) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("type_id", id);
        List<Article> list = this.list(wrapper);
        return list;
    }

}
