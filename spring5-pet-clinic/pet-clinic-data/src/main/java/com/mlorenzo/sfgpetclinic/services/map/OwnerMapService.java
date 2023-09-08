package com.mlorenzo.sfgpetclinic.services.map;

import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.Owner;
import com.mlorenzo.sfgpetclinic.model.Pet;
import com.mlorenzo.sfgpetclinic.services.OwnerService;
import com.mlorenzo.sfgpetclinic.services.PetService;
import com.mlorenzo.sfgpetclinic.services.PetTypeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Profile({"default","map"}) // Debido a que tenemos 2 implementaciones de la interfaz "OwnerService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "default" o "map"
public class OwnerMapService extends AbstractMapService<Owner> implements OwnerService{
	private final PetTypeService petTypeService;
	private final PetService petService;
	
	@Override
	public Set<Owner> findAll(){
		return super.findAll();
	}
	
	@Override
	public Owner findById(Long id) {
		return super.findById(id);
	}
	
	@Override
	public Owner save(Owner object) {
		if(object != null) {
			if(object.getPets() != null) {
				object.getPets().forEach(pet -> {
					if(pet.getPetType() != null) {
						if(pet.getPetType().getId() == null)
							pet.setPetType(petTypeService.save(pet.getPetType()));
					}
					else
						throw new RuntimeException("Pet Type is required");
					
					if(pet.getId() == null) {
						Pet savedPet = petService.save(pet);
						pet.setId(savedPet.getId());
					}
				});
			}
			return super.save(object);
		}
		else
			return null;
	}
	
	@Override
	public void delete(Owner object) {
		super.delete(object);
	}
	
	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}
	
	@Override
	public Owner findByLastName(String lastName) {
		return this.findAll()
					.stream()
					.filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
					.findFirst()
					.orElse(null);
	}

	@Override
	public List<Owner> findAllByLastNameLike(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}
}
