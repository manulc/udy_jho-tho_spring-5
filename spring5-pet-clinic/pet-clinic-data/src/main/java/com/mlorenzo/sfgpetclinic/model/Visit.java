package com.mlorenzo.sfgpetclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity{
	private static final long serialVersionUID = -875930165598690383L;
	
	// Relación bidireccional entre esta clase entidad y la clase entidad "Pet"
	// Esta parte de la relación es muchos a uno
	@ManyToOne
	@JoinColumn(name = "pet_id") // Personalizamos el nombre de la columna de la foreign key o clave extranjera que llega a esta clase entidad desde la clase entidad "Pet"
	private Pet pet;
	
	private String description;
	private LocalDate date;
	
}
