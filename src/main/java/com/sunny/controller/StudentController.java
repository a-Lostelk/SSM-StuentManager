package com.sunny.controller;

import com.sunny.dto.PageDTO;
import com.sunny.entity.Clazz;
import com.sunny.entity.Grade;
import com.sunny.service.ClazzService;
import com.sunny.service.GradeService;
import com.sunny.util.UploadFile;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.Collator;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/22
 *
 * 学生信息管理
 */
@Controller
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private GradeService gradeService;

    @Autowired
    private ClazzService clazzService;


    /**
     * 学生列表页
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("student/student_list");
        List<Grade> grades = gradeService.findAll();
        List<Clazz> clazzes = clazzService.findAll();
        //将list按照名字进行排序
        Collections.sort(clazzes, (o1, o2) -> {
            Collator collator = Collator.getInstance(Locale.CHINA);
            return collator.compare(o1.getName(), o2.getName());
        });
        modelAndView.addObject("clazzList", clazzes);
        modelAndView.addObject("clazzJSON", JSONArray.fromObject(clazzes));
        modelAndView.addObject("gradeList", grades);
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
     * 获取学生列表和模糊查询
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
        Map<String, Object> queryMap = new HashMap<>();
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
     * t图片上传
     *
     * @param filePhoto
     * @return
     */
    @RequestMapping(value = "/upload_Photo", method = RequestMethod.POST)
    public Map<String, String> uploadPhoto(MultipartFile filePhoto,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {

        HashMap<String, String> hashMap = new HashMap<>();
        response.setCharacterEncoding("UTF-8");
        if (filePhoto == null) {
            hashMap.put("type","error");
            hashMap.put("msg", "请选择文件");
            return hashMap;
        }
        int maxSize = 10485760;
        if (filePhoto.getSize() > maxSize) {
            hashMap.put("type","error");
            hashMap.put("msg", "文件太大了请重试");
            return hashMap;
        }
        String suffix = filePhoto.getOriginalFilename().substring(filePhoto.
                getOriginalFilename().lastIndexOf(".") + 1, filePhoto.getOriginalFilename().length());
        // 限制上传的文件类型
        String fileType = "jpg,png,jpeg,gif";
        if (!fileType.contains(suffix.toLowerCase())) {
            hashMap.put("type","error");
            hashMap.put("msg", "文件格式不正确，请请重新上传");
            return hashMap;
        }
        String savePath = request.getServletContext().getRealPath("/")+"\\upload\\";
        System.out.println(savePath);
        hashMap.put("type","success");
        hashMap.put("msg", "上传成功");
        return hashMap;
    }

    /**
     * 上传图片
     * @param photo
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPhoto(MultipartFile photo, HttpServletRequest request) {
        //保存在target包下的项目目录下
        final String dirPath = request.getServletContext().getRealPath("/upload/student_portrait/");
        //存储头像的项目发布目录
        final String portraitPath = request.getServletContext().getContextPath() + "/upload/student_portrait/";
        //返回头像的上传结果
        return UploadFile.getUploadResult(photo, dirPath, portraitPath, request);
    }

    /**
     * 添加
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Clazz clazz) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (StringUtils.isEmpty(clazz.getName())) {
            hashMap.put("type","error");
            hashMap.put("msg", "学生名称不能为空");
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
        HashMap<String, String> hashMap = new HashMap<>();
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
     * 学生信息删除
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
        try {
            if (clazzService.delete(string) <= 0) {
                map.put("type", "error");
                map.put("msg", "删除失败");
                return map;
            }
        } catch (Exception e) {
            map.put("type", "error");
            map.put("msg", "该学生下存在学生信息，请勿冲动");
            return map;
        }

        map.put("type", "success");
        map.put("msg", "删除成功");
        return map;
    }

}
