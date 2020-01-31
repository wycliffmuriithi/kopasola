package com.kopasolar.services;

import com.kopasolar.controllers.wrappers.ResponseWrapper;
import com.kopasolar.database.KopaproductsRepo;
import com.kopasolar.database.KopastockRepo;
import com.kopasolar.database.models.KopaProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class name: ProductsEngine
 * Creater: wgicheru
 * Date:1/29/2020
 */
@Service
public class ProductsEngine {
    @Autowired
    KopaproductsRepo kopaproductsRepo;

    @Autowired
    KopastockRepo kopastockRepo;

    public ResponseWrapper createProduct(String name, String description, double price){
        if(kopaproductsRepo.countByName(name)>0){
            //product exists
            return new ResponseWrapper("failed","product already exists");
        }
        KopaProducts kopaProducts = new KopaProducts();
        kopaProducts.setDescription(description);
        kopaProducts.setName(name);
        kopaProducts.setPrice(price);

        kopaproductsRepo.save(kopaProducts);
        return new ResponseWrapper("success","product created successfully");
    }

    public ResponseWrapper getProducts(){
        return new ResponseWrapper("success",kopaproductsRepo.findAll());
    }
}
