package demo.pattern.proxy.impl;

import demo.pattern.proxy.ToCPayment;

/**
 * @author jianghui
 * @date 2021-02-26 10:40
 */
public class AliPayToC implements ToCPayment {
    private ToCPayment toCPayment;

    public AliPayToC(ToCPayment toCPayment) {
        this.toCPayment = toCPayment;
    }

    @Override
    public void pay() {
        beforePay();
        toCPayment.pay();
        afterPay();
    }

    private void afterPay() {
        System.out.println("支付给个人");
    }

    private void beforePay() {
        System.out.println("从建行取款");
    }
}
