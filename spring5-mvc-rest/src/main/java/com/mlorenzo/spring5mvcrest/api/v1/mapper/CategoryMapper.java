package com.mlorenzo.spring5mvcrest.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.mlorenzo.spring5mvcrest.api.v1.model.CategoryDTO;
import com.mlorenzo.spring5mvcrest.domain.Category;

@Mapper
public interface CategoryMapper {
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	CategoryDTO categoryToCategoryDTO(Category category);
}
