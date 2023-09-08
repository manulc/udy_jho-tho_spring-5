package com.mlorenzo.activemqexample.services;

import java.util.List;

import com.mlorenzo.activemqexample.commands.ProductForm;
import com.mlorenzo.activemqexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(Long id);
    Product saveOrUpdate(Product product);
    void delete(Long id);
    Product saveOrUpdateProductForm(ProductForm productForm);
    void sendMessage(String id);
}
