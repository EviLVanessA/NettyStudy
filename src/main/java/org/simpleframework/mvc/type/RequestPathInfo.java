package org.simpleframework.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于封装Http请求路径和请求方法
 *
 * @author jianghui
 * @date 2021-03-05 12:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPathInfo {
    /**
     * 用于保存从httpServletRequest提取出来的请求方法
     */
    private String httpMethod;
    /**
     * 用于保存从httpServletRequest提取出来的请求路径
     */
    private String httpPath;
}
