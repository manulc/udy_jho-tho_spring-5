package com.mlorenzo.sfgdi.services;


// Se comentan estas anotaciones porque ahora creamos un bean de Spring de esta clase usando una clase de configuración llamada "GreetingServiceConfig"
//@Profile({"ES", "default"}) // Con esta anotación, se creará el bean de Spring para este servicio cuando el perfil activo sea "ES" o "default"(perfil por defecto cuando no hay ninguno activo)
//@Service("i18nService")
public class I18nSpanishGreetingService implements GreetingService {

	@Override
	public String sayGreeting() {
		return "Hola Mundo - ES";
	}

}
