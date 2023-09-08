package com.mlorenzo.spring5mvcrest.api.v1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mlorenzo.spring5mvcrest.api.v1.model.CategoryDTO;
import com.mlorenzo.spring5mvcrest.api.v1.model.CategoryListDTO;
import com.mlorenzo.spring5mvcrest.services.CategoryService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;

@AllArgsConstructor
@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
	public static final String BASE_URL = "/api/v1/categories";
	
	private final  CategoryService categoryService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK) // Si no se indica esta anotación, por defecto el estado de la respuesta es OK(200)
	public CategoryListDTO getAllCategories(){
		return new CategoryListDTO(categoryService.getAllCategories());
		
	}
	
	@GetMapping("/{categoryName}")
	public CategoryDTO getCategoriesByName(@PathVariable(value = "categoryName") String name){
		return categoryService.getCategoryByName(name);
	}

}
