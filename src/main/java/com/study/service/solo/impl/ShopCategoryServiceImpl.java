package com.study.service.solo.impl;

import com.study.entity.bo.ShopCategory;
import com.study.entity.dto.Result;
import com.study.service.solo.ShopCategoryService;

import java.util.List;

/**
 *
 * @author jianghui
 * @date 2020-11-25 14:30
 */
public class ShopCategoryServiceImpl implements ShopCategoryService{
    @Override
    public Result<Boolean> addShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<Boolean> removeShopCategory(int shopCategoryId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<ShopCategory> queryShopCategoryById(int shopCategoryId) {
        return null;
    }

    @Override
    public Result<List<ShopCategory>> queryAllShopCategory(ShopCategory shopCategory, int pageIndex, int pageSize) {
        return null;
    }
}
