package com.mlorenzo.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vets")
public class Vet extends Person {
	private static final long serialVersionUID = 5719734850419870704L;
	
	// Relación unidireccional muchoa a muchos de esta clase entidad con la clase entidad "Speciality" 
	// Por defecto, el tipo de búsqueda de una relación es "LAZY", o perezosa
	// El tipo de búsqueda "EAGER", o asiosa, hace que cuando se recupere una entidad de esta clase de la base de datos, también se recupere de la base de datos al mismo tiempo las entidades relacionadas de esa entidad pertenecientes a la clase entidad "Speciality"
	// El tipo de búsqueda "LAZY", o perezosa, es justo lo contrario, es decir, hace que cuando se recupere una entidad de esta clase de la base de datos, no se recupere de la base de datos al mismo tiempo las entidades relacionadas de esa entidad pertenecientes a la clase entidad "Speciality". Entonces, las entidades relacionadas se recuperarán de la base de datos posteriormente cuando se soliciten
	@ManyToMany(fetch = FetchType.EAGER)
	// Personalizamos la tabla adicional de la relación entre estas 2 entidades
	@JoinTable(name = "vet_specialities",joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "speciality_id"))
	private Set<Speciality> specialities = new HashSet<Speciality>();
}
