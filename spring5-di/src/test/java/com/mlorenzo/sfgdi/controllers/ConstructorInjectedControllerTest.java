package com.mlorenzo.sfgdi.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mlorenzo.sfgdi.services.ConstructorInjectedGreetingService;

public class ConstructorInjectedControllerTest {
	ConstructorInjectedController controller;

	@BeforeEach
	void setUp() {
		controller = new ConstructorInjectedController(new ConstructorInjectedGreetingService());
	}
	
	@Test
	void getGreeting() {
		System.out.println(controller.getGreeting());
	}
}
