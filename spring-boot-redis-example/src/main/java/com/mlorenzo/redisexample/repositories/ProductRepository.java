package com.mlorenzo.redisexample.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.redisexample.domain.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
}
