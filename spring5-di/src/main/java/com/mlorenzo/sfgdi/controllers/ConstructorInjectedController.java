package com.mlorenzo.sfgdi.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.mlorenzo.sfgdi.services.GreetingService;

// Forma más recomendada de las 3

@Controller
public class ConstructorInjectedController {
	private final GreetingService greetingService;

	// Spring realiza de forma automática la inyección de dependencias cuando se trata de construtores
	// Por lo tanto, usar aquí la anotación @Autowired es opcional
	// Como hay varias implementaciones de la interfaz "GreetingService", tenemos que indicar con @Qualifier la implementación que queremos inyectar
	public ConstructorInjectedController(@Qualifier("constructorInjectedGreetingService") GreetingService greetingService) {
		this.greetingService = greetingService;
	}
	
	public String getGreeting() {
		return greetingService.sayGreeting();
	}
}
