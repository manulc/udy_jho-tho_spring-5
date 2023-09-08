package com.mlorenzo.spring5webfluxrest.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mlorenzo.spring5webfluxrest.domain.Vendor;
import com.mlorenzo.spring5webfluxrest.domain.repositories.VendorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VendorControllerTest {
	WebTestClient webTestClient;
	VendorRepository vendorRepository;
	VendorController vendorController;
	
	@Before
	public void setUp() throws Exception{
		vendorRepository = Mockito.mock(VendorRepository.class);
		vendorController = new VendorController(vendorRepository);
		webTestClient = WebTestClient.bindToController(vendorController).build();
	}
	
	@Test
	public void testListVendors() {
		// Una manera correcta
		/*given(vendorRepository.findAll())
	        .willReturn(Flux.just(Vendor.builder().firstName("firstName1").lastName("lastName1").build(),
	                Vendor.builder().firstName("firstName2").lastName("lastName2").build()));*/
		// Otra manera correcta
		when(vendorRepository.findAll()).thenReturn(Flux.just(Vendor.builder().firstName("firstName1").lastName("lastName1").build(),
	                Vendor.builder().firstName("firstName2").lastName("lastName2").build()));
		webTestClient.get()
		        .uri("/api/v1/vendors")
		        .accept(MediaType.APPLICATION_JSON)
		        .exchange()
		        .expectBodyList(Vendor.class)
		        .hasSize(2);
	}
	
	@Test
	public void testGetVendorById() {
		// Una manera correcta
		given(vendorRepository.findById(anyString()))
        	.willReturn(Mono.just(Vendor.builder().firstName("firstName1").lastName("lastName1").build()));
		// Otra manera correcta
		//when(vendorRepository.findById(anyString())).thenReturn(Mono.just(Vendor.builder().firstName("firstName1").lastName("lastName1").build()));
		webTestClient.get()
	        .uri("/api/v1/vendors/someId")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectBody(Vendor.class);
	}
	
	@Test
	public void testCreateVendors() {
		given(vendorRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(Vendor.builder().build()));
		// Nuestro método handler asociado a la ruta "/api/v1/vendors" para peticiones http Post admite tanto un Mono, con un vendor a persistir en la base de datos, como un Flux con varios vendors 
		//Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder().firstName("Some FirstName").lastName("Some LastName").build());
		Flux<Vendor> vendorsToSaveFlux = Flux.just(Vendor.builder().firstName("Some FirstName1").lastName("Some LastName1").build(),Vendor.builder().firstName("Some FirstName2").lastName("Some LastName2").build());
		webTestClient.post()
	        .uri("/api/v1/vendors")
	        .body(vendorsToSaveFlux,Vendor.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isCreated();
	}
	
	@Test
	public void testUpdateVendor() {
		when(vendorRepository.save(any(Vendor.class))).thenReturn(Mono.just(Vendor.builder().build()));
		Vendor vendorToUpdate = Vendor.builder().firstName("FirstName").lastName("LastName").build();
		webTestClient.put()
	        .uri("/api/v1/vendors/someId")
	        .body(Mono.just(vendorToUpdate), Vendor.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isOk();
	}
	
	@Test
	public void testPatchFirstNameWithChanges() {
		given(vendorRepository.findById(anyString()))
			.willReturn(Mono.just(Vendor.builder().firstName("FirstName1").lastName("LastName1").build()));
		given(vendorRepository.save(any(Vendor.class)))
    		.willReturn(Mono.just(Vendor.builder().build()));
		Vendor vendorToUpdate = Vendor.builder().firstName("FirstName2").lastName("LastName1").build();
		webTestClient.patch()
	        .uri("/api/v1/vendors/someId")
	        .body(Mono.just(vendorToUpdate), Vendor.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isOk();
		verify(vendorRepository).save(any(Vendor.class));
	}
	
	@Test
	public void testPatchLastNameWithChanges() {
		given(vendorRepository.findById(anyString()))
			.willReturn(Mono.just(Vendor.builder().firstName("FirstName1").lastName("LastName1").build()));
		given(vendorRepository.save(any(Vendor.class)))
    		.willReturn(Mono.just(Vendor.builder().build()));
		Vendor vendorToUpdate = Vendor.builder().firstName("FirstName1").lastName("LastName2").build();
		webTestClient.patch()
	        .uri("/api/v1/vendors/someId")
	        .body(Mono.just(vendorToUpdate), Vendor.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isOk();
		verify(vendorRepository).save(any(Vendor.class));
	}
	
	public void testPatchFirstNameAndLastNameWithChanges() {
		given(vendorRepository.findById(anyString()))
			.willReturn(Mono.just(Vendor.builder().firstName("FirstName1").lastName("LastName1").build()));
		given(vendorRepository.save(any(Vendor.class)))
    		.willReturn(Mono.just(Vendor.builder().build()));
		Vendor vendorToUpdate = Vendor.builder().firstName("FirstName2").lastName("LastName2").build();
		webTestClient.patch()
	        .uri("/api/v1/vendors/someId")
	        .body(Mono.just(vendorToUpdate), Vendor.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isOk();
		
		verify(vendorRepository).save(any(Vendor.class));
	}
	
	@Test
	public void testPatchWithoutChanges() {
		given(vendorRepository.findById(anyString()))
			.willReturn(Mono.just(Vendor.builder().firstName("FirstName1").lastName("LastName1").build()));
		given(vendorRepository.save(any(Vendor.class)))
    		.willReturn(Mono.just(Vendor.builder().build()));
		Vendor vendorToUpdate = Vendor.builder().firstName("FirstName1").lastName("LastName1").build();
		webTestClient.patch()
	        .uri("/api/v1/vendors/someId")
	        .body(Mono.just(vendorToUpdate), Vendor.class)
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isOk();
		verify(vendorRepository,never()).save(any(Vendor.class));
	}

}
