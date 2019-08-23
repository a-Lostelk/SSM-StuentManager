package com.sunny.controller;

import com.sunny.dto.PageDTO;
import com.sunny.entity.Grade;
import com.sunny.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/21
 *
 * 班级管理控制器
 */
@RequestMapping("/grade")
@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 年级列表页
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("grade/grade_list");
        return modelAndView;
    }

    /**
     * 获取用户列表和模糊查询
     * @param name
     * @param pageDTO
     * @return
     */
    @RequestMapping(value = "/get_list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(
            @RequestParam(value = "name", required = false, defaultValue = "")String name,
            PageDTO pageDTO) {
        Map<String, Object> hashMap = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", "%" + name + "%");
        queryMap.put("offset", pageDTO.getOffset());
        queryMap.put("pageSize", pageDTO.getRows());
        hashMap.put("rows", gradeService.findList(queryMap));
        hashMap.put("total", gradeService.getTotal(queryMap));
        return hashMap;
    }

    /**
     * 添加
     * @param grade
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Grade grade) {
        Map<String, String> hashMap = new HashMap<>();
        if (StringUtils.isEmpty(grade.getName())) {
            hashMap.put("type","error");
            hashMap.put("msg", "名称不能为空");
            return hashMap;
        }
        if (gradeService.add(grade) <= 0) {
            hashMap.put("type","error");
            hashMap.put("msg", "添加失败");
            return hashMap;
        }
        hashMap.put("type","success");
        hashMap.put("msg", "添加成功");
        return hashMap;
    }

    /**
     * 编辑
     * @param grade
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Grade grade) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (StringUtils.isEmpty(grade.getName())) {
            hashMap.put("type","error");
            hashMap.put("msg", "名称不能为空");
            return hashMap;
        }
        if (gradeService.edit(grade) <= 0) {
            hashMap.put("type","error");
            hashMap.put("msg", "修改失败");
            return hashMap;
        }
        hashMap.put("type","success");
        hashMap.put("msg", "修改成功");
        return hashMap;
    }

    /**
     * 用户删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(
            @RequestParam(value = "ids[]", required = true) Long[] ids) {
        HashMap<String, String> map = new HashMap<>();
        if (ids == null || ids.length == 0) {
            map.put("type", "error");
            map.put("msg", "请选择要删除的数据");
            return map;
        }
        String string = "";
        for (Long id:
                ids) {
            string += id + ",";
        }
        //截取传递过来的id
        string = string.substring(0, string.length() - 1);
        if (gradeService.delete(string) <= 0) {
            map.put("type", "error");
            map.put("msg", "删除失败");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "删除成功");
        return map;
    }
}
