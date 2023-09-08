package com.mlorenzo.mysqlexample.services;

import java.util.List;

import com.mlorenzo.mysqlexample.commands.ProductForm;
import com.mlorenzo.mysqlexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(Long id);
    Product saveOrUpdate(Product product);
    void delete(Long id);
    Product saveOrUpdateProductForm(ProductForm productForm);
}
