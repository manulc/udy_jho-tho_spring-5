package com.mlorenzo.spring5webfluxrest.bootstrap;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mlorenzo.spring5webfluxrest.domain.Category;
import com.mlorenzo.spring5webfluxrest.domain.Vendor;
import com.mlorenzo.spring5webfluxrest.domain.repositories.CategoryRepository;
import com.mlorenzo.spring5webfluxrest.domain.repositories.VendorRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner{
	private final CategoryRepository categoryRepository;
	private final VendorRepository vendorRepository;

	@Override
	public void run(String... args) throws Exception {
		Mono.zip(categoryRepository.count(), vendorRepository.count())
			.flatMapMany(tuple -> {
				if(tuple.getT1() == 0 && tuple.getT2() == 0) {
					System.out.println("###### LOADING DATA ON BOOTSTRAP ######");
					return Flux.merge(loadCategories(), loadVendors());
				}
				return Flux.empty();
			})
			.then(Mono.zip(categoryRepository.count(), vendorRepository.count()).log())
			.subscribe(tuple -> {
				System.out.println("Loaded Categories: " + tuple.getT1());
				System.out.println("Loaded Vendors: " + tuple.getT2());
			});
			
	}
	
	private Flux<Category> loadCategories() {
		Category cat1 = Category.builder().description("Fruits").build();
		Category cat2 = Category.builder().description("Nuts").build();
		Category cat3 = Category.builder().description("Breads").build();
		Category cat4 = Category.builder().description("Meats").build();
		Category cat5 = Category.builder().description("Eggs").build();
		return categoryRepository.saveAll(List.of(cat1, cat2, cat3, cat4, cat5));
	}
	
	private Flux<Vendor> loadVendors() {
		Vendor ven1 = Vendor.builder().firstName("Joe").lastName("Buck").build();
		Vendor ven2 = Vendor.builder().firstName("Micheal").lastName("Weston").build();
		Vendor ven3 = Vendor.builder().firstName("Jessie").lastName("Waters").build();
		Vendor ven4 = Vendor.builder().firstName("Bill").lastName("Nershi").build();
		Vendor ven5 = Vendor.builder().firstName("Jimmy").lastName("Buffett").build();
		return vendorRepository.saveAll(Arrays.asList(ven1, ven2, ven3, ven4, ven5));
	}

}
