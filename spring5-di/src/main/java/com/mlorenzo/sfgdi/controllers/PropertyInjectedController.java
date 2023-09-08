package com.mlorenzo.sfgdi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.mlorenzo.sfgdi.services.GreetingService;

// Forma menos recomendada de las 3

@Controller
public class PropertyInjectedController {
	
	// Como hay varias implementaciones de la interfaz "GreetingService", tenemos que indicar con @Qualifier la implementación que queremos inyectar
	@Qualifier("propertyInjectedGreetingService")
	@Autowired
	public GreetingService greetingService;
	
	public String getGreeting() {
		return greetingService.sayGreeting();
	}

}
