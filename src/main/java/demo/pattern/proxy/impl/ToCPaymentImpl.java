package demo.pattern.proxy.impl;

import demo.pattern.proxy.ToCPayment;

/**
 * @author jianghui
 * @date 2021-02-26 10:39
 */
public class ToCPaymentImpl implements ToCPayment {
    @Override
    public void pay() {
        System.out.println("用户支付");
    }
}
