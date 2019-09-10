package com.sunny.enums;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: fang
 * @Date: 2019/8/29
 *
 * 登录类型枚举
 */
public enum LoginType {
    ADMIN_TYPE(1),
    STUDENT_TYPE(2);

    private Integer type;

    public Integer getType() {
        return type;
    }

    LoginType(Integer type) {
        this.type = type;
    }
    public static boolean isExist(Integer type) {
        for (LoginType loginType  : LoginType.values()) {
            if (loginType.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

}
