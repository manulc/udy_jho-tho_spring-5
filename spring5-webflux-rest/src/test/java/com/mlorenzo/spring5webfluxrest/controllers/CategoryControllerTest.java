package com.mlorenzo.spring5webfluxrest.controllers;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mlorenzo.spring5webfluxrest.domain.Category;
import com.mlorenzo.spring5webfluxrest.domain.repositories.CategoryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CategoryControllerTest {
	WebTestClient webTestClient;
	CategoryRepository categoryRepository;
	CategoryController categoryController;
	
	@Before
	public void setUp() throws Exception{
		categoryRepository = Mockito.mock(CategoryRepository.class);
		categoryController = new CategoryController(categoryRepository);
		webTestClient = WebTestClient.bindToController(categoryController).build();
	}
	
	@Test
	public void testListCategories() {
		// Una manera correcta
		/*given(categoryRepository.findAll())
	        .willReturn(Flux.just(Category.builder().description("Cat1").build(),
	                Category.builder().description("Cat2").build()));*/
		// Otra manera correcta
		when(categoryRepository.findAll()).thenReturn(Flux.just(Category.builder().description("Cat1").build(),
	                Category.builder().description("Cat2").build()));
		webTestClient.get()
		        .uri("/api/v1/categories/")
		        .accept(MediaType.APPLICATION_JSON)
		        .exchange()
		        .expectBodyList(Category.class)
		        .hasSize(2);
	}
	
	@Test
	public void testGetCategoryById() {
		// Una manera correcta
		given(categoryRepository.findById(anyString()))
        	.willReturn(Mono.just(Category.builder().description("Cat").build()));
		// Otra manera correcta
		//when(categoryRepository.findById(anyString())).thenReturn(Mono.just(Category.builder().description("Cat").build()));
		webTestClient.get()
	        .uri("/api/v1/categories/someId")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectBody(Category.class);
	}
	
	@Test
	public void testCreateCategories() {
		when(categoryRepository.saveAll(any(Publisher.class))).thenReturn(Flux.just(Category.builder().build()));
		// Nuestro método handler asociado a la ruta "/api/v1/categories" para peticiones http Post admite tanto un Mono, con una categoría a persistir en la base de datos, como un Flux con varias categorías 
		Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some Cat").build());
		//Flux<Category> catsToSaveFlux = Flux.just(Category.builder().description("Some Cat1").build(),Category.builder().description("Some Cat2").build());
		webTestClient.post()
	        .uri("/api/v1/categories")
	        .body(catToSaveMono,Category.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isCreated();
	}

	
	@Test
	public void testUpdateCategory() {
		given(categoryRepository.save(any(Category.class)))
    		.willReturn(Mono.just(Category.builder().build())); 
		Category catToUpdate = Category.builder().description("Some Cat").build();
		webTestClient.put()
	        .uri("/api/v1/categories/someId")
	        .body(Mono.just(catToUpdate), Category.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isOk();
	}
	
	@Test
	public void testPatchCategoryWithChanges() {
		given(categoryRepository.findById(anyString()))
			.willReturn(Mono.just(Category.builder().description("Some Cat1").build()));
		given(categoryRepository.save(any(Category.class)))
    		.willReturn(Mono.just(Category.builder().build()));
		Category catToUpdate = Category.builder().description("Some Cat2").build();
		webTestClient.patch()
	        .uri("/api/v1/categories/someId")
	        .body(Mono.just(catToUpdate), Category.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isOk();
		verify(categoryRepository).save(any(Category.class));
	}
	
	@Test
	public void testPatchCategoryWithoutChanges() {
		given(categoryRepository.findById(anyString()))
			.willReturn(Mono.just(Category.builder().description("Some Cat").build()));
		given(categoryRepository.save(any(Category.class)))
    		.willReturn(Mono.just(Category.builder().build()));
		Category catToUpdate = Category.builder().description("Some Cat").build();
		webTestClient.patch()
	        .uri("/api/v1/categories/someId")
	        .body(Mono.just(catToUpdate), Category.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isOk();
		verify(categoryRepository,never()).save(any(Category.class));
	}
}
