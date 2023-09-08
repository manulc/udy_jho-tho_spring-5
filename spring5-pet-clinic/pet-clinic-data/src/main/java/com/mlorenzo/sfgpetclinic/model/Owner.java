package com.mlorenzo.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {
	private static final long serialVersionUID = 6058308647075194395L;
	
	private String address;
	private String city;
	private String telephone;
	
	// Relación bidireccional con la clase entidad "Pet"
	// Esta parte de la relación es uno a muchos
	// Con el atributo "cascade" con el valor "CascadeType.ALL" propagamos todas las operaciones(INSERT,DELETE,UPDATE,etc..) que se hagan sobre esta entidad a la entidad "Pet" de manera automática
	// Con el atributo "mappedBy" con el valor "owner" establecemos la relación bidireccional con la clase entidad "Pet" a través de su propiedad "owner"
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<Pet> pets = new HashSet<Pet>();

	// Usamos la anotación @Builder a nivel de constructor en lugar de a nivel de clase porque también queremos usar el patrón Builder para las propiedades "id", "firstName" y "lastName" que nos llegan desde la clase que heredamos "Person"
	// Si ponemos la anotación @Builder a nivel de clase, solo podemos usar el patrón Builder para las propiedades "address", "city", "telephone" y "pets" de esta clase
	@Builder
	public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
		super(id,firstName,lastName);
		this.address = address;
		this.city = city;
		this.telephone = telephone;
		if(pets != null)
			this.pets = pets;
	}
	
	/**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name) {
        return getPet(name, false);
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : pets) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }
}
