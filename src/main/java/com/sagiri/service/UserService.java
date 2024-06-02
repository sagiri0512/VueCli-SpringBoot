package com.sagiri.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sagiri.entity.MailCode;
import com.sagiri.entity.User;
import com.sagiri.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User getUserByColumn(String columnName, String columnValue) {//根据列名的值查找一行
        QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper.eq(columnName, columnValue);
        return userMapper.selectOne(queryWrapper);
    }
    public int insert(User user) {
        return userMapper.insert(user);
    }
}
