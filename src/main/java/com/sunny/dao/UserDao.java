package com.sunny.dao;

import com.sunny.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/15
 */
@Repository
public interface UserDao {
    User findByUserName(String username);
}
