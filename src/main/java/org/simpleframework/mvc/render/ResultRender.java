package org.simpleframework.mvc.render;

import org.simpleframework.mvc.RequestProcessorChain;

/**
 * 渲染请求结果
 *
 * @author jianghui
 * @date 2021-03-04 12:24
 */
public interface ResultRender {
    /**
     * 执行渲染逻辑
     * @param requestProcessorChain
     * @throws Exception
     */
    void render(RequestProcessorChain requestProcessorChain) throws Exception;
}
