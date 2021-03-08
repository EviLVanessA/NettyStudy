package org.simpleframework.mvc.render.impl;

import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.render.ResultRender;

/**
 * 默认的渲染器
 *
 * @author jianghui
 * @date 2021-03-04 12:26
 */
public class DefaultResultRender implements ResultRender {
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        int responseCode = requestProcessorChain.getResponseCode();
        requestProcessorChain.getResponse().setStatus(responseCode);
    }
}
