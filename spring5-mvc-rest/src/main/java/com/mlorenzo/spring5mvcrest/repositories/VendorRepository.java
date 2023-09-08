package com.mlorenzo.spring5mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlorenzo.spring5mvcrest.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor,Long>{

}
