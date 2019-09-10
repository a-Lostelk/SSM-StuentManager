package com.sunny.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: fang
 * @Date: 2019/8/24
 */
@Component
@Data
public class Student {

    private Long id;

    /**
     * 学号
     */
    private Long studentNumber;

    private String username;

    private String password;

    private String sex;

    private String phone;

    private String email;


    /**
     * 所属班级
     */
    private Long clazzId;

    /**
     * 所属年级
     */
    private Long gradeId;

    /**
     * 头像 保存的是图片的路径
     */
    private String photo;

    /**
     * 备注
     */
    private String remark;

}
