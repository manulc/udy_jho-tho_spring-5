package com.mlorenzo.spring5mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlorenzo.spring5mvcrest.domain.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
}
