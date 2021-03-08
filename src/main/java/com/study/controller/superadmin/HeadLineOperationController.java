package com.study.controller.superadmin;

import com.study.entity.bo.HeadLine;
import com.study.entity.dto.Result;
import com.study.service.solo.HeadLineService;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;
import org.simpleframework.mvc.annotation.RequestMapping;
import org.simpleframework.mvc.annotation.RequestParam;
import org.simpleframework.mvc.annotation.ResponseBody;
import org.simpleframework.mvc.type.ModelAndView;
import org.simpleframework.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * stu: java.lang.reflect类库里面主要的类：
 * Field:表示类中的成员变量
 * Method：表示类中的方法
 * Constructor：表示类的构造函数
 * Array：该类提供了动态创建数组和访问数据元素的静态方法
 * 反射依赖的Class
 * Class类也是类的一种，class则是关键字
 * Class类只有一个私有的构造函数，只有JVM能够创建Class类的实例
 * JVM中只有唯一一个和类相应的Class对象来描述其类型信息
 * 获取Class对象的三种方式
 * Object   ->  getClass()
 * 任何数据类型都有一个静态的class属性
 * 通过Class的静态方法forName(String className) imp:常用
 * stu: 在运行期间，一个类，只有一个与之相对应的Class对象产生
 * 反射的主要用法
 * 获取类的构造方法、获取类的成员变量、获取类的成员方法
 *
 * @author jianghui
 * @date 2020-11-25 19:39
 */
@Controller
@RequestMapping(value = "/headline")
public class HeadLineOperationController {
    @Autowired(value = "HeadLineServiceImpl")
    private HeadLineService headLineService;

    /**
     * 新增头条
     *
     * @return true or false
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addHeadLine(@RequestParam("lineName") String lineName,
                                    @RequestParam("lineLink") String lineLink,
                                    @RequestParam("lineImg") String lineImg,
                                    @RequestParam("priority") Integer priority) {
        HeadLine headLine = new HeadLine();
        headLine.setLineImg(lineImg);
        headLine.setLineLink(lineLink);
        headLine.setLineName(lineName);
        headLine.setPriority(priority);
        Result<Boolean> result = headLineService.addHeadLine(headLine);
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView.setView("addHeadline.jsp").addViewData("result", result);
    }

    /**
     * 根据Id删除头条
     *
     * @return true or false
     */
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public void removeHeadLine() {
        //TODO:参数的校验以及请求参数转化
        System.out.println("删除headline");
//        return headLineService.removeHeadLine(1);
    }

    /**
     * 修改头条信息
     *
     * @return true or false
     */
    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        //TODO:参数的校验以及请求参数转化
        return headLineService.modifyHeadLine(new HeadLine());
    }

    /**
     * 根据ID查找头条信息
     *
     * @return 符合查询结果的头条信息
     */
    public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.queryHeadLineById(1);
    }

    /**
     * 获取全部的头条信息
     *
     * @return 返回头条列表
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<HeadLine>> queryAllHeadLine() {
        return headLineService.queryAllHeadLine(null, 1, 5);
    }


}
