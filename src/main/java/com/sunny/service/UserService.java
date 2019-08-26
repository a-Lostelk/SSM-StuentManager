package com.sunny.service;

import com.sunny.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * 添加
     * @param user
     * @return
     */
    int add(User user);

    /**
     * 列表展示
     * @param map
     * @return
     */
    List<User> findList(Map<String, Object> map);

    /**
     * 获取总记录数
     * @param map
     * @return
     */
    int getTotal(Map<String, Object> map);

    /**
     * 编辑
     * @param user
     * @return
     */
    int edit(User user);

    /**
     * 删除一个或多个
     * @param ids
     * @return
     */
    int delete(String ids);



}
