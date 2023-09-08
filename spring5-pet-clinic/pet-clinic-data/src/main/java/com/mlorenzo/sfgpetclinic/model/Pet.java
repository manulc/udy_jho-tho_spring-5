package com.mlorenzo.sfgpetclinic.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity{
	private static final long serialVersionUID = 9052231982640691480L;
	
	private String name;
	
	// Relación unidireccional muchos a uno de esta clase entidad con la clase entidad "PetType"
	// Esta parte de la relación es muchos a uno
	@ManyToOne
	@JoinColumn(name = "type_id") // Personalizamos el nombre de la columna de la foreign key o clave extranjera que llega a esta clase entidad desde la clase entidad "PetType"
	private PetType petType;
	
	// Relación bidireccional entre esta clase entidad y la clase entidad "Owner"
	// Esta parte de la relación es muchos a uno
	@ManyToOne
	@JoinColumn(name = "owner_id") // Personalizamos el nombre de la columna de la foreign key o clave extranjera que llega a esta clase entidad desde la clase entidad "Owner"
	private Owner owner;
	
	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd") // Con esta anotación establecemos que las fechas que se introduzcan desde los formularios para esta propiedad tienen que tener el formato "yyyy-MM-dd"
	private LocalDate birthDate;
	
	// Relación bidireccional con la clase entidad "Visit"
	// Esta parte de la relación es uno a muchos
	// Con el atributo "cascade" con el valor "CascadeType.ALL" propagamos todas las operaciones(INSERT,DELETE,UPDATE,etc..) que se hagan sobre esta entidad a la entidad "Visit" de manera automática
	// Con el atributo "mappedBy" con el valor "owner" establecemos la relación bidireccional con la clase entidad "Visit" a través de su propiedad "pet"
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
	private Set<Visit> visits = new HashSet<Visit>();
	
	// Usamos la anotación @Builder a nivel de constructor en lugar de a nivel de clase porque también queremos usar el patrón Builder para la propiedad "id" que nos llega desde la clase que heredamos "BaseEntity"
	// Si ponemos la anotación @Builder a nivel de clase, solo podemos usar el patrón Builder para las propiedades "name", "petType", "owner", "birthDate" y "visits" de esta clase
	@Builder
    public Pet(Long id, String name, PetType petType, Owner owner, LocalDate birthDate, Set<Visit> visits) {
        super(id);
        this.name = name;
        this.petType = petType;
        this.owner = owner;
        this.birthDate = birthDate;
        if (visits == null || visits.size() > 0) {
            this.visits = visits;
        }
    }
}
