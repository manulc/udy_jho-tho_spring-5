package com.mlorenzo.sfgpetclinic.model;

import javax.persistence.Entity;
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
@Entity
@Table(name = "types")
public class PetType extends BaseEntity{
	private static final long serialVersionUID = -3916053990363115879L;
	
	private String name;
	
	// Usamos la anotación @Builder a nivel de constructor en lugar de a nivel de clase porque también queremos usar el patrón Builder para la propiedad "id" que nos llega desde la clase que heredamos "BaseEntity"
	// Si ponemos la anotación @Builder a nivel de clase, solo podemos usar el patrón Builder para la propiedad "name" de esta clase
	@Builder
	public PetType(Long id, String name) {
		super(id);
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
