package com.kopasolar.controllers.wrappers;

import java.util.Date;

/**
 * Class name: SalesWrapper
 * Creater: wgicheru
 * Date:2/7/2020
 */
public class SalesWrapper {
    int quantity;
    double cost;
    Date date;
    String salesid;
    String productname;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSalesid() {
        return salesid;
    }

    public void setSalesid(String salesid) {
        this.salesid = salesid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}
