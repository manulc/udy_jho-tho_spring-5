package com.mlorenzo.sfgpetclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
//Anotación de JPA que indica a JPA que esta clase es una clase "padre" que va a ser heredada por clases de tipo Entity o Entidad, y que contiene anotaciones JPA, pero que no debe mapearse con ninguna tabla de la base de datos
@MappedSuperclass
public class Person extends BaseEntity{
	private static final long serialVersionUID = 6996174504746063233L;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	public Person(Long id, String firstName, String lastName) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
