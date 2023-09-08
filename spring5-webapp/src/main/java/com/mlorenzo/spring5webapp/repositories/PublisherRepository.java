package com.mlorenzo.spring5webapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.spring5webapp.domain.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

}
