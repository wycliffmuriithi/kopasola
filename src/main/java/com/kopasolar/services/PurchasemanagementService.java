package com.kopasolar.services;

import com.kopasolar.controllers.wrappers.ResponseWrapper;
import com.kopasolar.controllers.wrappers.SalesWrapper;
import com.kopasolar.database.KopaproductsRepo;
import com.kopasolar.database.KopasalesRepo;
import com.kopasolar.database.KopastockRepo;
import com.kopasolar.database.SalespaymentsRepo;
import com.kopasolar.database.models.KopaProducts;
import com.kopasolar.database.models.KopaSales;
import com.kopasolar.database.models.KopaStock;
import com.kopasolar.database.models.SalesPayments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Class name: PurchasemanagementService
 * Creater: wgicheru
 * Date:2/7/2020
 */
@Service
public class PurchasemanagementService {
    @Autowired
    KopastockRepo kopastockRepo;
    @Autowired
    KopasalesRepo kopasalesRepo;
    @Autowired
    KopaproductsRepo kopaproductsRepo;
    @Autowired
    SalespaymentsRepo salespaymentsRepo;

    public ResponseWrapper makeSale(int productid, int quantity) {
        int stockcount = kopastockRepo.countByProductid(productid);
        if (stockcount < 1) {
            return new ResponseWrapper("failed", "stock does not exist");
        }
        KopaStock kopaStock = kopastockRepo.findByProductid(productid);
        if (kopaStock.getQuantity() < quantity) {
            return new ResponseWrapper("failed", "not enough stock for transaction");
        }

        kopaStock.setQuantity(kopaStock.getQuantity() - quantity);
        kopastockRepo.save(kopaStock);

        KopaSales kopaSales = new KopaSales();
        KopaProducts kopaProducts = kopaproductsRepo.findById(kopaStock.getProductid()).get();
        kopaSales.setCost(kopaProducts.getPrice() * quantity);
        kopaSales.setStockid(kopaStock.getStockid());
        kopaSales.setDate(new Date());
        kopaSales.setQuantity(quantity);
        int randomint = new Random().nextInt(10000) + 9999;
        kopaSales.setOrderid("PO-" + randomint);
        kopasalesRepo.save(kopaSales);
        return new ResponseWrapper("success", "sales recorded successfully");
    }

    public List<SalesWrapper> getSales() {
        List<KopaSales> kopaSales = kopasalesRepo.findAll();
        List<SalesWrapper> salesWrappers = new ArrayList<>();
        kopaSales.stream().forEach((kopasale) -> {
            String productname = getproductname(kopasale.getStockid());
            SalesWrapper salesWrapper = new SalesWrapper();
            salesWrapper.setCost(kopasale.getCost());
            salesWrapper.setDate(kopasale.getDate());
            salesWrapper.setProductname(productname);
            salesWrapper.setQuantity(kopasale.getQuantity());
            salesWrapper.setSalesid(kopasale.getOrderid());

            salesWrappers.add(salesWrapper);
        });
        return salesWrappers;
    }

    private String getproductname(int stockid) {
        int productid = kopastockRepo.findById(stockid).get().getProductid();
        return kopaproductsRepo.findById(productid).get().getName();
    }

    public ResponseWrapper makePayment(String orderid, double amount){
        SalesPayments salesPayments = new SalesPayments();
        salesPayments.setAmount(amount);
        salesPayments.setOrderid(orderid);
        salesPayments.setDatepaid(new Date());

        salespaymentsRepo.save(salesPayments);

        return new ResponseWrapper("success","payment created successfully");
    }

    public List<SalesPayments> getPayments(){
        return salespaymentsRepo.findAll();
    }
}
