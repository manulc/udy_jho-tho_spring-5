package com.mlorenzo.mariadbexample.services;

import java.util.List;

import com.mlorenzo.mariadbexample.commands.ProductForm;
import com.mlorenzo.mariadbexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(Long id);
    Product saveOrUpdate(Product product);
    void delete(Long id);
    Product saveOrUpdateProductForm(ProductForm productForm);
}
