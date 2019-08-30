package com.sunny.service.impl;

import com.sunny.dao.StudentDao;
import com.sunny.entity.Student;
import com.sunny.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: fang
 * @Date: 2019/8/27
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> findList(Map<String, Object> map) {
        return studentDao.findList(map);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return studentDao.getTotal(map);
    }

    @Override
    public int add(Student student) {
        return studentDao.add(student);
    }

    @Override
    public int edit(Student student) {
        return studentDao.edit(student);
    }

    @Override
    public int delete(String ids) {
        return studentDao.delete(ids);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public Student findByStudentName(String studentName) {
        return studentDao.findByStudentName(studentName);
    }

}
