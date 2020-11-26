package com.study.service.combine;

import com.study.entity.dto.MainPageInfoDTO;
import com.study.entity.dto.Result;

/**
 * 头条和分类结合的业务逻辑类
 * @author jianghui
 * @date 2020-11-25 14:32
 */
public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDTO> getMainPageInfo();
}
