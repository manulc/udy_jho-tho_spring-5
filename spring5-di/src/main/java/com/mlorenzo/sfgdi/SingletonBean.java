package com.mlorenzo.sfgdi;

import org.springframework.stereotype.Component;

// Si no se indica otro scope con la anotación @Scope, por defecto es Singleton
@Component
public class SingletonBean {
	
	public SingletonBean() {
		System.out.println("Creating a Singleton bean!!!");
	}
	
	public String getMyScope() {
		return "I'm a Singleton";
	}

}
