package com.sunny.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/22
 */
@Component
@Data
public class Clazz {

    private Long id;

    /**
     * 年级ID
     */
    private Integer gradeId;

    private String name;

    private String remark;
}
