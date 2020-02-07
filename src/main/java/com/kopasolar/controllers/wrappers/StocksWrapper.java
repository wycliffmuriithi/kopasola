package com.kopasolar.controllers.wrappers;

import java.util.Date;

/**
 * Class name: StocksWrapper
 * Creater: wgicheru
 * Date:2/7/2020
 */
public class StocksWrapper {
    int quantity;
    int minquantity;
    Date lastorder;
    String productname;
    int stockid;

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

    public Date getLastorder() {
        return lastorder;
    }

    public void setLastorder(Date lastorder) {
        this.lastorder = lastorder;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getStockid() {
        return stockid;
    }

    public void setStockid(int stockid) {
        this.stockid = stockid;
    }
}
