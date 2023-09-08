package com.mlorenzo.spring5jokesapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mlorenzo.spring5jokesapp.services.JokeService;

@Controller
public class JokeController {
	private final JokeService jokeService;

	public JokeController(JokeService jokeService) {
		this.jokeService = jokeService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showJoke(Model model) {
		model.addAttribute("joke", jokeService.getJoke());
		return "index";
	}
}
