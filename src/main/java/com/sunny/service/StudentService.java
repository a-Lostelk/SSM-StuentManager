package com.sunny.service;

import com.sunny.entity.Student;
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
public interface StudentService {
    /**
     * 列表展示
     * @param map
     * @return
     */
    List<Student> findList(Map<String, Object> map);

    /**
     * 获取总记录数
     * @param map
     * @return
     */
    int getTotal(Map<String, Object> map);

    /**
     * 添加
     * @param student
     * @return
     */
    int add(Student student);

    /**
     * 编辑
     * @param student
     * @return
     */
    int edit(Student student);

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
    List<Student> findAll();

    /**
     * 根据名字查找
     * @param studentName
     * @return
     */
    Student findByStudentName(String studentName);
}
