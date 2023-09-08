package com.mlorenzo.rabbitmqexample.services;

import java.util.List;

import com.mlorenzo.rabbitmqexample.commands.ProductForm;
import com.mlorenzo.rabbitmqexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(Long id);
    Product saveOrUpdate(Product product);
    void delete(Long id);
    Product saveOrUpdateProductForm(ProductForm productForm);
    void sendProductMessage(String id);
}
