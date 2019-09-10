package com.sunny.controller;

import com.sunny.dto.PageDTO;
import com.sunny.entity.Clazz;
import com.sunny.entity.Student;
import com.sunny.service.ClazzService;
import com.sunny.service.StudentService;
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
import java.io.File;
import java.io.IOException;
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
    private StudentService studentService;

    @Autowired
    private ClazzService clazzService;

    /**
     * 上传图片最大大小
     */
    final int upload_File_MaxSize = 10485760;

    /**
     * 上传图片的类型
     */
    final String upload_File_Type = "jpg,png,gif,jpeg";


    /**
     * 学生列表页
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("student/student_list");
        List<Student> grades = studentService.findAll();
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
        List<Clazz> all = clazzService.findAll();
        hashMap.put("map", all);
        return hashMap;
    }

    /**
     * 获取学生数据和模糊查询
     *
     * @param username
     * @param pageDTO
     * @return
     */
    @RequestMapping(value = "/get_list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(
            @RequestParam(value = "username", required = false, defaultValue = "") String username,
            @RequestParam(value = "clazzId", required = false) Integer clazzId,
            HttpServletRequest request,
            PageDTO pageDTO) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();

        queryMap.put("username", "%" + username + "%");
        if (clazzId != null) {
            //根据年级Id查询
            queryMap.put("clazzId", clazzId);
        }
        queryMap.put("offset", pageDTO.getOffset());
        queryMap.put("pageSize", pageDTO.getRows());
        hashMap.put("rows", studentService.findList(queryMap));
        hashMap.put("total", studentService.getTotal(queryMap));
        return hashMap;
    }

    /**
     * 添加
     * @param student
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Student student) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (StringUtils.isEmpty(student.getUsername())) {
            hashMap.put("type","error");
            hashMap.put("msg", "姓名不能为空");
            return hashMap;
        }
        if (StringUtils.isEmpty(student.getPassword())) {
            hashMap.put("type","error");
            hashMap.put("msg", "密码不能为空");
            return hashMap;
        }
        if (student.getStudentNumber() == null) {
            hashMap.put("type","error");
            hashMap.put("msg", "学号不能为空");
            return hashMap;
        }
        if (studentService.add(student) <= 0) {
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
     * @param student
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Student student) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (StringUtils.isEmpty(student.getUsername())) {
            hashMap.put("type","error");
            hashMap.put("msg", "名称不能为空");
            return hashMap;
        }
        if (student.getClazzId() == null) {
            hashMap.put("type","error");
            hashMap.put("msg", "所属班级不能为空");
            return hashMap;
        }
        if (studentService.edit(student) <= 0) {
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
            if (studentService.delete(string) <= 0) {
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


    /**
     * 上传用户头像图片1(这个是将上传图片的公共代码抽离出来。提高代码的可复用性)
     * @param photo
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPhoto(MultipartFile photo, HttpServletRequest request) {
        //保存在target包下的项目目录下
        final String dirPath = request.getServletContext().getRealPath("/upload/");
        //存储头像的项目发布目录
        final String uploadPath =  request.getServletContext().getContextPath() + "/upload/";
        //返回头像的上传结果
        return UploadFile.getUploadResult(photo, dirPath, uploadPath);
    }

    /**
     * 上传用户头像图片2
     * @param photo
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/upload_photo",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadPhoto(MultipartFile photo,
                                           HttpServletRequest request,
                                           HttpServletResponse response
    ) throws IOException{
        Map<String, String> ret = new HashMap<>();
        if(photo == null){
            //文件没有选择
            ret.put("type", "error");
            ret.put("msg", "请选择文件！");
            return ret;
        }

        if(photo.getSize() > upload_File_MaxSize){
            //文件没有选择
            ret.put("type", "error");
            ret.put("msg", "文件大小超过10M，请上传小于10M的图片！");
            return ret;
        }
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().
                lastIndexOf(".") + 1,photo.getOriginalFilename().length());
        if(!upload_File_Type.contains(suffix.toLowerCase())){
            ret.put("type", "error");
            ret.put("msg", "文件格式不正确，请上传jpg,png,gif,jpeg格式的图片！");
            return ret;
        }
        String savePath = request.getServletContext().getRealPath("/") + "\\upload\\";
        System.out.println(savePath);
        File savePathFile = new File(savePath);
        if(!savePathFile.exists()){
            savePathFile.mkdir();//如果不存在，则创建一个文件夹upload
        }
        //把文件转存到这个文件夹下
        String filename = System.currentTimeMillis() + "." + suffix;
        photo.transferTo(new File(savePath + filename ));
        ret.put("type", "success");
        ret.put("msg", "图片上传成功！");
        ret.put("src", request.getServletContext().getContextPath() + "/upload/" + filename);
        return ret;
    }
}
