package com.sunny.util;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/21
 *
 * String工具类
 */
public class StringUtil {

    /**
     * 将指定的list按照指定的分隔符分隔
     * @param list
     * @param spilt 分隔符：，。等
     * @return
     */
    public static String joinString(List<Object> list, String spilt) {
        String s = "";
        for (Object o : list) {
            s = o + spilt;
        }
        if (!"".equals(s)) {
            s = s.substring(0, s.length() - spilt.length());
        }

        return s;
    }

}
