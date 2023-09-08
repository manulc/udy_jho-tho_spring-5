package com.mlorenzo.spring5webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mlorenzo.spring5webapp.repositories.AuthorRepository;

@Controller
public class AuthorController {
	private final AuthorRepository authorRepository;

	public AuthorController(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}
	
	@RequestMapping(path = "/authors", method = RequestMethod.GET)
	public String getAuthors(Model model) {
		model.addAttribute("authors", authorRepository.findAll());
		return "authors/list";
	}
}
