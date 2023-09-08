package com.mlorenzo.oracleexample.services;

import java.util.List;

import com.mlorenzo.oracleexample.commands.ProductForm;
import com.mlorenzo.oracleexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(Long id);
    Product saveOrUpdate(Product product);
    void delete(Long id);
    Product saveOrUpdateProductForm(ProductForm productForm);
}
