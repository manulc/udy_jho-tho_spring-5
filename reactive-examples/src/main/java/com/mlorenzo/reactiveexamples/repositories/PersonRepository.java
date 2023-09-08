package com.mlorenzo.reactiveexamples.repositories;

import com.mlorenzo.reactiveexamples.domain.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {
	Mono<Person> getById(final Integer id);
	Flux<Person> findAll();
}
