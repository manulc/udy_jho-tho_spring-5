package com.mlorenzo.spring5webfluxrest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mlorenzo.spring5webfluxrest.domain.Vendor;
import com.mlorenzo.spring5webfluxrest.domain.repositories.VendorRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class VendorController {
	private final VendorRepository vendorRepository;

	@GetMapping("/api/v1/vendors")
	public Flux<Vendor> listVendors(){
		return vendorRepository.findAll();
	}
	
	@GetMapping("/api/v1/vendors/{id}")
	public Mono<Vendor> getCategoryById(@PathVariable String id){
		return vendorRepository.findById(id);
	}
	
	// Pasándole como parámetro de entrada un Publisher de tipo Vendor, como Mono y Flux son Publishers, damos la posibilidad de poder persistir en la base de datos un Mono con un vendor o un Flux con varios de ellos al mismo tiempo
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/vendors")
	public Mono<Void> createVendors(@RequestBody Publisher<Vendor> vendorStream){
		// El método "then()" retorna un Mono vacío cuando el flujo reactivo creado por el método "saveAll" termina de emitir su último elemento
		return vendorRepository.saveAll(vendorStream).then();
	}
	
	@PutMapping("/api/v1/vendors/{idVendor}")
	public Mono<Vendor> updateVendor(@PathVariable String idVendor,@RequestBody Vendor vendor){
		vendor.setId(idVendor);
		return vendorRepository.save(vendor);
	}
	
	@PatchMapping("/api/v1/vendors/{idVendor}")
	public Mono<Vendor> patchVendor(@PathVariable String idVendor,@RequestBody Vendor vendor){
		System.out.println(vendor.getFirstName() + " - " + vendor.getLastName());
		return vendorRepository.findById(idVendor)
				.flatMap(foundVendor -> {
					boolean changed = false;
					if((vendor.getFirstName() != null) && (!foundVendor.getFirstName().equals(vendor.getFirstName()))) {
						foundVendor.setFirstName(vendor.getFirstName());
						changed = true;
					}
					if((vendor.getLastName() != null) && (!foundVendor.getLastName().equals(vendor.getLastName()))) { 
						foundVendor.setLastName(vendor.getLastName());
						changed = true;
					}
					if(changed)
						return vendorRepository.save(foundVendor);
					return Mono.just(foundVendor);
				});
	}

}
