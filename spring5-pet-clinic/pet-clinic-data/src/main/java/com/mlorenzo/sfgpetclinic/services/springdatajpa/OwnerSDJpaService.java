package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.Owner;
import com.mlorenzo.sfgpetclinic.repositories.OwnerRepository;
import com.mlorenzo.sfgpetclinic.services.OwnerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Profile("springdatajpa") // Debido a que tenemos 2 implementaciones de la interfaz "OwnerService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "springdatajpa"
public class OwnerSDJpaService implements OwnerService {
	private final OwnerRepository ownerRepository;

	@Override
	public Set<Owner> findAll() {
		Set<Owner> setOwners= new HashSet<Owner>();
		ownerRepository.findAll().forEach(setOwners::add); // Versión simplifivada de la expresión "owner -> setOwners.add(owner)"
		return setOwners;
	}

	@Override
	public Owner findById(Long id) {
		return ownerRepository.findById(id).orElse(null);
	}

	@Override
	public Owner save(Owner object) {
		return ownerRepository.save(object);
	}

	@Override
	public void delete(Owner object) {
		ownerRepository.delete(object);
		
	}

	@Override
	public void deleteById(Long id) {
		ownerRepository.deleteById(id);
		
	}

	@Override
	public Owner findByLastName(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	public List<Owner> findAllByLastNameLike(String lastName) {
		return ownerRepository.findAllByLastNameLike(lastName);
	}

}
