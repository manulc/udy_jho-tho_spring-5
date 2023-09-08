package com.mlorenzo.spring5webfluxrest.domain.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mlorenzo.spring5webfluxrest.domain.Vendor;

public interface VendorRepository extends ReactiveMongoRepository<Vendor,String>{

}
