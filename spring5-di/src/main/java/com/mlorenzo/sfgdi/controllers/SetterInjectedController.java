package com.mlorenzo.sfgdi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.mlorenzo.sfgdi.services.GreetingService;

@Controller
public class SetterInjectedController {
	private GreetingService greetingService;
	
	// Como hay varias implementaciones de la interfaz "GreetingService", tenemos que indicar con @Qualifier la implementación que queremos inyectar
	@Qualifier("setterInjectedGreetingService")
	@Autowired
	public void setGreetingService(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String getGreeting() {
		return greetingService.sayGreeting();
	}

}
