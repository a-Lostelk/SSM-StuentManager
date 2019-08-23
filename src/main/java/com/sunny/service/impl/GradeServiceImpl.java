package com.sunny.service.impl;

import com.sunny.dao.GradeDao;
import com.sunny.entity.Grade;
import com.sunny.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/21
 */
@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeDao gradeDao;

    @Override
    public List<Grade> findList(Map<String, Object> map) {
        return gradeDao.findList(map);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return gradeDao.getTotal(map);
    }

    @Override
    public int add(Grade grade) {
        return gradeDao.add(grade);
    }

    @Override
    public int edit(Grade grade) {
        return gradeDao.edit(grade);
    }

    @Override
    public int delete(String ids) {
        return gradeDao.delete(ids);
    }

    @Override
    public List<Grade> findAll() {
        return gradeDao.findAll();
    }
}
