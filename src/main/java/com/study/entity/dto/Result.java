package com.study.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果数据传输类
 * @author jianghui
 * @date 2020-11-25 14:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    /**
     * 请求结果返回的状态码
     */
    private int code;
    /**
     * 结果请求的详情
     */
    private String msg;
    /**
     * 请求结果的返回集
     */
    private T data;

}
