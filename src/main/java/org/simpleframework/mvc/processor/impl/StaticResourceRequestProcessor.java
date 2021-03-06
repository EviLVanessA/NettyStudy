package org.simpleframework.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * 静态资源请求处理，包括但不限于图片、CSS、以及JS文件等
 *
 * @author jianghui
 * @date 2021-03-04 10:20
 */
@Slf4j
public class StaticResourceRequestProcessor implements RequestProcessor {
    /**
     * 默认servlet常量
     */
    public static final String DEFAULT_TOMCAT_SERVLET = "default";
    /**
     * 静态资源请求地址
     */
    public static final String STATIC_RESOURCE_PREFIX = "/static/";
    /**
     * Tomcat默认请求派发器RequestDispatcher
     */
    private final RequestDispatcher defaultDispatcher;


    public StaticResourceRequestProcessor(ServletContext servletContext) {
        this.defaultDispatcher = servletContext.getNamedDispatcher(DEFAULT_TOMCAT_SERVLET);
        if (this.defaultDispatcher == null) {
            throw new RuntimeException("There is no default tomcat servlet");
        }
        log.info("The default servlet for static resource is {}", DEFAULT_TOMCAT_SERVLET);
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        //通过请求路径判断是否是请求静态资源webapp/static
        if (isStaticResource(requestProcessorChain.getRequestPath())) {
            //是静态资源的话则将请求转发给default Servlet进行处理
            defaultDispatcher.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 判断请求路径前缀是否为静态资源/static/来判断
     *
     * @param path
     * @return
     */
    private boolean isStaticResource(String path) {
        return path.startsWith(STATIC_RESOURCE_PREFIX);
    }

}
