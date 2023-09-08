package com.mlorenzo.springrestclientexamples.models;

import lombok.Getter;
import lombok.Setter;

// Nota: Clase generada a partir de la web www.jsonschema2pojo.org usando un Json obtenido de la REST API JSONPlaceholder mediante la url https://jsonplaceholder.typicode.com/users?_limit=3

@Getter
@Setter
public class Address {
	private String street;
	private String suite;
	private String city;
	private String zipcode;
	private Geo geo;
}
