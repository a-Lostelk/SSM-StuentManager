package com.sunny.controller;

import com.sunny.dto.PageDTO;
import com.sunny.entity.User;
import com.sunny.service.UserService;
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
 * 管理员控制器
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/18
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    public UserService userService;

    /**
     * 展示用户列表
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("user/user_list");
        return modelAndView;
    }

    @RequestMapping(value = "/get_list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(
            @RequestParam(value = "username", required = false, defaultValue = "")String username,
            PageDTO pageDTO) {
        Map<String, Object> hashMap = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("username", "%" + username + "%");
        queryMap.put("offset", pageDTO.getOffset());
        queryMap.put("pageSize", pageDTO.getRows());
        hashMap.put("rows", userService.findList(queryMap));
        hashMap.put("total", 10);
        return hashMap;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody

    public Map<String, String> add(User user) {
        Map<String, String> hashMap = new HashMap<>();
        if (user == null) {
            hashMap.put("type", "error");
            hashMap.put("msg", "服务器出现了异常");
            return hashMap;
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            hashMap.put("type", "error");
            hashMap.put("msg", "用户名不能为空");
            return hashMap;
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            hashMap.put("type", "error");
            hashMap.put("msg", "密码不能为空");
            return hashMap;
        }
        //如果用户存在
        User userName = userService.findByUserName(user.getUsername());
        if (userName != null) {
            hashMap.put("type", "error");
            hashMap.put("msg", "用户已存在");
            return hashMap;
        }
        if (userService.add(user) <= 0) {
            hashMap.put("type", "error");
            hashMap.put("msg", "添加失败");
            return hashMap;
        }
        hashMap.put("type", "success");
        hashMap.put("msg", "添加成功");
        return hashMap;

    }

}
