package com.mlorenzo.neo4jexample.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.mlorenzo.neo4jexample.domain.Product;

public interface ProductRepository extends Neo4jRepository<Product, Long> {
}
