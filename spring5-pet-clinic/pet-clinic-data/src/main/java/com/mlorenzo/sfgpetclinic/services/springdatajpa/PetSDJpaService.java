package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.Pet;
import com.mlorenzo.sfgpetclinic.repositories.PetRepository;
import com.mlorenzo.sfgpetclinic.services.PetService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Profile("springdatajpa") // Debido a que tenemos 2 implementaciones de la interfaz "PetService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "springdatajpa"
public class PetSDJpaService implements PetService {
	private final PetRepository petRepository;

	@Override
	public Set<Pet> findAll() {
		Set<Pet> setPets= new HashSet<Pet>();
		petRepository.findAll().forEach(setPets::add); // Versión simplifivada de la expresión "pet -> setPets.add(pet)"
		
		return setPets;
	}

	@Override
	public Pet findById(Long id) {
		return petRepository.findById(id).orElse(null);
	}

	@Override
	public Pet save(Pet object) {
		return petRepository.save(object);
	}

	@Override
	public void delete(Pet object) {
		petRepository.delete(object);
		
	}

	@Override
	public void deleteById(Long id) {
		petRepository.deleteById(id);
		
	}
}
