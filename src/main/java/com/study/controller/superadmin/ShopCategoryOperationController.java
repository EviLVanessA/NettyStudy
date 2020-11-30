package com.study.controller.superadmin;

import com.study.entity.bo.ShopCategory;
import com.study.entity.dto.Result;
import com.study.service.solo.ShopCategoryService;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author jianghui
 * @date 2020-11-25 19:46
 */
@Controller
public class ShopCategoryOperationController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    /**
     * 新增分类
     * @return true or false
     */
    public Result<Boolean> addShopCategory(HttpServletRequest req, HttpServletResponse resp){
        return shopCategoryService.addShopCategory(new ShopCategory());
    }

    /**
     *  根据Id删除分类
     * @return true or false
     */
    public Result<Boolean> removeShopCategory(HttpServletRequest req, HttpServletResponse resp){
        return shopCategoryService.removeShopCategory(1);
    }

    /**
     * 修改分类信息
     * @return true or false
     */
    public Result<Boolean> modifyShopCategory(HttpServletRequest req, HttpServletResponse resp){
        return shopCategoryService.modifyShopCategory(null);
    }

    /**
     * 根据ID查找分类信息
     * @return 符合查询结果的分类信息
     */
    public Result<ShopCategory> queryShopCategoryById(HttpServletRequest req, HttpServletResponse resp){
        return shopCategoryService.queryShopCategoryById(1);
    }

    /**
     * 获取全部的分类信息
     * @return 返回分类列表
     */
    public Result<List<ShopCategory>> queryAllShopCategory(HttpServletRequest req, HttpServletResponse resp){
        return shopCategoryService.queryAllShopCategory(null,1,5);
    }

}
