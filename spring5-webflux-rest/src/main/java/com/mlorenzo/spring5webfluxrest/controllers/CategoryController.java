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

import com.mlorenzo.spring5webfluxrest.domain.Category;
import com.mlorenzo.spring5webfluxrest.domain.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class CategoryController {
	private final CategoryRepository categoryRepository;

	@GetMapping("/api/v1/categories")
	public Flux<Category> listCategories(){
		return categoryRepository.findAll();
	}
	
	@GetMapping("/api/v1/categories/{idCategory}")
	public Mono<Category> getCategoryById(@PathVariable("idCategory") String id){
		return categoryRepository.findById(id);
	}
	
	// Pasándole como parámetro de entrada un Publisher de tipo Category, como Mono y Flux son Publishers, damos la posibilidad de poder persistir en la base de datos un Mono con una categoría o un Flux con varias de ellas al mismo tiempo
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/categories")
	public Mono<Void> createCategories(@RequestBody Publisher<Category> categoryStream){
		// El método "then()" retorna un Mono vacío cuando el flujo reactivo creado por el método "saveAll" termina de emitir su último elemento
		return categoryRepository.saveAll(categoryStream).then();
	}
	
	@PutMapping("/api/v1/categories/{id}")
	public Mono<Category> updateCategory(@PathVariable(value = "id") String idCategory,@RequestBody Category category){
		category.setId(idCategory);
		return categoryRepository.save(category);
	}
	
	@PatchMapping("/api/v1/categories/{id}")
	public Mono<Category> patchCategory(@PathVariable(value = "id") String idCategory,@RequestBody Category category){
		return categoryRepository.findById(idCategory)
				.flatMap(foundCategory -> {
					if((category.getDescription() != null) && (!foundCategory.getDescription().equals(category.getDescription()))) {
						foundCategory.setDescription(category.getDescription());
						return categoryRepository.save(foundCategory);
					}
					
					return Mono.just(foundCategory);
				});
	}
}
