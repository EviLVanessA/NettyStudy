package demo.pattern.proxy;

import demo.pattern.proxy.impl.AliPayToC;
import demo.pattern.proxy.impl.ToCPaymentImpl;

/**
 * @author jianghui
 * @date 2021-02-26 10:44
 */
public class ProxyDemo {
    public static void main(String[] args) {
        new AliPayToC(new ToCPaymentImpl()).pay();
    }
}
