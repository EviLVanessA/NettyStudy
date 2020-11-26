package com.study.service.solo;

import com.study.entity.bo.ShopCategory;
import com.study.entity.dto.Result;

import java.util.List;

/**
 * @author jianghui
 * @date 2020-11-25 14:13
 */
public interface ShopCategoryService {
    /**
     * 新增分类
     * @param shopCategory 分类信息
     * @return true or false
     */
    Result<Boolean> addShopCategory(ShopCategory shopCategory);

    /**
     *  根据Id删除分类
     * @param shopCategoryId 分类id
     * @return true or false
     */
    Result<Boolean> removeShopCategory(int shopCategoryId);

    /**
     * 修改分类信息
     * @param shopCategory 分类信息
     * @return true or false
     */
    Result<Boolean> modifyShopCategory(ShopCategory shopCategory);

    /**
     * 根据ID查找分类信息
     * @param shopCategoryId 分类ID
     * @return 符合查询结果的分类信息
     */
    Result<ShopCategory> queryShopCategoryById(int shopCategoryId);

    /**
     * 获取全部的分类信息
     * @param shopCategory 查询条件
     * @param pageIndex 分页索引
     * @param pageSize  分页大小
     * @return 返回分类列表
     */
    Result<List<ShopCategory>> queryAllShopCategory(ShopCategory shopCategory, int pageIndex, int pageSize);
}
