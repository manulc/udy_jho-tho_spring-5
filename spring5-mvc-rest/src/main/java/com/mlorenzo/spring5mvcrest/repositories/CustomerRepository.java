package com.mlorenzo.spring5mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlorenzo.spring5mvcrest.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
