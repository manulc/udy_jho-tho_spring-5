package com.mlorenzo.redisexample.services;

import java.util.List;

import com.mlorenzo.redisexample.commands.ProductForm;
import com.mlorenzo.redisexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(String id);
    Product saveOrUpdate(Product product);
    void delete(String id);
    Product saveOrUpdateProductForm(ProductForm productForm);
}
