package com.sunny.interceptor;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/15
 *
 * 登录过滤拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {


    /**
     * 方法执行前进入拦截器
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        //用户未登录或登录失效
        if (request.getSession().getAttribute("userInfo") == null) {
            System.out.println("error:用户未登录或登录失效" + uri);
            //获取请求头，ajax请求头会携带XMLHttpRequest参数
            String header = "XMLHttpRequest";
            if (header.equals(request.getHeader("X-Requested-With"))) {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("type", "error");
                hashMap.put("msg", "登录已经失效，请重新登录");
                response.getWriter().write(JSONObject.fromObject(hashMap).toString());
                return false;
            }
            //未登录跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/System/login");
            return false;

        }
        return true;
    }

    /**
     * 方法在执行的时候调用
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在视图渲染结束后执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
