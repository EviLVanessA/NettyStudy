package org.simpleframework.mvc.render.impl;

import com.google.gson.Gson;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.render.ResultRender;

import java.io.PrintWriter;

/**
 * json渲染器
 * @author jianghui
 * @date 2021-03-04 12:26
 */
public class JsonResultRender implements ResultRender {
    private Object jsonData;
    public JsonResultRender(Object jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        //设置响应头
        requestProcessorChain.getResponse().setContentType("application/json");
        //设置字符集编码
        requestProcessorChain.getResponse().setCharacterEncoding("UTF-8");
        //响应流写入经过gson格式化之后的处理结果
        try (PrintWriter writer = requestProcessorChain.getResponse().getWriter()) {
            Gson gson = new Gson();
            writer.write(gson.toJson(jsonData));
            writer.flush();
        }
    }
}
