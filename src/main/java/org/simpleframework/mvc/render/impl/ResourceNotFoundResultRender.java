package org.simpleframework.mvc.render.impl;

import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * 资源找不到时的渲染器
 *
 * @author jianghui
 * @date 2021-03-04 12:26
 */
public class ResourceNotFoundResultRender implements ResultRender {

    private String httpMethod;
    private String httpPath;

    public ResourceNotFoundResultRender(String httpMethod, String httpPath) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND,
                "获取不到对应的请求资源:请求路径为" + httpPath + "  方法为：" + httpMethod);
    }
}
