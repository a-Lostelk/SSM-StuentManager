package com.sunny.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    private Integer number;

    private String teacherName;

    private String email;

    private String telephone;

    private String remark;

    public Clazz(){

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gradeId, name, remark);
    }

    @Override

    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", gradeId=" + gradeId +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
