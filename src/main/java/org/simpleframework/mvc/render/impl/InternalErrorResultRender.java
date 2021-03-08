package org.simpleframework.mvc.render.impl;

import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * 内部异常渲染器
 *
 * @author jianghui
 * @date 2021-03-04 12:26
 */
public class InternalErrorResultRender implements ResultRender {
    private String errorMsg;

    public InternalErrorResultRender(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        //给响应流发送错误码和状态信息
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMsg);
    }
}
