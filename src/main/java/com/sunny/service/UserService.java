package com.sunny.service;

import com.sunny.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/15
 */

public interface UserService {

    /**
     *  用户名查找
     * @param username
     * @return
     */
    User findByUserName(String username);

}
