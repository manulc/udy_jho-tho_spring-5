package com.mlorenzo.postgresexample.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.postgresexample.domain.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {
}
