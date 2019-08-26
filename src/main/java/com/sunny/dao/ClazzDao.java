package com.sunny.dao;

import com.sunny.entity.Clazz;
import com.sunny.entity.Grade;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/22
 */
@Repository
public interface ClazzDao {


    /**
     * 列表展示
     * @param map
     * @return
     */
    List<Clazz> findList(Map<String, Object> map);

    /**
     * 获取总记录数
     * @param map
     * @return
     */
    int getTotal(Map<String, Object> map);

    /**
     * 添加
     * @param clazz
     * @return
     */
    int add(Clazz clazz);

    /**
     * 编辑
     * @param clazz
     * @return
     */
    int edit(Clazz clazz);

    /**
     * 删除一个或多个
     * @param ids
     * @return
     */
    int delete(String ids);

    /**
     * 查询所有
     * @return
     */
    List<Clazz> findAll();

}
