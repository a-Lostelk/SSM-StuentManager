package com.sunny.controller;

import com.sunny.dto.PageDTO;
import com.sunny.entity.Clazz;
import com.sunny.entity.Grade;
import com.sunny.service.ClazzService;
import com.sunny.service.GradeService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/22
 *
 * 班级信息管理
 */
@Controller
@RequestMapping("/clazz")
public class ClazzController {


    @Autowired
    private GradeService gradeService;

    @Autowired
    private ClazzService clazzService;

    /**
     * 班级列表页
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("clazz/clazz_list");
        List<Grade> all = gradeService.findAll();
        modelAndView.addObject("gradeList", all);
        modelAndView.addObject("gradeJSON", JSONArray.fromObject(all));
        return modelAndView;
    }

    /**
     * 获取年级级信息
     */
    @RequestMapping(value = "/getGrade", method = RequestMethod.GET)
    @ResponseBody
    public Map getGrade(){
        HashMap hashMap = new HashMap<>();
        List<Grade> all = gradeService.findAll();
        hashMap.put("map", all);
        return hashMap;
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
            @RequestParam(value = "gradeId", required = false)Integer gradeId,
            PageDTO pageDTO) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", "%" + name + "%");
        if (gradeId != null) {
            //根据年级Id查询
            queryMap.put("gradeId", gradeId);
        }
        queryMap.put("offset", pageDTO.getOffset());
        queryMap.put("pageSize", pageDTO.getRows());
        hashMap.put("rows", clazzService.findList(queryMap));
        hashMap.put("total", clazzService.getTotal(queryMap));
        return hashMap;
    }

    /**
     * 添加
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/addClazz", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Clazz clazz) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (StringUtils.isEmpty(clazz.getName())) {
            hashMap.put("type","error");
            hashMap.put("msg", "班级名称不能为空");
            return hashMap;
        }
        if (clazz.getGradeId() == null) {
            hashMap.put("type","error");
            hashMap.put("msg", "请选择所属年级");
            return hashMap;
        }
        if (clazzService.add(clazz) <= 0) {
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
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Clazz clazz) {
        Map<String, String> hashMap = new HashMap<>();
        if (StringUtils.isEmpty(clazz.getName())) {
            hashMap.put("type","error");
            hashMap.put("msg", "名称不能为空");
            return hashMap;
        }
        if (clazz.getGradeId() == null) {
            hashMap.put("type","error");
            hashMap.put("msg", "所属年级不能为空");
            return hashMap;
        }
        if (clazzService.edit(clazz) <= 0) {
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
        Map<String, String> map = new HashMap<>();
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
        if (clazzService.delete(string) <= 0) {
            map.put("type", "error");
            map.put("msg", "删除失败");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "删除成功");
        return map;
    }

}
