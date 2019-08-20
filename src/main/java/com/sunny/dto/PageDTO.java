package com.sunny.dto;

import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: fang
 * @Date: 2019/8/19
 *
 * 分页类封装
 */
@Component
public class PageDTO {

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页显示数量
     */
    private Integer rows;

    /**
     * 偏移量
     */
    private Integer offset;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getOffset() {
        this.offset = (page - 1) * rows;
        return offset;
    }

    /**
     * 当前页是第1页，数据库中的偏移量是从下标为0的第一条数据开始
     * 当前页是第2页，(1-1)*10=0 (2-1)*10=10 (3-1)*10=20,以此类推
     */
    public void setOffset(Integer offset) {
        this.offset = (page - 1) * rows;
    }
}
