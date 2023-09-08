package com.mlorenzo.sfgdi.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.mlorenzo.sfgdi.services.GreetingService;

@Controller
public class I18nController {
	private final GreetingService greetingService;

	// Spring realiza de forma automática la inyección de dependencias cuando se trata de construtores
	// Por lo tanto, usar aquí la anotación @Autowired es opcional
	// Como hay varias implementaciones de la interfaz "GreetingService", tenemos que indicar con @Qualifier la implementación que queremos inyectar
	public I18nController(@Qualifier("i18nService") GreetingService greetingService) {
		this.greetingService = greetingService;
	}
	
	public String getGreeting() {
		return greetingService.sayGreeting();
	}

}
