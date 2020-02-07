package com.kopasolar.database.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Class name: KopaSales
 * Creater: wgicheru
 * Date:2/7/2020
 */
@Entity
public class KopaSales {
    int salesid;
    int stockid;
    int quantity;
    double cost;
    String orderid;
    Date date;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getSalesid() {
        return salesid;
    }

    public void setSalesid(int salesid) {
        this.salesid = salesid;
    }

    public int getStockid() {
        return stockid;
    }

    public void setStockid(int stockid) {
        this.stockid = stockid;
    }

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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}

