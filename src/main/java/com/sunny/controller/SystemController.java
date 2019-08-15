package com.sunny.controller;

import com.sunny.dto.LoginParamDTO;
import com.sunny.entity.User;
import com.sunny.service.UserService;
import com.sunny.util.CpachaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/14
 *
 * 系统主页控制器
 */
@Controller
@RequestMapping("/System")
public class SystemController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
         return "admin/admin";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     *
     * @param loginParamDTO userName.pwssword等参数DTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, String> login(LoginParamDTO loginParamDTO,
                                     HttpServletRequest request) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (StringUtils.isEmpty(loginParamDTO.getUsername())) {
            hashMap.put("type", "error");
            hashMap.put("msg", "用户名不能为空");
            //返回不继续执行
            return hashMap;
        }
        if (StringUtils.isEmpty(loginParamDTO.getPassword())) {
            hashMap.put("type", "error");
            hashMap.put("msg", "密码不能为空");
            return hashMap;
        }
        if (StringUtils.isEmpty(loginParamDTO.getVcode())) {
            hashMap.put("type", "error");
            hashMap.put("msg", "验证码不能为空");
            return hashMap;
        }
        //一般出现在浏览器关闭会话销毁的情况
        String loginCpacha = (String) request.getSession().getAttribute("loginCpacha");
        if (StringUtils.isEmpty(loginCpacha)) {
            hashMap.put("type", "error");
            hashMap.put("msg", "长时间操作，验证码已失效");
            return hashMap;

        }
        if (!loginParamDTO.getVcode().toUpperCase().equals(loginCpacha.toUpperCase())) {
            hashMap.put("type", "error");
            hashMap.put("msg", "验证码错误");
            return hashMap;
        }
        //释放session空间
        request.getSession().setAttribute("loginCpacha", null);

        //学生
        if (loginParamDTO.getType() == 2) {
            //从数据库中查找用户
            User user = userService.findByUserName(loginParamDTO.getUsername());
            if (user == null) {
                hashMap.put("type", "error");
                hashMap.put("msg", "用户不存在");
                return hashMap;
            }
            if (!loginParamDTO.getPassword().equals(user.getPassword())) {
                hashMap.put("type", "error");
                hashMap.put("msg", "密码不正确");
                return hashMap;
            }
            request.getSession().setAttribute("user", user);
        }
        //管理员登录
        if (loginParamDTO.getType() == 1) {

        }
        hashMap.put("type", "success");
        hashMap.put("msg", "登录成功");
        return hashMap;
    }

    /**
     * @param request
     * @param vl    验证码数量
     * @param w     验证图片宽度
     * @param h     验证图片高度
     * @param response
     */
    @RequestMapping(value = "/get_Cpacha", method = RequestMethod.GET)
    public void getCpacha( HttpServletRequest request,
                           @RequestParam(value = "vl", defaultValue = "4", required = false) Integer vl,
                           @RequestParam(value = "w", defaultValue = "98", required = false) Integer w,
                           @RequestParam(value = "h", defaultValue = "33", required = false) Integer h,
                                   HttpServletResponse response) {
        CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
        //生成验证码
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute("loginCpacha", generatorVCode);
        //生成图片，true表示画干扰线
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        try {
            //将生成的验证码图片存放于response对象中通过ImageIO写入页面
            ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
