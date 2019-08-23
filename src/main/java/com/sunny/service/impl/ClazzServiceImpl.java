package com.sunny.service.impl;

import com.sunny.dao.ClazzDao;
import com.sunny.entity.Clazz;
import com.sunny.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/22
 *
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzDao clazzDao;

    @Override
    public List<Clazz> findList(Map<String, Object> map) {
        return clazzDao.findList(map);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return clazzDao.getTotal(map);
    }

    @Override
    public int add(Clazz grade) {
        return clazzDao.add(grade);
    }

    @Override
    public int edit(Clazz grade) {
        return clazzDao.edit(grade);
    }

    @Override
    public int delete(String ids) {
        return clazzDao.delete(ids);
    }
}
