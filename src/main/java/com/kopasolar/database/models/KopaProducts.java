package com.kopasolar.database.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Class name: KopaProducts
 * Creater: wgicheru
 * Date:1/27/2020
 */
@Entity
public class KopaProducts {
    int productid;
    String name;
    String description;
    double price;
    Date createdon;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }
}
