package org.simpleframework.mvc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.mvc.processor.RequestProcessor;
import org.simpleframework.mvc.render.ResultRender;
import org.simpleframework.mvc.render.impl.DefaultResultRender;
import org.simpleframework.mvc.render.impl.InternalErrorResultRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * 该类以责任链模式执行注册的请求处理器
 * 委派给特定的Render实例对处理后的结果进行渲染
 *
 * @author jianghui
 * @date 2021-03-04 10:17
 */
@Data
@Slf4j
public class RequestProcessorChain {
    /**
     * 请求处理器迭代器
     */
    private Iterator<RequestProcessor> requestProcessorIterator;
    /**
     * 请求request
     */
    private HttpServletRequest request;
    /**
     * 请求的response
     */
    private HttpServletResponse response;
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * http请求路径
     */
    private String requestPath;
    /**
     * 响应的请求状态码
     */
    private int responseCode;
    /**
     * 请求结果渲染器
     */
    private ResultRender resultRender;

    public RequestProcessorChain(Iterator<RequestProcessor> requestProcessorIterator, HttpServletRequest req, HttpServletResponse resp) {
        this.requestProcessorIterator = requestProcessorIterator;
        this.request = req;
        this.response = resp;
        this.requestMethod = req.getMethod();
        this.requestPath = req.getPathInfo();
        this.responseCode = HttpServletResponse.SC_OK;

    }

    /**
     * 通过责任链模式来依次调用请求处理器对请求进行处理
     */
    public void doRequestProcessorChain() {
        //通过迭代器遍历注册的请求处理器实现类列表
        try {
            while (requestProcessorIterator.hasNext()) {
                //直到某个请求处理器执行后返回false为止
                RequestProcessor requestProcessor = requestProcessorIterator.next();
                if (!requestProcessor.process(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            //期间如果出现异常，则交由内部异常渲染器处理
            this.resultRender = new InternalErrorResultRender();
            log.error("doRequestProcessorChain:" + e);
        }

    }

    /**
     * 对处理结果进行渲染
     */
    public void doRender() {
        //如果请求处理器均未选择出合适的渲染器,则使用默认的渲染器
        if (this.resultRender == null) {
            this.resultRender = new DefaultResultRender();
        }
        //调用渲染器的render方法对结果进行渲染
        try {
            resultRender.render(this);
        } catch (Exception e) {
            log.error("doRender error: " + e);
            throw new RuntimeException(e);
        }
    }
}
