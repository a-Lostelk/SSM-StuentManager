package com.sunny.service.impl;

import com.sunny.dao.UserDao;
import com.sunny.entity.User;
import com.sunny.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/15
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public int add(User user) {
        return userDao.add(user);
    }

    @Override
    public List<User> findList(Map<String, Object> map) {
        return userDao.findList(map);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return userDao.getTotal(map);
    }

    @Override
    public int edit(User user) {
        return userDao.edit(user);
    }

    @Override
    public int delete(String ids) {
        return userDao.delete(ids);
    }

}
