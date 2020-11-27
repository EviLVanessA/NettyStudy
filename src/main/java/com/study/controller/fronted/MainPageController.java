package com.study.controller.fronted;

import com.study.entity.dto.MainPageInfoDTO;
import com.study.entity.dto.Result;
import com.study.service.combine.HeadLineShopCategoryCombineService;
import org.simpleframework.core.annotation.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jianghui
 * @date 2020-11-25 19:10
 */
@Controller
public class MainPageController {
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;
    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
}
