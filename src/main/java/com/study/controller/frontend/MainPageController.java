package com.study.controller.frontend;

import com.study.entity.dto.MainPageInfoDTO;
import com.study.entity.dto.Result;
import com.study.service.combine.HeadLineShopCategoryCombineService;
import lombok.Data;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;
import org.simpleframework.mvc.annotation.RequestMapping;
import org.simpleframework.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jianghui
 * @date 2020-11-25 19:10
 */
@Controller
@Data
@RequestMapping(value = "/main")
public class MainPageController {
    @Autowired
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
        return headLineShopCategoryCombineService.getMainPageInfo();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void throwException() {
        throw new RuntimeException("抛出异常测试");
    }
}
