package com.kopasolar.controllers.wrappers;

/**
 * Class name: ProductWrapper
 * Creater: wgicheru
 * Date:1/29/2020
 */
public class ProductWrapper {
    String name;
    String description;
    double price;

    public String getName() {
        return name.toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
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
