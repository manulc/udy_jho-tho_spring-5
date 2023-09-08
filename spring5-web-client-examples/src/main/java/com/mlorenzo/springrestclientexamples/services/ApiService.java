package com.mlorenzo.springrestclientexamples.services;

import com.mlorenzo.springrestclientexamples.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiService {
	Flux<User> getUsers(Mono<Integer> limit);
}
