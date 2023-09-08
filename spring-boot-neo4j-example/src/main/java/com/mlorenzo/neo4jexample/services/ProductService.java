package com.mlorenzo.neo4jexample.services;

import java.util.List;

import com.mlorenzo.neo4jexample.commands.ProductForm;
import com.mlorenzo.neo4jexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(Long id);
    Product saveOrUpdate(Product product);
    void delete(Long id);
    Product saveOrUpdateProductForm(ProductForm productForm);
}
