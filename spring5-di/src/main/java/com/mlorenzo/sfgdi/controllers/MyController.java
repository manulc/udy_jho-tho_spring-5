package com.mlorenzo.sfgdi.controllers;

import org.springframework.stereotype.Controller;

import com.mlorenzo.sfgdi.services.GreetingService;

@Controller
public class MyController {
	
	// En este caso, como no estamos indicando ninguna implementación en concreto de la interfaz "GreetingService",
	// Spring inyectará la implementación por defecto que es aquella anotada con la anotación @Primary
	private final GreetingService greetingService;
	
	public MyController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String getGreeting() {
		return greetingService.sayGreeting();
	}
}
