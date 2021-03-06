package org.simpleframework.mvc.processor.impl;

import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * 负责jsp资源请求的处理操作
 *
 * @author jianghui
 * @date 2021-03-04 10:22
 */
public class JspRequestProcessor implements RequestProcessor {
    /**
     * jsp请求的RequestDispatcher的名称
     */
    private static final String JSP_SERVLET = "jsp";
    /**
     * jsp请求资源的路径前缀
     */
    private static final String JSP_RESOURCE_PREFIX = "/templates";
    /**
     * jsp的RequestDispatcher,处理jsp资源
     */
    private RequestDispatcher jspServlet;

    public JspRequestProcessor(ServletContext servletContext) {
        jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);
        if (null == jspServlet) {
            throw new RuntimeException("there is no jsp servlet");
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        if (isJspResource(requestProcessorChain.getRequestPath())){
            jspServlet.forward(requestProcessorChain.getRequest(),requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 判断是否为jsp资源
     * @param url
     * @return
     */
    private boolean isJspResource(String url){
        return url.startsWith(JSP_RESOURCE_PREFIX);
    }
}
