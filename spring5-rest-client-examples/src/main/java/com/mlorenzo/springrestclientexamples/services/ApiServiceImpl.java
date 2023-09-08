package com.mlorenzo.springrestclientexamples.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mlorenzo.springrestclientexamples.models.User;

@Service
public class ApiServiceImpl implements ApiService {
	private final RestTemplate restTemplate;
	private final String apiUrl;
	
	public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}")String apiUrl) {
		this.restTemplate = restTemplate;
		this.apiUrl = apiUrl;
	}

	@Override
	public List<User> getUsers(Integer limit) {
		// Este componente nos permite construir una Uri y pasarle parámetros. Es muy util cuando tenemos que pasar una gran cantidad de parámetros a una Uri
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromUriString(apiUrl)
				.queryParam("_limit", limit);
		//ResponseEntity<List<User>> response = restTemplate.exchange(apiUrl + "?_limit=" + limit, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
		// Otra manera de usar RestTemplate usando un componente UriComponentsBuilder
		ResponseEntity<List<User>> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
		return response.getBody();
	}

}
