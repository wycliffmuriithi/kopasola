package com.kopasolar.controllers.wrappers;

/**
 * Class name: AddproductWrapper
 * Creater: wgicheru
 * Date:2/7/2020
 */
public class AddproductWrapper {
    String productname;
    String description;
    double price;

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
