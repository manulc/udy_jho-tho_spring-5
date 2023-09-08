package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.PetType;
import com.mlorenzo.sfgpetclinic.repositories.PetTypeRepository;
import com.mlorenzo.sfgpetclinic.services.PetTypeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Profile("springdatajpa") // Debido a que tenemos 2 implementaciones de la interfaz "PetTypeService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "springdatajpa"
public class PetTypeSDJpaService implements PetTypeService {
	private final PetTypeRepository petTypeRepository;

	@Override
	public Set<PetType> findAll() {
		Set<PetType> setPetTypes = new HashSet<PetType>();
		petTypeRepository.findAll().forEach(setPetTypes::add); // Versión simplifivada de la expresión "petType -> setPetTypes.add(petType)"
		return setPetTypes;
	}

	@Override
	public PetType findById(Long id) {
		return petTypeRepository.findById(id).orElse(null);
	}

	@Override
	public PetType save(PetType object) {
		return petTypeRepository.save(object);
	}

	@Override
	public void delete(PetType object) {
		petTypeRepository.delete(object);
	}

	@Override
	public void deleteById(Long id) {
		petTypeRepository.deleteById(id);
	}

}
