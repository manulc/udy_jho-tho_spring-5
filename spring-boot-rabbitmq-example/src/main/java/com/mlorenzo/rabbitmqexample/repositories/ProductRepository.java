package com.mlorenzo.rabbitmqexample.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.rabbitmqexample.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
