package com.study.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 头条信息
 * @author jianghui
 * @date 2020-11-25 09:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadLine {
    /**
     * 头条ID
     */
    private Long lineId;
    /**
     * 头条名称
     */
    private String lineName;
    /**
     * 头条链接
     */
    private String lineLink;
    /**
     * 头条图片
     */
    private String lineImg;
    /**
     * 头条的显示优先级
     */
    private Integer priority;
    /**
     * 头条的可用性 1可用0不可用
     */
    private Integer enableStatus;
    /**
     * 头条最后的修改时间
     */
    private Date lastEditTime;


}
