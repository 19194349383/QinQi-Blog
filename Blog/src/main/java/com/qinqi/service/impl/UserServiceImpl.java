package com.qinqi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinqi.dao.UserDao;
import com.qinqi.domain.User;
import com.qinqi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
