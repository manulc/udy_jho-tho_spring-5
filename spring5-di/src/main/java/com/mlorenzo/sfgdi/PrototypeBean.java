package com.mlorenzo.sfgdi;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// Si no se indica otro scope con la anotación @Scope, por defecto es Singleton
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class PrototypeBean {
	
	public PrototypeBean() {
		System.out.println("Creating a Prototype bean!!!");
	}
	
	public String getMyScope() {
		return "I'm a Prototype";
	}
}
