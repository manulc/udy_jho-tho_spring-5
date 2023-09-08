package com.mlorenzo.activemqexample.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.activemqexample.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
