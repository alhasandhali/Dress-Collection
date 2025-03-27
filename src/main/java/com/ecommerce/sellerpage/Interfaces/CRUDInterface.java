package com.ecommerce.sellerpage.Interfaces;

import com.ecommerce.sellerpage.Classes.Product;

import java.util.List;

public interface CRUDInterface {
    List<Product> getList();
    void insertIntoDB(Product product);
    void updateFromDB(Product product);
    void deleteFromDB(Product product);
}
