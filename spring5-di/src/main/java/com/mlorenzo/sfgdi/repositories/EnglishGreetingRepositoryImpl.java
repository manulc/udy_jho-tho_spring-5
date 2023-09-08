package com.mlorenzo.sfgdi.repositories;


// Se comenta esta anotación porque ahora creamos un bean de Spring de esta clase usando una clase de configuración llamada "GreetingServiceConfig"
//@Repository
public class EnglishGreetingRepositoryImpl implements EnglishGreetingRepository {

	@Override
	public String getGreeting() {
		return "Hello World - EN";
	}

}
