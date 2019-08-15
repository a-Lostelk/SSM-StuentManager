package com.sunny.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/15
 */
@Data
public class LoginParamDTO {
    private String username;
    private String password;
    private String vcode;
    private int type;
}
