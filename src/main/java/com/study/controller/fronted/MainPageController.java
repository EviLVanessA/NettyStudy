package com.study.controller.fronted;

import com.study.entity.dto.MainPageInfoDTO;
import com.study.entity.dto.Result;
import com.study.service.combine.HeadLineShopCategoryCombineService;
import lombok.Data;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jianghui
 * @date 2020-11-25 19:10
 */
@Controller
@Data
public class MainPageController {
    @Autowired
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
}
