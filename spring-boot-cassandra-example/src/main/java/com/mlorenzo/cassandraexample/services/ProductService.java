package com.mlorenzo.cassandraexample.services;

import java.util.List;
import java.util.UUID;

import com.mlorenzo.cassandraexample.commands.ProductForm;
import com.mlorenzo.cassandraexample.domain.Product;

public interface ProductService {
    List<Product> listAll();
    Product getById(UUID id);
    Product saveOrUpdate(Product product);
    void delete(UUID id);
    Product saveOrUpdateProductForm(ProductForm productForm);
}
