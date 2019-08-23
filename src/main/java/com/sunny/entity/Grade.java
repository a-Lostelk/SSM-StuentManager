package com.sunny.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/21
 *
 * 年级实体表
 */
@Component
@Data
public class Grade {

    private Long id;

    private String name;

    private String remark;
}
