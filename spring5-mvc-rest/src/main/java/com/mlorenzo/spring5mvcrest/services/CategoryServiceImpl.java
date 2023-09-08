package com.mlorenzo.spring5mvcrest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mlorenzo.spring5mvcrest.api.v1.mapper.CategoryMapper;
import com.mlorenzo.spring5mvcrest.api.v1.model.CategoryDTO;
import com.mlorenzo.spring5mvcrest.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository.findAll().stream()
				.map(categoryMapper::categoryToCategoryDTO) // Versión simplificada de la expresión "category -> categoryMapper.categoryToCategoryDTO(category)"
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {
		return categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(name));
	}
}
