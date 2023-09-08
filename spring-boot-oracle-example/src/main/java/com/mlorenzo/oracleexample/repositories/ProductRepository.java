package com.mlorenzo.oracleexample.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.oracleexample.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
