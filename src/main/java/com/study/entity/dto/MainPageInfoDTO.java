package com.study.entity.dto;

import com.study.entity.bo.HeadLine;
import com.study.entity.bo.ShopCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * dto 数据传输对象
 * @author jianghui
 * @date 2020-11-25 14:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainPageInfoDTO {
    /**
     * 全部的头条信息
     */
    private List<HeadLine> headLines;
    /**
     * 全部的分类信息
     */
    private List<ShopCategory> shopCategories;
}
