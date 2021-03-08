package org.simpleframework.mvc.render.impl;

import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.render.ResultRender;
import org.simpleframework.mvc.type.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 页面渲染器
 *
 * @author jianghui
 * @date 2021-03-04 12:26
 */
public class ViewResultRender implements ResultRender {
    private static final String VIEW_PATH = "/templates/";
    private ModelAndView modelAndView;

    public ViewResultRender(Object mv) {
        if (mv instanceof ModelAndView) {
            this.modelAndView = (ModelAndView) mv;
        } else if (mv instanceof String) {
            this.modelAndView = new ModelAndView().setView((String) mv);
        } else {
            throw new RuntimeException("Illegal request result type");
        }
    }

    /**
     * 将请求处理结果按照试图路径转发至对应视图进行展示
     *
     * @param requestProcessorChain
     * @throws Exception
     */
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        HttpServletRequest request = requestProcessorChain.getRequest();
        HttpServletResponse response = requestProcessorChain.getResponse();
        String path = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        for (String key : model.keySet()) {
            request.setAttribute(key, model.get(key));
        }
        //JSP视图的展示
        request.getRequestDispatcher(VIEW_PATH + path).forward(request, response);
    }
}
