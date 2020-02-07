package com.kopasolar.controllers.wrappers;

/**
 * Class name: AddstockWrapper
 * Creater: wgicheru
 * Date:2/7/2020
 */
public class AddstockWrapper {
    int productid;
    int quantity;
    int minquantity;

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinquantity() {
        return minquantity;
    }

    public void setMinquantity(int minquantity) {
        this.minquantity = minquantity;
    }
}
