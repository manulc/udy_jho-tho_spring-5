package com.mlorenzo.postgresexample.services;

import java.util.List;

import com.mlorenzo.postgresexample.commands.ProductForm;
import com.mlorenzo.postgresexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(Long id);
    Product saveOrUpdate(Product product);
    void delete(Long id);
    Product saveOrUpdateProductForm(ProductForm productForm);
}
