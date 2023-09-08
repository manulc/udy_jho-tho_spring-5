package com.mlorenzo.sfgdi.services;


// Como tenemos varias implementaciones de la interfaz "GreetingService", con esta anotación @Primary hacemos que la implementación
// de esta clase sea la implementación por defecto a la hora de inyectarse en caso de que no se indique ninguna otra implementación
// Se comentan estas anotaciones porque ahora creamos un bean de Spring de esta clase usando una clase de configuración llamada "GreetingServiceConfig"
//@Primary
//@Service
public class PrimaryGreetService implements GreetingService {
	
	@Override
	public String sayGreeting() {
		return "Hello World - From the PRIMARY bean";
	}

}
