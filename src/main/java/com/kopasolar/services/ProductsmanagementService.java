package com.kopasolar.services;

import com.kopasolar.controllers.wrappers.ResponseWrapper;
import com.kopasolar.controllers.wrappers.StocksWrapper;
import com.kopasolar.database.KopaproductsRepo;
import com.kopasolar.database.KopastockRepo;
import com.kopasolar.database.models.KopaProducts;
import com.kopasolar.database.models.KopaStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class name: ProductsEngine
 * Creater: wgicheru
 * Date:1/29/2020
 */
@Service
public class ProductsmanagementService {
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
        kopaProducts.setCreatedon(new Date());

        kopaproductsRepo.save(kopaProducts);
        return new ResponseWrapper("success","product created successfully");
    }

    public List<KopaProducts> getProducts(){
        return kopaproductsRepo.findAll();
    }

    public ResponseWrapper createStock(int productid, int quantity,int minquantity){
        //confirm that the productid does not exist
        int productcount = kopastockRepo.countByProductid(productid);
        if(productcount>0){
            return new ResponseWrapper("failed","could not create stock, already exists");
        }
        KopaStock kopaStock = new KopaStock();
        kopaStock.setQuantity(quantity);
        kopaStock.setMinquantity(minquantity);
        kopaStock.setProductid(productid);
        kopaStock.setLastorder(new Date());

        kopastockRepo.save(kopaStock);
        return new ResponseWrapper("success","product created successfully");
    }

    public ResponseWrapper restock(int productid, int quantity){
        //confirm stock exists
        int stockcount = kopastockRepo.countByProductid(productid);
        if (stockcount<1){
            return new ResponseWrapper("failed","stock item does not exist");
        }

        KopaStock kopaStock = kopastockRepo.findByProductid(productid);
        kopaStock.setQuantity(kopaStock.getQuantity() + quantity);
        kopaStock.setLastorder(new Date());
        kopastockRepo.save(kopaStock);
        return new ResponseWrapper("success","stock updated successfully");
    }

    public List<StocksWrapper> getStock(){
        List<KopaStock> kopastocklist = kopastockRepo.findAll();
        List<StocksWrapper> stocksWrappers = new ArrayList<>();
        kopastocklist.stream().forEach((stockitem)->{
            String productname = kopaproductsRepo.findById(stockitem.getProductid()).get().getName();
            StocksWrapper stocksWrapper = new StocksWrapper();
            stocksWrapper.setLastorder(stockitem.getLastorder());
            stocksWrapper.setMinquantity(stockitem.getMinquantity());
            stocksWrapper.setProductname(productname);
            stocksWrapper.setQuantity(stockitem.getQuantity());
            stocksWrapper.setStockid(stockitem.getStockid());

            stocksWrappers.add(stocksWrapper);
        });
        return stocksWrappers;
    }
}
