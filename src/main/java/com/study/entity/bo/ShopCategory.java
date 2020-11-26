package com.study.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商品分类
 * @author jianghui
 * @date 2020-11-25 09:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCategory {
    /**
     * 分类主键ID
     */
    private Long shopCategoryId;
    /**
     * 分类名称
     */
    private String shopCategoryName;
    /**
     * 分类描述
     */
    private String shopCategoryDesc;
    /**
     * 图片地址
     */
    private String shopCategoryImg;
    /**
     * 分类优先级
     */
    private Integer priority;
    /**
     * 分类创建时间
     */
    private Date createTime;
    /**
     * 分类最近修改时间
     */
    private Date lastEditTime;
    /**
     * 分类的父类
     */
    private ShopCategory parent;



}
