package com.sunny.service;

import com.sunny.entity.Clazz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/22
 */
@Service
public interface ClazzService {
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
     * @param grade
     * @return
     */
    int add(Clazz grade);

    /**
     * 编辑
     * @param grade
     * @return
     */
    int edit(Clazz grade);

    /**
     * 删除一个或多个
     * @param ids
     * @return
     */
    int delete(String ids);
}
