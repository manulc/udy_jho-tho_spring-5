package com.mlorenzo.mongodbexample.services;

import java.util.List;

import com.mlorenzo.mongodbexample.commands.ProductForm;
import com.mlorenzo.mongodbexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(String id);
    Product saveOrUpdate(Product product);
    void delete(String id);
    Product saveOrUpdateProductForm(ProductForm productForm);
}
