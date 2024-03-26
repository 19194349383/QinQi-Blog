package com.qinqi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qinqi.domain.ArticleType;

public interface ArticleTypeService extends IService<ArticleType> {

    public ArticleType getByType(String type);

}
