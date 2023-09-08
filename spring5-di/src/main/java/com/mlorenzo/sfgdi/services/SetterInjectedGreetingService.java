package com.mlorenzo.sfgdi.services;


// Se comenta esta anotación porque ahora creamos un bean de Spring de esta clase usando una clase de configuración llamada "GreetingServiceConfig"
//@Service
public class SetterInjectedGreetingService implements GreetingService {

	@Override
	public String sayGreeting() {
		return "Hello World - Setter";
	}

}
