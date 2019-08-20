package com.sunny.dao;

import com.sunny.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/15
 */
@Repository
public interface UserDao {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int add(User user);

    /**
     * 列表用户
     * @param map
     * @return
     */
    List<User> findList(Map<String, Object> map);

    /**
     * 查询总记录数
     * @param map
     * @return
     */
    int getTotal(Map<String, Object> map);

    int edit(User user);

}
