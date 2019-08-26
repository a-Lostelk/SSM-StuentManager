package com.sunny.dao;

import com.sunny.entity.Grade;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/21
 */
@Repository
public interface GradeDao {

    /**
     * 列表展示
     * @param map
     * @return
     */
     List<Grade> findList(Map<String, Object> map);

    /**
     * 获取总记录数
     * @param map
     * @return
     */
    int getTotal(Map<String, Object> map);

    /**
     * 添加
     * @param grade
     * @return
     */
    int add(Grade grade);

    /**
     * 编辑
     * @param grade
     * @return
     */
    int edit(Grade grade);

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
    List<Grade> findAll();

}
