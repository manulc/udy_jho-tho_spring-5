package com.mlorenzo.mariadbexample.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.mariadbexample.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
