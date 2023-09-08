package com.mlorenzo.db2example.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.db2example.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
