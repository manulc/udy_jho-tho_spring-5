package com.mlorenzo.springrestclientexamples.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mlorenzo.springrestclientexamples.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApiServiceImpl implements ApiService {
	private final String api_url;
	
	public ApiServiceImpl(@Value("${api.url}")String api_url) {
		this.api_url = api_url;
	}

	@Override
	public Flux<User> getUsers(Mono<Integer> limit) {
		// 2 maneras de hacerlo:
		// El método "flatMapMany" nos permite cambiar un flujo reactivo Mono a un flujo reactivo Flux, es decir, cuando tenemos un flujo reactivo Mono con un flujo reactivo Flux como elemento, aplicando este método sobre el flujo reactivo Mono, obtenemos como resultado el flujo reactivo Flux
		return limit.flatMapMany(l -> WebClient.create(api_url)
				.get()
				.uri(uriBuilder -> uriBuilder.queryParam("_limit",l).build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(User.class));
		/*return limit.flatMapMany(l -> WebClient.create(api_url)
				.get()
				.uri(uriBuilder -> uriBuilder.queryParam("_limit",l).build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.flatMapMany(resp -> resp.bodyToFlux(User.class)));*/
	}

}
