package com.mlorenzo.db2example.services;

import java.util.List;

import com.mlorenzo.db2example.commands.ProductForm;
import com.mlorenzo.db2example.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(Long id);
    Product saveOrUpdate(Product product);
    void delete(Long id);
    Product saveOrUpdateProductForm(ProductForm productForm);
}
