package com.mlorenzo.mongodbexample.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.mongodbexample.domain.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
}
