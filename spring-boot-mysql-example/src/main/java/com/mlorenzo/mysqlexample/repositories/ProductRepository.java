package com.mlorenzo.mysqlexample.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.mysqlexample.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
