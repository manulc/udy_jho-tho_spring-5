package com.mlorenzo.sfgdi.services;

import com.mlorenzo.sfgdi.repositories.EnglishGreetingRepository;

// Se comentan estas anotaciones porque ahora creamos un bean de Spring de esta clase usando una clase de configuración llamada "GreetingServiceConfig"
//@Profile("EN") // Con esta anotación, se creará el bean de Spring para este servicio cuando el perfil activo sea "EN"
//@Service("i18nService")
public class I18nEnglishGreetingService implements GreetingService {
	private final EnglishGreetingRepository repository;
	
	public I18nEnglishGreetingService(EnglishGreetingRepository repository) {
		this.repository = repository;
	}

	@Override
	public String sayGreeting() {
		return repository.getGreeting();
	}

}
