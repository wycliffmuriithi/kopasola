package com.kopasolar.controllers.wrappers;

/**
 * Class name: PaymentsWrapper
 * Creater: wgicheru
 * Date:2/7/2020
 */
public class PaymentsWrapper {
    String orderid;
    double amount;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
