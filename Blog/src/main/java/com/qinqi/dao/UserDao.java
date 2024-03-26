package com.qinqi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qinqi.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
