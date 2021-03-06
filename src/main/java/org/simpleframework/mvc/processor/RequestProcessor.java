package org.simpleframework.mvc.processor;

import org.simpleframework.mvc.RequestProcessorChain;

/**
 * 请求处理器
 * @author jianghui
 * @date 2021-03-04 10:14
 */
public interface RequestProcessor {
    /**
     * 如果返回为true 则接着执行下一下Processor false终止
     * @param requestProcessorChain
     * @return
     * @throws Exception
     */
    boolean process(RequestProcessorChain requestProcessorChain) throws Exception;
}
