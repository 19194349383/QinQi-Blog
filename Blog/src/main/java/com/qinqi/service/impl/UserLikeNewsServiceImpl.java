package com.qinqi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinqi.dao.UserLikeNewsDao;
import com.qinqi.domain.UserLikeNews;
import com.qinqi.service.UserLikeNewsService;
import org.springframework.stereotype.Service;

@Service
public class UserLikeNewsServiceImpl extends ServiceImpl<UserLikeNewsDao, UserLikeNews> implements UserLikeNewsService {
}
