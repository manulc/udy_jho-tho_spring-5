package com.mlorenzo.cassandraexample.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.cassandraexample.domain.Product;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}
