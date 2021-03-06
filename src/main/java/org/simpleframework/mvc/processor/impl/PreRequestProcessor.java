package org.simpleframework.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.processor.RequestProcessor;

/**
 * 对请求进行预处理操作,包括编码(UTF8编码)以及路径处理
 *
 * @author jianghui
 * @date 2021-03-04 10:17
 */
@Slf4j
public class PreRequestProcessor implements RequestProcessor {

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        //设置请求编码,将其统一设置为UTF-8
        requestProcessorChain.getRequest().setCharacterEncoding("UTF-8");
        //将请求路径末尾的 / 进行剔除操作,为后续匹配Controller请求路径做准备 假设传入aa/bb/ 则处理为aa/bb
        String requestPath = requestProcessorChain.getRequestPath();
        if (requestPath.length() > 1 && requestPath.endsWith("/")) {
            requestProcessorChain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
        }
        log.info("preprocess request {} {}",
                requestProcessorChain.getRequestMethod(), requestProcessorChain.getRequestPath());
        return true;
    }
}
