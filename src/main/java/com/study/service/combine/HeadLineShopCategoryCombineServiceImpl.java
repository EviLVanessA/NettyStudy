package com.study.service.combine;

import com.study.entity.bo.HeadLine;
import com.study.entity.bo.ShopCategory;
import com.study.entity.dto.MainPageInfoDTO;
import com.study.entity.dto.Result;
import com.study.service.solo.HeadLineService;
import com.study.service.solo.ShopCategoryService;

import java.util.List;

/**
 * @author jianghui
 * @date 2020-11-25 14:35
 */
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {
    private HeadLineService headLineService;
    private ShopCategoryService shopCategoryService;

    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        //获取头条列表
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        Result<List<HeadLine>> queryAllHeadLine = headLineService.queryAllHeadLine(headLineCondition, 1, 5);
        //获取类别列表
        ShopCategory shopCategoryCondition = new ShopCategory();
        Result<List<ShopCategory>> queryAllShopCategory = this.shopCategoryService.queryAllShopCategory(shopCategoryCondition, 1, 100);
        //合并
        return mergeMainPageInfoResult(queryAllHeadLine.getData(),queryAllShopCategory.getData());
    }

    private Result<MainPageInfoDTO> mergeMainPageInfoResult(List<HeadLine> queryAllHeadLine, List<ShopCategory> queryAllShopCategory) {
        MainPageInfoDTO mainPageInfoDTO = new MainPageInfoDTO(queryAllHeadLine, queryAllShopCategory);
        Result<MainPageInfoDTO> result = new Result<>();
        result.setData(mainPageInfoDTO);
        return result;
    }
}
