package com.mlorenzo.spring5webfluxrest.domain.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mlorenzo.spring5webfluxrest.domain.Category;

public interface CategoryRepository extends ReactiveMongoRepository<Category,String>{

}
