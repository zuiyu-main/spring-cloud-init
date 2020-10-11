package com.tz.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author https://github.com/TianPuJun @醉鱼
 * @ClassName ResultBean
 * @Description 统一返回结果
 * @Date 11:24 2020/10/11
 **/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultBean<T> {
    private String msg;
    private int code;
    private T data;
    private long total;
    private long timestamp;
    private String path;
}
